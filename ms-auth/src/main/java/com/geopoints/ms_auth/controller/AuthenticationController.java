package com.geopoints.ms_auth.controller;

import com.geopoints.ms_auth.service.AuthenticationService;
import com.geopoints.ms_auth.utils.request.LoginRequest;
import com.geopoints.ms_auth.utils.response.StandardResponse;
import com.geopoints.ms_auth.utils.response.TokenResponse;
import com.geopoints.ms_auth.utils.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geopoints/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/user/login")
    public ResponseEntity<StandardResponse<TokenResponse>> SingInUser(@RequestBody LoginRequest loginRequest){
        TokenResponse token=authenticationService.signIn(loginRequest);
        return new ResponseEntity<>(new StandardResponse<>(200,"Usuario logeado correctamente",token));
    }
}
