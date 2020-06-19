package com.merrycodes.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云设置
 *
 * @author MerryCodes
 * @date 2020/6/15 20:37
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "qiniu")
public class QiniuConfig {

    /**
     * 外链后缀
     */
    private String urlSuffix = "/";

    /**
     * AccessKey
     */
    private String accessKey;

    /**
     * SecretKey
     */
    private String secretKey;

    /**
     * 空间
     */
    private String bucket;

    /**
     * 域名
     */
    private String host;

    public String getHost() {
        if (!host.endsWith(urlSuffix)) {
            return host + "/";
        }
        return host;
    }
}
