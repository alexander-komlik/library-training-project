package com.pvt.app.daoImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.entity.User;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test-context.xml", "/spring-test-context-beans.xml"})
public class AuthorDaoImplTest {


    @Autowired
    ApplicationContext context;

    @Autowired
    @Qualifier("authorDao")
    MyDao<Author> authorDao;

    @Test
    public void testCRUD() {
        Author testAuthor = (Author) context.getBean("testAuthor");
        Author updatedAuthor = (Author) context.getBean("testAuthorNew");

        long createResult = authorDao.create(testAuthor);
        assert (createResult>0);

        Author authorFromDB = authorDao.read(createResult);
        assert (testAuthor.getName().equals(authorFromDB.getName()));
        assert (testAuthor.getName4table().equals(authorFromDB.getName4table()));
        assert (testAuthor.getUser().getId() == authorFromDB.getUser().getId());
        assert (testAuthor.getText().getText().equals(authorFromDB.getText().getText()));

        updatedAuthor.setId(createResult);
        long updateResult = authorDao.update(updatedAuthor);
        assert (updateResult == createResult);

        authorFromDB = authorDao.read(createResult);
        assert (updatedAuthor.getName().equals(authorFromDB.getName()));
        assert (updatedAuthor.getName4table().equals(authorFromDB.getName4table()));
        assert (updatedAuthor.getUser().getId() != authorFromDB.getUser().getId());
        assert (updatedAuthor.getText().getText().equals(authorFromDB.getText().getText()));

        boolean deleteResult = authorDao.delete(createResult);
        assert (deleteResult == true);

        authorFromDB = authorDao.read(createResult);
        assert (authorFromDB.getId() == 0);
    }

    @Test
    public void updateNotFound() {
        Author author = new Author();
        author.setId(1001);
        long result = authorDao.update(author);
        assert (result == 0);
    }

    @Test
    public void deleteNotFound() {
        boolean result = authorDao.delete(1001);
        assert (result == false);
    }

    @Test
    public void testGetAll() {
        List<Author> authors = authorDao.getAll(0, Integer.MAX_VALUE, null, null);
        assert (authors.size() >= 2);

        Author author = authors.get(0);
        boolean isFail;

        isFail = true;
        try { author.getText().getText(); }
        catch (LazyInitializationException e)
        {isFail = false;}
        if(isFail) fail();

        isFail = true;
        try { author.getBooks().get(0).getText(); }
        catch (LazyInitializationException e)
        {isFail = false;}
        if(isFail) fail();

        isFail = true;
        try { author.getBooks().get(0).getAuthors(); }
        catch (LazyInitializationException e)
        {isFail = false;}
        if(isFail) fail();

        isFail = true;
        try { author.getBooks().get(0).getUser(); }
        catch (LazyInitializationException e)
        {isFail = false;}
        if(isFail) fail();
    }

    @Test
    public void testGetByUser() {
        User filter = new User();
        filter.setId(3);
        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(filter);
        List<Author> authors = authorDao.getAll(0, Integer.MAX_VALUE, filters, null);
        assert (authors.size() == 1);
    }

    @Test
    public void testGetCount() {
        long result = authorDao.getCount(null);
        assert (result>=2);
    }

}
