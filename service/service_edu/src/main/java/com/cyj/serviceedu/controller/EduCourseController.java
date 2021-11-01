package com.cyj.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyj.commonutils.R;
import com.cyj.serviceedu.domain.EduCourse;
import com.cyj.serviceedu.domain.EduCourse;
import com.cyj.serviceedu.domain.vo.CoursePublishQuery;
import com.cyj.serviceedu.domain.vo.CourseQuery;
import com.cyj.serviceedu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@ApiParam(name = "id", value = "课程ID", required = true)
                          @PathVariable String courseId){
        eduCourseService.removeCourse(courseId);
        return R.ok();
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

    //查询课程列表
    @GetMapping("findAll")
    public R findAll(){
        List<EduCourse> courselist = eduCourseService.list(null);
        return R.ok().data("courseList",courselist);
    }

    //条件分页查询课程列表
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current,@PathVariable long limit,
                                 @RequestBody(required = false) EduCourse eduCourse ){
        //创建page对象
        Page<EduCourse> page = new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 多条件组合查询 ; mybatis学过 动态sql
        String id = eduCourse.getId();
        String title = eduCourse.getTitle();
        BigDecimal price = eduCourse.getPrice();
        Integer lessonNum = eduCourse.getLessonNum();
        String status = eduCourse.getStatus();

        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(id)){
            wrapper.eq("id",id);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(price)){
            wrapper.eq("price",price);
        }
        if(!StringUtils.isEmpty(lessonNum)){
            wrapper.eq("lesson_num",lessonNum);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }

        //调用方法实现条件查询分页
        eduCourseService.page(page,wrapper);

        long total = page.getTotal();//总记录数
        List<EduCourse> records = page.getRecords(); //数据list集合

        return R.ok().data("total",total).data("rows",records);

    }


}

