package com.geopoints.ms_auth.utils.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponse<T> {
    private Integer code;
    private String message;
    private T data;
}
