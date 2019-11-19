package cn.com.arcsoft.arcci.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Fururur
 * @create 2019-11-04-13:50
 */
@Data
@Component
@ConfigurationProperties(prefix = "meta")
public class PropertiesConfig {
    private String accessToken;
    private String gitlabUrl;
}

