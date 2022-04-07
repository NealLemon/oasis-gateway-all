package com.oasis.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/25 11:03
 * @Description:
 * @Version 1.0.0
 */
public class DefaultJwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String access_token;

    private String refresh_token;



    public DefaultJwtAuthenticationToken(Object principal,
                                         Object credentials, Collection<? extends GrantedAuthority> authorities,
                                         String access_token,
                                         String refresh_token) {
        super(principal, credentials, authorities);
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
