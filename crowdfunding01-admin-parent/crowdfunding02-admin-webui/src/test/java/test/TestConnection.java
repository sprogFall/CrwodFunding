package test;

import crowd.entity.Admin;
import crowd.entity.Role;
import org.fall.mapper.AdminMapper;
import org.fall.mapper.RoleMapper;
import org.fall.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * RunWith与ContextConfiguration指定xml的作用与
 * ApplicationContext context = new ClassPathXmlApplicationContext("spring-persist-mybatis.xml");类似
 * 前者通过让测试在Spring容器环境下执行，使得DataSource可以被自动注入，后者通过getBean方法得到DataSource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class TestConnection {

    @Autowired
    DataSource dataSource;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AdminService adminService;

    @Autowired
    RoleMapper roleMapper;

    @Test
    public void test01() throws SQLException {
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring-persist-mybatis.xml");
        //DataSource dataSource = context.getBean(DataSource.class);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void test02(){
        Admin admin = new Admin(null,"tom", "123456", "汤姆", "tom@qq.com", null);
        adminMapper.insert(admin);
        //在实际开发中，如果在所有想查看的地方都使用System.out来打印，会给项目上线带来很多问题
        //System.out本质是一个IO操作，通常IO操作比较消耗性能。如果项目中过多的System.out，可能对性能造成影响
        //如果使用日志系统，就可以通过控制日志级别，来批量控制打印信息。
        System.out.println("successful!");
    }

    //打印自定义的日志
    @Test
    public void test03(){
        //获取Logger对象，这里传入的Class就是当前打印日志的类
        Logger logger = LoggerFactory.getLogger(TestConnection.class);
        //等级 DEBUG < INFO < WARN < ERROR
        logger.debug("I am DEBUG!!!");
        logger.debug("I am DEBUG!!!");
        logger.debug("I am DEBUG!!!");

        logger.info("I am INFO!!!");
        logger.info("I am INFO!!!");
        logger.info("I am INFO!!!");

        logger.warn("I am WARN!!!");
        logger.warn("I am WARN!!!");
        logger.warn("I am WARN!!!");

        logger.error("I am ERROR!!!");
        logger.error("I am ERROR!!!");
        logger.error("I am ERROR!!!");
    }

    //测试声明式事务
    @Test
    public void test04(){
        Admin admin = new Admin(null, "Jerry", "123456", "杰瑞", "jerry@qq.com", null);
        //adminService.saveAdmin(admin);

        System.err.println(adminService.queryAdmin(1));
        System.out.println("finish...");
    }

    //添加数据库数据，测试分页
    @Test
    public void test05(){
        for (int i = 0; i < 102; i++)
            adminMapper.insert(new Admin(null,"loginAcct"+i,"password"+i,"userName"+i,"email"+i,null));
    }

    @Test
    public void test06(){
        for (int i = 0; i < 102; i++){
            roleMapper.insert(new Role(null,"role"+i));
        }
    }

    @Test
    public void test07(){
        List<Role> roles = roleMapper.queryAssignedRoleList(421);
        System.out.println(roles);
    }

}
