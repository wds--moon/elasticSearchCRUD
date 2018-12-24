package com.dmzl.sentiment.mapper;

import com.dmzl.sentiment.core.util.TkMapper;
import com.dmzl.sentiment.entity.SentimentTheme;
import org.apache.ibatis.annotations.Mapper;
/**
* @Description:    舆情主题操作类
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:24
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:24
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Mapper
public interface SentimentThemeMapper extends TkMapper<SentimentTheme> {
}