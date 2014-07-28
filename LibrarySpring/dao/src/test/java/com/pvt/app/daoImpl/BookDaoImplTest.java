package com.pvt.app.daoImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.Book;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test-context.xml", "/spring-test-context-beans.xml"})
public class BookDaoImplTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    @Qualifier("bookDao")
    MyDao<Book> bookDao;

    @Test
    public void testCRUD() {
        Book testBook = (Book) context.getBean("testBook");
        Book updatedBook = (Book) context.getBean("testBookNew");
        long createResult = bookDao.create(testBook);
        assert (createResult>0);

        Book bookFromDB = bookDao.read(createResult);
        assert (bookFromDB.getId() > 0);
        assert (bookFromDB.getText().getText().equals(testBook.getText().getText()));
        assert (bookFromDB.getUser().getId() == testBook.getUser().getId());
        assert (bookFromDB.getAuthors().size() == testBook.getAuthors().size());
        assert (bookFromDB.getAuthors().get(0).getId() == testBook.getAuthors().get(0).getId());

        updatedBook.setId(createResult);
        long updateResult = bookDao.update(updatedBook);
        System.out.println("update="+updateResult);
        assert (updateResult == createResult);

        bookFromDB = bookDao.read(createResult);
        assert (bookFromDB.getId() > 0);
        assert (bookFromDB.getText().getText().equals(updatedBook.getText().getText()));
        assert (bookFromDB.getUser().getId() != updatedBook.getUser().getId());
        assert (bookFromDB.getAuthors().size() == updatedBook.getAuthors().size());
        assert (bookFromDB.getAuthors().get(0).getId() == updatedBook.getAuthors().get(0).getId());

        boolean deleteResult = bookDao.delete(createResult);
        assert (deleteResult == true);

        bookFromDB = bookDao.read(createResult);
        System.out.println("title1="+bookFromDB.getTitle());
        assert (bookFromDB.getId() == 0);
    }

    @Test
    public void testUpdateNotFound() {
        Book book = new Book();
        book.setId(1001);
        long result =  bookDao.update(book);
        assert (result == 0);
    }

    @Test
    public void testDeleteNotFound() {
        boolean result = bookDao.delete(1001);
        assert (result == false);
    }

    @Test
    public void testGetAll() {
        List<Book> books = bookDao.getAll(0, 3, null, null);
        assert (books.size() == 3);

        Book book = books.get(0);
        boolean isFail;

        isFail = true;
        try { book.getText().getText(); }
        catch (LazyInitializationException e)
            {isFail = false;}
        if(isFail) fail();

        isFail = true;
        try { book.getAuthors().get(0).getText().getText(); }
        catch (LazyInitializationException e)
        {isFail = false;}
        if(isFail) fail();
    }

    @Test
    public void testGetByAuthor() {
        Author filter = new Author();
        filter.setId(1);
        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(filter);
        List<Book> books = bookDao.getAll(0, Integer.MAX_VALUE, filters, null);
        assert (books.size() == 3);
    }

    @Test
    public void testGetByUser() {
        User filter = new User();
        filter.setId(3);
        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(filter);
        List<Book> books = bookDao.getAll(0, Integer.MAX_VALUE, filters, null);
        assert (books.size() == 3);
    }

    @Test
    public void testGetCount() {
        long result = bookDao.getCount(null);
        assert (result == 4);
    }

}
