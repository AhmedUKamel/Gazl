package org.ahmedukamel.gazl.service.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.JwtConstants;
import org.ahmedukamel.gazl.model.BearerToken;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.repository.BearerTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BearerTokenService {
    @Value(value = "${application.security.access-token.secret-key}")
    private String secretKey;

    @Value(value = "${application.security.access-token.expiration}")
    private long expiration;

    final BearerTokenRepository bearerTokenRepository;

    public BearerToken getBearerToken(User user) {
        final String token = generateToken(user);

        BearerToken bearerToken = new BearerToken();
        bearerToken.setToken(token);
        bearerToken.setUser(user);
        bearerToken.setExpiration(extractClaim(token, Claims::getExpiration));

        return bearerTokenRepository.save(bearerToken);
    }

    public boolean isValidToken(String token) {
        return extractClaim(token, Claims::getExpiration)
                .after(new Date(System.currentTimeMillis()));
    }

    private String generateToken(User user) {
        long currentTime = System.currentTimeMillis();
        return Jwts
                .builder()
                .setClaims(Map.of(JwtConstants.ROLE_CLAIM, user.getRole()))
                .setIssuer(JwtConstants.ISSUER)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }
}