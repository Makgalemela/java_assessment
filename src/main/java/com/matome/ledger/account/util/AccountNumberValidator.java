package com.matome.ledger.account.util;


import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AccountNumberValidator implements ConstraintValidator<ACCOUNT, String> {

    Pattern account_number_pattern;

    @Override
    public void initialize(ACCOUNT constraintAnnotation) {
        account_number_pattern = Pattern.compile("^[0-9]{1,10}$");
    }

    @Override
    public boolean isValid(String msisdn, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = account_number_pattern.matcher(msisdn);
        return matcher.matches();
    }
}
