package com.qccr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 启动类
 * @author shife
 *扩展@Transactional注解事务管理资料
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages="com.qccr.mapper")
@EnableTransactionManagement
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }
}
