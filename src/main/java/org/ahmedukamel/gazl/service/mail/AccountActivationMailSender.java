package org.ahmedukamel.gazl.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.ApiConstant;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.model.AccountActivationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AccountActivationMailSender {
    final TemplateEngine templateEngine;
    final JavaMailSender javaMailSender;
    final MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(AccountActivationMailSender.class);

    @Value(value = "${application.mail.username}")
    private String from;

    public void send(AccountActivationToken token) {
        try {
            Context context = new Context();
            context.setVariable("name", token.getUser().getName());
            context.setVariable("link", ApiConstant.ACCOUNT_ACTIVATION_API_URL
                    .formatted(token.getId(), token.getUser().getEmail()));
            context.setVariable("expiration", token.getExpiration()
                    .format(DateTimeFormatter.ofPattern("E, d MMM yyyy HH:mm")));
            String text = templateEngine.process("account-activation-mail", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(from);
            messageHelper.setTo(token.getUser().getEmail());
            messageHelper.setText(text, true);

            String subject = messageSource.getMessage("org.ahmedukamel.gazl.service.mail.AccountActivationMailSender.send.subject",
                    null, LocaleConstants.ARABIC);
            messageHelper.setSubject(subject);

            javaMailSender.send(message);
        } catch (MessagingException exception) {
            logger.error("Sending Account Activation Email Failed: %s".formatted(exception.getMessage()));
        }
    }
}