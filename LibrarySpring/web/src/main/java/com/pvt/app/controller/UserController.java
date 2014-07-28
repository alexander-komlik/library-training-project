package com.pvt.app.controller;

import com.pvt.app.entity.AccessData;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.exceptionHandler.*;
import com.pvt.app.service.UserServices;
import com.pvt.app.validator.AccessDataValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;


@Controller
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserServices userServices;

    @Autowired
    private MyExceptionHandler exceptionHandler;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/users")
    public String userList(Map<String, Object> map,
                           @RequestParam(value = "from", defaultValue = "0") int from,
                           @RequestParam(value = "number", defaultValue = "0") int number,
                           @RequestParam(value = "order", defaultValue = "id_asc") String order) throws ServiceException {
        log.info("request: /author/{id}");
        map.put("userList", userServices.getAllUsers(map, from, number, order));
        map.put("lastPage", userServices.getLastPageNumber(number));
        map.put("order", order);
        return "userList";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerInit(Map<String, Object> map) {
        log.info("request: /register:get");
        map.put("newAccount", new AccessData());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerProcess(@ModelAttribute("newAccount") AccessData newAccount, BindingResult result,
                                  Map<String, Object> map, RedirectAttributes redirectAttributes) throws ServiceException {
        log.info("request: /register:post");
        new AccessDataValidator().validate(newAccount, result);
        if(result.hasErrors()) {
            return "register";
        }
        long success = userServices.register(newAccount);
        if(success == 0) {
            map.put("msg", "msg.user.add.fail");
            return "register";
        }
        redirectAttributes.addFlashAttribute("msg", "msg.user.add.success");
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping("/login")
    public String login() {
        log.info("request: /login");
        return "redirect:/login.jsp";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/user/{id}/{role}")
    public String setRole(@PathVariable("id") long id, @PathVariable("role") String role,
                          RedirectAttributes redirectAttributes) throws ServiceException {
        log.info("request: /user/{id}/{role}");
        boolean result = userServices.setRole(id, role);
        if(result)
            redirectAttributes.addFlashAttribute("msg", "msg.user.roleChange.success");
        else
            redirectAttributes.addFlashAttribute("msg", "msg.user.roleChange.fail");
        return "redirect:/users";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        return exceptionHandler.handleException(e);
    }
}
