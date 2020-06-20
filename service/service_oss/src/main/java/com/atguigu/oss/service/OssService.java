package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author zsh
 * @date 2020/6/20
 */
public interface OssService {
    /**
     * 上传头像到oss
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file) throws IOException;
}
