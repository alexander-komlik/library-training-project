package com.pvt.app.validator;

import com.pvt.app.entity.AccessData;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessDataValidator implements Validator {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);
    private static final int MAX_NAME_LENGTH = 40;
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_PASS_LENGTH = 40;
    private static final int MIN_PASS_LENGTH = 3;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccessData.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.name", "required.user.name", "Name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.email", "required.user.email", "Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pass", "required.pass", "Password is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPass", "required.confirmPass", "Password confirm is required");

        AccessData accessData = (AccessData) target;

        if(accessData.getUser().getName().length()>MAX_NAME_LENGTH ||
                accessData.getUser().getName().length()<MIN_NAME_LENGTH)
            errors.rejectValue("user.name", "incorrect.user.name", "Name length: "+MIN_NAME_LENGTH+"-"+MAX_NAME_LENGTH);

        if(accessData.getPass().length()>MAX_PASS_LENGTH ||
                accessData.getPass().length()<MIN_PASS_LENGTH)
            errors.rejectValue("pass", "incorrect.pass", "Password length: "+MIN_PASS_LENGTH+"-"+MAX_PASS_LENGTH);

        if(!accessData.getPass().equals(accessData.getConfirmPass()))
            errors.rejectValue("confirmPass", "notmatch.confirmPass", "Should be same as password");

        Matcher matcher = PATTERN.matcher(accessData.getUser().getEmail());
        if(!matcher.matches())
            errors.rejectValue("user.email", "incorrect.user.email", "Incorrect email");
    }
}
