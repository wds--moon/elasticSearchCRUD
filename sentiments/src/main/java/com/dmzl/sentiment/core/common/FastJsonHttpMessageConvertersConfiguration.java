package com.dmzl.sentiment.core.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @Description:    改变springboot中默认的json, 修改成fastjson,需要做下面的bean设置
 * @Author:         moon
 * @CreateDate:     2018/12/17 0017 10:25
 * @UpdateUser:     moon
 * @UpdateDate:     2018/12/17 0017 10:25
 * @UpdateRemark:   修改内容
 * @Version:        1.0
 */
@Configuration
@ConditionalOnClass({ JSON.class })
public class FastJsonHttpMessageConvertersConfiguration {

	private static Logger logger = LoggerFactory.getLogger(FastJsonHttpMessageConvertersConfiguration.class);

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		logger.info("=================fastJsonHttpMessageConverters===================");
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}
}
