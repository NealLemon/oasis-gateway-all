package com.oasis.security.converter;

import com.oasis.security.config.OasisSecurityProperties;
import com.oasis.security.util.CollectionUtil;
import com.oasis.security.util.JwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/24 16:18
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultJwtAuthenticationConverter implements AuthenticationConverter {

    private final OasisSecurityProperties oasisSecurityProperties;

    private final JwtUtil jwtUtil;

    @Override
    public UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
        if(checkJWTToken(request)) {
            Optional<Claims> claimsOptional = validateToken(request);
            if(claimsOptional.isPresent()) {
                if(null != claimsOptional.get().get("authorities")) {
                    val rawList = CollectionUtil.convertObjectToList(claimsOptional.get().get("authorities"));
                    val authorities = rawList.stream().map(String::valueOf).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    return new UsernamePasswordAuthenticationToken(claimsOptional.get().getSubject(), null, authorities);
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }
        return null;
    }


    /**
     * 解析 JWT 得到 Claims
     *
     * @param req HTTP 请求
     * @return JWT Claims
     */
    private Optional<Claims> validateToken(HttpServletRequest req) {
        String jwtToken = req.getHeader(oasisSecurityProperties.getJwt().getHeader()).replace(oasisSecurityProperties.getJwt().getPrefix(), "");
        try {
            return Optional.of(Jwts.parserBuilder().setSigningKey(jwtUtil.getKey()).build().parseClaimsJws(jwtToken).getBody());
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Error parsing jwt {}", e.getLocalizedMessage());
            return Optional.empty();
        }
    }




    /**
     * 检查 JWT Token 是否在 HTTP 报头中
     *
     * @param req HTTP 请求
     * @return 是否有 JWT Token
     */
    private boolean checkJWTToken(HttpServletRequest req) {
        String authenticationHeader = req.getHeader(oasisSecurityProperties.getJwt().getHeader());
        return authenticationHeader != null && authenticationHeader.startsWith(oasisSecurityProperties.getJwt().getPrefix());
    }

}
