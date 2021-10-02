package com.cyj.serviceedu.controller;


import com.cyj.commonutils.R;
import com.cyj.serviceedu.domain.EduVideo;
import com.cyj.serviceedu.domain.EduVideo;
import com.cyj.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-20
 */
@RestController
@RequestMapping("/serviceedu/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //根据小节id查询
    @GetMapping("getVideoInfo/{VideoId}")
    public R getVideoInfo(@PathVariable String VideoId) {
        EduVideo eduVideo = videoService.getById(VideoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }
    
    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        videoService.removeById(id);
        return R.ok();
    }
    


}

