package com.cyj.serviceedu.controller;

import com.cyj.commonutils.R;
import com.cyj.serviceedu.domain.subject.OneSubject;
import com.cyj.serviceedu.service.EduSubjectService;
import com.cyj.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-14
 */
@RestController
@RequestMapping("/serviceedu/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表（树形）
   @GetMapping("getAllSubject")
    public R getAllSubject(){

       //list集合泛型是一级分类
       List<OneSubject> list = eduSubjectService.getAllSubject();

        return R.ok().data("list",list);
   }

}

