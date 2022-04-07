package com.oasis.security.provider;

import com.oasis.security.authentication.DefaultJwtAuthenticationToken;
import com.oasis.security.service.EnvironmentUserService;
import com.oasis.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/25 09:46
 * @Description:
 * @Version 1.0.0
 */
@RequiredArgsConstructor
public class DefaultJwtAuthenticationProvider implements AuthenticationProvider {

    private final EnvironmentUserService environmentUserService;

    private final JwtUtil jwtUtil;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            UserDetails userDetails = environmentUserService.loadUserByUsername(String.valueOf(authentication.getPrincipal()));
            DefaultJwtAuthenticationToken defaultJwtAuthenticationToken = new DefaultJwtAuthenticationToken(userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities(),
                    jwtUtil.createAccessToken(userDetails),
                    jwtUtil.createRefreshToken(userDetails));
            return defaultJwtAuthenticationToken;
        }catch (UsernameNotFoundException usernameNotFoundException) {
            throw new BadCredentialsException("用户认证失败");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
