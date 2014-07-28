package com.pvt.app.securityServices;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component("ownershipCheck")
public class OwnershipCheck {

    private MyDao<Book> bookDao;
    private MyDao<Author> authorDao;

    @Autowired
    @Qualifier("bookDao")
    public void setBookDao(MyDao<Book> bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
    @Qualifier("authorDao")
    public void setAuthorDao(MyDao<Author> authorDao) {
        this.authorDao = authorDao;
    }

    public boolean isBookOwner(long bookId, Object principal) {
        String currentUserName = ((User) principal).getUsername();
        Book book = bookDao.read(bookId);
        if(book.getId() != 0) {
            String ownerName = book.getUser().getName();
            if(currentUserName.equals(ownerName))
                return true;
        }

        return false;
    }

    public boolean isAuthorOwner(long authorId, Object principal) {
        String currentUserName = ((User) principal).getUsername();
        Author author = authorDao.read(authorId);
        if(author.getId() != 0) {
            String ownerName = author.getUser().getName();
            if(currentUserName.equals(ownerName))
                return true;
        }

        return false;
    }


}