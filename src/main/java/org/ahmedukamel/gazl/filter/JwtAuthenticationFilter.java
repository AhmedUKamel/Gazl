package org.ahmedukamel.gazl.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.JwtConstants;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.dto.api.ApiResponse;
import org.ahmedukamel.gazl.model.BearerToken;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.repository.BearerTokenRepository;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.ahmedukamel.gazl.service.token.BearerTokenService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final BearerTokenRepository bearerTokenRepository;
    final BearerTokenService bearerTokenService;
    final DatabaseService databaseService;
    final MessageSource messageSource;
    final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isValidHeader(authorizationHeader)) {

            try {

                final String token = extractToken(authorizationHeader);

                if (bearerTokenService.isValidToken(token)) {
                    BearerToken bearerToken = databaseService.get(
                            bearerTokenRepository::findByToken, token, BearerToken.class
                    );

                    if (bearerToken.isRevoked()) {
                        throw new IllegalStateException();
                    }

                    User user = bearerToken.getUser();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, user.getAuthorities()
                            );
                    authentication.setDetails(new WebAuthenticationDetails(request));

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authentication);
                    SecurityContextHolder.setContext(securityContext);
                }

            } catch (ExpiredJwtException exception) {
                String message = messageSource.getMessage("org.ahmedukamel.gazl.filter.JwtAuthenticationFilter.doFilterInternal.ExpiredJwtException", null, LocaleConstants.ARABIC);
                sendError(response, message);
                return;

            } catch (MalformedJwtException exception) {
                String message = messageSource.getMessage("org.ahmedukamel.gazl.filter.JwtAuthenticationFilter.doFilterInternal.MalformedJwtException", null, LocaleConstants.ARABIC);
                sendError(response, message);
                return;

            } catch (SignatureException exception) {
                String message = messageSource.getMessage("org.ahmedukamel.gazl.filter.JwtAuthenticationFilter.doFilterInternal.SignatureException", null, LocaleConstants.ARABIC);
                sendError(response, message);
                return;

            } catch (IllegalStateException exception) {
                String message = messageSource.getMessage("org.ahmedukamel.gazl.filter.JwtAuthenticationFilter.doFilterInternal.IllegalStateException", null, LocaleConstants.ARABIC);
                sendError(response, message);
                return;

            } catch (Exception exception) {
                String message = messageSource.getMessage("org.ahmedukamel.gazl.filter.JwtAuthenticationFilter.doFilterInternal.Exception", null, LocaleConstants.ARABIC);
                sendError(response, message);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), new ApiResponse(false, message, ""));
    }

    private boolean isValidHeader(String authorizationHeader) {
        return Objects.nonNull(authorizationHeader) &&
               authorizationHeader.startsWith(JwtConstants.HEADER_PREFIX);
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(JwtConstants.HEADER_PREFIX.length());
    }
}