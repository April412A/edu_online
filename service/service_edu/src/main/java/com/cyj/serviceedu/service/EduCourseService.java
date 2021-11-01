package com.cyj.serviceedu.service;

import com.cyj.serviceedu.domain.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.serviceedu.domain.vo.CoursePublishQuery;
import com.cyj.serviceedu.domain.vo.CourseQuery;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-09-20
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程信息
    String saveCourseInfo(CourseQuery courseQuery);

    //根据课程ID获取课程信息
    CourseQuery getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseQuery courseQuery);

    //根据课程id查询课程确认信息（CoursePublishQuery)
    CoursePublishQuery getCoursePublishQuery(String courseId);

    //删除课程
    void removeCourse(String courseId);
}
