package com.cyj.serviceedu.controller;


import com.cyj.commonutils.R;
import com.cyj.serviceedu.domain.EduCourse;
import com.cyj.serviceedu.domain.vo.CoursePublishQuery;
import com.cyj.serviceedu.domain.vo.CourseQuery;
import com.cyj.serviceedu.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //根据课程ID获取课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseQuery courseQuery = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseQuery",courseQuery);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseQuery courseQuery){
        eduCourseService.updateCourseInfo(courseQuery);
        return R.ok();
    }

    //根据课程id查询课程确认信息（CoursePublishQuery)
    @GetMapping("getCoursePublishQuery/{courseId}")
    public R getCoursePublishQuery(@PathVariable String courseId){
        CoursePublishQuery coursePublishQuery = eduCourseService.getCoursePublishQuery(courseId);
        return R.ok().data("coursePublishQuery",coursePublishQuery);
    }

    //最终发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//课程发布状态
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

}

