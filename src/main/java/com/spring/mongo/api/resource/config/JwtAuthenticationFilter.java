package com.spring.mongo.api.resource.config;

import com.spring.mongo.api.resource.service.CustomUserDetailService;
import com.spring.mongo.api.resource.serviceImpl.JwtHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bearer
        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {
                username = jwtHelper.extractUserName(token);
            } catch (IllegalArgumentException e) {
                log.info("Illegal Argument while fetching the username.");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                log.info("Given jwt token is expired.");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                log.info("Invalid Token.");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("Invalid Header Value.");
        }

        if (username != null) {
            //fetch user detail from username
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            boolean validateToken = this.jwtHelper.isTokenValidate(token, userDetails);
            if (Boolean.TRUE.equals(validateToken)) {
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.info("Validation fails.");
            }
        }
        filterChain.doFilter(request, response);
    }
}
