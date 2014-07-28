package com.pvt.app.service;

import com.pvt.app.entity.Book;
import com.pvt.app.exception.ServiceException;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

public interface BookListServices {

    public List<Book> getBooksByTitle(String substring, int from, int number, String order) throws ServiceException;
    public List<Book> getBooksByAuthor(long id, int from, int number, String order) throws ServiceException;
    public List<Book> getBooksByUser(long id, int from, int number, String order) throws ServiceException;
    public List<Book> getAllBooks(int from, int number, String order) throws ServiceException;

    public int getLastPageNumber(int itemsPerPage) throws ServiceException;
    public int getLastPageNumberByAuthor(long id, int itemsPerPage) throws ServiceException;
    public int getLastPageNumberByUser(long id, int itemsPerPage) throws ServiceException;

}
