package com.jsell.latte.global.Config;

import com.jsell.latte.domain.User.Repository.UserRepository;
import com.jsell.latte.global.Config.Filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/user/login",
            "/api/v1/user/join"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable).sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests.requestMatchers("/api/v1/user/login", "/api/v1/user/join").permitAll().anyRequest().authenticated())
                .addFilterBefore(new JwtAuthorizationFilter(jwtProvider, userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
