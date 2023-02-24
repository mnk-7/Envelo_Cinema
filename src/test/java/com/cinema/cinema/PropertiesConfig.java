package com.cinema.cinema;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties
public class PropertiesConfig {

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.prefix}")
    private String appPrefix;

    @Value("${app.server}")
    private String appServer;

    public String getPath(String controllerPrefix) {
        return "/" + appPrefix + "/" + appVersion+ "/" + controllerPrefix;
    }

    public String getFullPath(String controllerPrefix) {
        return appServer + getPath(controllerPrefix);
    }

}
