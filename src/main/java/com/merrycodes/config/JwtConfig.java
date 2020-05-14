package com.merrycodes.config;

import com.merrycodes.utils.RsaUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 项目的Jwt相关配置类
 *
 * @author MerryCodes
 * @date 2020/5/10 9:56
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * 公钥对象
     */
    @Setter(AccessLevel.NONE)
    private PublicKey publicKey;

    /**
     * 私钥对象
     */
    @Setter(AccessLevel.NONE)
    private PrivateKey privateKey;

    /**
     * 登录处理路径
     */
    private String authLoginUrl;

    /**
     * 登出处理路径
     */
    private String authLogoutUrl;

    /**
     * 公钥路径
     */
    private String publicKeyPath;

    /**
     * 私钥路径
     */
    private String privateKeyPath;

    /**
     * Token存放数据的key
     */
    private String jwtPayloadUserKey;

    /**
     * remember-me 为 false 的时候过期时间是一天
     */
    private Long expiration;

    /**
     * remember-me 为 true 的时候过期时间是一周
     */
    private Long expirationRemember;

    /**
     * Token存放Header的名称
     */
    private String tokenHeader = "Authorization";

    /**
     * Token的前缀
     */
    private String tokenPrefix = "Bearer ";

    @PostConstruct
    private void getRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(publicKeyPath);
        privateKey = RsaUtils.getPrivateKey(privateKeyPath);
    }

}
