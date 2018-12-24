package com.dmzl.sentiment.core.common;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;
/**
* @Description:    TkMybatis 配置类
* @Author:         moon
* @CreateDate:     2018/12/17 0017 10:35
* @UpdateUser:     moon
* @UpdateDate:     2018/12/17 0017 10:35
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Configuration
public class TkMybatisConfig {
	@Resource
	private DataSource dataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setTypeAliasesPackage("com.dmzl");
		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		bean.setMapperLocations(resolver.getResources("classpath:com/dmzl/mapper/*.xml"));
		return bean.getObject();
	}

	@Configuration
	@AutoConfigureAfter(TkMybatisConfig.class)
	public static class MyBatisMapperScannerConfigurer {

		@Bean
		public MapperScannerConfigurer mapperScannerConfigurer() {
			MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
			mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
			mapperScannerConfigurer.setBasePackage("com.dmzl.mapper.*");
			// 配置通用mappers
			Properties properties = new Properties();
			properties.setProperty("mappers", "com.dmzl.sentiment.core.util.TkMapper");
			properties.setProperty("notEmpty", "false");
			properties.setProperty("IDENTITY", "MYSQL");
			properties.setProperty("mapUnderscoreToCamelCase", "true");
			mapperScannerConfigurer.setProperties(properties);

			return mapperScannerConfigurer;
		}

	}
}
