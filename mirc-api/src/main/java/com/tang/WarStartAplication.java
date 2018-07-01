package com.tang;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 继承SpringBootServletInitializer相当与web.xml启动
 */
public class WarStartAplication extends SpringBootServletInitializer {

    /**
     * 从写配置 configure
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

       //指向springboot启动类
        return builder.sources(Application.class);
    }

}
