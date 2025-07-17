package com.geopoints.ms_auth.utils.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String token;
    private String refreshToken;
}
