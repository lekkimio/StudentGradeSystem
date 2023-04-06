package com.example.gradesys.controller;


import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status435NoAuthorities;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.exception.Status440ManagerNotFound;
import com.example.gradesys.model.Manager;
import com.example.gradesys.model.Subject;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ManagerDto;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.ResultService;
import com.example.gradesys.service.SubjectService;
import com.example.gradesys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppController {


    private final ResultService resultService;
    private final UserService userService;
    private final SubjectService subjectService;

    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    @GetMapping("/students")
    public String getStudents(Model model) throws Status440ManagerNotFound {

        List<User> studentList = userService.getAllStudents();
        model.addAttribute("studentList", studentList);

        List<User> managerList = userService.getAllManagers();
        model.addAttribute("managerList", managerList);

        List<User> studentManagerList = new ArrayList<>();

        for (User student : studentList) {
            Manager managerByStudent = userService.getManagerByStudent(student.getId());
            studentManagerList.add(managerByStudent!=null? managerByStudent.getMentor():null);
        }

        model.addAttribute("studentManagerList", studentManagerList);
        return "students";
    }



    @GetMapping("/journal")
    public String getJournal(Model model) {

        List<User> students = userService.getAllStudents();
        List<Subject> subjects = subjectService.getAllSubjects();

        model.addAttribute("students", students);
        model.addAttribute("subjects", subjects);
        model.addAttribute("resultList", resultService.getUserResult());

        return "journal";
    }



    //TODO wrong http-method for edit and wrong naming
    @PostMapping("/journal/result/edit")
    public String editResult(ResultDto resultDto, CustomUserDetails userDetails) throws Status440ManagerNotFound, Status437SubjectNotFound, Status435NoAuthorities, Status434UserNotFound {
        resultService.editResult(resultDto, userDetails);
        return "redirect:/journal";
    }

    //TODO wrong http-method for edit and wrong naming
    @PostMapping("/students/manager/edit")
    public String editManager(ManagerDto managerDto, CustomUserDetails userDetails) throws  Status435NoAuthorities, Status434UserNotFound {
        userService.editManagerToStudent(managerDto, userDetails) ;
        return "redirect:/students";
    }

    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin";
    }

    @GetMapping("/auth/signup")
    public String getSignupPage() {
        return "signup";
    }

}
