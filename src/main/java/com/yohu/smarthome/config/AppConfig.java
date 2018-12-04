package com.yohu.smarthome.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "web")
@Getter
@Setter
public class AppConfig {

    private String uploadPath;
}
