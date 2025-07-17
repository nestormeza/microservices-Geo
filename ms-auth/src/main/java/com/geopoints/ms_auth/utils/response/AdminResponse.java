package com.geopoints.ms_auth.utils.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AdminResponse {
    private int userId;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String city;
    private RolResponse rol;
}
