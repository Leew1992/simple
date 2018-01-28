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
import org.simple.annotation.MonitorAccess;
import org.simple.constant.FileConst;
import org.simple.constant.ResponseConst;
import org.simple.dto.AttachmentDTO;
import org.simple.dto.ImageDTO;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.AttachmentDO;
import org.simple.entity.PostAttachmentDO;
import org.simple.entity.UserDO;
import org.simple.exception.WebException;
import org.simple.service.AttachmentService;
import org.simple.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 附件控制器
 */
@Controller
@RequestMapping("/attachment")
public class AttachmentController extends BaseController {
	
	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	@Autowired
	private AttachmentService attachService;
	
	@Autowired
	private PostService postService;
	
	
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
	public PageDTO pagingAttachments(AttachmentDTO attachmentDTO, QueryDTO queryDTO) {
		return attachService.pagingAttachments(attachmentDTO, queryDTO);
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
	
	@MonitorAccess
	@RequestMapping("/uploadImage.do")
	@ResponseBody
	public ImageDTO uploadImage(String idPost, MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		ImageDTO imageDTO = new ImageDTO();
         if(file!=null){
			//获取时间的路径  
			Date date = new Date();
			String realPath = request.getSession().getServletContext().getRealPath("/").split("webapps")[0];
			String fileDir = realPath + "assets/images/";
			String fileName = date.getTime() + FileConst.Format.POINT_JPG;
			File dir = new File(fileDir);  
			
			if(!dir.exists()){  
			    dir.mkdirs();  
			}  
			//先把用户上传到原图保存到服务器上  
			try {
				file.transferTo(new File(dir, fileName));
			} catch (IllegalStateException | IOException e) {
				throw new WebException(e);
			}  
			imageDTO.setResult(fileDir + fileName);
			imageDTO.setMessage("success");
			
			// 获取用户信息
			UserDO userDO = getCurrentUser();
			
			// 保存附件信息
			AttachmentDO attachmentDO = new AttachmentDO();
			attachmentDO.setIdUser(userDO.getIdUser());
			attachmentDO.setAttachName(file.getOriginalFilename());
			attachmentDO.setAttachPath(fileDir + fileName);
			attachmentDO.setAttachSize(file.getSize());
			attachmentDO.setAttachSource("");
			attachmentDO.setMediaType(file.getContentType());
			attachService.saveAttachment(attachmentDO);
			
			// 保存贴子附件信息
			if(StringUtils.isNotEmpty(idPost)) {
				PostAttachmentDO postAttachmentDO = new PostAttachmentDO();
				postAttachmentDO.setIdAttachment(attachmentDO.getIdAttachment());
				postAttachmentDO.setIdPost(idPost);
				postService.savePostAttachment(postAttachmentDO);
			}
        }
		
		return imageDTO;
	}

	/**
	 * 获取图片
	 */
	@RequestMapping("/getImage.do")
	@ResponseBody
	public byte[] getImage(String filePath) {
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
			throw new WebException(e);
		}
	}
	
	/**
	 * 附件下载
	 */
	@RequestMapping("/downloadAttachment.do")
	public void download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String filePath=request.getParameter("filePath");
		
		String fileName = getFileName(filePath);
		String fileType=fileName.substring(fileName.lastIndexOf('.')+1, fileName.length());
		
		response.setHeader(CONTENT_DISPOSITION, "attachment;filename="+ new String(fileName.getBytes(FileConst.Encoding.GBK),FileConst.Encoding.ISO88591));
		if (FileConst.Format.DOC.equals(fileType) || FileConst.Format.DOCX.equals(fileType)) {
			response.setContentType(ResponseConst.ContentType.WORD);
		} else if(FileConst.Format.XLS.equals(fileType) || FileConst.Format.XLSX.equals(fileType)) {
			response.setContentType(ResponseConst.ContentType.EXCEL);
		}
		
		File file = new File(filePath);
		if(file.exists()){
			response.getOutputStream().write(FileUtil.readAsByteArray(file));
		}else{
			response.getOutputStream().write("".getBytes());
		}
	}
	
	/**
	 * 获取文件名
	 */
	private String getFileName(String filePath) {
		String[] filePathArr = filePath.split(File.separator);
		return filePathArr[filePathArr.length-1];
	}
}