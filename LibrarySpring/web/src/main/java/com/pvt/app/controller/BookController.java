package com.pvt.app.controller;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.exceptionHandler.MyExceptionHandler;
import com.pvt.app.service.AuthorServices;
import com.pvt.app.service.BookServices;
import com.pvt.app.util.BookForm;
import com.pvt.app.validator.BookFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
public class BookController {

    private static final Logger log = Logger.getLogger(BookController.class);

    @Autowired
    private BookServices bookServices;

    @Autowired
    private AuthorServices authorServices;

    @Autowired
    @Qualifier("bookDao")
    private MyDao<Book> bookDao;

    @Autowired
    private MyExceptionHandler exceptionHandler;

    @RequestMapping("/book/{id}")
    public String readBook(@PathVariable("id") long id, Map<String, Object> map, RedirectAttributes redirectAttributes) throws ServiceException {
        log.info("request: /book/{id}");
        Book book = bookServices.getBook(id);
        if(book.getId() == 0) {
            redirectAttributes.addFlashAttribute("msg", "msg.book.search.fail");
            return "redirect:/";
        }
        map.put("book", book);
        return "book";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/book/new", method = RequestMethod.GET)
    public String createBookInit(Map<String, Object> map) throws ServiceException {
        log.info("request: /book/new:get");
        map.put("book", new BookForm());
        map.put("authorList", authorServices.getAllAuthors());
        map.put("action", "book/new");
        return "createOrUpdateBook";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/book/new", method = RequestMethod.POST)
    public String createBookProcess(@ModelAttribute("book") BookForm bookForm, BindingResult result,
                                    Map<String, Object> map, RedirectAttributes redirectAttributes,
                                    Principal principal) throws ServiceException {
        log.info("request: /book/new:post");
        new BookFormValidator().validate(bookForm, result);
        if(result.hasErrors()) {
            map.put("action", "book/new");
            map.put("authorList", authorServices.getAllAuthors());
            return "createOrUpdateBook";
        }
        Book book = bookForm.getBook();
        User user = new User();
        user.setName(principal.getName());
        book.setUser(user);
        long id = bookServices.createBook(book);
        if(id == 0) {
            map.put("msg", "msg.book.add.fail");
            map.put("action", "book/new");
            map.put("authorList", authorServices.getAllAuthors());
            return "createOrUpdateBook";
        }
        redirectAttributes.addFlashAttribute("msg", "msg.book.add.success");
        return "redirect:/book/"+id;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') or (hasRole('ROLE_USER') and @ownershipCheck.isBookOwner(#id, principal))")
    @RequestMapping(value = "/book/{id}/edit", method = RequestMethod.GET)
    public String editBookInit(@PathVariable("id") long id, Map<String, Object> map,
                               RedirectAttributes redirectAttributes) throws ServiceException {
        log.info("request: /book/{id}/edit:get");
        Book book = bookServices.getBook(id);
        if(book.getId() == 0) {
            redirectAttributes.addFlashAttribute("msg", "msg.book.search.fail");
            return "redirect:/";
        }
        map.put("book", new BookForm(book));
        map.put("authorList", authorServices.getAllAuthors());
        map.put("action", id+"/edit");
        return "createOrUpdateBook";
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') or (hasRole('ROLE_USER') and @ownershipCheck.isBookOwner(#id, principal))")
    @RequestMapping(value = "/book/{id}/edit", method = RequestMethod.POST)
    public String editBookProcess(@PathVariable("id") long id, @ModelAttribute("book") BookForm bookForm,
                                        BindingResult result, Map<String, Object> map,
                                        RedirectAttributes redirectAttributes) throws ServiceException {
        log.info("request: /book/{id}/edit:post");
        bookForm.setId(id);
        new BookFormValidator().validate(bookForm, result);
        if(result.hasErrors()) {
            map.put("action", id+"/edit");
            map.put("authorList", authorServices.getAllAuthors());
            return "createOrUpdateBook";
        }
        Book book = bookForm.getBook();
        long id2 = bookServices.editBook(book);
        if(id2 == 0) {
            map.put("msg", "msg.book.edit.fail");
            map.put("action", id+"/edit");
            map.put("authorList", authorServices.getAllAuthors());
            return "createOrUpdateBook";
        }
        redirectAttributes.addFlashAttribute("msg", "msg.book.edit.success");
        return "redirect:/book/"+id2;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') or (hasRole('ROLE_USER') and @ownershipCheck.isBookOwner(#id, principal))")
    @RequestMapping(value = "/book/{id}/delete", method = RequestMethod.GET)
    public String deleteBookProcess(@PathVariable("id") long id, RedirectAttributes redirectAttributes) throws ServiceException {
        log.info("request: /book/{id}/delete");
        boolean success = bookServices.deleteBook(id);
        if(!success)
            redirectAttributes.addFlashAttribute("msg", "msg.book.delete.fail");
        else
            redirectAttributes.addFlashAttribute("msg", "msg.book.delete.success");
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        return exceptionHandler.handleException(e);
    }

}
