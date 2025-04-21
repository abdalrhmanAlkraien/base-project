package com.alrajhi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/15/2025
 * @Time: 3:01 PM
 */
@Configuration
@ConfigurationProperties(prefix = "soap.urls")
@Getter
@Setter
public class UrlConfig {

    private String mainUrl;
    private String commonUrl;
    private String uploadFile;
}
