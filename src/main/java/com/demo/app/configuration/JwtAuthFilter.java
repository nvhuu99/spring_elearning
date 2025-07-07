package com.demo.app.configuration;

import com.demo.models.data.AuthUser;
import com.demo.services.IUserService;
import com.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final IUserService userService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token;
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else {
                token = getJwtFromCookie(request);
            }
            if (token != null) {
                var jws = jwtUtil.parseToken(token);
                // Get user detail or fail
                var username = jwtUtil.extractUsername(jws);
                var user = userService.findByUsername(username);
                var accessToken = jwtUtil.extractAccessToken(jws);
                if (user != null && userService.checkAccessToken(username, accessToken)) {
                    // Token validated successfully, set auth data
                    var authData = new AuthUser(username, user.getPassword());
                    var authCtx = new UsernamePasswordAuthenticationToken(authData, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authCtx);
                }
            }
        } catch (Exception e) {
            // Invalid token => skip setting authentication
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("JWT".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}

