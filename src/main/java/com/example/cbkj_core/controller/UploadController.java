package com.example.cbkj_core.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.DateUtil;
import com.example.cbkj_core.common.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Controller
public class UploadController {

    @Value("${file.address}")
    private String location;

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 上传到阿里云OSS
     * @param file
     * @return
     */
    @RequestMapping(value = "upload/post2", method = RequestMethod.POST)
    @ResponseBody
    public Object fileUpload(MultipartFile file){
        Object obj = null;
        try {
            obj = OSSUtil.uploadFile(file,"cbkj");
            return new ResEntity(true,"SUCCESS",obj);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResEntity(false,"上传服务异常",e.getMessage());
        }
    }

    /**
     * 用一个tomcat 作为图片服务器 上传
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "upload/post", method = RequestMethod.POST)
    @ResponseBody
    public Object handleFileUpload(HttpServletRequest request, MultipartFile file) {

        if(file.isEmpty()){
            return new ResEntity(false,"未选择文件",null);
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        StringBuffer sb = new StringBuffer("ideaFile").append("/")
                .append(DateUtil.getDateFormats(DateUtil.date2,null))
                .append("/");
        String filename = String.format("%s%s", UUID.randomUUID().toString(),suffix);
        sb.append(filename);
        File dest = new File(String.format("%s%s", location,sb.toString()));
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest); //保存文件
            return new ResEntity(true,"SUCCESS",sb.toString());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResEntity(false,"服务异常",null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResEntity(false,"服务异常",null);
        }
    }

    /**
     * 前往图片上传页面
     * @return
     */
    @RequestMapping("upload/index")
    public String toUpload(){
        return "test/uploadP";
    }
}
