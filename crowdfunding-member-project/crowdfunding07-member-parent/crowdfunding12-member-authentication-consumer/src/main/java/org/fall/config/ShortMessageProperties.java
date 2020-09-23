package org.fall.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "short.message")
public class ShortMessageProperties {
    private String host;
    private String path;
    private String appCode;
    private String sign;
    private String skin;
}
