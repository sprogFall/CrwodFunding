package org.fall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.fall.mapper")
@SpringBootApplication
public class CrowdMainApp {

    public static void main(String[] args) {
        SpringApplication.run(CrowdMainApp.class, args);
    }
}
