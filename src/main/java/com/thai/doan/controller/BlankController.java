package com.thai.doan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlankController {
    @GetMapping("/admin/blank")
    public ModelAndView getBlankPageTest() {
        return new ModelAndView("/admin/blank");
    }
}
