package com.pvt.app.daoImpl;


import com.pvt.app.criteriaBuilder.GetAllCriteriaBuilder;
import com.pvt.app.criteriaBuilder.PredicateBuilder;
import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.util.OrderManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

@Transactional
abstract public class AbstractDaoImpl<T extends MyEntity> implements MyDao<T> {

    private static final Logger log = Logger.getLogger(AbstractDaoImpl.class);

    abstract protected void preCreate(T item);
    abstract protected void doUpdate(T persistItem, T item);
    abstract protected void preDelete(T item);
    abstract protected Query getReadByIdNamedQuery(EntityManager em);
    abstract protected Map<Class, PredicateBuilder> getBuilderMap();
    abstract public EntityManager getEntityManager();

    private Class clazz;

    public AbstractDaoImpl() {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public long create(T item) {
        log.info("create "+clazz.getName());
        preCreate(item);
        getEntityManager().persist(item);
        getEntityManager().flush();
        return item.getId();
    }

    @Override
    public long update(T item) {
        log.info("update "+clazz.getName());
        T persistItem = (T) getEntityManager().find(clazz, item.getId());
        if(persistItem == null) return 0;
        doUpdate(persistItem, item);
        getEntityManager().merge(persistItem);
        return persistItem.getId();
    }

    @Override
    public boolean delete(long id) {
        log.info("delete "+clazz.getName());
        T persistItem = (T) getEntityManager().find(clazz, id);
        if(persistItem == null) return false;
        preDelete(persistItem);
        getEntityManager().remove(persistItem);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public T read(long id) {
        log.info("read "+clazz.getName());
        Query query = getReadByIdNamedQuery(getEntityManager());
        query.setParameter("id", id);
        List<T> tList = query.getResultList();
        T item = null;
        for(T t: tList){
            if(t.getId() == id)
                return t;
        }

        try {
            item = (T) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return item;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll(int from, int number, List<MyEntity> filters, List<OrderManager> orders) {
        log.info("get all "+clazz.getName());
        GetAllCriteriaBuilder<T> builder = new GetAllCriteriaBuilder<T>(clazz, getEntityManager(), filters, getBuilderMap());
        return builder.getList(from, number, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount(List<MyEntity> filters) {
        log.info("get number of "+clazz.getName());
        GetAllCriteriaBuilder<T> builder = new GetAllCriteriaBuilder<T>(clazz, getEntityManager(), filters, getBuilderMap());
        return builder.getCount();
    }
}
