package com.pvt.app.serviceImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.daoImpl.BookDaoImpl;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.util.OrderManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BookListServiceImplTest {

    public static MyDao<Book> bookDao;
    public static BookListServicesImpl bookListServices;
    public static String order = "asc_id";
    public static List<Book> outList;
    public static long outBookId = 42;
    public static long outPageNumber = 42;

    @BeforeClass
    public static void beforeClass() {

        bookListServices = new BookListServicesImpl();
        bookDao = mock(BookDaoImpl.class);
        outList = new ArrayList<Book>();
        Book testBook = new Book();
        testBook.setId(outBookId);
        outList.add(testBook);

        bookListServices.setBookDao(bookDao);
    }

    @Test
    public void testGetBookByTitle() throws ServiceException {
        String title = "title";
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(bookDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList() )).thenReturn(outList);

        List<Book> bookList =  bookListServices.getBooksByTitle(title, 0, Integer.MAX_VALUE, order);

        assert (bookList.get(0).getId() == outBookId);
        assert (((Book)(listCaptor.getValue().get(0))).getTitle().equals("title"));
    }

    @Test
    public void testGetBookByAuthor() throws ServiceException {
        long authorId = 4242;
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(bookDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList() )).thenReturn(outList);

        List<Book> bookList = bookListServices.getBooksByAuthor(authorId, 0, Integer.MAX_VALUE, order);

        assert (bookList.get(0).getId() == outBookId);
        assert (((Author)(listCaptor.getValue().get(0))).getId() == 4242);
    }

    @Test
    public void testGetBookByUser() throws ServiceException {
        long userId = 4242;
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(bookDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList() )).thenReturn(outList);

        List<Book> bookList = bookListServices.getBooksByUser(userId, 0, Integer.MAX_VALUE, order);

        assert (bookList.get(0).getId() == outBookId);
        assert (((User)(listCaptor.getValue().get(0))).getId() == 4242);
    }

    @Test
    public void testGetAllBooks() throws ServiceException {
        ArgumentCaptor<List> filterCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List> orderCaptor = ArgumentCaptor.forClass(List.class);
        when(bookDao.getAll(anyInt(), anyInt(), filterCaptor.capture(), orderCaptor.capture() )).thenReturn(outList);

        List<Book> bookList = bookListServices.getAllBooks(0, Integer.MAX_VALUE, order);

        assert (bookList.get(0).getId() == outBookId);
        List<MyEntity> filters = filterCaptor.getValue();
        assert (filters == null || filters.size() ==0 || filters.get(0) == null);

        OrderManager orderManager = (OrderManager) orderCaptor.getValue().get(0);
        assert (orderManager.getField().equals("id"));
        assert (orderManager.getType().equals("ASC"));
    }

    @Test
    public void testGetLastPageNumber() throws ServiceException {
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(bookDao.getCount(listCaptor.capture())).thenReturn(outPageNumber * 100);

        long pageNumber = bookListServices.getLastPageNumber(100);

        assert (pageNumber == outPageNumber);
        List<MyEntity> filters = listCaptor.getValue();
        assert (filters == null || filters.size() == 0 || filters.get(0) == null);
    }

    @Test
    public void testGetLastPageNumberByAuthor() throws ServiceException {
        long authorId = 4242;
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(bookDao.getCount(listCaptor.capture())).thenReturn(outPageNumber * 100);

        long pageNumber = bookListServices.getLastPageNumberByAuthor(authorId , 100);

        assert (pageNumber == outPageNumber);
        assert (((Author)(listCaptor.getValue().get(0))).getId() == 4242);
    }

    @Test
    public void testGetLastPageNumberByUser() throws ServiceException {
        long userId = 4242;
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(bookDao.getCount(listCaptor.capture())).thenReturn(outPageNumber * 100);

        long pageNumber = bookListServices.getLastPageNumberByUser(userId , 100);

        assert (pageNumber == outPageNumber);
        assert (((User)(listCaptor.getValue().get(0))).getId() == 4242);
    }
}
