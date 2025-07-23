package com.geopoints.ms_auth.service.impl;

import com.geopoints.ms_auth.entity.UserEntity;
import com.geopoints.ms_auth.exception.GlobalException;
import com.geopoints.ms_auth.repository.UserRepository;
import com.geopoints.ms_auth.service.AuthenticationService;
import com.geopoints.ms_auth.service.JwtService;
import com.geopoints.ms_auth.service.UserAdminService;
import com.geopoints.ms_auth.utils.response.AdminResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;


    @Override
    public List<AdminResponse> allUserAdmin(String token) {
        authenticationService.validateToken(token);
        try {
            List<String> roles = jwtService.getRolesClaims(token);
            if(roles.contains("SUPERADMIN")){
                List<UserEntity> users = userRepository.findAll();

                return users.stream().map(this::);

            }else{
                throw new GlobalException(HttpStatus.UNAUTHORIZED.value(), "No tiene permisos")
            }

        }catch (Exception ex){
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error al obtener el listado de usuarios");
        }
        return List.of();
    }

    @Override
    public AdminResponse deleteUserAdmin(int id, String token) {
        return null;
    }

    @Override
    public AdminResponse updateAdmin(AdminResponse adminResponse, String token) {
        return null;
    }
}
