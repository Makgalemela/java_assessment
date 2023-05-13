package com.matome.ledger.account.util;


import org.springframework.messaging.handler.annotation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountNumberValidator.class)
public @interface ACCOUNT {
    String message() default "Account number must be length 10 with all numerical characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
