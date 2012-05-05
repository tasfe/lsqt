package com.lsqt.modules.resource.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用于测式文件下传
 * @author dell
 *
 */
@Controller
@RequestMapping("/upload.sp")
public class FileUploadController {
	@RequestMapping("/imageUpload")  
	   public String processImageUpload( 
	     @RequestParam("imageFile") MultipartFile image) throws IOException {  

	      System.out.println(image.getBytes().length);  
	      
	      image.transferTo(new File("C:/Temp/temp/ttt"+image.getOriginalFilename()));
	     return "imageList";  
	   }
}
