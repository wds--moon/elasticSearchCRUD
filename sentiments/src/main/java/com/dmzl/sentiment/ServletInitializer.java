package com.dmzl.sentiment;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
/**
* @Description:    系统自动生成,暂时不需要特别处理
* @Author:         moon
* @CreateDate:     2018/12/17 0017 10:34
* @UpdateUser:     moon
* @UpdateDate:     2018/12/17 0017 10:34
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SentimentApplication.class);
	}

}
