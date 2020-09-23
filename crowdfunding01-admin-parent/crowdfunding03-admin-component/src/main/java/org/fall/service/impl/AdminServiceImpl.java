package org.fall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import crowd.entity.Admin;
import crowd.entity.AdminExample;
import crowd.entity.Role;
import org.fall.constant.CrowdConstant;
import org.fall.exception.LoginAcctAlreadyInUseException;
import org.fall.exception.LoginAcctAlreadyInUseForUpdateException;
import org.fall.exception.LoginFailedException;
import org.fall.mapper.AdminMapper;
import org.fall.service.api.AdminService;
import org.fall.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveAdmin(Admin admin) {
        // 生成当前系统时间
        Date date = new Date();

        // 格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        // 设置管理员创建时间
        admin.setCreateTime(createTime);

        // 得到前端传入的密码，加密并放回原本的admin对象
        String source = admin.getUserPswd();

        // 替换为Spring Security的带盐值的加密方式
        String encoded = passwordEncoder.encode(source);

        admin.setUserPswd(encoded);

        // 执行插入操作
        try {
            adminMapper.insert(admin);
        } catch (Exception e){
            e.printStackTrace();
            // 这里出现异常的话一般就是DuplicateKeyException（因为插入的loginAcct已存在而触发）
            if(e instanceof DuplicateKeyException){
                // 如果确实是DuplicateKeyException，此时抛出一个自定义的异常
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
            }
        }
    }

    @Override
    public Admin queryAdmin(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByUsername(String adminLoginAcct, String adminPassword) {
        // 1、根据登陆账号查询Admin对象

        // 创建AdminExample对象
        AdminExample adminExample = new AdminExample();

        // 创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();

        // 在Criteria对象中封装查询的条件
        criteria.andLoginAcctEqualTo(adminLoginAcct);

        // 调用AdminMapper的方法来查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 2、判断Admin对象是否为null或数据库数据不正常
        if (admins == null || admins.size() == 0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size() > 1){
            // 数据库的数据存在重复
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        // 前面判断完后无异常，取出admin对象
        Admin admin = admins.get(0);

        // 3、如果Admin对象为null 则抛出异常
        if (admin == null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4、如果Admin对象不为null，则取出Admin对象的密码
        String userPswdDB = admin.getUserPswd();

        // 5、对表单提交的密码进行md5加密
        String userPswdForm = CrowdUtil.md5(adminPassword);

        // 6、对比两个密码
        // 因为两个密码都是对象，使用字符串的equals方法，如果存在空的密码则会触发空指针异常
        // 因此选用Objects.equals方法
        if (!Objects.equals(userPswdDB,userPswdForm)){
            // 密码不匹配
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 7、比对结果一致，返回admin对象
        return admin;
    }

    /**
     * @param keyword 关键字
     * @param pageNum 当前页码
     * @param pageSize 每一页显示的信息数量
     * @return 最后的pageInfo对象
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 利用PageHelper的静态方法开启分页
        PageHelper.startPage(pageNum,pageSize);

        // 调用Mapper接口的对应方法
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);

        // 为了方便页面的使用，把Admin的List封装成PageInfo（放别得到页码等数据）
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);

        // 返回得到的pageInfo对象
        return pageInfo;
    }

    // 根据id删除管理员
    @Override
    public void removeById(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        // 利用try-catch块，处理更新管理员信息时，修改后的loginAcct已经在数据库中存在
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                // 当触发该异常时，抛出另一个针对更新时loginAcct已存在的异常
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {

        // 先清除旧的对应inner_admin_role表中对应admin_id的数据
        adminMapper.clearOldRelationship(adminId);

        // 如果roleIdList非空，则将该list保存到数据库表中，且admin_id=adminId
        if (roleIdList != null && roleIdList.size() > 0){
            adminMapper.saveAdminRoleRelationship(adminId,roleIdList);
        }
        // roleIdList为空，则清空后不做操作

    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(example);
        Admin admin = admins.get(0);
        return admin;
    }
}
