package com.geopoints.ms_auth.mapper;

import com.geopoints.ms_auth.entity.UserEntity;
import com.geopoints.ms_auth.utils.constants.Constants;
import com.geopoints.ms_auth.utils.constants.Role;
import com.geopoints.ms_auth.utils.request.UserRequest;
import com.geopoints.ms_auth.utils.response.UserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Collectors;

public class UserMapper {
    private final BCryptPasswordEncoder passwordEncoder;

    public UserMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //CONVERTIR A USER ENTITY
    public UserEntity convertUserEntity(UserRequest userRequest){
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
    public UserResponse convertUserResponse(UserEntity userEntity){
        return  UserResponse.builder()
                .userId(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .Rol(userEntity.getRoles().stream()
                        .map(Role::name)  // convierte de Role enum a String
                        .collect(Collectors.toList()))
                .city(userEntity.getCiudadId())
                .build();
    }
}
