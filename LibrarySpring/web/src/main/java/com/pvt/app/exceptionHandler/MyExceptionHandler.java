package com.pvt.app.exceptionHandler;

import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface MyExceptionHandler {

    public ModelAndView handleException(Exception e);

}
