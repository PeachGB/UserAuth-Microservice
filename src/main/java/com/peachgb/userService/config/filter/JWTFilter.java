package com.peachgb.userService.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.peachgb.userService.auth.utils.JWTUtil;
import com.peachgb.userService.users.model.User;
import com.peachgb.userService.users.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);
            String email = jwtUtil.validateToken(jwt);
            if(email == null){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            }
            try{
                request.setAttribute("email", email);
                User user = userService.loadUserByEmail(email);
                UsernamePasswordAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                if (SecurityContextHolder.getContext().getAuthentication() == null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }} catch (JWTVerificationException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            }
                filterChain.doFilter(request, response);
        }

    }
}
