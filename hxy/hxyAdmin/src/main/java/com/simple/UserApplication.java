package com.simple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenkx
 * @date   2017-12-26
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.simple.mapper"})
@EnableSwagger2
//解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
