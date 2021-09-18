package com.cyj.serviceedu.service;

import com.cyj.serviceedu.domain.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.serviceedu.domain.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-09-14
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllSubject();
}
