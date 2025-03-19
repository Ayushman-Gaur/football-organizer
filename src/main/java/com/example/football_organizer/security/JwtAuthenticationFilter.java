package com.example.football_organizer.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        logger.info("Request URI: "+request.getRequestURI());
        logger.info("Auth Header: "+ authHeader);
        String token = null;
        String username= null;

        //Extract token from "Bearer " header
        if (authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);// from this token will be extracted from request after 7 index
            try {
                username = jwtUtil.extractUsername(token);// from here username variable will store username from token
                logger.info("Extracted username: "+username);
            } catch (Exception e) {
                logger.error("Token parsing failed: " + e.getMessage());
            }
        }

        //Check for validation and set authentication
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()== null){
            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(token,username)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null ,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Authentication set for: " + username);
            }else{
                logger.warn("Token validation failed for: " + username);
            }
        }
        filterChain.doFilter(request,response);
    }
}
