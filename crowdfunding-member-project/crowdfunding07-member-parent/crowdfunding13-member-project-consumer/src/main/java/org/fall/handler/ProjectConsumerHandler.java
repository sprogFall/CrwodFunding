package org.fall.handler;

import org.fall.api.MySQLRemoteService;
import org.fall.config.OSSProperties;
import org.fall.constant.CrowdConstant;
import org.fall.entity.vo.*;
import org.fall.util.CrowdUtil;
import org.fall.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectConsumerHandler {


    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;


    /**
     * 进行创建项目操作，成功后进入回报页面
     * @param projectVO  前端表单的数据自动装入ProjectVO对象
     * @param headerPicture 前端上传的头图
     * @param detailPictureList 前端上传的详情图片的list
     * @param session 用于存放信息
     * @param modelMap 用于发生错误时传递信息
     * @return 进入下一步的页面
     * @throws IOException
     */
    @RequestMapping("/create/project/information")
    public String createProject(
            ProjectVO projectVO,
            MultipartFile headerPicture,
            List<MultipartFile> detailPictureList,
            HttpSession session,
            ModelMap modelMap ) throws IOException {

        // 一、完成头图的上传
        // 判断headerPicture对象是否为空
        boolean headerPictureEmpty = headerPicture.isEmpty();

        if (headerPictureEmpty){
            // 头图为空，存入提示信息，且返回原本的页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";
        }
        // 头图不为空 进行上传操作
        ResultEntity<String> headerPictureResultEntity = CrowdUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());
        // 判断是否上传成功
        String result = headerPictureResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)){
            // 上传失败
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        } else {
            // 上传成功
            // 得到存入OSS服务器的文件名
            String headerPicturePath = headerPictureResultEntity.getData();

            // 存入ProjectVO对象
            projectVO.setHeaderPicturePath(headerPicturePath);
        }

        // 二、完成详情图片的上传

        // 创建用于存放详情图片的路径的List对象
        List<String> detailPicturePathList = new ArrayList<>();

        // 判断详情图片是否为空
        if (detailPictureList == null || detailPictureList.size() == 0){
            // 详情图片为空，加入提示信息，返回原本页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }
        // 详情图片不为空 遍历List
        for (MultipartFile detailPicture : detailPictureList) {
            // 判断当前MultipartFile是否有效
            if (detailPicture.isEmpty()){
                // 当前图片为空，也返回原本的页面
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }
            // 不为空，开始存放详情图片
            ResultEntity<String> detailPictureResultEntity = CrowdUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename());
            // 检查上传的结果
            String detailPictureResult = detailPictureResultEntity.getResult();
            if (ResultEntity.FAILED.equals(detailPictureResult)){
                // 上传失败
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
            // 上传成功
            // 将当前上传后的路径放入list
            detailPicturePathList.add(detailPictureResultEntity.getData());
        }

        // 将detailPicturePathList存入ProjectVO对象
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 后续操作
        // 将ProjectVO对象放入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

        // 进入下一个收集回报信息的页面
        return "redirect:http://localhost/project/return/info/page.html";
    }





    // 回报页面上传图片时触发的ajax请求对应的handler方法
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        // 判断是否是有效上传
        boolean pictureIsEmpty = returnPicture.isEmpty();
        if (pictureIsEmpty){
            // 如果上传文件为空
            ResultEntity.failed(CrowdConstant.MESSAGE_RETURN_PIC_EMPTY);
        }

        // 进行上传到OSS服务器的操作
        ResultEntity<String> returnPictureResultEntity = CrowdUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());
        return returnPictureResultEntity;
    }





    // 回报页面保存回报信息的ajax请求对应的方法
    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
        try {
            // 从session域取出ProjectVO对象
            ProjectVO projectVO = (ProjectVO)session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            // 判断ProjectVO是否回null
            if (projectVO == null){
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            // ProjectVO不为null
            // 取出projectVO中的returnVOList
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            // 判断取出的list是否为空或长度为0
            if (returnVOList == null || returnVOList.size() == 0){
                // 初始化returnVOList
                returnVOList = new ArrayList<>();
                // 存入projectVO
                projectVO.setReturnVOList(returnVOList);
            }
            // 向returnVOList中存放当前接收的returnVO
            returnVOList.add(returnVO);

            // 重新将ProjectVO存入session域
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);

            // 全部操作正常完成，返回成功的ResultEntity
            return ResultEntity.successWithoutData();

        } catch (Exception e){
            e.printStackTrace();
            // 出现异常返回failed，带上异常信息
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("/create/confirm.html")
    public String saveConfirm(MemberConfirmInfoVO memberConfirmInfoVO,HttpSession session, ModelMap modelMap){
        // 从session域取出ProjectVO对象
        ProjectVO projectVO = (ProjectVO)session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        // 判断ProjectVO是否回null
        if (projectVO == null){
            // 这里不多做处理了，就直接抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }

        // ProjectVO正常，开始向其中存放MemberConfirmInfo
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        // 从session域中读取当前登录的用户
        LoginMemberVO loginMember = (LoginMemberVO)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = loginMember.getId();

        // 调用远程方法保存ProjectVO对象和当前登录的用户的id
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectRemote(projectVO, memberId);

        String result = saveResultEntity.getResult();

        if (ResultEntity.FAILED.equals(result)){
            // 保存出错，返回确认的界面，并且携带错误的消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
            return "project-confirm";
        }

        // 保存正常完成，删除session中临时存放的ProjectVO
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        // 进入成功页面
        return "redirect:http://localhost/project/create/success.html";
    }


    @RequestMapping("/show/project/detail/{projectId}")
    public String getDetailProject(@PathVariable("projectId") Integer projectId,ModelMap modelMap){
        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            DetailProjectVO detailProjectVO = resultEntity.getData();
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_DETAIL_PROJECT,detailProjectVO);
        }
        return "project-show-detail";
    }



}
