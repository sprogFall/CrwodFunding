package org.fall.test;

import org.fall.entity.po.MemberPO;
import org.fall.entity.vo.DetailProjectVO;
import org.fall.mapper.MemberPOMapper;
import org.fall.mapper.ProjectPOMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class MyBatisTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    MemberPOMapper memberPOMapper;

    @Autowired
    ProjectPOMapper projectPOMapper;


    Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.info("connection:   " + connection);
        connection.close();
    }

    @Test
    public void testMyBatis01(){
        int i = memberPOMapper.insert(new MemberPO(null, "test", "test", "test", "test", 1, 1, "test", "test", 1));
        System.err.println("操作"+i+"完成");
    }

    @Test
    public void test02(){
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(3);
        System.err.println(detailProjectVO);
    }


}
