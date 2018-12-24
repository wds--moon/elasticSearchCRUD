package com.dmzl.sentiment.mapper;

import com.dmzl.sentiment.core.util.TkMapper;
import com.dmzl.sentiment.entity.Search;
import org.apache.ibatis.annotations.Mapper;
/**
* @Description:    舆情操作类
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:21
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:21
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Mapper
public interface SearchMapper extends TkMapper<Search> {
}