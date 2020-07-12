package com.test.bootapp.controller;

import com.test.bootapp.service.UserDetailServiceImpl;
import com.test.bootapp.authhelper.JWTAuthTokenUtil;
import com.test.bootapp.domain.entities.AppUser;
import com.test.bootapp.domain.entities.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

import static com.test.bootapp.constants.ApplicationConstants.GET_AUTH_TOKEN_FROM_REQUEST;

@RestController
@CrossOrigin
public class AuthController
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTAuthTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    /**
     *
     * @param authenticationRequest  - request having user's username and password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AppUser authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUserName());

        if(Objects.isNull(userDetails))
            return ResponseEntity.ok(new AuthResponse(null, "Invalid Credentials"));

        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println(jwtTokenUtil.getExpirationDateFromToken(token));

        return ResponseEntity.ok(new AuthResponse(token, "User "+userDetails.getUsername()+" Authenticated"));
    }

    private void authenticate(String username, String password) throws Exception {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e)
        {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/regenerateToken/{user}", method = RequestMethod.POST)
    public ResponseEntity<AuthResponse> regenerateToken(HttpServletRequest request, @PathVariable String user)
    {

        String requestHeader = request.getHeader(GET_AUTH_TOKEN_FROM_REQUEST);

        String token = jwtTokenUtil.getToken(requestHeader);

        if(!jwtTokenUtil.isTokenExpired(token))
            token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(user));

        return ResponseEntity.ok(new AuthResponse(token, "Token re-generated successfully for user "+user));
    }
}
