package com.pvt.app.controller;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.exceptionHandler.MyExceptionHandler;
import com.pvt.app.service.AuthorServices;
import com.pvt.app.validator.AuthorValidator;
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
public class AuthorController {

    private static final Logger log = Logger.getLogger(AuthorController.class);

    @Autowired
    private AuthorServices authorServices;

    @Autowired
    @Qualifier("authorDao")
    private MyDao<Author> authorDao;

    @Autowired
    private MyExceptionHandler exceptionHandler;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/author/new", method = RequestMethod.GET)
    public String createAuthorInit(Map<String, Object> map) {
        log.info("request: /author/new:get");
        map.put("author", new Author());
        map.put("action", "author/new");
        return "createOrUpdateAuthor";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/author/new", method = RequestMethod.POST)
    public String createAuthorProcess(@ModelAttribute("author") Author author, BindingResult result,
                                      Map<String, Object> map, RedirectAttributes redirectAttributes,
                                      Principal principal) throws ServiceException {
        log.info("request: /author/new:post");
        new AuthorValidator().validate(author, result);
        if(result.hasErrors()) {
            map.put("action", "author/new");
            return "createOrUpdateAuthor";
        }
        User user = new User();
        user.setName(principal.getName());
        author.setUser(user);
        long id = authorServices.createAuthor(author);
        if(id == 0) {
            map.put("msg", "msg.author.add.fail");
            return "createOrUpdateAuthor";
        }
        redirectAttributes.addFlashAttribute("msg", "msg.author.add.success");
        return "redirect:/author/"+id;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') or (hasRole('ROLE_USER') and @ownershipCheck.isAuthorOwner(#id, principal))")
    @RequestMapping(value = "/author/{id}/edit", method = RequestMethod.GET)
    public String editAuthorInit(@PathVariable("id") long id, Map<String, Object> map,
                                 RedirectAttributes redirectAttributes) throws ServiceException {
        log.info("request: /author/{id}/edit:get");
        Author author = authorServices.getAuthor(id);
        if(author.getId() == 0) {
            redirectAttributes.addFlashAttribute("msg", "msg.author.search.fail");
            return "redirect:/";
        }
        map.put("author", author);
        map.put("action", id+"/edit");
        return "createOrUpdateAuthor";
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') or (hasRole('ROLE_USER') and @ownershipCheck.isAuthorOwner(#id, principal))")
    @RequestMapping(value = "/author/{id}/edit", method = RequestMethod.POST)
    public String editAuthorProcess(@PathVariable("id") long id, @ModelAttribute("author") Author author,
                                          BindingResult result, Map<String, Object> map,
                                          RedirectAttributes redirectAttributes) throws ServiceException {
        log.info("request: /author/{id}/edit:post");
        author.setId(id);
        new AuthorValidator().validate(author, result);
        if(result.hasErrors()) {
            map.put("action", id+"/edit");
            return "createOrUpdateAuthor";
        }

        long id2 = authorServices.editAuthor(author);
        if(id2 == 0) {
            map.put("action", id+"/edit");
            map.put("msg", "msg.author.edit.fail");
            return "createOrUpdateAuthor";
        }
        redirectAttributes.addFlashAttribute("msg", "msg.author.edit.success");
        return "redirect:/book/"+id2;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        return exceptionHandler.handleException(e);
    }
}
