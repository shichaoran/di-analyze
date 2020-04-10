package com.vd.canary.data.common.es.configuration;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringBootConfiguration;

@SpringBootConfiguration
public class ElasticSearchConfig {

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
