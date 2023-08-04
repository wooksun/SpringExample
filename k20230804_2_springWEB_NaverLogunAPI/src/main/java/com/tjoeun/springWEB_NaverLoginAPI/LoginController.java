package com.tjoeun.springWEB_NaverLoginAPI;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.tjoeun.springWEB_NaverLoginAPI.oauth.NaverLoginBO;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	//	NaverLoginBO
	private NaverLoginBO naverLoginBO;
	private String apiResult;
	
	@Autowired
	public void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}
	
	//	로그인 첫 화면 요청 메소드
	@RequestMapping("/login")
	public String login(HttpServletRequest request ,Model model, HttpSession session) {
		logger.info("LoginController의 login() 메소드 실행");
		//	네이버 아이디로 인증 URL을 생성하기 위해서 NaverLoginBO 클래스의 getAuthorizationUrl() 메소드를 호출한다.
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		//logger.info("네이버: {}", naverAuthUrl);
		model.addAttribute("url", naverAuthUrl);
		
		return "login";
	}
	
	//	네이버 로그인 성공시 Callback 호출 메소드
	@RequestMapping("/callback")
	public String callback(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam String code, @RequestParam String state) throws IOException, ParseException {
		logger.info("LoginController의 callback() 메소드 실행");
		//String code = request.getParameter("code");
		//String state = request.getParameter("state");
		//logger.info("code: {}", code);
		//logger.info("state: {}", state);
		
		OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
		
		//	로그인 사용자 정보를 얻어온다.
		apiResult = naverLoginBO.getUserProfile(oauthToken);
		logger.info("apiResult: {}", apiResult);
		
		//	String 형식인 로그인 사용자 정보를 json 형태로 바꾼다.
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		
		//	top 레벨 단계 데이터 파싱 - response
		JSONObject response_obj = (JSONObject) jsonObj.get("response");
		logger.info("response_obj: {}", response_obj);
		//	top 레벨 단계 데이터 파싱 결과에서 name만 파싱
		String name = (String) response_obj.get("name");
		logger.info("name: {}", name);
		
		//	파싱된 값을 세션에 저장한다.
		session.setAttribute("sessionId", response_obj);
		
		model.addAttribute("result", apiResult);
		
		return "naverSuccess";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, Model model, HttpSession session) {
		logger.info("LoginController의 logout() 메소드 실행");
		session.invalidate();
		return "redirect:login";
	}
}
