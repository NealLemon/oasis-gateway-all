package com.oasis.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.common.vo.OasisResponseVO;
import com.oasis.security.converter.DefaultJwtAuthenticationConverter;
import com.oasis.security.entrypoint.DefaultJwtAuthenticationEntryPoint;
import com.oasis.security.filter.DefaultJwtAuthenticationFilter;
import com.oasis.security.filter.RestAuthenticationFilter;
import com.oasis.security.provider.DefaultJwtAuthenticationProvider;
import com.oasis.security.service.EnvironmentUserService;
import com.oasis.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/24 18:41
 * @Description:
 * @Version 1.0.0
 */
@RequiredArgsConstructor
@EnableWebSecurity()
@Configuration
@Slf4j
public class OasisSecurityConfig extends WebSecurityConfigurerAdapter {


    private final ObjectMapper objectMapper;

    private final EnvironmentUserService environmentUserService;

    private final JwtUtil jwtUtil;

    private final OasisSecurityProperties oasisSecurityProperties;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 配置跨域
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .mvcMatchers("/auth/**").permitAll()
                        .mvcMatchers("/api/**").authenticated()
                        .anyRequest().denyAll())
                .addFilterBefore(defaultJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置 LdapAuthenticationProvider
        auth.authenticationProvider(new DefaultJwtAuthenticationProvider(environmentUserService,jwtUtil));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader("X-Authenticate");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    private RestAuthenticationFilter restAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter(objectMapper);
        filter.setAuthenticationSuccessHandler(jsonLoginSuccessHandler());
        filter.setAuthenticationFailureHandler(jsonLoginFailureHandler());
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/auth/login");
        return filter;
    }

    private AuthenticationSuccessHandler jsonLoginSuccessHandler() {
        return (req, res, auth) -> {
            res.setStatus(HttpStatus.OK.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF8");
            res.getWriter().write(objectMapper.writeValueAsString(OasisResponseVO.success(auth)));
            log.debug("认证成功");
        };
    }

    private LogoutSuccessHandler jsonLogoutSuccessHandler() {
        return (req, res, auth) -> {
            if (auth != null && auth.getDetails() != null) {
                req.getSession().invalidate();
            }
            res.setStatus(HttpStatus.OK.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF8");
            res.getWriter().write(objectMapper.writeValueAsString(OasisResponseVO.success(auth)));
            log.debug("成功退出登录");
        };
    }

    private AuthenticationFailureHandler jsonLoginFailureHandler() {
        return (req, res, exp) -> {
            log.error("loginFailure exception : {}",exp.getMessage());
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");
            res.getWriter().println(objectMapper.writeValueAsString(OasisResponseVO.error(401,"authentication failed")));
        };
    }

    @Bean
    public DefaultJwtAuthenticationFilter defaultJwtAuthenticationFilter() throws Exception {
        return new DefaultJwtAuthenticationFilter(new DefaultJwtAuthenticationEntryPoint(),
                authenticationManager(),
                new DefaultJwtAuthenticationConverter(oasisSecurityProperties,jwtUtil));
    }

}
