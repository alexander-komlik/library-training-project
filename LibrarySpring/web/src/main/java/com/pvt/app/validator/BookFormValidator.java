package com.pvt.app.validator;

import com.pvt.app.entity.Book;
import com.pvt.app.util.BookForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BookFormValidator implements Validator {

    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MIN_TITLE_LENGTH = 2;
    private static final int MAX_TEXT_LENGTH = 10000;

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required.title", "Title is required.");

        BookForm book = (BookForm) target;

        if(book.getTitle().length()<MIN_TITLE_LENGTH || book.getTitle().length()>MAX_TEXT_LENGTH)
            errors.rejectValue("title", "incorrect.title", "Title length: "+MIN_TITLE_LENGTH+"-"+MAX_TITLE_LENGTH);

        if(book.getText().getText().length()>MAX_TEXT_LENGTH)
            errors.rejectValue("text.text", "incorrect.text.text", "Max text length: "+MAX_TEXT_LENGTH);

        if((book.getAuthor1()==null || book.getAuthor1().getId() == 0) &&
                (book.getAuthor2()==null || book.getAuthor2().getId() == 0) &&
                (book.getAuthor3()==null || book.getAuthor3().getId() == 0) &&
                (book.getAuthor4()==null || book.getAuthor4().getId() == 0) &&
                (book.getAuthor5()==null || book.getAuthor5().getId() == 0))
            errors.rejectValue("author1", "required.author1", "Book should have at least 1 author");

    }
}
