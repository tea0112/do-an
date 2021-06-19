package com.thai.doan.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class LectureHallController {
    // admin view
    @GetMapping("/admin/giang-duong/them")
    public ModelAndView getAddView () {
        return new ModelAndView("admin/lecture-hall/add-lecture-hall");
    }

    @GetMapping("/admin/giang-duong/sua")
    public ModelAndView getEditView () {
        return new ModelAndView("admin/lecture-hall/edit-lecture-hall");
    }

    @GetMapping("/admin/giang-duong/xoa")
    public ModelAndView getDeleteView () {
        return new ModelAndView("admin/lecture-hall/delete-lecture-hall");
    }

    // restful api

}
