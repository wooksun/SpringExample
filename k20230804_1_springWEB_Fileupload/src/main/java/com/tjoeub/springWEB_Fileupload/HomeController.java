package com.tjoeub.springWEB_Fileupload;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping("/fileUploadTest")
	public String fileUploadTest(HttpServletRequest request, Model model) {
		logger.info("컨트롤러의 fileUploadTest() 메소드 실행"); 
		
		return "fileUploadTest";
	}
	
	//	HttpServletRequest가 아닌 MultipartHttpServletRequest로 => fileUploadTest.jsp 파일의 enctype 속성이 multipart/form-data일 경우
	@RequestMapping("/fileUploadResult")
	public String fileUploadResult(MultipartHttpServletRequest request, Model model) {
		logger.info("컨트롤러의 fileUploadResult() 메소드 실행"); 
		
		//	업로드하는 파일이 저장될 업로드 디렉토리를 지정한다.
		//logger.info("{}", File.separator);
		String rootUploadDir = "C:" + File.separator + "upload"; // 파일경로 = C:\Upload
		//logger.info("{}", rootUploadDir);
		File dir = new File(rootUploadDir + File.separator + "testfile"); // 파일경로 = rootUploadDir(C:\Upload)의 하위폴더 C:\Upload\testfile
		
		//	업로드 디렉토리가 존재하지 않을 경우, 업로드 디렉토리를 만든다.
		//	File 클래스 객체 dir에 디렉토리가 존재하지 않을 경우
		//logger.info("{}", dir.exists());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		//	업로드되는 파일 정보 수집(2개: file1, file2)
		Iterator<String> iterator = request.getFileNames();
		String uploadFilename = "";
		MultipartFile multipartFile = null;
		String originalName = "";
		ArrayList<String> list = new ArrayList<String>();
		
		while(iterator.hasNext()) {
			uploadFilename = iterator.next();
			multipartFile = request.getFile(uploadFilename);
			originalName = multipartFile.getOriginalFilename();
			//logger.info("{}", originalName);
			
			if(originalName != null && originalName.length() != 0) {
				//	MultipartFile 인터페이스 객체에서 transferTo() 메소드로 File 객체를 만들어 업로드한다.
				try {
					//	C:\Upload\testfile\originalName
					multipartFile.transferTo(new File(dir + File.separator + originalName)); // 업로드하는 코드
					list.add("원본 파일명: " + originalName);
				} catch (Exception e) {
					e.printStackTrace();
					list.add("파일 업로드 중 에러 발생!");
				}
			}
		}
		
		model.addAttribute("list", list);
		return "fileUploadResult";
	}
}
