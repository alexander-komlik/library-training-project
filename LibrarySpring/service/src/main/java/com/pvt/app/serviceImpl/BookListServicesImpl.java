package com.pvt.app.serviceImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.service.BookListServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookListServicesImpl implements BookListServices {

    private static final int DEFAULT_NUMBER_VALUE = 5;
    private static final Logger log = Logger.getLogger(BookListServicesImpl.class);

    private MyDao<Book> bookDao;

    @Autowired
    @Qualifier("bookDao")
    public void setBookDao(MyDao<Book> bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    public List<Book> getBooksByTitle(String substring, int from, int number, String order) throws ServiceException {
        Book book = new Book();
        book.setTitle(substring);
        return getBookList(book, from, number, order);
    }

    @Override
    public List<Book> getBooksByAuthor(long id, int from, int number, String order) throws ServiceException {
        Author author = new Author();
        author.setId(id);
        return getBookList(author, from, number, order);
    }

    @Override
    public List<Book> getBooksByUser(long id, int from, int number, String order) throws ServiceException {
        User user = new User();
        user.setId(id);
        return getBookList(user, from, number, order);
    }

    @Override
    public List<Book> getAllBooks(int from, int number, String order) throws ServiceException {
        return getBookList(null, from, number, order);
    }

    @Override
    public int getLastPageNumber(int itemsPerPage) throws ServiceException {
        return getLastPageNumber(null, itemsPerPage);
    }

    @Override
    public int getLastPageNumberByAuthor(long id, int itemsPerPage) throws ServiceException {
        Author author = new Author();
        author.setId(id);
        return getLastPageNumber(author, itemsPerPage);
    }

    @Override
    public int getLastPageNumberByUser(long id, int itemsPerPage) throws ServiceException {
        User user = new User();
        user.setId(id);
        return getLastPageNumber(user, itemsPerPage);
    }

    private List<Book> getBookList(MyEntity filter, int from, int number, String order) throws ServiceException {

        if(from<0) from = 0;
        if(number<1) number = DEFAULT_NUMBER_VALUE;

        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(filter);
        try {
            return bookDao.getAll(from, number, filters, UtilityServices.getOrders(order));
        } catch (Exception e) {
            log.error("get all error");
            throw new ServiceException("error.service.getBookList");
        }

    }

    private int getLastPageNumber(MyEntity filter, int itemsPerPage) throws ServiceException {

        if(itemsPerPage<1)
            itemsPerPage = DEFAULT_NUMBER_VALUE;
        List<MyEntity> filters = new ArrayList<MyEntity>();
        if(filter!=null) filters.add(filter);
        long number = 0;

        try{
            number = bookDao.getCount(filters);
        } catch (Exception e) {
            log.error("get count error");
            throw new ServiceException("error.service.bookNumberError");
        }

        long lastPage = number/itemsPerPage;
        if(number%itemsPerPage != 0) lastPage++;
        return (int) lastPage;
    }
}
