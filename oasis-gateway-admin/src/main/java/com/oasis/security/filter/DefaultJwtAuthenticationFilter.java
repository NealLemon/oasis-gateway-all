package com.oasis.security.filter;

import com.oasis.security.converter.DefaultJwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/24 15:48
 * @Description: Spring Security 默认 JWT 安全过滤器
 * @Version 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultJwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AuthenticationManager authenticationManager;

    private final DefaultJwtAuthenticationConverter authenticationConverter;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try{
            UsernamePasswordAuthenticationToken authRequest = authenticationConverter.convert(request);
            if (authRequest == null) {
                this.logger.trace("Did not process authentication request since failed to find "
                        + "jwt token in Authorization header");
                chain.doFilter(request, response);
                return;
            }
            String username = authRequest.getName();
            this.logger.trace(LogMessage.format("Found username '%s' in jwt token Authorization header", username));
            if (authenticationIsRequired(username)) {
                Authentication authResult = authenticationManager.authenticate(authRequest);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authResult);
                SecurityContextHolder.setContext(context);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
                }
                onSuccessfulAuthentication(request, response, authResult);
            }
        }
        catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            this.logger.debug("Failed to process authentication request", ex);
            authenticationEntryPoint.commence(request, response, ex);
            return;
        }
        chain.doFilter(request, response);
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              Authentication authResult) throws IOException {
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {
    }


    private boolean authenticationIsRequired(String username) {
        // Only reauthenticate if username doesn't match SecurityContextHolder and user
        // isn't authenticated (see SEC-53)
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }
        // Limit username comparison to providers which use usernames (ie
        // UsernamePasswordAuthenticationToken) (see SEC-348)
        if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(username)) {
            return true;
        }
        // Handle unusual condition where an AnonymousAuthenticationToken is already
        // present. This shouldn't happen very often, as BasicProcessingFitler is meant to
        // be earlier in the filter chain than AnonymousAuthenticationFilter.
        // Nevertheless, presence of both an AnonymousAuthenticationToken together with a
        // BASIC authentication request header should indicate reauthentication using the
        // BASIC protocol is desirable. This behaviour is also consistent with that
        // provided by form and digest, both of which force re-authentication if the
        // respective header is detected (and in doing so replace/ any existing
        // AnonymousAuthenticationToken). See SEC-610.
        return (existingAuth instanceof AnonymousAuthenticationToken);
    }
}
