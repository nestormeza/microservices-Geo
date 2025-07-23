package com.geopoints.ms_auth.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    private  int code;

    //EXCEPTION GENERA MESAJE Y CODIGO
    public GlobalException (int code,String message){
        super(message);
        this.code = code;
    }

    //EXCEPTION CON CAUSA
    public GlobalException(int code,String message,Throwable cause){
        super(message,cause);
        this.code=code;
    }

}
