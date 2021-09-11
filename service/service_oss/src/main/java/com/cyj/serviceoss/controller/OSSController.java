package com.cyj.serviceoss.controller;

import com.cyj.commonutils.R;
import com.cyj.serviceoss.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/serviceoss/fileoss")
@CrossOrigin
public class OSSController {

    @Autowired
    public OSSService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
