package com.pvt.app.service;

import com.pvt.app.entity.Author;
import com.pvt.app.exception.ServiceException;

import java.util.List;

public interface AuthorServices {

    public Author getAuthor(long id) throws ServiceException;
    public long createAuthor(Author author) throws ServiceException;
    public long editAuthor(Author author) throws ServiceException;
    public List<Author> getAllAuthors() throws ServiceException;

}
