package com.pvt.app.securityServices;

import com.pvt.app.dao.MyDao;
import com.pvt.app.daoImpl.AuthorDaoImpl;
import com.pvt.app.daoImpl.BookDaoImpl;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.User;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;

import static org.mockito.Mockito.*;

public class OwnershipCheckTest {

    private static MyDao<Book> bookDao;
    private static MyDao<Author> authorDao;
    private static org.springframework.security.core.userdetails.User principal;

    private static String ownerName = "owner";
    private static String notOwnerName = "notOwner";
    private static User owner;
    private static User notOwner;
    private static long ownedId = 1;
    private static long notOwnedId = 2;
    private static Book ownedBook;
    private static Book notOwnedBook;
    private static Author ownedAuthor;
    private static Author notOwnedAuthor;

    private static OwnershipCheck ownershipCheck;

    @BeforeClass
    public static void beforeClass() {
        owner = new User();
        owner.setName(ownerName);
        notOwner = new User();
        notOwner.setName(notOwnerName);

        ownedBook = new Book();
        ownedBook.setUser(owner);
        ownedBook.setId(42);
        notOwnedBook = new Book();
        notOwnedBook.setUser(notOwner);
        notOwnedBook.setId(42);

        ownedAuthor = new Author();
        ownedAuthor.setUser(owner);
        ownedAuthor.setId(42);
        notOwnedAuthor = new Author();
        notOwnedAuthor.setUser(notOwner);
        notOwnedAuthor.setId(42);

        bookDao = mock(BookDaoImpl.class);
        when(bookDao.read(ownedId)).thenReturn(ownedBook);
        when(bookDao.read(notOwnedId)).thenReturn(notOwnedBook);

        authorDao = mock(AuthorDaoImpl.class);
        when(authorDao.read(ownedId)).thenReturn(ownedAuthor);
        when(authorDao.read(notOwnedId)).thenReturn(notOwnedAuthor);

        principal = new org.springframework.security.core.userdetails.User(ownerName, "", new HashSet<GrantedAuthority>());

        ownershipCheck = new OwnershipCheck();
        ownershipCheck.setBookDao(bookDao);
        ownershipCheck.setAuthorDao(authorDao);

    }

    @Test
    public void isBookOwnerSuccess() {
        boolean result = ownershipCheck.isBookOwner(ownedId, principal);
        System.out.println(result);
        assert (result == true);
    }

    @Test
    public void isBookOwnerFail() {
        boolean result = ownershipCheck.isBookOwner(notOwnedId, principal);
        assert (result == false);
    }

    @Test
    public void isAuthorOwnerSuccess() {
        boolean result = ownershipCheck.isAuthorOwner(ownedId, principal);
        assert (result == true);
    }

    @Test
    public void isAuthorOwnerFail() {
        boolean result = ownershipCheck.isAuthorOwner(notOwnedId, principal);
        assert (result == false);
    }

}
