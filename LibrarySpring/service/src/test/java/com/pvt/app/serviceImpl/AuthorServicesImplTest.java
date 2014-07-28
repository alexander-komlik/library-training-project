package com.pvt.app.serviceImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.daoImpl.AuthorDaoImpl;
import com.pvt.app.daoImpl.UserDaoImpl;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AuthorServicesImplTest {

    private static MyDao<Author> authorDao;
    private static MyDao<User> userDao;
    private static AuthorServicesImpl authorServices;

    @BeforeClass
    public static void beforeClass() {
        authorServices = new AuthorServicesImpl();
        authorDao = mock(AuthorDaoImpl.class);
        userDao = mock(UserDaoImpl.class);

        authorServices.setAuthorDao(authorDao);
        authorServices.setUserDao(userDao);
    }

    @Test
    public void testCreateAuthorUserNotFound() throws ServiceException {
        User user = new User();
        user.setName("not found");
        Author author = new Author();
        author.setUser(user);
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(userDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList())).thenReturn(new ArrayList<User>());
        when(authorDao.create(any(Author.class))).thenReturn(new Long(42));

        long result = authorServices.createAuthor(author);

        assert (((User)(listCaptor.getValue().get(0))).getName().equals("not found"));
        assert (result == 0);
    }

    @Test
    public void testCreateAuthorUserFound() throws ServiceException {
        User user = new User();
        user.setName("name");
        Author author = new Author();
        author.setUser(user);
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(userDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList())).thenReturn(userList);
        when(authorDao.create(any(Author.class))).thenReturn(new Long(42));

        long result = authorServices.createAuthor(author);

        assert (((User)(listCaptor.getValue().get(0))).getName().equals("name"));
        assert (result == 42);
    }

}
