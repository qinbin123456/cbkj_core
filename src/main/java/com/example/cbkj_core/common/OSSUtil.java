package com.example.cbkj_core.common;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云OSS 上传工具类
 */
public class OSSUtil {

    final static String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    final static String ACCESSKEYID = "LTAIinqNaC5ZG4bF";
    final static String ACCESSKEYSECRET = "NnfFpIO9sHqBuWJUmp8qbesyDAPXpN";
    final static String BUCKETNAME = "bucket-v1";

    public static Object uploadFile(MultipartFile file,String path)throws Exception{
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // 上传文件流。
        InputStream inputStream = file.getInputStream();

        StringBuffer key = new StringBuffer(path).append("/").append(UUID.randomUUID().toString());
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String saveName = String.format("%s%s",key ,suffix);
        PutObjectResult result =  ossClient.putObject(BUCKETNAME,saveName , inputStream);


        StringBuffer resultURL = new StringBuffer("http://").append(BUCKETNAME).append(".");
        resultURL.append(ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1)).append("/");
        resultURL.append(saveName);
        ossClient.shutdown();
        return resultURL;
    }
}
