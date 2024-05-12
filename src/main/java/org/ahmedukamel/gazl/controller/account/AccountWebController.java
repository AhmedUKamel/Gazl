package org.ahmedukamel.gazl.controller.account;

import org.ahmedukamel.gazl.service.account.AccountWebService;
import org.ahmedukamel.gazl.service.account.IAccountWebService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping(value = "api/v1/public/account")
public class AccountWebController {
    private final IAccountWebService service;

    public AccountWebController(AccountWebService service) {
        this.service = service;
    }

    @GetMapping(value = "activate")
    public ModelAndView activateAccount(@RequestParam(value = "token") UUID id,
                                        @RequestParam(value = "email") String email) {
        return service.activateAccount(id, email);
    }
}