package org.fall.handler;

import org.fall.api.MySQLRemoteService;
import org.fall.api.RedisRemoteService;
import org.fall.config.ShortMessageProperties;
import org.fall.constant.CrowdConstant;
import org.fall.entity.po.MemberPO;
import org.fall.entity.vo.LoginMemberVO;
import org.fall.entity.vo.MemberVO;
import org.fall.util.CrowdUtil;
import org.fall.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    // 自动注入对象，对象的属性从application.yml文件中获取
    @Autowired
    private ShortMessageProperties shortMessageProperties;

    // 发送验证码
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendShortMessage(@RequestParam("phoneNum") String phoneNum){

        // 调用工具类中的发送验证码的方法，可以从配置文件中读取配置的接口信息
        ResultEntity<String> sendResultEntity = CrowdUtil.sendCodeByShortMessage(
                shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getAppCode(),
                phoneNum,
                shortMessageProperties.getSign(),
                shortMessageProperties.getSkin());

        // 判断-发送成功
        if (ResultEntity.SUCCESS.equals(sendResultEntity.getResult())){

            // 得到ResultEntity中的验证码
            String code = sendResultEntity.getData();

            // 将验证码存入到redis中（设置TTL，这里设置为5分钟）
            ResultEntity<String> redisResultEntity = redisRemoteService.setRedisKeyValueWithTimeoutRemote(
                    CrowdConstant.REDIS_CODE_PREFIX + phoneNum, code, 5, TimeUnit.MINUTES);

            // 判断存入redis是否成功
            if (ResultEntity.SUCCESS.equals(redisResultEntity.getResult())){
                // 存入成功，返回成功
                return ResultEntity.successWithoutData();
            } else {
                // 存入失败，返回redis返回的ResultEntity
                return redisResultEntity;
            }
        } else {
            // 发送验证码失败，返回发送验证码的ResultEntity
            return sendResultEntity;
        }
    }

    // 进行用户注册操作
    @RequestMapping("/auth/member/do/register.html")
    public String doMemberRegister(MemberVO memberVO, ModelMap modelMap){
        // 获取手机号
        String phoneNum = memberVO.getPhoneNum();

        // 拼接为redis存放的key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

        // 通过key寻找value（验证码）
        ResultEntity<String> resultEntity = redisRemoteService.getRedisValueByKeyRemote(key);

        String result = resultEntity.getResult();

        // 判断获取redis中的验证码是否成功
        if (ResultEntity.FAILED.equals(result)){
            // 失败，返回主页页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-reg";
        }

        // 获取redis中的验证码的值
        String redisCode = resultEntity.getData();
        if (redisCode == null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXIST);
            return "member-reg";
        }

        // 获取表单提交的验证码
        String formCode = memberVO.getCode();

        // 如果redis中的验证码与表单提交的验证码不同
        if (!Objects.equals(formCode,redisCode)){
            // request域存入不匹配的message
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_INVALID);
            // 返回注册页面
            return "member-reg";
        }

        // 验证码比对一致，删除redis中的验证码数据
        redisRemoteService.removeRedisKeyByKeyRemote(key);

        // 进行注册操作

        // 1、加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String formPwd = memberVO.getUserPswd();
        String encode = bCryptPasswordEncoder.encode(formPwd);

        // 2、将加密后的密码放入MemberVO对象
        memberVO.setUserPswd(encode);

        // 3、执行保存
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO,memberPO);
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveMemberRemote(memberPO);

        // 4、判断保存是否成功
        String saveResult = saveResultEntity.getResult();
        if (ResultEntity.FAILED.equals(saveResult)){
            // 保存失败，则返回保存操作的ResultEntity中的message，存入request域的message
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
            // 回到注册页面
            return "member-reg";
        }

        // 全部判断成功，跳转到登录页面
        return "redirect:http://localhost/auth/to/member/login/page.html";
    }


    // 登录操作
    @RequestMapping("/auth/do/member/login.html")
    public String doMemberLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("loginPswd") String loginPswd,
            ModelMap modelMap,
            HttpSession session) {

        // 远程方法调用，通过loinAcct，得到数据库中的对应Member
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginAcct);

        // 判断-查询操作是否成功
        if (ResultEntity.FAILED.equals(resultEntity.getResult())){
            // 查询失败，返回登陆页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-login";
        }

        // 查询操作成功，则取出MemberPO对象
        MemberPO memberPO = resultEntity.getData();

        // 判断得到的MemberPO是否为空
        if (memberPO == null){
            // 为空则返回登陆页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 返回的MemberPO非空，取出数据库中的密码（已经加密的）
        String userPswd = memberPO.getUserPswd();

        // 使用BCryptPasswordEncoder，比对表单的密码与数据库中的密码是否匹配
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(loginPswd, userPswd);

        // 判断-密码不同
        if (!matches){
            // 返回登陆页面，存入相应的提示信息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 密码匹配，则通过一个LoginMemberVO对象，存入需要在session域通信的用户信息（这样只在session域放一些相对不私秘的信息，保护用户隐私）
        LoginMemberVO loginMember = new LoginMemberVO(memberPO.getId(), memberPO.getUserName(), memberPO.getEmail());

        // 将LoginMemberVO对象存入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,loginMember);

        // 重定向到登陆成功后的主页面
        return "redirect:http://localhost/auth/to/member/center/page.html";
    }

    // 退出登录
    @RequestMapping("/auth/do/member/logout.html")
    public String doLogout(HttpSession session){
        // 清除session域数据
        session.invalidate();

        // 重定向到首页
        return "redirect:http://localhost/";
    }


}
