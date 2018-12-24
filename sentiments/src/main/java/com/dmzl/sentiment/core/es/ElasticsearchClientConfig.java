package com.dmzl.sentiment.core.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description: elasticsearch 初始化链接工具类
 * 如果是集群配置需要多加几个TransportAddress
 * TransportAddress node = new TransportAddress(
 * InetAddress.getByName("localhost"),
 * 9300
 * );
 * TransportAddress node1 = new TransportAddress(
 * InetAddress.getByName("localhost"),
 * 9301
 * );
 * client.addTransportAddress(node);
 * client.addTransportAddress(node1);
 * @Author: moon
 * @CreateDate: 2018/12/21 0021 10:27
 * @UpdateUser: moon
 * @UpdateDate: 2018/12/21 0021 10:27
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Configuration
public class ElasticsearchClientConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${elasticsearch.cluster.name}")
    private String elasticsearchClusterName;

    @Value("${elasticsearch.cluster.ip}")
    private String elasticsearchClusterIp;

    @Value("${elasticsearch.cluster.port}")
    private Integer elasticsearchClusterPort;

    @Bean
    public TransportClient client() {
        TransportClient client = null;
        Settings settings = Settings.builder().put("cluster.name", elasticsearchClusterName).put("client.transport.sniff", true)
                .build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticsearchClusterIp), elasticsearchClusterPort));
        } catch (UnknownHostException e) {
            logger.error("elasticsearch initialization failed!{}", e);
        }
        return client;
    }
}
