package org.ahmedukamel.gazl.service.account;

import java.util.UUID;

public interface IAccountService {
    Object resendAccountActivationMail(String email);

    Object forgotPassword(String email);

    Object checkForgetPasswordToken(UUID id, String email);

    Object resetPassword(UUID id, String email, String password);
}