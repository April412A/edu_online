package com.cyj.serviceedu.controller;


import com.cyj.serviceedu.domain.EduTeacher;
import com.cyj.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-15
 */
@RestController
@RequestMapping("/serviceedu/edu-teacher")
//try 3
public class EduTeacherController {

    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1 查询讲师表所有数据
    //rest风格
    @GetMapping("findAll")
    public List<EduTeacher> findAll(){
        List<EduTeacher> list = teacherService.list(null);
        System.out.println("list=="+list);
        return list;
    }

}

