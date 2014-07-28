package com.pvt.app.service;

import com.pvt.app.entity.Book;
import com.pvt.app.exception.ServiceException;

public interface BookServices {

    public Book getBook(long id) throws ServiceException;
    public long createBook(Book book) throws ServiceException;
    public long editBook(Book book) throws ServiceException;
    public boolean deleteBook(long id) throws ServiceException;

}
