package com.oasis;

import com.oasis.security.config.OasisSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {OasisSecurityProperties.class})
@SpringBootApplication
public class OasisGatewayAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(OasisGatewayAdminApplication.class, args);
    }

}
