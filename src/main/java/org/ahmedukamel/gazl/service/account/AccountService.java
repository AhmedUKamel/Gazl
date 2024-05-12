package org.ahmedukamel.gazl.service.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    @Override
    public Object resendAccountActivationMail(String email) {
        return null;
    }

    @Override
    public Object forgotPassword(String email) {
        return null;
    }

    @Override
    public Object checkForgetPasswordToken(UUID id, String email) {
        return null;
    }

    @Override
    public Object resetPassword(UUID id, String email, String password) {
        return null;
    }
}