package com.tjoeun.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.MvcBoardDAO;
import com.tjoeun.vo.MvcBoardVO;

public class ReplyService implements MvcBoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReplyService.class);

	@Override
	public void execute(MvcBoardVO boardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("ReplyService 클래스의 execute() 메소드 실행");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//	Model 인터페이스 객체에 저장되서 넘어온 HttpServletRequest 인터페이스 객체에서 원본 글번호, 글그룹, 글레벨,
		//	같은 글 그룹에서 글 출력순서, 답글 작성자 이름, 답글 제목, 답글 내용을 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
		int gup = Integer.parseInt(request.getParameter("gup"));
		int lev = Integer.parseInt(request.getParameter("lev"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO", MvcBoardDAO.class);
		
		//	HttpServletRequest 인터페이스 객체에서 받은 데이터를 MvcBoardVO 클래스의 bean에 넣어준다.
		MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
		mvcBoardVO.setIdx(idx);
		mvcBoardVO.setName(name);
		mvcBoardVO.setSubject(subject);
		mvcBoardVO.setContent(content);
		mvcBoardVO.setGup(gup);
		//	답글은 질문글 바로 아래에 위치해야 하므로 lev와 seq를 1씩 증가시켜 넣어준다.
		mvcBoardVO.setLev(lev + 1);
		mvcBoardVO.setSeq(seq + 1);
		
		//	답글이 삽입될 위치를 정하기 위해 조건에 만족하는 seq를 1씩 증가시키는 메소드를 실행한다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		//hmap.put("gup", mvcBoardVO.getGup()); // seq는 안되지만 gup는 가능
		hmap.put("gup", gup);
		hmap.put("seq", mvcBoardVO.getSeq());
		mvcBoardDAO.replyIncrement(hmap);
		
		//	답글을 저장하는 메소드를 실행한다.
		mvcBoardDAO.replyInsert(mvcBoardVO);
		
		//	답글 저장 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("currentPage", request.getParameter("currentPage"));
	}

}
