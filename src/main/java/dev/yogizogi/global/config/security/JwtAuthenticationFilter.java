package dev.yogizogi.global.config.security;

import static dev.yogizogi.domain.security.model.TokenType.REFRESH_TOKEN;
import static dev.yogizogi.global.common.model.constant.Format.REISSUE_ACCESS_TOKEN_URL;

import dev.yogizogi.domain.security.exception.EmptyTokenException;
import dev.yogizogi.domain.security.exception.InvalidTokenException;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.global.common.code.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtService.extractToken(request).filter(jwtService::isValid)
                .orElseThrow(() -> new EmptyTokenException(ErrorCode.EMPTY_TOKEN));

        if (Objects.equals(request.getRequestURI(), REISSUE_ACCESS_TOKEN_URL)) {

            if (!Objects.equals(jwtService.extractSubject(token).getType(), REFRESH_TOKEN)) {
                throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
            }

        }

        Authentication auth = jwtService.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);

    }


}
