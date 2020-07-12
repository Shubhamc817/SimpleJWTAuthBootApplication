package com.test.bootapp.authhelper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.test.bootapp.constants.ApplicationConstants.SECRET;

@Component
public class JWTAuthTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;


    //Defaulted to 5 minutes, configurable through application.properties
    /*
     * Could have been configured by passing it in http header, but comes into mind later, So avoiding
     */
    @Value("${jwt.token.expiry:300")
    public static long JWT_TOKEN_VALIDITY = 300; // 5 minutes

        //retrieve username from jwt token
        public String getUsernameFromToken(String token) {
            return getClaimFromToken(token, Claims::getSubject);
        }

        //retrieve expiration date from jwt token
        public Date getExpirationDateFromToken(String token) {
            return getClaimFromToken(token, Claims::getExpiration);
        }

        public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        }
        //Retrieve any information aka claims from token
        private Claims getAllClaimsFromToken(String token) {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        }

        //check if the token has expired
        public Boolean isTokenExpired(String token) {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }

        //generate token for user
        public String generateToken(UserDetails userDetails) {
            Map<String, Object> claims = new HashMap<>();
            return doGenerateToken(claims, userDetails.getUsername());
        }

        private String doGenerateToken(Map<String, Object> claims, String subject) {

            //System.out.println("Token will expire in " + JWT_TOKEN_VALIDITY +"seconds");
            return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                    .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        }

        //validate token
        public Boolean validateToken(String token, UserDetails userDetails) {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        public  String getToken(String requestTokenHeader)
        {
            String token = null;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
            {
                token = requestTokenHeader.substring(7);
            }
            return token;

        }

}
