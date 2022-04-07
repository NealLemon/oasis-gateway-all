package com.oasis.security.service;

import com.oasis.security.config.OasisSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/25 10:12
 * @Description:
 * @Version 1.0.0
 */
@RequiredArgsConstructor
@Service
public class EnvironmentUserService implements UserDetailsService {

    private final OasisSecurityProperties oasisSecurityProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals(oasisSecurityProperties.getAdmin().getUserName())) {
            return new User(oasisSecurityProperties.getAdmin().getUserName(),
                    "",
                    oasisSecurityProperties.getAdmin().getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        throw new UsernameNotFoundException("Admin is not found");
    }
}
