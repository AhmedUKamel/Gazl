package org.ahmedukamel.gazl.service.auth;

public interface IAuthService {
    Object register(Object object);

    Object login(String username, String password);

    Object logout(String accessToken);
}