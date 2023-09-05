package dev.yogizogi.global.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                .httpBasic(HttpBasicConfigurer::disable)

                .csrf(CsrfConfigurer::disable)

                .cors(configurer -> {

                    CorsConfigurationSource source = request -> {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(List.of("*"));
                        configuration.setAllowedMethods(List.of("*"));

                        return configuration;
                    };

                    configurer.configurationSource(source);
                })

                .sessionManagement(configure -> configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize ->
                        authorize
                                // Open API(Swagger) 관련 Uri
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**")
                                .permitAll()

                                // Todo: 토큰 구현 후 권한에 맞게 Uri 수정
                                .requestMatchers("/**").permitAll()
                )

                //에러 핸들링
                .exceptionHandling(configurer -> {

                    configurer.authenticationEntryPoint(new AuthenticationEntryPoint() {
                        @Override
                        public void commence(
                                HttpServletRequest request,
                                HttpServletResponse response,
                                AuthenticationException authException
                        ) throws IOException {
                            response.setStatus(401);
                            response.setCharacterEncoding("utf-8");
                            response.setContentType("text/html; charset=UTF-8");
                            response.getWriter().write("인증되지 않은 사용자입니다.");
                        }
                    });

                    configurer.accessDeniedHandler(new AccessDeniedHandler() {
                        @Override
                        public void handle(
                                HttpServletRequest request,
                                HttpServletResponse response,
                                AccessDeniedException accessDeniedException
                        ) throws IOException {
                            response.setStatus(403);
                            response.setCharacterEncoding("utf-8");
                            response.setContentType("text/html; charset=UTF-8");
                            response.getWriter().write("권한이 없는 사용자입니다.");
                        }
                    });
                });

        return httpSecurity.getOrBuild();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
