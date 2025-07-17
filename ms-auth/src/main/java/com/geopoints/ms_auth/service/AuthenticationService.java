package com.geopoints.ms_auth.service;

import com.geopoints.ms_auth.utils.request.LoginRequest;
import com.geopoints.ms_auth.utils.request.UserRequest;
import com.geopoints.ms_auth.utils.response.TokenResponse;
import com.geopoints.ms_auth.utils.response.UserResponse;
import com.geopoints.ms_auth.utils.response.ValidateResponse;

import java.util.List;

public interface AuthenticationService {
    //USERS, ADMIN, SUPERADMIN
    UserResponse signUpUser(UserRequest userRequest);
    TokenResponse signIn(LoginRequest loginRequest);
    TokenResponse getTokenByRefresh(String token) throws IllegalAccessException;
    ValidateResponse validateToken(String token);
}
