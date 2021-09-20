package com.cyj.serviceedu.service;

import com.cyj.serviceedu.domain.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.serviceedu.domain.vo.CourseQuery;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-09-20
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseQuery courseQuery);
}
