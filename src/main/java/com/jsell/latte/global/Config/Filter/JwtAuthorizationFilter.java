package com.jsell.latte.global.Config.Filter;

import com.jsell.latte.domain.User.Domain.User;
import com.jsell.latte.domain.User.Repository.UserRepository;
import com.jsell.latte.global.Config.JwtProvider;
import com.jsell.latte.global.Exception.InvalidTokenException;
import com.jsell.latte.global.Exception.NotExistsTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;
import java.util.HashMap;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 1. Request Header 에서 JWT 토큰 추출
            String token = resolveToken((HttpServletRequest) request);

            // 토큰이 존재하지 않는 경우
            if(token == null) throw new NotExistsTokenException("토큰이 존재하지 않습니다");

            // 2. validateToken 으로 토큰 유효성 검사
            if (jwtProvider.validateToken(token)) {
                // 토큰이 유효할 경우 토큰에서 유저의 ID를 가지고 와서 유효한 ID인지 확인
                Long userId = jwtProvider.getIdFromToken(token);

                // ID가 유효하지 않을 경우 InvalidTokenException 을 던짐
                User existsUser = userRepository.findById(userId).orElseThrow();
                chain.doFilter(request, response);
            } else {
                // 토큰이 유효하지 않는 경우
                throw new InvalidTokenException("토큰이 유효하지 않습니다.");
            }
        } catch (Exception e) {
            // Token 내에 Exception이 발생 하였을 경우 => 클라이언트에 응답값을 반환하고 종료
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = createJsonResponse(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    /**
     * 토큰 관련 Exception 발생 시 예외 응답값 구성
     *
     * @param e Exception
     * @return JSONObject
     */
    private JSONObject createJsonResponse(Exception e) {
        String errorMsg = "";

        if(e instanceof ExpiredJwtException) {
            errorMsg = "만료된 토큰입니다";
        } else if (e instanceof SignatureException) {
            errorMsg = "허용된 토큰이 아닙니다";
        } else if (e instanceof JwtException) {
            errorMsg = "토큰에서 오류가 발생했습니다";
        } else {
            errorMsg = e.getMessage();
        }

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("code", 401);
        jsonMap.put("msg", errorMsg);
        jsonMap.put("data", e.getMessage());

        return new JSONObject(jsonMap);
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}