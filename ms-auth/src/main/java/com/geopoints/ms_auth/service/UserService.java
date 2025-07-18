package com.geopoints.ms_auth.service;

import com.geopoints.ms_auth.entity.UserEntity;
import com.geopoints.ms_auth.utils.request.UpdateProfile;
import com.geopoints.ms_auth.utils.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService {
    //USERS
    UserDetailsService userDetails();
    UserResponse updateAccount(UpdateProfile UpdateProfile, String token);
    UserResponse deleteAccount(String token );
    UserEntity findById(Long id);
}
