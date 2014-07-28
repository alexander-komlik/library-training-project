package com.pvt.app.controller;

import com.pvt.app.entity.Author;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.exceptionHandler.MyExceptionHandler;
import com.pvt.app.service.AuthorServices;
import com.pvt.app.service.BookListServices;
import com.pvt.app.service.UserServices;
import com.pvt.app.securityServices.RoleManager;
import com.pvt.app.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class BookListController {

    private static final Logger log = Logger.getLogger(BookListController.class);

    private static final String DEFAULT_NUMBER_VALUE = "5";

    @Autowired
    private BookListServices bookListServices;

    @Autowired
    private AuthorServices authorServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private MyExceptionHandler exceptionHandler;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String getAllBooks(Map<String, Object> map, HttpServletRequest req,
                              @RequestParam(value = "from", defaultValue = "0") int from,
                              @RequestParam(value = "number", defaultValue = DEFAULT_NUMBER_VALUE) int number,
                              @RequestParam(value = "order", defaultValue = "id_asc") String order) throws ServiceException {
        log.info("request: /index");
        map.put("bookList", bookListServices.getAllBooks(from, number, order));
        map.put("lastPage", bookListServices.getLastPageNumber(number));
        map.put("roles", new RoleManager(req));
        map.put("order", order);
        return "bookList";
    }

    @RequestMapping(value = "/author/{id}", method = RequestMethod.GET)
    public String getBooksByAuthor(@PathVariable("id") long id, Map<String, Object> map,
                                   HttpServletRequest req,
                                   @RequestParam(value = "from", defaultValue = "0") int from,
                                   @RequestParam(value = "number", defaultValue = DEFAULT_NUMBER_VALUE) int number,
                                   @RequestParam(value = "order", defaultValue = "id_asc") String order) throws ServiceException {
        log.info("request: /author/{id}");
        Author author = authorServices.getAuthor(id);
        if(author.getId() == 0) {
            return "redirect:/";
        }
        map.put("author", author);
        map.put("bookList", bookListServices.getBooksByAuthor(id, from, number, order));
        map.put("lastPage", bookListServices.getLastPageNumberByAuthor(id, number));
        map.put("roles", new RoleManager(req));
        map.put("order", order);
        return "bookList";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getBooksByUser(@PathVariable("id") long id, Map<String, Object> map,
                                 HttpServletRequest req,
                                 @RequestParam(value = "from", defaultValue = "0") int from,
                                 @RequestParam(value = "number", defaultValue = DEFAULT_NUMBER_VALUE) int number,
                                 @RequestParam(value = "order", defaultValue = "id_asc") String order) throws ServiceException {
        log.info("request: /user/{id}");
        User user = userServices.getUser(id);

        if(user.getId()==0) {
            return "redirect:/";
        }
        map.put("user", user);
        map.put("bookList", bookListServices.getBooksByUser(id, from, number, order));
        map.put("lastPage", bookListServices.getLastPageNumberByUser(id, number));
        map.put("roles", new RoleManager(req));
        map.put("order", order);
        return "bookList";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        return exceptionHandler.handleException(e);
    }
}
