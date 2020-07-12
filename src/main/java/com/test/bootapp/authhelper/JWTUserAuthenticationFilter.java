package com.test.bootapp.authhelper;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import static com.test.bootapp.constants.ApplicationConstants.INVALID_REQUEST;
import static com.test.bootapp.constants.ApplicationConstants.TOKEN_EXPIRED;

/**
 * Class that is the entry point for authentication,
 * It rejects every unauthenticated request
 *
 */
@Component
public class JWTUserAuthenticationFilter implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        if(Objects.nonNull(request.getAttribute(INVALID_REQUEST)))
        {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, (String) request.getAttribute(INVALID_REQUEST));
        }
        else if(Objects.nonNull(request.getAttribute(TOKEN_EXPIRED)))
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, (String) request.getAttribute(TOKEN_EXPIRED));
        }
        else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}