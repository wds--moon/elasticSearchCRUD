package com.dmzl.sentiment.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
* @Description:    设置编码
* @Author:         moon
* @CreateDate:     2018/12/17 0017 10:23
* @UpdateUser:     moon
* @UpdateDate:     2018/12/17 0017 10:23
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Configuration
public class CharacterEncodingFilterConfiguration {

    private static Logger logger = LoggerFactory.getLogger(CharacterEncodingFilterConfiguration.class);

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        logger.info("===============characterEncodingFilter================");
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
}
