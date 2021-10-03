package com.cyj.serviceedu.mapper;

import com.cyj.serviceedu.domain.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.serviceedu.domain.vo.CoursePublishQuery;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-09-20
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishQuery getCoursePublishQuery(String cousreId);

}
