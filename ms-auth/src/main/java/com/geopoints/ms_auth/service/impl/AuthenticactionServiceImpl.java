package com.geopoints.ms_auth.service.impl;

import com.geopoints.ms_auth.entity.RolEntity;
import com.geopoints.ms_auth.entity.UserEntity;
import com.geopoints.ms_auth.repository.RolRepository;
import com.geopoints.ms_auth.repository.UserRepository;
import com.geopoints.ms_auth.service.AuthenticationService;
import com.geopoints.ms_auth.service.JwtService;
import com.geopoints.ms_auth.service.UserService;
import com.geopoints.ms_auth.utils.constants.Constants;
import com.geopoints.ms_auth.utils.constants.Role;
import com.geopoints.ms_auth.utils.request.LoginRequest;
import com.geopoints.ms_auth.utils.request.UserRequest;
import com.geopoints.ms_auth.utils.response.AdminResponse;
import com.geopoints.ms_auth.utils.response.TokenResponse;
import com.geopoints.ms_auth.utils.response.UserResponse;
import com.geopoints.ms_auth.utils.response.ValidateResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticactionServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public TokenResponse signIn(LoginRequest loginRequest) {
        //VALIDAR USUARIO
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        UserEntity user=  validateEmail(loginRequest.getEmail());

        //GENERAR TOKEN Y REFRESH TOKEN
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        return TokenResponse.builder().token(token).refreshToken(refreshToken).build();
    }

    @Override
    public UserResponse signUpUser(UserRequest userRequest) {
        return registerUser(userRequest,Set.of("USER"),1L);
    }

    @Override
    public UserResponse signUpAdmin(UserRequest userRequest, String token) {
        return registerUser(userRequest,Set.of("ADMIN"),1L);
    }

    @Override
    public UserResponse signUpSuperAdmin(UserRequest userRequest, String token) {
        return registerUser(userRequest,Set.of("ADMIN","SUPERADMIN"),1L);
    }

    @Override
    public UserResponse signUpOwner(UserRequest userRequest, String token) {
        return registerUser(userRequest,Set.of("ADMIN","SUPERADMIN","OWNER"),1L);
    }

    @Override
    public TokenResponse getTokenByRefresh(String token) throws IllegalAccessException {
        return null;
    }

    @Override
    public ValidateResponse validateToken(String token) {
        try {
            Claims claims = jwtService.claims(token);
            List<String> roles = claims.get("roles",List.class);

            String username= jwtService.getUserName(token);
            UserDetails userDetails = userService.userDetails().loadUserByUsername(username);
            boolean result = jwtService.validateToken(token,userDetails) && !jwtService.ValidateIsRefreshToken(token);

            return new ValidateResponse("El token es valido",result);
        }catch (Exception ex){
            throw new RuntimeException("ERROR");
        }
    }



    //METODOS DE APOYO
    //SABER SI EXISTE EL EMAIL EN LA BASE DE DATOS
    private UserEntity validateEmail(String email){
        log.info("Buscando usuario...");
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Error"));
    };

    //SABER SI EXISTE EL NOMBRE DE USUARIO EN LA BASE DE DATOS
    private UserEntity validateUserName(String username){
        return userRepository.findByUserName(username).orElseThrow(()->new RuntimeException("Error"));
    }

    //OBTENER EL ROL
    private RolEntity getRol(Role rol){
        return rolRepository.findByName(rol.name()).orElseThrow(()->new RuntimeException("Error"));
    }

    //ASIGNACION DE ROLES AL USUARIO
    private Set<RolEntity> assignedRoles(Set<String> roles){
        Set<RolEntity> rolesAssigned = new HashSet<>();
        for (String rol : roles){
            try {
                Role roleEnum = Role.valueOf(rol.toLowerCase());
                rolesAssigned.add(getRol(roleEnum));
            }catch (Exception ex){
                throw new RuntimeException("Error");
            }
        }
        return rolesAssigned;
    }

    //CONVERTIR A USER ENTITY
    private UserEntity convertUserEntity(UserRequest userRequest){
        return UserEntity.builder()
                .name(userRequest.getName())
                .lastName(userRequest.getLastname())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .isAccountNonExpired(Constants.STATUS_ACTIVE)
                .isAccountNonLocked(Constants.STATUS_ACTIVE)
                .isCredentialsNonExpired(Constants.STATUS_ACTIVE)
                .isEnabled(Constants.STATUS_ACTIVE)
                .build();
    }

    //CONVERTIR A RESPONSE USER
    private UserResponse convertUserResponse(UserEntity userEntity){
        return  UserResponse.builder()
                .userId(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .Rol(userEntity.getRoles().toString())
                .city(userEntity.getCiudadId())
                .build();
    }

    //REGISTRAR UN USUARIO
    private UserResponse registerUser(UserRequest user, Set<String> roles,Long city){
        //VALIDAR CON DATOS EXISTENTES
        validateEmail(user.getEmail());
        validateUserName(user.getUsername());

        //OBTENER LOS ROLES DE BASE DED DATOS
        Set<RolEntity> rolesAssigned = assignedRoles(roles);

        try {
            log.info("Registrando usuario");

            UserEntity userRegister = convertUserEntity(user);
            userRegister.setRoles(rolesAssigned);
            userRegister.setCiudadId(city);
            userRepository.save(userRegister);

            return  convertUserResponse(userRegister);
        }catch (Exception ex){
            throw new RuntimeException("Error");
        }
    }


}
