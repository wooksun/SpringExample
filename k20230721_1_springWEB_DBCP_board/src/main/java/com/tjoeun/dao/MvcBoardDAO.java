package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

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
			//logger.info("연결성공!");
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
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} // 커넥션 반납
		} 
	}

	//	SelectService 클래스에서 호출되는 테이블에 저장된 전체 글의 개수를 얻어오는 select sql 명령을 실행하는 메소드
	public int selectCount() {
		logger.info("MvcBoardDAO 클래스의 selectCount() 매소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0; // 테이블에 저장된 전체 글의 개수를 저장해서 return시킬 변수를 선언한다.
		
		try {
			conn = dataSource.getConnection(); // db연결
			String sql = "select count(*) from mvcboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//	테이블에 저장된 글의 개수는 null이 나올 수 없으므로 조건 비교는 필요없다.
			rs.next();
			result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
		} 
		
		return result;
	}
	
	//	SelectService 클래스에서 호출되는 브라우저에 표시할 1페이지 분량의 글 목록을 얻어오기 위해서 시작 인덱스, 끝 인덱스가 저장된
	//	HashMap 객체를 넘겨받고, 1페이지 분량의 글 목록을 얻어오는 select sql 명령을 실행하는 메소드
	public ArrayList<MvcBoardVO> selectList(HashMap<String, Integer> hmap) {
		logger.info("MvcBoardDAO 클래스의 selectCount() 매소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MvcBoardVO> list = null; // 1페이지 분량의 글 목록을 저장해 리턴시킬 ArrayList 선언
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from(" + 
						 "    select rownum rnum, AA.* from (" +
						 "        select * from mvcBoard order by gup desc, seq asc" + 
						 "    ) AA where rownum <= ?" +
						 ") where rnum >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hmap.get("endNo"));
			pstmt.setInt(2, hmap.get("startNo"));
			rs = pstmt.executeQuery();
			
			//	ResultSet 객체에 저장된 1페이지 분량의 글 목록을 저장할 ArrayList 객체 생성
			list = new ArrayList<MvcBoardVO>();
			
			//	ResultSet 객체에 저장된 글의 개수만큼 반복하며 ArrayList에 저장한다.
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
				mvcBoardVO.setIdx(rs.getInt("idx"));
				mvcBoardVO.setName(rs.getString("name"));
				mvcBoardVO.setSubject(rs.getString("subject"));
				mvcBoardVO.setContent(rs.getString("content"));
				mvcBoardVO.setGup(rs.getInt("gup"));
				mvcBoardVO.setLev(rs.getInt("lev"));
				mvcBoardVO.setSeq(rs.getInt("seq"));
				mvcBoardVO.setHit(rs.getInt("hit"));
				mvcBoardVO.setWriteDate(rs.getTimestamp("writeDate"));
				list.add(mvcBoardVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
		} 
		
		return list;
	}

	//	IncrementService 클래스에서 호출되는 조회수를 증가시킬 글번호를 넘겨받고 조회수를 증가시키는 update sql 명령을 실행하는 메소드
	public void increment(int idx) {
		logger.info("MvcBoardDAO 클래스의 increment() 매소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set hit = hit + 1 where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
		} 
	}

	//	ContentViewService 클래스에서 호출되는 조회수를 증가시킨 글번호를 넘겨받고 조회수를 증가시킨 글 1건을 얻어오는 
	//	select sql 명령을 실행하는 메소드
	public MvcBoardVO selectByIdx(int idx) {
		logger.info("MvcBoardDAO 클래스의 selectByIdx() 매소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MvcBoardVO mvcBoardVO = null; // 조회수를 증가시킨 글 1건을 얻어와 저장시켜 리턴할 객체 선언
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			//	ResultSet 객체에 저장된 글 1건을 리턴시키기 위해서 MvcBoardVO 클래스 객체에 저장한다.
			if (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
				mvcBoardVO.setIdx(rs.getInt("idx"));
				mvcBoardVO.setName(rs.getString("name"));
				mvcBoardVO.setSubject(rs.getString("subject"));
				mvcBoardVO.setContent(rs.getString("content"));
				mvcBoardVO.setGup(rs.getInt("gup"));
				mvcBoardVO.setLev(rs.getInt("lev"));
				mvcBoardVO.setSeq(rs.getInt("seq"));
				mvcBoardVO.setHit(rs.getInt("hit"));
				mvcBoardVO.setWriteDate(rs.getTimestamp("writeDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
		} 
		
		return mvcBoardVO;
	}

	//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을 실행하는 메소드
	//	UpdateService의 방법 1
	public void update(int idx, String subject, String content) {
		logger.info("MvcBoardDAO 클래스의 update() 매소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setInt(3, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
		} 
	}

	//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을 실행하는 메소드
	//	UpdateService의 방법 2
	public void update(MvcBoardVO mvcBoardVO) {
		logger.info("MvcBoardDAO 클래스의 update() 매소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcBoardVO.getSubject());
			pstmt.setString(2, mvcBoardVO.getContent());
			pstmt.setInt(3, mvcBoardVO.getIdx());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
		} 
	}

	//	DeleteService 클래스에서 호출되는 삭제할 글번호를 넘겨받고 글 1건을 삭제하는 delete sql 명령을 실행하는 메소드
	public void delete(int idx) {
		logger.info("MvcBoardDAO 클래스의 delete() 매소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "delete from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
		} 
	}
	
}
