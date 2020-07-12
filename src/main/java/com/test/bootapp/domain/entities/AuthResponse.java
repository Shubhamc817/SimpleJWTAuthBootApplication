package com.test.bootapp.domain.entities;

import java.io.Serializable;

public class AuthResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;

    private String message;

    public String getMessage() {
        return message;
    }

    public AuthResponse(String jwttoken, String message)
    {
        this.jwttoken = jwttoken;
        this.message = message;
    }

    public String getToken()
    {
        return this.jwttoken;
    }
}

