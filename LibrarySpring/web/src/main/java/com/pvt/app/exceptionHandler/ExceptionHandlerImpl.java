package com.pvt.app.exceptionHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ExceptionHandlerImpl implements MyExceptionHandler {

    private static final String SERVICE_EXCEPTION = "com.pvt.app.exception.ServiceException";
    private static final String PERSISTENCE_EXCEPTION = "javax.persistence.PersistenceException";

    @Override
    public ModelAndView handleException(Exception e) {

        ModelAndView model = new ModelAndView("error500");
        String className = e.getClass().getName();

        if(e.getClass().getName().equals(SERVICE_EXCEPTION)) {
            model.addObject("msg", e.getMessage());
        } else if(e.getClass().getName().equals(PERSISTENCE_EXCEPTION)) {
            model.addObject("msg", "error.database");
        } else {
            model.addObject("msg", "error.unknown");
        }

        return model;
    }
}
