package com.tjoeun.springWEB_Transaction;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tjoeun.dao.TransactionDAO;
import com.tjoeun.vo.CardVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//	TransactionDAO 클래스의 메소드를 실행하기 위해서 servlet-context.xml 파일에서 설정한 bean을 얻어오기 위해서
	//	TransactionDAO 객체를 선언한다.
	private TransactionDAO dao;
	
	//	@Autowired 어노테이션을 이용해서 servlet-context.xml 파일에서 설정한 TransactionDAO 클래스의 bean을 얻어온다.
	@Autowired
	public void setDao(TransactionDAO dao) {
		logger.info("안녕하세요! 최진욱입니다.");
		this.dao = dao;
	}
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}

	@RequestMapping("/ticket")
	public String ticket(HttpServletRequest request, Model model) {
		logger.info("컨트롤러의 ticket() 메소드 실행");
		
		return "ticket";
	}
	
	@RequestMapping("/ticketCard")
	public String ticketCard(HttpServletRequest request, Model model, CardVO cardVO) {
		logger.info("컨트롤러의 ticketCard() 메소드 실행");
		
		dao.buyTicket(cardVO);
		
		return "ticketEnd";
	}
	
}
