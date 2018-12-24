package com.dmzl.sentiment.web.controller;

import com.dmzl.sentiment.core.constant.SystemParam;
import com.dmzl.sentiment.core.util.LocalTimeUtil;
import com.dmzl.sentiment.core.util.Result;
import com.dmzl.sentiment.core.util.TimeFormat;
import com.dmzl.sentiment.service.SentimentService;
import com.dmzl.sentiment.vo.TopWordsParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Description: 舆情相关接口类
 * @Author: moon
 * @CreateDate: 2018/12/19 0019 11:43
 * @UpdateUser: moon
 * @UpdateDate: 2018/12/19 0019 11:43
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@RestController
@RequestMapping("/sentiment")
public class SentimentController {
    @Autowired
    SentimentService sentimentManager;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @ApiOperation(value = "热词查询", notes = "热词查询")
    @RequestMapping(value = "listGroupSource", method = RequestMethod.GET)
    @ResponseBody
    public Result listGroupSource(@ModelAttribute TopWordsParam top) {
        if (isValidParam(top)) {
            return new Result("时间格式出现错误,格式如下{yyyy-MM-dd HH:mm:ss}");
        }
        return new Result(sentimentManager.listGroupSource(top));
    }

    /**
     * 基本参数校验
     *
     * @param top
     * @return
     */
    private boolean isValidParam(TopWordsParam top) {
        if (top.getTopn() == null || top.getTopn() < SystemParam.PageInitInfo.PAGE_NUM) {
            top.setTopn(SystemParam.PageInitInfo.PAGE_MIN_SIZE);
        } else if (top.getTopn() > SystemParam.PageInitInfo.PAGE_MAX_SIZE) {
            top.setTopn(SystemParam.PageInitInfo.PAGE_MAX_SIZE);
        }
        if (StringUtils.isNotEmpty(top.getStart())) {
            if (!LocalTimeUtil.isValidDate(top.getStart(), TimeFormat.LONG_DATE_PATTERN_LINE.formatter)) {
                return true;
            }
        }
        if (StringUtils.isNotEmpty(top.getEnd())) {
            if (!LocalTimeUtil.isValidDate(top.getEnd(), TimeFormat.LONG_DATE_PATTERN_LINE.formatter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param topn  取多少个热词
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    @ApiOperation(value = "热词查询", notes = "热词查询")
    @RequestMapping(value = "hotWorkTop", method = RequestMethod.GET)
    @ResponseBody
    public Result hotWorkTop(@ModelAttribute TopWordsParam top) {
        if (isValidParam(top)) {
            return new Result("时间格式出现错误,格式如下{yyyy-MM-dd HH:mm:ss}");
        }

        return new Result(sentimentManager.listTopWords(top));
    }

    @ApiOperation(value = "按照条件舆情", notes = "按照条件舆情")
    @RequestMapping(value = "listSentimentInfo", method = RequestMethod.GET)
    @ResponseBody
    public Result listSentimentInfo(@ModelAttribute TopWordsParam top) {
        if (isValidParam(top)) {
            return new Result("时间格式出现错误,格式如下{yyyy-MM-dd HH:mm:ss}");
        }
        return new Result(sentimentManager.listSentimentInfo(top));
    }

    @ApiOperation(value = "按照舆情正负和媒体类型+时间分组查询", notes = "按照舆情正负和媒体类型+时间分组查询")
    @RequestMapping(value = "sentimentAndMediaType", method = RequestMethod.GET)
    @ResponseBody
    public Result sentimentAndMediaType(@ModelAttribute TopWordsParam top) {
        if (top == null) {
            return new Result("请传递参数!");
        }
        if (isValidParam(top)) {
            return new Result("时间格式出现错误,格式如下{yyyy-MM-dd HH:mm:ss}");
        }
        return new Result(sentimentManager.listPublishTimeAndSentimentTypeGroupBy(top));
    }
}
