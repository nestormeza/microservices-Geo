package com.geopoints.ms_auth.service.impl;

import com.geopoints.ms_auth.entity.UserEntity;
import com.geopoints.ms_auth.service.JwtService;
import com.geopoints.ms_auth.utils.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class JwtServiceImpl implements JwtService {
    @Override
    public String getUserName(String token) {
        return Claim(token,Claims::getSubject);
    }

    @Override
    public String generateToken(UserEntity user) {
        log.info("Generando token....");
        Map<String,Object> claims= allClaimsPersonalization(user);
        claims.put("type", Constants.ACCESS);
        claims.put("id",user.getId());
        claims.put("name",user.getName());
        claims.put("email", user.getEmail());

        //tiempo de vide del token
        Date dateCreation = new Date();
        Date dateExpiration = new Date(dateCreation.getTime() + 6000000);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(dateCreation)
                .setExpiration(dateExpiration)
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username= getUserName(token);
        log.info("Validando token...");
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claim("type", Constants.REFRESH)
                .setClaims(claims != null ? claims : new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+604_800_000))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public boolean ValidateIsRefreshToken(String token) {
        log.info("Validando token refresh...");
        Claims claims = allClaims(token);
        String typeToken = claims.get("type",String.class);
        return Constants.REFRESH.equalsIgnoreCase(typeToken);
    }

    @Override
    public Claims claims(String token) {
        return allClaims(token);
    }

    @Override
    public List<String> getRolesClaims(String token) {
        Claims claims = claims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles;
    }

    //GENERAR CLAVE USER
    private Key getKey(){
        byte[]  key = Decoders.BASE64.decode("clave");
        return Keys.hmacShaKeyFor(key);
    }

    //RETORNAR TODOS LOS CLAIMS USER
    private Claims allClaims(String token){
        return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    //RETORNAR UN CLAIMS EN ESPECIFICO
    private <T> T Claim(String token, Function<Claims, T> claimsTFunction){
        return  claimsTFunction.apply(allClaims(token));
    }

    //PERSONALIZAR DEL TOKEN, CLAINS
    private Map<String,Object> allClaimsPersonalization(UserEntity user){
        Map<String,Object> claimsPersonalization = new HashMap<>();
        claimsPersonalization.put("accountNonExpired",user.isAccountNonExpired());
        claimsPersonalization.put("accountNonLocked", user.isAccountNonLocked());
        claimsPersonalization.put("credentialsNonExpired",user.isCredentialsNonExpired());
        claimsPersonalization.put("enabled", user.isEnabled());
        claimsPersonalization.put("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return claimsPersonalization;
    }

    //TOKEN VALIDADOS
    private boolean isTokenExpired(String token){
        log.info(Constants.EXPIRED_TOKEN);
        return Claim(token, Claims::getExpiration).before(new Date());
    }
}
