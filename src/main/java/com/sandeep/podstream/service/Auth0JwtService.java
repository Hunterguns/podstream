package com.sandeep.podstream.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sandeep.podstream.core.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class Auth0JwtService {

    @Value("${security.jwt.issuer}")
    private static String issuer;

    @Value("${security.jwt.secret-key}")
    private static String secret;

    @Value("${security.jwt.expiration-time}")
    private static String expirationTimeInMillis;

    @Value("${security.jwt.subject")
    private static String subject;


    private static Algorithm algorithm;
    public static JWTVerifier jwtVerifier;

    public static void initialize() {
        algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public String generateJwtToken(UserEntity userEntity) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withClaim("user_type", userEntity.getUserType())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public DecodedJWT verifyJwtToken(String jwtToken) throws AuthenticationException {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            return decodedJWT;
        } catch (JWTVerificationException e) {
            log.error("Error while verifying jwt token");
            throw new AuthenticationException("JWT Token is invalid.");
        }
    }

    public boolean isJwtExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.getTime() < System.currentTimeMillis();
    }

    public Map<String, Claim> getAllJwtClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

    public String getJwtClaimValue(DecodedJWT decodedJWT, String claimName) {
        Claim claim = decodedJWT.getClaim(claimName);
        return claim != null ? claim.asString() : null;
    }
}
