package org.ahmedukamel.gazl.service.account;

import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public interface IAccountWebService {
    ModelAndView activateAccount(UUID id, String email);
}