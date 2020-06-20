package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author zsh
 * @date 2020/6/20
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传头像到oss
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCSEE_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 获取上传文件输入流
        InputStream inputStream = file.getInputStream();

        // 获取文件名称
        String fileName = file.getOriginalFilename();

        // 在文件名中添加唯一随机值
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        fileName = uuid + "_" + fileName;

        // 获取当前日期：2020/06/20
        String datePath = new DateTime().toString("yyyy/MM/dd");
        fileName = datePath + "/" +fileName;

        /**
         * 调用oss方放实现上传
         * 第一个参数 Bucket名称
         * 第一个参数上传到oss文件路径和文件名称  /aa/bb/1.jpg
         */
        ossClient.putObject(bucketName, fileName, inputStream);

        // 关闭OSSClient
        ossClient.shutdown();

        // 把返回的值即上传到阿里员oss的路径手动拼接出来
        // 如：https://guli-edu-1010-zhu.oss-cn-beijing.aliyuncs.com/01.jpg
        String url = "http://" + bucketName + "." + endpoint + "/"+ fileName;
        return url;
    }
}
