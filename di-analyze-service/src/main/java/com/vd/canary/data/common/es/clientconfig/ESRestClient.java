package com.vd.canary.data.common.es.clientconfig;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Slf4j
@Configuration
@Getter
@Setter
@ComponentScan(basePackageClasses=ESClientSpringFactory.class)
public class ESRestClient {

    @Value("${spring.elasticSearch.client.connectNum}")
    private Integer connectNum;

    @Value("${spring.elasticSearch.client.connectPerRoute}")
    private Integer connectPerRoute;

    @Value("${spring.elasticSearch.hostlist}")
    private String hostlist;

    @Bean
    public HttpHost[] httpHost(){
        //解析hostlist配置信息
        String[] split = hostlist.split(",");
        //创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for(int i=0;i<split.length;i++){
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        log.info("init HttpHost");
        return httpHostArray;
    }

    @Bean(initMethod="init",destroyMethod="close")
    public ESClientSpringFactory getFactory(){
        log.info("ESClientSpringFactory 初始化");
        return ESClientSpringFactory.
                build(httpHost(), connectNum, connectPerRoute);
    }

    @Bean
    @Scope("singleton")
    public RestClient getRestClient(){
        log.info("RestClient 初始化");
        return getFactory().getClient();
    }

    /*
    @Bean(name = "restHighLevelClient")
    @Scope("singleton")
    public RestHighLevelClient getRHLClient(){
        log.info("RestHighLevelClient 初始化");
        return getFactory().getRhlClient();
    }*/
    @Bean(name = "restHighLevelClient")
    @Scope("singleton")
    public RestHighLevelClient getRHLClient(){
        log.info("RestHighLevelClient 初始化");
        return getFactory().getRhlClient();
    }

}
