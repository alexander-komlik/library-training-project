package com.pvt.app.criteriaBuilder;

import com.pvt.app.entity.MyEntity;
import com.pvt.app.util.OrderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetAllCriteriaBuilder<T extends MyEntity> {

    private List<MyEntity> filters;
    private EntityManager manager;
    private Map<Class, PredicateBuilder> builderMap;
    private Class clazz;

    public GetAllCriteriaBuilder(Class clazz, EntityManager manager, List<MyEntity> filters, Map<Class, PredicateBuilder> builderMap) {
        this.manager = manager;
        this.filters = filters;
        this.builderMap = builderMap;
        this.clazz = clazz;
    }

    public List<T> getList(int from, int amount, List<OrderManager> orders) {
        CriteriaQuery query = createCriteriaQuery(manager, filters, orders, false);
        TypedQuery typedQuery = manager.createQuery(query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(amount);
        return typedQuery.getResultList();
    }

    public long getCount() {
        CriteriaQuery query = createCriteriaQuery(manager, filters, null, true);
        TypedQuery typedQuery = manager.createQuery(query);
        long result = (Long) typedQuery.getSingleResult();
        return result;
    }

    private CriteriaQuery createCriteriaQuery(EntityManager manager, List<MyEntity> filters, List<OrderManager> orders, boolean isCounter) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root<T> from = criteriaQuery.from(clazz);
        List<Predicate> predicates = new ArrayList<Predicate>();



        if(filters != null && filters.size()>0) {
            for(MyEntity entity: filters){
                if(entity != null)   {
                    PredicateBuilder builder = builderMap.get(entity.getClass());
                    if(builder != null) {
                        predicates.add(builder.getPredicate(criteriaBuilder, from, entity));
                    }
                }
            }
        }

        CriteriaQuery select;
        if(isCounter) {
            Expression countExpression = criteriaBuilder.count(from);
            select = criteriaQuery.select(countExpression);
        } else {
            select = criteriaQuery.select(from);
        }

        if(predicates.size()>0) {
            Predicate[] predicateArray = new Predicate[predicates.size()];
            int counter = 0;
            for(Predicate p:predicates) {
                predicateArray[counter++] = p;
            }
            select.where(predicateArray);
        }

        if(orders != null && orders.size()>0) {
            List<Order> orderList = new ArrayList<Order>();
            for(OrderManager order: orders) {
                if(order.getType().equals(order.ASC))
                    orderList.add(criteriaBuilder.asc(from.get(order.getField())));
                if(order.getType().equals(order.DESC))
                    orderList.add(criteriaBuilder.desc(from.get(order.getField())));
            }
            select.orderBy(orderList);
        }

        return select;
    }

}
