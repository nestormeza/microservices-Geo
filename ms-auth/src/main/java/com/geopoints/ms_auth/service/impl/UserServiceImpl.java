package com.geopoints.ms_auth.service.impl;

import com.geopoints.ms_auth.service.UserService;
import com.geopoints.ms_auth.utils.request.UpdateProfile;
import com.geopoints.ms_auth.utils.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserServiceImpl implements UserService {
    @Override
    public UserDetailsService userDetails() {
        return null;
    }

    @Override
    public UserResponse updateAccount(UpdateProfile UpdateProfile, String token) {
        return null;
    }

    @Override
    public UserResponse deleteAccount(String token) {
        return null;
    }
}
