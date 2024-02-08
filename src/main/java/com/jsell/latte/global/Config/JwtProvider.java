package com.jsell.latte.global.Config;

import com.jsell.latte.domain.User.Domain.User;
import com.jsell.latte.global.Common.Dto.Token;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtProvider {
    @Value("${latte.jwt.security_key}")
    private String securityKey; // jwt security key
//    private final Long expiredTime = 1000 * 60L * 60L * 3L; // token 유효시간 : 3시간
    private final Long expiredTime = 1000L;

    /**
     * User 정보를 담은 JWT 토큰을 생성
     * @param user User 정보를 담은 객체
     * @return jwt 토큰
     */
    public Token generateJwtToken(User user) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(user.getEmail()).setClaims(createClaims(user))
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, securityKey).compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();

        return Token.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();
    }

    /**
     * User 정보를 가지고 Claims를 만드는 메서드
     * @param user
     * @return
     */
    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());

        return claims;
    }

    /**
     * Token 에서 claim 추출
     * @param token JWT 토큰
     * @return Claims 클레임
     */
    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(securityKey).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * 토큰으로부터 id 추출
     * @param token JWT 토큰
     * @return String User의 id
     */
    public Long getIdFromToken(String accessToken) {
        Claims claims = getClaims(accessToken);

        if(claims.get("id") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        return (Long) claims.get("id");
    }

    /**
     *  토큰이 유효한지 검사하는 메서드
     * @param token Jwt 토큰
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(securityKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}
