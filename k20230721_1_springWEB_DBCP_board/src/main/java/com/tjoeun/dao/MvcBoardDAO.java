package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tjoeun.vo.MvcBoardVO;

public class MvcBoardDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(MvcBoardDAO.class);
	
	//	DBCP에 사용할 DataSouece 인터페이스 객체를 선언한다.
	private DataSource dataSource;
	
	//	MvcBoardDAO 클래스의 bean이 생성될 때 오라클과 연결한다.
	public MvcBoardDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			logger.info("연결성공!");
		} catch (NamingException e) {
			//e.printStackTrace();
			logger.info("연결실패!");
		}
	}

	public void insert(MvcBoardVO mvcBoardVO) {
		logger.info("MvcBoardDAO 클래스의 insert() 매소드 실행");
		//logger.info("{}", mvcBoardVO);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " + 
					"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcBoardVO.getName());
			pstmt.setString(2, mvcBoardVO.getSubject());
			pstmt.setString(3, mvcBoardVO.getContent());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
		} 
	}

	public int selectCount() {
		
		
		return 0;
	}
	
}
