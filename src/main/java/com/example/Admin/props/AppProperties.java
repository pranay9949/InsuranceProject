package com.example.Admin.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="planapi")
public class AppProperties {
    private HashMap<String,String> messages = new HashMap<>();
}
