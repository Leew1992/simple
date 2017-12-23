package org.simple.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.util.FileUtil;
import org.simple.dto.AttachmentDTO;
import org.simple.dto.ImageDTO;
import org.simple.dto.Page;
import org.simple.dto.ResultDTO;
import org.simple.entity.AttachmentDO;
import org.simple.service.AttachmentService;
import org.simple.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 附件Controller
 */
@Controller
@RequestMapping("/attachment")
public class AttachmentController extends BaseController {
	
	@Autowired
	private AttachmentService attachService;
	
	/**
	 * 获取附件信息
	 */
	@RequestMapping("/getAttachmentById.do")
	@ResponseBody
	public AttachmentDO getAttachmentById(String idAttachment) {
		return attachService.getAttachmentById(idAttachment);
	}
	
	/**
	 * 获取附件分页列表
	 */
	@RequestMapping("/pagingAttachments.do")
	@ResponseBody
	public Page<AttachmentDO> pagingAttachments(AttachmentDTO attachmentDTO) {
		return attachService.pagingAttachments(attachmentDTO);
	}
	
	/**
	 * 保存附件信息
	 */
	@RequestMapping("/saveAttachment.do")
	@ResponseBody
	public ResultDTO saveAttachment(AttachmentDO attachmentDO) {
		return attachService.saveAttachment(attachmentDO);
	}
	
	/**
	 * 删除附件信息
	 */
	@RequestMapping("/deleteAttachment.do")
	@ResponseBody
	public ResultDTO deleteAttachment(String idAttachment) {
		return attachService.deleteAttachment(idAttachment);
	}
	
	@RequestMapping("/uploadAvatar.do")
	@ResponseBody
	public ImageDTO uploadUserAvatar(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		ImageDTO imageDTO = new ImageDTO();
		try {
	         if(file!=null){
	             try{
		             //获取时间的路径  
		             Date date = new Date();             
		             String realPath = "D:/attach/";
		             File dir = new File(realPath);  
		             
		             if(!dir.exists()){  
		                 dir.mkdirs();  
		             }  
		             //先把用户上传到原图保存到服务器上  
		             file.transferTo(new File(dir, date.getTime() + ".jpg"));  
		             imageDTO.setResult(realPath + date.getTime()+".jpg");
		             imageDTO.setMessage("success");
	             }catch (Exception e) {  
	                 e.printStackTrace();  
	            }  
	         }  
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return imageDTO;
	}

	/**
	 * 图片上传
	 */
	@RequestMapping("/uploadImage.do")
	@ResponseBody
	public ImageDTO updateUserLogo(@RequestParam(value = "avatar_data") String avatarData,
			@RequestParam(value = "avatar_file") MultipartFile avatarFile, HttpServletRequest request, HttpServletResponse response) {
		ImageDTO imageDTO = new ImageDTO();
		try {
			JSONObject jObject = JSON.parseObject(avatarData);
			//获取服务器的实际路径  
	        //String realPath = request.getSession().getServletContext().getRealPath("/").split("webapps")[0];
	        String realPath = "D:/attach/";
	        int x = (int)Math.floor(jObject.getDouble("x"));
	        int y = (int)Math.floor(jObject.getDouble("y"));
	        int width = (int)Math.floor(jObject.getDouble("width"));
	        int height = (int)Math.floor(jObject.getDouble("height"));
	        int rotate = (int)Math.floor(jObject.getDouble("rotate"));
	      
	         if(avatarFile!=null){  
	             try{  
	             //获取时间的路径  
	             Date date=new Date();             
	             String resourcePath = "avatar/";
	             //String path = request.getServletPath();
	             File dir=new File(realPath+resourcePath);  
	             
	             if(!dir.exists()){  
	                 dir.mkdirs();  
	             }  
	             //先把用户上传到原图保存到服务器上  
	             File file=new File(dir,date.getTime()+".jpg");  
	             avatarFile.transferTo(file);  
	             if(file.exists()){  
	            	 String src = realPath+resourcePath+date.getTime();
	                 boolean[] flag=new boolean[1];  
	                 //旋转后剪裁图片  
	                 flag[0]=ImageUtils.cutAndRotateImage(src+".jpg", src+"_s.jpg", x, y, width, height, rotate);  
	                 if(flag[0]){
	                	 System.out.println("successful");
	                 }  
	             }
	             imageDTO.setResult(realPath + resourcePath+date.getTime()+"_s.jpg");
	             imageDTO.setMessage("success");
	             }catch (Exception e) {  
	                 e.printStackTrace();  
	            }  
	         }  
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return imageDTO;
	}
	
	/**
	 * 获取图片
	 */
	@RequestMapping("/getAvatar.do")
	@ResponseBody
	public byte[] getAvatar(String filePath) {
		 try {
			 File file =new File(filePath);
			 if(StringUtils.isEmpty(filePath)) {
			    throw new NullPointerException("无效的文件路径");
			 }
			 long len = file.length();
			 byte[] bytes = new byte[(int)len];

			 BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
			 int r = bufferedInputStream.read( bytes );
			 if (r != len) {
				 bufferedInputStream.close();
				 throw new IOException("读取文件不正确");	    	 
			 }
			 bufferedInputStream.close();

			 return bytes;
		} catch (Exception e) {
			 return e.getMessage().getBytes();
		}
	}
	
	/**
	 * 附件下载
	 */
	@RequestMapping("/downloadAttachment.do")
	public void download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String filePath=request.getParameter("filePath");
		
		String fileName = getFileName(filePath);
		String fileType=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		
		if (("doc".equals(fileType) || "docx".equals(fileType))) {
			// word
			response.setContentType("application/vnd.ms-word;charset=UTF-8");
			//文件名中已包含后缀
			response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("GBK"),"iso-8859-1"));
		} else if(("xls".equals(fileType) || "xlsx".equals(fileType))) {
			// excel
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			//文件名中已包含后缀
			response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GBK"),"iso-8859-1"));
		}else{
			response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GBK"),"iso-8859-1"));
		}
		
		File file = new File(filePath);
		if(file.exists()){
			response.getOutputStream().write(FileUtil.readAsByteArray(file));
		}else{
			response.getOutputStream().write("".getBytes());
		}
	}
	
	private String getFileName(String filePath) {
		String[] filePathArr = filePath.split("/");
		String fileName = filePathArr[filePathArr.length-1];
		return fileName;
	}
}