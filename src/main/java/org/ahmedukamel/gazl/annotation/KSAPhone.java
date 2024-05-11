package org.ahmedukamel.gazl.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ahmedukamel.gazl.validator.KSAPhoneValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Constraint(validatedBy = KSAPhoneValidator.class)
public @interface KSAPhone {
    String message() default "Invalid KSA phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}