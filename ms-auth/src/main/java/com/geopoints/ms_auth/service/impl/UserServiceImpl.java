package com.geopoints.ms_auth.service.impl;

import com.geopoints.ms_auth.entity.UserEntity;
import com.geopoints.ms_auth.repository.UserRepository;
import com.geopoints.ms_auth.service.UserService;
import com.geopoints.ms_auth.utils.request.UpdateProfile;
import com.geopoints.ms_auth.utils.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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

    @Override
    public UserEntity findById(Long id) {
        log.info("Buscando usuario...");
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("error"));
    }


}
