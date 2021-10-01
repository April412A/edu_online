package com.cyj.serviceedu.controller;


import com.cyj.commonutils.R;
import com.cyj.serviceedu.domain.chapter.ChapterVo;
import com.cyj.serviceedu.service.EduChapterService;
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
@Api(tags="课程章节管理")
@RestController
@RequestMapping("/serviceedu/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //课程大纲列表,根据课程id进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideo(courseId);
        return R.ok().data("list",list);
    }

}

