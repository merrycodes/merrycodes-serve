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

    @Setter(AccessLevel.NONE)
    private PublicKey publicKey;

    @Setter(AccessLevel.NONE)
    private PrivateKey privateKey;

    private String authLoginUrl;

    private String authLogoutUrl;

    private String publicKeyPath;

    private String privateKeyPath;

    private String jwtPayloadUserKey;

    private Long expiration;

    private Long expirationRemember;

    private String tokenHeader = "Authorization";

    private String tokenPrefix = "Bearer ";

    @PostConstruct
    private void getRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(publicKeyPath);
        privateKey = RsaUtils.getPrivateKey(privateKeyPath);
    }

}
