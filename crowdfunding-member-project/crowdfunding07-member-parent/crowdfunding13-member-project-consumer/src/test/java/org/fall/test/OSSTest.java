package org.fall.test;

import org.fall.config.OSSProperties;
import org.fall.util.CrowdUtil;
import org.fall.util.ResultEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
public class OSSTest {

    @Autowired
    OSSProperties ossProperties;


    @Test
    public void testOSSStore(){

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("1234.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                inputStream,
                ossProperties.getBucketName(), ossProperties.getBucketDomain(), "org/fall/test/1234.png");
        String data = resultEntity.getData();
        System.out.println(data);
    }
}
