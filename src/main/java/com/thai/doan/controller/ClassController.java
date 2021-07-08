package com.thai.doan.controller;

import com.thai.doan.dao.model.Classes;
import com.thai.doan.dto.request.ClassAddingRequest;
import com.thai.doan.dto.request.ClassUpdatingRequest;
import com.thai.doan.dto.request.SessionUpdatingRequest;
import com.thai.doan.service.ClassesService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@Data
public class ClassController {
    private final ClassesService classSv;

    @GetMapping("/admin/lop/them")
    public ModelAndView getAddClassPage() {
        return new ModelAndView("admin/class/add-class");
    }

    @GetMapping("/admin/lop/sua")
    public ModelAndView getEditClassPage() {
        return new ModelAndView("admin/class/edit-class");
    }

    @GetMapping("/admin/lop/xoa")
    public ModelAndView getDeleteClassPage() {
        return new ModelAndView("admin/class/delete-class");
    }

    @GetMapping("/admin/lop/them-sinh-vien")
    public ModelAndView getAddStudentClassPage() {
        return new ModelAndView("admin/class/add-student-class");
    }

    @GetMapping("/admin/lop/xoa-sinh-vien")
    public ModelAndView getDeleteStudentClassPage() {
        return new ModelAndView("admin/class/delete-student-class");
    }


    // api
//    @GetMapping("/api/admin/classes")
//    public List<Classes> getWithClassTypeAndDepartment(@RequestParam Integer classType,
//                                                       @RequestParam Integer departmentId,
//                                                       @RequestParam Integer sessionId) {
//        return classSv.getWithClassTypeAndDepartmentAndSession(classType, departmentId, sessionId);
//    }

    @RequestMapping(
        value = "/api/classes",
        method = RequestMethod.GET,
        params = {"sessionId", "departmentId"})
    public List<Classes> getWithDepartmentAndSession(
        @RequestParam(name = "departmentId") int departmentId, @RequestParam(name = "sessionId") int sessionId) {
        return classSv.getWithDepartmentAndSession(departmentId, sessionId);
    }

    @PostMapping("/api/admin/classes")
    public ResponseEntity<?> add(@RequestBody @Valid ClassAddingRequest classAddingReq) {
        classSv.add(classAddingReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api/admin/classes")
    public ResponseEntity<?> updateWithId(@RequestBody @Valid ClassUpdatingRequest classUpdatingReq,
                                          @RequestParam int classId) {
        classSv.updateWithId(classId, classUpdatingReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/classes/{classId}")
    public ResponseEntity<?> deleteWithId(@PathVariable int classId) {
        classSv.deleteWithId(classId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/classes", method = RequestMethod.GET, params = "sessionId")
    public List<Classes> getGeneralWithSession(@RequestParam Integer sessionId) {
        return classSv.getGeneralWithSession(sessionId);
    }

    @RequestMapping(value = "/api/specializedClass", method = RequestMethod.GET, params = "sessionId")
    public List<Classes> getSpecializedClassWithSessionId(@RequestParam Integer sessionId) {
        return classSv.getSpecializedClassWithSessionId(sessionId);
    }

    @RequestMapping(value = "/api/admin/classes", method = RequestMethod.GET, params = "sessionId")
    public List<Classes> getWithSession(@RequestParam Integer sessionId) {
        return classSv.getWithSession(sessionId);
    }
}
