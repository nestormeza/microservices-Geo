package com.geopoints.ms_auth.service.impl;

import com.geopoints.ms_auth.service.UserAdminService;
import com.geopoints.ms_auth.utils.response.AdminResponse;

import java.util.List;

public class UserAdminServiceImpl implements UserAdminService {
    @Override
    public List<AdminResponse> allUserAdmin(String token) {
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
