package com.thai.doan.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class ClassroomController {
    // admin view
    @GetMapping("/admin/phong-hoc/them")
    public ModelAndView getAddView () {
        return new ModelAndView("admin/classroom/add-classroom");
    }

    @GetMapping("/admin/phong-hoc/sua")
    public ModelAndView getEditView () {
        return new ModelAndView("admin/classroom/edit-classroom");
    }

    @GetMapping("/admin/phong-hoc/xoa")
    public ModelAndView getDeleteView () {
        return new ModelAndView("admin/classroom/delete-classroom");
    }

    // restful api

}
