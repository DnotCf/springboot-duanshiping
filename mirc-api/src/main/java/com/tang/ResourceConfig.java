package com.tang;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "com.tang")
@PropertySource("classpath:resource-prod.properties")
public class ResourceConfig {


    private String zookeeperServer;
    private String filePath;
    private String downlodBgmUrl;

    public String getZookeeperServer() {
        return zookeeperServer;
    }

    public void setZookeeperServer(String zookeeperServer) {
        this.zookeeperServer = zookeeperServer;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDownlodBgmUrl() {
        return downlodBgmUrl;
    }

    public void setDownlodBgmUrl(String downlodBgmUrl) {
        this.downlodBgmUrl = downlodBgmUrl;
    }
}
