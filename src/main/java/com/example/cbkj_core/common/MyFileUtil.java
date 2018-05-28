package com.example.cbkj_core.common;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public class MyFileUtil {
	/**
	 * 文件上传单个
	 * @param request
	 * @param destPath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object uploadFile(MultipartFile file, HttpServletRequest request, String destPath,String rootPath) throws Exception{

		request.setCharacterEncoding("utf-8");

		StringBuffer sb = new StringBuffer();
		if(null == destPath || "".equals(destPath.trim())){
			destPath = "default";
		}

		StringBuffer cPath=new StringBuffer("/yunCoreUpload/"+destPath+"/");
		cPath.append(DateUtil.getDateFormats(DateUtil.date2,null));
		File dirFile = new File(rootPath,cPath.toString());
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		//上传文件名称
		String filename = file.getOriginalFilename();
		//处理文件名的绝对路径问题
		int index = filename.lastIndexOf("\\");
		if(index!=-1){
			filename=filename.substring(index+1);
		}
		//文件添加uuid前缀，处理同名问题
		String savename=IDUtil.getID()+"_"+filename;
		File destFile=new File(dirFile,savename);
		FileOutputStream out = new FileOutputStream(destFile);
		out.write(file.getBytes());

		sb.append(cPath.toString()).append("/"+savename).append(",");

		if(sb!=null&&sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
		return null;
	}

}
