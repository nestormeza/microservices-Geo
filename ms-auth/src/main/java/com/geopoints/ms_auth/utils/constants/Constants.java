package com.geopoints.ms_auth.utils.constants;

public class Constants {
    public static final String  EXPIRED_TOKEN="Verificando si el token a expirado..";

    public static final String REFRESH = "refresh";
    public static final String ACCESS = "access";
    public static final String AUTHORIZATION = "Authorization";
    public static final String[] ENDPOINTS_PERMIT = {"/api/authentication/v1/**","/actuador/**"};
    public static final String ENDPOINTS_USER= "/api/user/v1/**";
    public static final String ENDPOINTS_ADMIN = "/api/admin/v1/**";

    public static final Boolean STATUS_ACTIVE = true;


    public static final String TOKEN_REFRESH_400 = "El token proporcionado no es un refresh token válido";
    public static final String TOKEN_INVALIDATE = "El token proporcionado no es valido";
    public static final String TOKEN_VALIDATE = "Token valido";
    public static final String NO_FUND = "no encontrado";
}
