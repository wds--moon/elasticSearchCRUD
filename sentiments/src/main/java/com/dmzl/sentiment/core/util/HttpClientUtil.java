package com.dmzl.sentiment.core.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wendongshan on 2016/11/2.
 * 访问远程连接工具类
 */
/**
* @Description:    http访问工具类
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:19
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:19
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class HttpClientUtil {
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private static CloseableHttpClient closeableHttpClient = null;
    private static RequestConfig requestPostConfig = null;
    private static RequestConfig requestGetConfig = null;

    /**
     * 设置基本配置
     */
    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(50);
        closeableHttpClient = HttpClients.custom().setConnectionManager(cm).build();
        requestPostConfig = RequestConfig.custom().setConnectionRequestTimeout(1000).setSocketTimeout(60000).setConnectTimeout(60000).build();
        requestGetConfig = RequestConfig.custom().setConnectionRequestTimeout(1000).setSocketTimeout(50000).setConnectTimeout(50000).build();
    }
    /**
     * 设置大小
     */
    static int BUFFER_SIZE = 8192;

    static final String ENCODING = "UTF-8";

    /**
     * 默认编码格式
     *
     * @param url
     * @return
     */
    public static String getHttpUrl(String url) {
        return getHttpUrl(url, ENCODING);
    }

    /**
     * 默认编码格式
     *
     * @param url
     * @param data
     * @return
     */
    public static String postHttpUrlJson(String url, String data) {
        return postHttpUrl(url, data, ENCODING, "application/json");
    }
    public static String postHttpUrlHttpEntity(String url,HttpEntity httpEntity ) {
        return postHttpUrlEntry(url, httpEntity, ENCODING,"application/x-www-form-urlencoded");
    }

    public static String postHttpUrlForm(String url, String data) {
        return postHttpUrl(url, data, ENCODING, "application/x-www-form-urlencoded");
    }

    public static String postHttpUrlMultipart(String url, String data) {
        return postHttpUrl(url, data, ENCODING, "multipart/form-data");
    }

    /**
     * 采用httpclient 抓取数据 post 4.1最新方法 手动指定编码
     *
     * @param url
     * @param encoding
     * @return
     */
    public static String getHttpUrl(String url, String encoding) {
        LOGGER.info("get...开始.....url={}......data={}",url);
        String content = "";
        CloseableHttpResponse httpResponse = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestGetConfig);
            //执行get请求
            httpResponse = closeableHttpClient.execute(httpGet);
            //响应状态
            content = getRequstString(encoding, content, httpResponse);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.toString());
            }
        }
        return content;
    }


    /**
     * 采用httpclient 抓取数据 post 4.1最新方法 手动指定编码
     *
     * @param url
     * @param encoding
     * @return
     */
    public static String postHttpUrl(String url, String data, String encoding, String contentType) {
        LOGGER.info("post...开始.....url={}......data={}",url,data);
        String content = "";
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestPostConfig);

            //设置参数
            StringEntity stringEntry = new StringEntity(data, encoding);
            //设置参数类型
            stringEntry.setContentType(contentType);
            httpPost.setEntity(stringEntry);
            //执行post请求
            httpResponse = closeableHttpClient.execute(httpPost);
            //响应状态
            content = getRequstString(encoding, content, httpResponse);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.toString());
            }
        }
        return content;
    }

    public static String postHttpUrlEntry(String url, HttpEntity entity, String encoding,String contentType) {
        LOGGER.info("post...开始.....url={}......data={}",url,entity);
        String content = "";
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestPostConfig);
            httpPost.setEntity(entity);
            //执行post请求
            httpResponse = closeableHttpClient.execute(httpPost);
            //响应状态
            content = getRequstString(encoding, content, httpResponse);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.toString());
            }
        }
        return content;
    }

    /**
     * 指定heder
     *
     * @param url
     * @param data
     * @param header
     * @return
     */
    public static String postHttpUrl(String url, String data, Header header) {
        LOGGER.info("post...开始.....url={}......data={}",url,data);
        String content = "";
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestPostConfig);

            //设置参数
            StringEntity stringEntry = new StringEntity(data, "UTF-8");
            //设置参数类型
            stringEntry.setContentType("application/json");
            httpPost.setEntity(stringEntry);
            httpPost.setHeader(header);
            //执行post请求
            httpResponse = closeableHttpClient.execute(httpPost);
            //响应状态
            content = getRequstString("UTF-8", content, httpResponse);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.toString());
            }
        }
        return content;
    }

    /**
     * 获取返回结果
     *
     * @param encoding
     * @param content
     * @param httpResponse
     * @return
     * @throws Exception
     */
    private static String getRequstString(String encoding, String content, HttpResponse httpResponse) throws Exception {
        //成功响应吗为200
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //判断响应实体是否为空
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    content = inputStreamTOString(instream, encoding);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                } finally {
                    instream.close();
                }
            }
        }
        return content;
    }


    /**
     * 将InputStream转换成某种字符编码的String
     *
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String inputStreamTOString(InputStream in, String encoding)
            throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
            outStream.write(data, 0, count);
        }
        data = null;
        return new String(outStream.toByteArray(), encoding);
    }

}
