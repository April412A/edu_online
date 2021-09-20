package com.cyj.serviceedu.service.impl;

import com.cyj.servicebase.exceptionhandler.MyException;
import com.cyj.serviceedu.domain.EduCourse;
import com.cyj.serviceedu.domain.EduCourseDescription;
import com.cyj.serviceedu.domain.vo.CourseQuery;
import com.cyj.serviceedu.mapper.EduCourseMapper;
import com.cyj.serviceedu.service.EduCourseDescriptionService;
import com.cyj.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-20
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Override
    public String saveCourseInfo(CourseQuery courseQuery) {
        //向课程表添加课程基本信息
        //courseQuery对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseQuery,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert==1){
            System.out.println("success");
        }else{
            //添加失败
            throw new MyException(20001,"添加课程信息失败");
        }

        //获取添加之后课程id
        String id = eduCourse.getId();
        //向课程简介表添加课程简介 edu_course_description
        EduCourseDescription courseDescription = new EduCourseDescription();
        //设置描述id就是课程id
        courseDescription.setId(id);
        courseDescription.setDescription(courseQuery.getDescription());
        courseDescriptionService.save(courseDescription);

        return id;
    }
}
