package com.pvt.app.securityServices;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
public class RoleManager {

    private String userName;
    private boolean isGuest;
    private boolean isUser;
    private boolean isModerator;
    private boolean isAdmin;

    public RoleManager() {}

    public RoleManager(HttpServletRequest req) {
        Principal principal = req.getUserPrincipal();
        if(principal == null) {
            userName = "guest" ;
        } else  {
            userName = principal.getName();
        }
        isUser = req.isUserInRole("ROLE_USER");
        isModerator = req.isUserInRole("ROLE_MODERATOR");
        isAdmin = req.isUserInRole("ROLE_ADMIN");
        isGuest = (!isUser && !isModerator && !isAdmin);
    }

    public String getUserName() {
        return userName;
    }

    public boolean getIsGuest() {
        return isGuest;
    }

    public boolean getIsUser() {
        return isUser;
    }

    public boolean getIsModerator() {
        return isModerator;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean checkAccessToBook(long id, MyDao<Book> bookDao){
        if(isAdmin || isModerator)
            return true;
        if(!isUser)
            return false;
        Book book = null;
        try {
            book = bookDao.read(id);
        } catch (Exception e) {
            return false;
        }
        if(book.getId() == 0 || book.getUser() == null)
            return false;
        return book.getUser().getName().equals(userName);
    }

    public boolean checkAccessToAuthor(long id, MyDao<Author> authorDao){
        if(isAdmin || isModerator)
            return true;
        if(!isUser)
            return false;

        Author author = null;
        try {
            author = authorDao.read(id);
        } catch (Exception e) {
            return false;
        }
        if(author.getId() == 0 || author.getUser().getName() == null)
            return false;
        return author.getUser().getName().equals(userName);
    }
}
