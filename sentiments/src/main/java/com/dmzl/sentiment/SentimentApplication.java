package com.dmzl.sentiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
* @Description:    舆情接口项目,springboot入口
* @Author:         moon
* @CreateDate:     2018/12/17 0017 10:32
* @UpdateUser:     moon
* @UpdateDate:     2018/12/17 0017 10:32
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@EnableScheduling
@EnableWebMvc
@SpringBootApplication
public class SentimentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SentimentApplication.class, args);
	}
}
