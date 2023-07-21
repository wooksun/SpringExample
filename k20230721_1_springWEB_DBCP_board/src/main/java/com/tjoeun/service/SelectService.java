package com.tjoeun.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.MvcBoardDAO;
import com.tjoeun.vo.MvcBoardVO;

public class SelectService implements MvcBoardService {

	private static final Logger logger = LoggerFactory.getLogger(SelectService.class);
	
	@Override
	public void execute(MvcBoardVO boardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("SelectService 클래스의 execute() 메소드 실행");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		//	logger.info("{}", request.getParameter("currentPage"));
		
		//	MvcBoardDAO 클래스의 bean을 얻어온다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO", MvcBoardDAO.class);
		
		//	브라우저에 출력할 글의 개수를 정한다.
		int pageSize = 10;
		//	 HttpServletRequest 인터페이스 객체에 저장되서 넘어오는 화면에 표시할 페이지 번호를 받는다.
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) { e.printStackTrace(); }
		
		//	테이블에 저장된 전체 글의 개수를 얻어오는 메소드를 실행한다.
		int totalCount = mvcBoardDAO.selectCount();
		logger.info("전체 글의 개쉬: {}", totalCount);
		
	}

}
