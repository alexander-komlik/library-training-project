package com.pvt.app.serviceImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.daoImpl.BookDaoImpl;
import com.pvt.app.daoImpl.UserDaoImpl;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.service.BookServices;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BookServicesImplTest {

    private static MyDao<Book> bookDao;
    private static MyDao<User> userDao;
    private static BookServicesImpl bookServices;

    @BeforeClass
    public static void beforeClass() {
        bookServices = new BookServicesImpl();
        bookDao = mock(BookDaoImpl.class);
        userDao = mock(UserDaoImpl.class);

        bookServices.setBookDao(bookDao);
        bookServices.setUserDao(userDao);
    }

    @Test
    public void testCreateBookUserNotFound() throws ServiceException {
        User user = new User();
        user.setName("not found");
        Book book = new Book();
        book.setUser(user);
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(userDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList())).thenReturn(new ArrayList<User>());
        when(bookDao.create(any(Book.class))).thenReturn(new Long(42));

        long result = bookServices.createBook(book);

        assert (((User)(listCaptor.getValue().get(0))).getName().equals("not found"));
        assert (result == 0);
    }

    @Test
    public void testCreateBookUserFound() throws ServiceException {
        User user = new User();
        user.setName("name");
        Book book = new Book();
        book.setUser(user);
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(userDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList())).thenReturn(userList);
        when(bookDao.create(any(Book.class))).thenReturn(new Long(42));

        long result = bookServices.createBook(book);

        assert (((User)(listCaptor.getValue().get(0))).getName().equals("name"));
        assert (result == 42);
    }
}
