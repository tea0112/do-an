package com.thai.doan.controller;

import com.thai.doan.dao.model.User;
import com.thai.doan.util.UserChecker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {
    @GetMapping({"/", "/index"})
    public ModelAndView getHome() {
        ModelAndView userView = new ModelAndView("index");
        ModelAndView adminView = new ModelAndView("admin/index");
        if (UserChecker.doesUserIsAdmin()) {
            return adminView;
        }
        User user = UserChecker.getUserFromCtx();
        userView.addObject(user);
        return userView;
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @GetMapping("/login_success")
    public String getLoginSuccess() {
        if (UserChecker.doesUserIsAdmin()) {
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    @GetMapping("/admin")
    public ModelAndView getAdmin() {
        return new ModelAndView("admin/index");
    }
}
