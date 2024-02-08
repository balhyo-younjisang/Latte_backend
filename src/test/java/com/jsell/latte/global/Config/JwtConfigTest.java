package com.jsell.latte.global.Config;

import com.jsell.latte.domain.User.Domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Jwt Config Test")
class JwtConfigTest {
    private JwtProvider jwtConfig;

    @BeforeEach
    void setUp() {
        jwtConfig = new JwtProvider();
    }

    @Test
    @DisplayName("토큰 생성 및 복호화 테스트")
    void tokenTest() {
        // given
        final User user = User.builder().name("yunjisang").intro("hello").password("1234").email("yjs12180825@gmail.com").build();

        // when
        final String token = jwtConfig.generateJwtToken(user);
        String userEmailFromToken = jwtConfig.getEmailFromToken(token);

        // then
        assertEquals("yunjisang 01", userEmailFromToken);
    }
}