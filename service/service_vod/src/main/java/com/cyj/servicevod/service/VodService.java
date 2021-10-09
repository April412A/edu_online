package com.cyj.servicevod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
     String uploadVideo(MultipartFile file) ;
}
