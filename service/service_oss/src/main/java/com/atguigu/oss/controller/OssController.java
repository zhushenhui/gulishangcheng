package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zsh
 * @date 2020/6/20
 */
@Api(description="阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像的方法
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public R uploadOssFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
            // 获取上传文件 MultipartFile
            // 返回上传到oss的路径
        try {
            String url = ossService.uploadFileAvatar(file);
            return R.ok().data("url",url).message("文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().message("文件上传异常");
        }
    }
}
