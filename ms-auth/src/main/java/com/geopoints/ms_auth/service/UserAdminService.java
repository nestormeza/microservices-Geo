package com.geopoints.ms_auth.service;

import com.geopoints.ms_auth.utils.response.AdminResponse;

import java.util.List;

public interface UserAdminService {
    //ADMIN
    List<AdminResponse> allUserAdmin(String token);
    AdminResponse deleteUserAdmin(int id, String token);
    AdminResponse updateAdmin(AdminResponse adminResponse,String token);
}
