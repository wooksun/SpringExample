package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tjoeun.vo.CardVO;

public class TransactionDAO {

	//	base-package가 아닌 패키지에서 logger를 사용하려면, resources 폴더의 log4j.xml 파일에서 <!-- Application Loggers --> 부분을
	//	똑같이 복사하여 패키지를 알맞게 설정하여 logger를 사용할 수 있도록 한다.
	private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);
	
	//	JdbcTemplate 객체 선언
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	//	컨트롤러에서 구매정보를 갖고 넘어오는 메소드
	public void buyTicket(final CardVO cardVO) {
		logger.info("TransactionDAO 클래스의 buyTicket() 메소드 실행"); 
		//logger.info("{}", cardVO); 
		
		//	방법1
		String sql = "insert into card(consumerId, amount) values (?, ?)";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, cardVO.getConsumerId());
				ps.setString(2, cardVO.getAmount());
			}
		});
		
		//	방법2
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into ticket(consumerId, countnum) values (?, ?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, cardVO.getConsumerId());
				pstmt.setString(2, cardVO.getAmount());
				return pstmt;
			}
		});
	}
	
	

	
	
}
