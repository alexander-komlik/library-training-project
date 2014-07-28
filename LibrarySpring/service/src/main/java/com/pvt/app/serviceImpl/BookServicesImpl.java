package com.pvt.app.serviceImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.service.BookServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServicesImpl implements BookServices {

    private static final Logger log = Logger.getLogger(BookServicesImpl.class);

    private MyDao<Book> bookDao;
    private MyDao<User> userDao;

    @Autowired
    @Qualifier("bookDao")
    public void setBookDao(MyDao<Book> bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(MyDao<User> userDao) {
        this.userDao = userDao;
    }

    @Override
    public Book getBook(long id) throws ServiceException {
        try {
            return bookDao.read(id);
        } catch (Exception e) {
            log.error("Get book error.");
            throw new ServiceException("error.service.getBook");
        }
    }

    @Override
    public long createBook(Book book) throws ServiceException {
        User filter = new User();
        filter.setName(book.getUser().getName());
        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(filter);
        List<User> users = userDao.getAll(0, 2, filters, null);
        if(users.size() == 0)
            return 0;
        User user = users.get(0);
        book.setUser(user);
        try {
            return bookDao.create(book);
        } catch (Exception e) {
            log.error("Create book error");
            throw new ServiceException("error.service.createBook");
        }
    }

    @Override
    public long editBook(Book book) throws ServiceException {
        try {
            return bookDao.update(book);
        } catch (Exception e) {
            log.error("Edit book error");
            throw new ServiceException("error.service.editBook");
        }
    }

    @Override
    public boolean deleteBook(long id) throws ServiceException {
        try {
            return bookDao.delete(id);
        } catch (Exception e) {
            log.error("Delete book error");
            throw new ServiceException("error.service.deleteBook");
        }
    }
}
