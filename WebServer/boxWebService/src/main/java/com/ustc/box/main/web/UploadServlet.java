package com.ustc.box.main.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.google.gson.Gson;
import com.ustc.box.core.utils.PropertiesUtils;
import com.ustc.box.main.service.UserService;
import com.ustc.box.main.vo.ResponseBase;
import com.ustc.box.main.vo.UserResponse;

@SuppressWarnings("serial")
public class UploadServlet extends ServletBase{
	
	private static String path = PropertiesUtils.getProperty("img_path");
	private static String downLoadpath = PropertiesUtils.getProperty("img_downLoadpath");
	private static final Logger LOGGER = LogManager.getLogger(UploadServlet.class);
	
	@Autowired
	private UserService userService;
	
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}
	
	 public void doPost(HttpServletRequest request, HttpServletResponse response)  
	            throws ServletException, IOException { 
		 
			LOGGER.info(">>> doPost(request,response)");
			long oldTime = System.currentTimeMillis();
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("application/json");
			String tel = request.getParameter("tel");
			UserResponse user = null;
		    String result;
		    Gson gson = new Gson();
			ResponseBase resp = ResponseBase.getDefaultErrorResp();
			if(StringUtils.isNotEmpty(tel)){
				user = userService.getUserInfoByTel(tel);
			}
			if(StringUtils.isEmpty(tel)||user.getToken() == null){
				result =  gson.toJson(resp);
				LOGGER.info(String.format("返回消息：%s", result));
				output(response, result);
				long newTime = System.currentTimeMillis();
				LOGGER.info("<<< doPost(request,response)(time:"+(newTime-oldTime)+")");
				return ;
			}
			
	        request.setCharacterEncoding("utf-8");  
	        //获得磁盘文件条目工厂。  
	        DiskFileItemFactory factory = new DiskFileItemFactory();  
	        //获取文件上传需要保存的路径，upload文件夹需存在。  
	        //设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。  
	        factory.setRepository(new File(path));  
	        //设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。  
	        factory.setSizeThreshold(1024*1024);  
	        //上传处理工具类（高水平API上传处理？）  
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	          
	        try{  
	            //调用 parseRequest（request）方法  获得上传文件 FileItem 的集合list 可实现多文件上传。  
	            FileItemIterator fit = upload.getItemIterator(request);;  
	            while(fit.hasNext()){
	                	FileItemStream fs = fit.next();
	               //     String name = fs.getFieldName();  
	                    //获取路径名  
	               //     String value = fs.getName();  
	                    //取到最后一个反斜杠。  
	                 //   int start = value.lastIndexOf("\\");  
	                    //截取上传文件的 字符串名字。+1是去掉反斜杠。  
	                   // String filename = value.substring(start+1);  
	                    String fileExt = getFileNameExtension(fs.getName());
	            		String filename = UUID.randomUUID().toString() + "." + fileExt;
	                      
	                    /*第三方提供的方法直接写到文件中。 
	                     * item.write(new File(path,filename));*/  
	                    //收到写到接收的文件中。  
	                    OutputStream out = new FileOutputStream(new File(path,filename));  
	                    InputStream in = fs.openStream();  
	                      
	                    int length = 0;  
	                    byte[] buf = new byte[1024];  
	                      
	                    while((length = in.read(buf))!=-1){  
	                        out.write(buf,0,length);  
	                    }  
	                    in.close();  
	                    out.close();  
	                    user.setAvatar_url(downLoadpath+"/"+filename);
	                    userService.updateUserBaseInfo(user);
	                    resp.fillSuccessResp();
	                    resp.setRes(user);
	            }
	         
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	        
	    	// 5.把相应结果返回客户端
			result =  gson.toJson(resp);
			LOGGER.info(String.format("返回消息：%s", result));
			output(response, result);
			long newTime = System.currentTimeMillis();
			LOGGER.info("<<< doPost(request,response)(time:"+(newTime-oldTime)+")");
	          
	    }  
	 
	   /**
	     * 获取文件扩展名
	     * 
	     * @param fileName
	     * @return
	     */
	    public static String getFileNameExtension(String fileName) {

	        String fileExt = "";
	        if (!StringUtils.isEmpty(fileName)) {
	            int beginIndex = fileName.lastIndexOf(".");
	            if (beginIndex > 0) {
	                fileExt = fileName.substring(beginIndex + 1).intern();
	            }
	        }
	        return fileExt;
	    }
}
