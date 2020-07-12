package com.test.bootapp.constants;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationConstants
{

    public static String SECRET="SecretKeyToGenJWT";

    public static final String AUTH_URL = "/authenticate";

    public static final String SIGN_UP_URL = "/sign-up";

    public static final String INVALID_REQUEST = "InvalidRequest";

    public static final String TOKEN_EXPIRED = "TokenExpired";

    public static final String GET_AUTH_TOKEN_FROM_REQUEST = "Authorization";

}
