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

public class UpdateService implements MvcBoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateService.class);

	@Override
	public void execute(MvcBoardVO boardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("UpdateService 클래스의 execute() 메소드 실행");
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		logger.info("수정할 글번호: ", request.getParameter("idx") + ", 제목: " + request.getParameter("subject") + 
				", 내용: " + request.getParameter("content"));
		
		//	Model 안터페이스 객체에 저장되서 넘어온 HttpServletRequest 인터페이스 객체에서 수정할 글번호화 데이터를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		//	넘겨받은 데이터로 글 1건을 수정하는 메소드를 실행한다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO", MvcBoardDAO.class);
		
		//	방법1 
		mvcBoardDAO.update(idx, subject, content);
		
		//	방법2
		MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
		mvcBoardVO.setIdx(idx);
		mvcBoardVO.setSubject(subject);
		mvcBoardVO.setContent(content);
		mvcBoardDAO.update(mvcBoardVO);
		
		//	글 수정 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("currentPage", request.getParameter("currentPage"));
	}

}
