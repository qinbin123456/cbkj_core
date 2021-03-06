package com.example.cbkj_core.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
/**
 * 图片工具类
 */
public class ImageUtil {


	/**
	 * 获取图片的每个RGB值的出现HashMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getImageHashMap(BufferedImage bufferedImage){
		HashMap hs=new HashMap();
		int height2 = bufferedImage.getHeight();
		int width2 = bufferedImage.getWidth();

		System.out.println("width:"+width2+"----height:"+height2);

		int width=0,height=0;
		if(width==0){
			width=500;
		}
		if(height==0){
			height=500;
		}

		int imgWidth = width;
		int imgHeight = height;

		if (height2 >= width2) {
			imgWidth = (int) Math.round(((width * 1.0 / height2) * width2));
		} else {
			imgHeight = (int) Math.round(((height * 1.0 / width2) * height2));
		}


		BufferedImage newImage = new BufferedImage(imgWidth, imgHeight, bufferedImage.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(bufferedImage, 0, 0, imgWidth, imgHeight, null);
		g.dispose();
		width=newImage.getWidth();
		height=newImage.getHeight();
		System.out.println("width:"+width+"----height:"+height);
		//3024*4032 
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				double rgb=bufferedImage.getRGB(i,j);
				if(hs.get(rgb)==null){
					hs.put(rgb, 1);
				}else{
					Object ob=hs.get(rgb);
					int l=Integer.valueOf(ob.toString())+1;
					hs.put(rgb,l);
				}
			}
		}
		return hs;
	}
	/**
	 *  a*b=|a|*|b|*cos<a,b>  
	 * @param RGBMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap VectorNormalizing(HashMap RGBMap){
		//求图片特征向量
		double ModulaLength=0;
		for(Object i:RGBMap.keySet()) {
			//先将所有平方相加  
			ModulaLength+=Math.pow((double)(int)RGBMap.get(i), 2);
		}
		//求平方根
		ModulaLength=Math.sqrt(ModulaLength);
		//将图片特征向量1标准化 
		for(Object i:RGBMap.keySet()) {
			double a=(double)(int) RGBMap.get(i);
			a=a/ModulaLength;
			RGBMap.put(i, a);
		}
		return RGBMap;
	}

	@SuppressWarnings("rawtypes")
	public static float compareImage(BufferedImage bufferedImage1,BufferedImage bufferedImage2){

		Long startTime=System.currentTimeMillis();
		System.out.println("第一阶段开始时间："+startTime);

		HashMap RGBMap1=getImageHashMap(bufferedImage1);
		HashMap RGBMap2=getImageHashMap(bufferedImage2);


		Long endTime=System.currentTimeMillis();
		System.out.println("第一阶段结束时间："+endTime+"-----共用时："+(endTime-startTime)+"毫秒");

		Long startTime2=System.currentTimeMillis();
		System.out.println("第二阶段开始时间："+startTime2);

		RGBMap1=VectorNormalizing(RGBMap1);
		RGBMap2=VectorNormalizing(RGBMap2);

		Long endTime2=System.currentTimeMillis();
		System.out.println("第二阶段结束时间："+endTime2+"-----共用时："+(endTime2-startTime2)+"毫秒");

		float similarity=0;

		for(Object i:RGBMap1.keySet()){
			double value2;
			if(RGBMap2.get(i)==null){
				value2=0;
			}else{
				value2=(double) RGBMap2.get(i);
			}
			double value1=(double) RGBMap1.get(i);
			similarity+=value1*value2;
		}
		return similarity*100;
	}





	/**
	 * 获取图片每个像素的颜色
	 * @param filePath
	 * @return
	 */
	public static int[][] getImageGRB(String filePath) {
		File file  = new File(filePath);
		int[][] result = null;
		if (!file.exists()) {
			return result;
		}
		try {
			BufferedImage bufImg = ImageIO.read(file);
			int height = bufImg.getHeight();
			int width = bufImg.getWidth();
			result = new int[width][height];
			//			FileWriter writer = new FileWriter("d://img.txt", false);  
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					result[i][j] = bufImg.getRGB(i, j) & 0xFFFFFF;
					//    System.out.println(bufImg.getRGB(i, j) & 0xFFFFFF);
					//					writer.write((bufImg.getRGB(i, j))+"\t");  
				}
			}
			//			writer.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//TODO
	/**
	 * 给图片添加水印、可设置水印图片旋转角度
	 * @param iconPath 水印图片路径
	 * @param srcImgPath  源图片路径
	 * @param targerPath 目标图片路径
	 * @param degree 水印图片旋转角度
	 * @param width  宽度(与左相比)
	 * @param height  高度(与顶相比)
	 * @param clarity  透明度(小于1的数)越接近0越透明
	 */
	public static void waterMarkImageByIcon(String logoText, String iconPath, String srcImgPath, String targerPath,Integer degree, Integer width, Integer height, float clarity) {
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			int srcWidth=srcImg.getWidth(null);
			int srcHeight=srcImg.getHeight(null);
			//获取图片宽高
			System.out.println("srcWidth:" + srcWidth);
			System.out.println("srcHeight:" + srcHeight);
			BufferedImage buffImg = new BufferedImage(srcWidth,srcHeight, BufferedImage.TYPE_INT_RGB);
			// 得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			if (null != degree) {
				// 设置水印旋转
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}
			// 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(iconPath);
			// 得到Image对象。
			Image img = imgIcon.getImage();
			float alpha = clarity; // 透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			//计算水印图片的位置
			//          g.drawImage(img, width, height, null);
			g.drawImage(img, srcWidth- img.getWidth(null), srcHeight - img.getHeight(null), null);

			//          g.drawString(logoText, srcImg.getWidth(null) - img.getWidth(null), srcImg.getHeight(null) - img.getHeight(null));

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			os = new FileOutputStream(targerPath);
			// 生成图片
			ImageIO.write(buffImg, "JPG", os);
			System.out.println("添加水印图片完成!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给图片添加水印、可设置水印文字旋转角度
	 * @param logoText 水印文字
	 * @param srcImgPath  源图片路径
	 * @param targerPath  目标图片路径
	 * @param degree  水印图片旋转角度
	 * @param width  宽度(与左相比)
	 * @param height   高度(与顶相比)
	 * @param clarity   透明度(小于1的数)越接近0越透明
	 */
	public static void waterMarkByText(String logoText, String srcImgPath, String targerPath, Integer degree, Integer width,Integer height, Float clarity) {
		// 主图片的路径
		InputStream is = null;
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// 得到画笔对象
			// Graphics g= buffImg.getGraphics();
			Graphics2D g = buffImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			if (null != degree) {
				// 设置水印旋转
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}
			// 设置颜色(默认灰色)
			g.setColor(Color.gray);
			// 设置 Font
			g.setFont(new Font("宋体", Font.BOLD, 30));
			float alpha = clarity;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
			g.drawString(logoText, width, height);
			g.dispose();
			os = new FileOutputStream(targerPath);
			// 生成图片
			ImageIO.write(buffImg, "JPG", os);
			System.out.println("添加水印文字完成!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is)
					is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 图片缩放(图片等比例缩放为指定大小，空白部分以白色填充)
	 * @param srcPath 源图片路径
	 * @param destPath  缩放后图片路径
	 */
	public static void zoomImage(String srcPath, String destPath, int destHeight, int destWidth) {
		try {
			BufferedImage srcBufferedImage = ImageIO.read(new File(srcPath));
			int imgWidth = destWidth;
			int imgHeight = destHeight;
			int srcWidth = srcBufferedImage.getWidth();
			int srcHeight = srcBufferedImage.getHeight();
			if (srcHeight >= srcWidth) {
				imgWidth = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
			} else {
				imgHeight = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
			}
			BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, destWidth, destHeight);
			graphics2D.drawImage(srcBufferedImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH), (destWidth / 2)- (imgWidth / 2), (destHeight / 2) - (imgHeight / 2), null);
			graphics2D.dispose();
			ImageIO.write(destBufferedImage, "JPEG", new File(destPath));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 压缩图片
	 * @param imageFile
	 * @param newPath
	 * @param bufferedImage
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	private static void zoomImageUtils(File imageFile, String newPath, BufferedImage bufferedImage, int width, int height)
			throws IOException{
		String fileName=imageFile.getName();
		String suffix =fileName.substring(fileName.lastIndexOf(".")+1);
		// 处理 png 背景变黑的问题
		if(suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))){
			BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = to.createGraphics();
			to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			g2d.dispose();

			g2d = to.createGraphics();
			Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose();

			ImageIO.write(to, suffix, new File(newPath));
		}else{

			BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
			Graphics g = newImage.getGraphics();
			g.drawImage(bufferedImage, 0, 0, width, height, null);
			g.dispose();
			ImageIO.write(newImage, suffix, new File(newPath));
		}
	}
	/**
	 *
	 * @param imageFile
	 * @param newPath
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static String zoomImageS(File imageFile, String newPath, int width, int height) throws IOException {
		if (imageFile != null && !imageFile.canRead())
			return null;
		BufferedImage bufferedImage = ImageIO.read(imageFile);
		if (null == bufferedImage)
			return null;

		if(width==0){
			width=1000;
		}

		if(height==0){
			height=1000;
		}
		int imgWidth = width;
		int imgHeight = height;

		int srcWidth = bufferedImage.getWidth();
		int srcHeight = bufferedImage.getHeight();
		String dataSize=srcWidth+"*"+srcHeight;
		if (srcHeight >= srcWidth) {
			imgWidth = (int) Math.round(((width * 1.0 / srcHeight) * srcWidth));
		} else {
			imgHeight = (int) Math.round(((height * 1.0 / srcWidth) * srcHeight));
		}
		zoomImageUtils(imageFile, newPath, bufferedImage, imgWidth, imgHeight);
		return dataSize;
	}
	/**
	 * 判断文件是否翻转
	 * @param file
	 * @return
	 */
	public static int getAngel(File file){
		int angel = 0;
		Metadata metadata;
		try{
			metadata = JpegMetadataReader.readMetadata(file);
			Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
			if(directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)){
				int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
				if(6 == orientation ){
					angel = 90;
				}else if( 3 == orientation){
					angel = 180;
				}else if( 8 == orientation){
					angel = 270;
				}
			}
		} catch(JpegProcessingException e){
			e.printStackTrace();
		} catch(MetadataException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return angel;
	}
	/**
	 * 计算旋转参数
	 * @param src
	 * @param angel
	 * @return
	 */
	public static Rectangle CalcRotatedSize(Rectangle src,int angel){
		// if angel is greater than 90 degree,we need to do some conversion.  
		if(angel > 90){
			if(angel / 9%2 ==1){
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}
		double r = Math.sqrt(src.height * src.height + src.width * src.width ) / 2 ;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new Rectangle(new Dimension(des_width, des_height));
	}

	public static void imageFn(File destFile,int angel) throws IOException{
		BufferedImage src=ImageIO.read(destFile);
		String fileName=destFile.getName();
		String suffix =fileName.substring(fileName.lastIndexOf(".")+1);
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

		BufferedImage  bi = new BufferedImage(rect_des.width, rect_des.height,BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = bi.createGraphics();

		g2.translate((rect_des.width - src_width) / 2,  (rect_des.height - src_height) / 2);

		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

		g2.drawImage(src, null, null);

		try {
			ImageIO.write(bi,suffix,destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)throws Exception{
		File file=new File("d://testImage/c.JPG");
		BufferedImage bufferedImage = ImageIO.read(file);

		File file2=new File("d://testImage/d.JPG");
		BufferedImage bufferedImage2 = ImageIO.read(file2);
		Long startTime=System.currentTimeMillis();
		System.out.println("总开始时间："+startTime);
		//测试图片相似度
		System.out.println("相似度："+compareImage(bufferedImage,bufferedImage2));
		Long endTime=System.currentTimeMillis();
		System.out.println("总结束时间："+endTime+"-----共用时："+(endTime-startTime)+"毫秒");


	}
}
