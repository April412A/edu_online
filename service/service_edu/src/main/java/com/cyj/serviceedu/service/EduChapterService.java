package com.cyj.serviceedu.service;

import com.cyj.serviceedu.domain.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.serviceedu.domain.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-09-20
 */
public interface EduChapterService extends IService<EduChapter> {

    //课程大纲列表,根据课程id进行查询
    List<ChapterVo> getChapterVideo(String courseId);
}
