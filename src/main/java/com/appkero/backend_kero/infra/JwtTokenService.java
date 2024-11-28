package com.appkero.backend_kero.infra;

import com.appkero.backend_kero.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access-token.expiration-ms}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refresh-token.expiration-ms}")
    private Long REFRESH_TOKEN_EXPIRATION;

    private final Set<String> blackList = new HashSet<>();

    public String generateAccessToken(Usuario user) {
        return JWT.create()
                .withIssuer("kero-api")
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withExpiresAt(genExpirationDate(ACCESS_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String generateRefreshToken(Usuario user) {
        return JWT.create()
                .withIssuer("kero-api")
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withExpiresAt(genExpirationDate(REFRESH_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer("kero-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Invalid oe expired Token...");
        }
    }

    private Date genExpirationDate(long seconds) {
        return Date.from(Instant.now().plusSeconds(seconds));
    }

    public String generatePasswordResetToken(String email) {
        return JWT.create()
            .withSubject(email)
            .withIssuedAt(new Date())
            .withExpiresAt(genExpirationDate(REFRESH_TOKEN_EXPIRATION))
            .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .withIssuer("kero-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido ou expirado");
        }
    }

    public String extractEmailFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        return decodedJWT.getSubject();
    }

    public void blackListToken(String token) {
        blackList.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blackList.contains(token);
    }

}
