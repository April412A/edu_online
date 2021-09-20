package com.cyj.serviceedu.controller;


import com.cyj.commonutils.R;
import com.cyj.serviceedu.domain.vo.CourseQuery;
import com.cyj.serviceedu.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-20
 */
@Api(tags="课程信息管理")
@RestController
@RequestMapping("/serviceedu/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseQuery courseQuery){
        String id = eduCourseService.saveCourseInfo(courseQuery);

        return R.ok().data("courseId",id);
    }

}

