package com.pvt.app.dao;

import com.pvt.app.entity.MyEntity;
import com.pvt.app.util.OrderManager;

import javax.persistence.EntityManager;
import java.util.List;

public interface MyDao<T extends MyEntity> {

    long create(T item);
    long update(T item);
    boolean delete(long id);
    T read(long id);
    List<T> getAll(int from, int count, List<MyEntity> filters, List<OrderManager> orders);
    long getCount(List<MyEntity> filters);

}
