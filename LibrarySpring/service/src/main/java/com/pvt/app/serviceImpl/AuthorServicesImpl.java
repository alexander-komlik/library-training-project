package com.pvt.app.serviceImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.service.AuthorServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServicesImpl implements AuthorServices {

    private static final Logger log = Logger.getLogger(AuthorServicesImpl.class);

    private MyDao<Author> authorDao;
    private MyDao<User> userDao;

    @Autowired
    @Qualifier("authorDao")
    public void setAuthorDao(MyDao<Author> authorDao) {
        this.authorDao = authorDao;
    }

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(MyDao<User> userDao) {
        this.userDao = userDao;
    }

    @Override
    public Author getAuthor(long id) throws ServiceException {
        try{
            return authorDao.read(id);
        } catch (Exception e) {
            log.error("Read author error");
            throw new ServiceException("error.service.getAuthor");
        }
    }

    @Override
    public long createAuthor(Author author) throws ServiceException {
        User filter = new User();
        filter.setName(author.getUser().getName());
        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(filter);
        List<User> users = userDao.getAll(0, 2, filters, null);
        if(users.size() == 0)
            return 0;
        User user = users.get(0);
        author.setUser(user);
        try {
            return authorDao.create(author);
        }catch (Exception e) {
            log.error("Create author error");
            throw new ServiceException("error.service.createAuthor");
        }
    }

    @Override
    public long editAuthor(Author author) throws ServiceException {
        try {
            return authorDao.update(author);
        } catch (Exception e) {
            log.error("Update author error");
            throw new ServiceException("error.service.editAuthor");
        }

    }

    @Override
    public List<Author> getAllAuthors() throws ServiceException {
        try {
            return authorDao.getAll(0, Integer.MAX_VALUE, null, null);//todo
        } catch (Exception e) {
            log.error("Get all authors error");
            throw new ServiceException("error.service.getAllAuthors");
        }
    }
}
