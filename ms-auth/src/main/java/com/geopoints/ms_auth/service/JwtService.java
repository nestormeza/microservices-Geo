package com.geopoints.ms_auth.service;

import com.geopoints.ms_auth.entity.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface JwtService {
    String getUserName(String token);
    String generateToken(UserEntity user);
    boolean validateToken(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String,Object> claims, UserDetails userDetails);
    boolean ValidateIsRefreshToken(String token);
    Claims claims(String token);
    List<String> getRolesClaims(String token);
}
