package com.geopoints.ms_auth.utils.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidateResponse {
    private String message;
    private Boolean isValid;
    private List<String> roles;
}
