package com.pvt.app.serviceImpl;

import com.pvt.app.util.OrderManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UtilityServices {

    private static final Logger log = Logger.getLogger(UtilityServices.class);

    public static int getIntParameter(String paramName, Map<String, Object> map) {

        int result;
        try {
            result = Integer.valueOf((String) map.get(paramName));                  //todo
        } catch (Exception e) {
            log.debug("Parameter "+paramName+" not found. Default result returned");
            result = -1;
        }
        return result;
    }

    public static List<OrderManager> getOrders(String order) {
        OrderManager orderManager = new OrderManager();
        if(order == null) {
            //NOP
        } else if(order.equals("id_asc")) {
            orderManager.asc("id");
        } else if(order.equals("id_desc")) {
            orderManager.desc("id");
        }else if(order.equals("date_asc")) {
            orderManager.asc("lastUpdate");
        } else if(order.equals("date_desc")) {
            orderManager.desc("lastUpdate");
        } else if(order.equals("title_asc")) {
            orderManager.asc("title");
        } else if(order.equals("title_desc")) {
            orderManager.desc("title");
        } else if(order.equals("name_asc")) {
            orderManager.asc("name");
        } else if(order.equals("name_desc")) {
            orderManager.desc("name");
        } else {
            //NOP
        }

        List<OrderManager> orders = new ArrayList<OrderManager>();
        orders.add(orderManager);
        return orders;
    }
}
