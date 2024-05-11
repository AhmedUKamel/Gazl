package org.ahmedukamel.gazl.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SessionManagementCustomizer implements Customizer<SessionManagementConfigurer<HttpSecurity>> {
    @Override
    public void customize(SessionManagementConfigurer<HttpSecurity> configurer) {
        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}