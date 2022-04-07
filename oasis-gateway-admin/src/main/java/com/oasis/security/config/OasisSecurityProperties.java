package com.oasis.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "oasis")
public class OasisSecurityProperties {

    @Getter
    @Setter
    private Jwt jwt = new Jwt();
    @Getter
    @Setter
    private User admin = new User();
    @Getter
    @Setter
    public static class Jwt {

        private String header = "Authorization"; // HTTP 报头的认证字段的 key

        private String prefix = "Bearer "; // HTTP 报头的认证字段的值的前缀

        private long accessTokenExpireTime = 60 * 1000L; // Access Token 过期时间

        private long refreshTokenExpireTime = 30 * 24 * 3600 * 1000L; // Refresh Token 过期时间

        private String key;

        private String refreshKey;
    }

    @Getter
    @Setter
    public static class User {
        private String userName;
        private Set<String> roles;
    }

}
