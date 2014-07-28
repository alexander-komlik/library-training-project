package com.pvt.app.validator;

import com.pvt.app.entity.Author;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AuthorValidator implements Validator {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME4TABLE_LENGTH = 20;
    private static final int MIN_NAME4TABLE_LENGTH = 2;
    private static final int MAX_DESCRIPTION_LENGTH = 2000;

    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name", "Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name4table", "required.name4table", "Name4table is required.");

        Author author = (Author) target;

        if(author.getName().length()>MAX_NAME_LENGTH || author.getName().length()<MIN_NAME_LENGTH)
            errors.rejectValue("name", "incorrect.name", "Name length: "+MIN_NAME_LENGTH+"-"+MAX_NAME_LENGTH);

        if(author.getName4table().length()>MAX_NAME4TABLE_LENGTH || author.getName4table().length()<MIN_NAME4TABLE_LENGTH)
            errors.rejectValue("name4table", "incorrect.name", "Name4table length: "+MIN_NAME4TABLE_LENGTH+"-"+MAX_NAME4TABLE_LENGTH);

        if(author.getText().getText().length()>MAX_DESCRIPTION_LENGTH)
            errors.rejectValue("text.text", "incorrect.text.text", "Max description length: "+MAX_DESCRIPTION_LENGTH);
    }
}
