package com.thai.doan.controller;

import com.thai.doan.dao.model.Classroom;
import com.thai.doan.dto.request.ClassroomAddingRequest;
import com.thai.doan.dto.request.ClassroomUpdatingRequest;
import com.thai.doan.service.impl.ClassroomService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Data
@RestController
public class ClassroomController {
    private final ClassroomService classroomSrv;

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
    @PostMapping("/api/admin/classrooms")
    public ResponseEntity<Classroom> add(@Valid @RequestBody ClassroomAddingRequest classroomAddingReq) {
        return new ResponseEntity<>(classroomSrv.add(classroomAddingReq), HttpStatus.OK);
    }

    @PatchMapping("/api/admin/classrooms/{id}")
    public ResponseEntity<Classroom> update(@Valid @RequestBody ClassroomUpdatingRequest classroomUpdatingReq,
                                            @PathVariable Integer id) {
        return new ResponseEntity<>(classroomSrv.update(classroomUpdatingReq, id), HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/classrooms/{id}")
    public ResponseEntity<Classroom> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(classroomSrv.delete(id), HttpStatus.OK);
    }

    @GetMapping("/api/admin/classrooms")
    public ResponseEntity<List<Classroom>> getWithLectureHall(@RequestParam Integer lectureHallId) {
        return new ResponseEntity<>(classroomSrv.getWithLectureHall(lectureHallId), HttpStatus.OK);
    }
}
