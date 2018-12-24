package com.dmzl.sentiment.mapper;

import com.dmzl.sentiment.entity.Search;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
/**
* @Description:    查询数据库舆情信息,存储到es dao类
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:21
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:21
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Mapper
public interface SentimentMapper {

    /**
     * 查询所有的需要存储到es的数据集合的id
     *
     * @return
     */
    @Select("select id from t_search where es is null order by id asc ")
    public List<String> getUnMarked();

    /**
     * in 查询只能使用$
     *
     * @param ids
     * @return
     */
    @Select("select id,author_name as authorName,content,content_html as contentHtml,dup_count as dupCount,duplicates,\n" +
            "uuid,image,media_id as mediaId,media_type as mediaType,publish_time as publishTime,crawl_time as crawlTime,reply_count as replyCount,\n" +
            "sentiment_type as sentimentType,thread_starter as threadStarter,title,url,view_count as viewCount,sentiment,es,probability from t_search where id in(${ids})")
    public List<Search> getUnEs(@Param("ids") String ids);


    /**
     * in 查询只能是$
     *
     * @param ids
     */
    @Update("update t_search set es = 1 where id in(${ids}) ")
    public void setEs(@Param("ids") String ids);

}
