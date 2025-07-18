package com.geopoints.ms_auth.service;

import com.geopoints.ms_auth.utils.request.LoginRequest;
import com.geopoints.ms_auth.utils.request.UserRequest;
import com.geopoints.ms_auth.utils.response.AdminResponse;
import com.geopoints.ms_auth.utils.response.TokenResponse;
import com.geopoints.ms_auth.utils.response.UserResponse;
import com.geopoints.ms_auth.utils.response.ValidateResponse;

public interface AuthenticationService {
    //USERS, ADMIN, SUPERADMIN
    UserResponse signUpUser(UserRequest userRequest);
    TokenResponse signIn(LoginRequest loginRequest);
    UserResponse signUpAdmin(UserRequest userRequest, String token);
    UserResponse signUpSuperAdmin(UserRequest userRequest, String token);
    UserResponse signUpOwner(UserRequest userRequest, String token);
    TokenResponse getTokenByRefresh(String token) throws IllegalAccessException;
    ValidateResponse validateToken(String token);
}
