package com.cyj.servicevod.controller;

import com.cyj.commonutils.R;
import com.cyj.servicevod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/servicevod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }
}
