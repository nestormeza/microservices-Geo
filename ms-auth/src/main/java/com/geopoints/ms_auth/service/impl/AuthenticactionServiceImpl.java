package com.geopoints.ms_auth.service.impl;

import com.geopoints.ms_auth.repository.RolRepository;
import com.geopoints.ms_auth.repository.UserRepository;
import com.geopoints.ms_auth.service.AuthenticationService;
import com.geopoints.ms_auth.service.JwtService;
import com.geopoints.ms_auth.service.UserService;
import com.geopoints.ms_auth.utils.request.LoginRequest;
import com.geopoints.ms_auth.utils.request.UserRequest;
import com.geopoints.ms_auth.utils.response.TokenResponse;
import com.geopoints.ms_auth.utils.response.UserResponse;
import com.geopoints.ms_auth.utils.response.ValidateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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
    public UserResponse signUpUser(UserRequest userRequest) {

        return null;
    }

    @Override
    public TokenResponse signIn(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        return null;
    }

    @Override
    public TokenResponse getTokenByRefresh(String token) throws IllegalAccessException {
        return null;
    }

    @Override
    public ValidateResponse validateToken(String token) {
        return null;
    }

}
