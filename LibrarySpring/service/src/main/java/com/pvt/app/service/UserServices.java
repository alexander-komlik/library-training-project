package com.pvt.app.service;

import com.pvt.app.entity.AccessData;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface UserServices {

    public List<User> getAllUsers(Map<String, Object> map, int from, int number, String order) throws ServiceException;
    public User getUser(long id) throws ServiceException;
    public long register(AccessData accessData) throws ServiceException;
    public boolean setRole(long id, String role) throws ServiceException;

    public int getLastPageNumber(int usersPerPage) throws ServiceException;

}
