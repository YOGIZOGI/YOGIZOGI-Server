package dev.yogizogi.global.config.security;

import dev.yogizogi.domain.security.service.JwtService;
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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                .httpBasic(HttpBasicConfigurer::disable)

                .csrf(CsrfConfigurer::disable)

                .cors(cors -> {

                    CorsConfigurationSource source = request -> {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(List.of("*"));
                        configuration.setAllowedMethods(List.of("*"));

                        return configuration;
                    };

                    cors.configurationSource(source);
                })

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(request ->
                        request
                                .anyRequest().hasRole("USER")
                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)

                //에러 핸들링
                .exceptionHandling(exception -> {

                    exception.authenticationEntryPoint(new AuthenticationEntryPoint() {
                        @Override
                        public void commence(
                                HttpServletRequest request,
                                HttpServletResponse response,
                                AuthenticationException authException
                        ) throws IOException {
                            response.setStatus(401);
                            response.setCharacterEncoding("utf-8");
                            response.setContentType("text/html; charset=UTF-8");
                            response.getWriter().write("유효하지 않은 토큰입니다.");
                        }

                    });

                    exception.accessDeniedHandler(new AccessDeniedHandler() {
                        @Override
                        public void handle(
                                HttpServletRequest request,
                                HttpServletResponse response,
                                AccessDeniedException accessDeniedException
                        ) throws IOException {
                            response.setStatus(403);
                            response.setCharacterEncoding("utf-8");
                            response.setContentType("text/html; charset=UTF-8");
                            response.getWriter().write("유효하지 않은 토큰입니다.");
                        }

                    });
                });

        return httpSecurity.getOrBuild();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**")
                .requestMatchers("/h2-console/**")
                .requestMatchers(
                        "/api/auth/**", "/api/sign-up/**", "/api/verification/**",
                        "/api/users/find-password", "/api/users/update-password", "/api/s3/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
