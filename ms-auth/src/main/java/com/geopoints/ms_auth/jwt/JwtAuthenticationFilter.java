package com.geopoints.ms_auth.jwt;

import com.geopoints.ms_auth.service.JwtService;
import com.geopoints.ms_auth.service.UserService;
import com.geopoints.ms_auth.utils.constants.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String tokenHeader = request.getHeader(Constants.AUTHORIZATION);

        if(!StringUtils.hasText(tokenHeader) || !tokenHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        //Obtener el token
        String token = tokenHeader.substring(7);
        String username = jwtService.getUserName(token);

        //ver existencias
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            log.info("Cargando datos de usuario...");
            UserDetails userDetails = userService.userDetails().loadUserByUsername(username);
            if(jwtService.validateToken(token,userDetails) && !jwtService.ValidateIsRefreshToken(token)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }
        }
    }
}