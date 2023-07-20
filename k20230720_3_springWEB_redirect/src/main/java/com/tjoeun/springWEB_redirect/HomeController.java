package com.tjoeun.springWEB_redirect;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping("/confirm")
	   public String confirm(HttpServletRequest request, Model model) {
	      logger.info("컨트롤러의 confirm() 메소드 실행");
	      String id = request.getParameter("id");
	      logger.info("{}", id);
	      model.addAttribute("id", id);
	      
	      // id가 "abc"면 idOK.jsp를 브라우저에 표시하고 "abc"가 아니면 idNG.jsp를 브라우저에 표시한다.
	      if(id.equals("abc")) {
	    	  // 아래와 같이 그냥 리턴시키면 "/WEB-INF/views/" + "idOK" + ".jsp"가 연결되서 브라우저에
	    	  // idOK.jsp가 나타난다.
	    	  // return "idOK"; // 뷰 페이지 이름
	    	  
	    	  // 아래와 같이 "redirect:"를 붙여서 리턴시키면 "/WEB-INF/views/" 폴더의 confirmOK.jsp를 호출하는 것이 아니고,
	    	  // 컨트롤러의 @RequestMapping("/confirmOK") 어노테이션이 붙어있는 메소드를 호출한다.
	    	  return "redirect:confirmOK"; // 컨트롤러에 새로운 요청을 한다.
	      } else {
	    	  return"idNG";
	      }
	}
	
	@RequestMapping("/confirmOK")
	public String confirmOK(HttpServletRequest request, Model model) {
		logger.info("컨트롤러의 confirmOK() 메소드 실행");
		
		return "idOK";
	}
}
