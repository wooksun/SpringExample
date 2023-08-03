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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tjoeun.springWEB_DBCP_board.Constant;
import com.tjoeun.vo.MvcBoardVO;

public class MvcBoardDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(MvcBoardDAO.class);
	
	private JdbcTemplate template;
	
	public MvcBoardDAO() {
		template = Constant.template;
	} 

	public void insert(final MvcBoardVO mvcBoardVO) {
		logger.info("MvcBoardDAO 클래스의 insert() 매소드 실행");
		String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " + 
				"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
		template.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, mvcBoardVO.getName());
				pstmt.setString(2, mvcBoardVO.getSubject());
				pstmt.setString(3, mvcBoardVO.getContent());
			}
		});
	}

	public int selectCount() {
		logger.info("MvcBoardDAO 클래스의 selectCount() 매소드 실행");
		String sql = "select count(*) from mvcboard";
		//return template.queryForInt(sql);
		//	queryForObject(sql 명령, 리턴타입.class)
		return template.queryForObject(sql, Integer.class);
	}
	
	public ArrayList<MvcBoardVO> selectList(HashMap<String, Integer> hmap) {
		logger.info("MvcBoardDAO 클래스의 selectCount() 매소드 실행");
		String sql = "select * from(" + 
				 "    select rownum rnum, AA.* from (" +
				 "        select * from mvcBoard order by gup desc, seq asc" + 
				 "    ) AA where rownum <= " + hmap.get("endNo") +
				 ") where rnum >= " + hmap.get("startNo");
		return (ArrayList<MvcBoardVO>) template.query(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
	}

	public void increment(final int idx) {
		logger.info("MvcBoardDAO 클래스의 increment() 매소드 실행");
		String sql = "update mvcboard set hit = hit + 1 where idx = " + idx;
		template.update(sql);
	}

	public MvcBoardVO selectByIdx(int idx) {
		logger.info("MvcBoardDAO 클래스의 selectByIdx() 매소드 실행");
//		ResultSet rs = null;
//		MvcBoardVO mvcBoardVO = null; // 조회수를 증가시킨 글 1건을 얻어와 저장시켜 리턴할 객체 선언
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "select * from mvcboard where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, idx);
//			rs = pstmt.executeQuery();
//			
//			//	ResultSet 객체에 저장된 글 1건을 리턴시키기 위해서 MvcBoardVO 클래스 객체에 저장한다.
//			if (rs.next()) {
//				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//				mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
//				mvcBoardVO.setIdx(rs.getInt("idx"));
//				mvcBoardVO.setName(rs.getString("name"));
//				mvcBoardVO.setSubject(rs.getString("subject"));
//				mvcBoardVO.setContent(rs.getString("content"));
//				mvcBoardVO.setGup(rs.getInt("gup"));
//				mvcBoardVO.setLev(rs.getInt("lev"));
//				mvcBoardVO.setSeq(rs.getInt("seq"));
//				mvcBoardVO.setHit(rs.getInt("hit"));
//				mvcBoardVO.setWriteDate(rs.getTimestamp("writeDate"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
//		} 
//		
//		return mvcBoardVO;
		
		//	새로운 방식
		String sql = "select * from mvcboard where idx = " + idx;
		return template.queryForObject(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
	}

	//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을 실행하는 메소드
	//	UpdateService의 방법 1
	public void update(final int idx, final String subject, final String content) {
		logger.info("MvcBoardDAO 클래스의 update() 매소드 실행");
		
		//	이전방식
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, subject);
//			pstmt.setString(2, content);
//			pstmt.setInt(3, idx);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
//		} 
		
		//	새로운방식(1)
//		String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//		template.update(sql, new PreparedStatementSetter() {
//			
//			@Override
//			public void setValues(PreparedStatement pstmt) throws SQLException {
//				pstmt.setString(1, subject);
//				pstmt.setString(2, content);
//				pstmt.setInt(3, idx);
//			}
//		});
		
		//	새로운방식(2)
		//String sql = "update mvcboard set subject = '" + subject + "', content = '" + content + "' where idx = " + idx;
		String sql = String.format("update mvcboard set subject = '%s', content = '%s' where idx = %d", subject, content, idx);
		template.update(sql);
	}

	//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을 실행하는 메소드
	//	UpdateService의 방법 2
	public void update(final MvcBoardVO mvcBoardVO) {
		logger.info("MvcBoardDAO 클래스의 update() 매소드 실행");
		
		//	이전방식
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, mvcBoardVO.getSubject());
//			pstmt.setString(2, mvcBoardVO.getContent());
//			pstmt.setInt(3, mvcBoardVO.getIdx());
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
//		} 
		
		//	새로운 방식(1)
//		String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//		template.update(sql, new PreparedStatementSetter() {
//			
//				@Override
//				public void setValues(PreparedStatement pstmt) throws SQLException {
//					pstmt.setString(1, mvcBoardVO.getSubject());
//					pstmt.setString(2, mvcBoardVO.getContent());
//					pstmt.setInt(3, mvcBoardVO.getIdx());
//				}
//			});
//		}
		
//		새로운방식(2)
//		String sql = "update mvcboard set subject = '" + mvcBoardVO.getSubject() + "', content = '" + mvcBoardVO.getContent() +
//						"' where idx = " + mvcBoardVO.getIdx();
		String sql = String.format("update mvcboard set subject = '%s', content = '%s' where idx = %d", 
				mvcBoardVO.getSubject(), mvcBoardVO.getContent(), mvcBoardVO.getIdx());
			template.update(sql);
		}

	//	DeleteService 클래스에서 호출되는 삭제할 글번호를 넘겨받고 글 1건을 삭제하는 delete sql 명령을 실행하는 메소드
	public void delete(int idx) {
		logger.info("MvcBoardDAO 클래스의 delete() 매소드 실행");
		
		//	이전방식
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "delete from mvcboard where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, idx);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();}} 
//		} 
		
		//	새로운방식(1)
//		String sql = "delete from mvcboard where idx = ?";
//		template.update(sql, new PreparedStatementSetter() {
//			
//				@Override
//				public void setValues(PreparedStatement pstmt) throws SQLException {
//					pstmt.setInt(1, idx);
//				}
//			});
//		}
		
		//	새로운 방식(2)
		String sql = "delete from mvcboard where idx = " + idx;
		template.update(sql);
		
	}

	//	ReplyService 클래스에서 호출되는 글그룹과 글이 출력되는 순서가 저장된 HashMap 객체를 넘겨받고, 조건에 만족하는 레코드의
	//	seq를 1씩 증가시키는 update sql 명령을 실행하는 메소드
	public void replyIncrement(final HashMap<String, Integer> hmap) {
		logger.info("MvcBoardDAO 클래스의 replyIncrement() 매소드 실행");
		String sql = "update mvcboard set seq = seq + 1 where gup = ? and seq >= ?";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, hmap.get("gup"));
				pstmt.setInt(2, hmap.get("seq"));
			}
		});
	}

	//	ReplyService 클래스에서 호출되는 답글이 저장된 객체를 넘겨받고, 답글을 저장하는 insert sql 명령을 실행하는 메소드
	public void replyInsert(final MvcBoardVO mvcBoardVO) {
		logger.info("MvcBoardDAO 클래스의 replyInsert() 매소드 실행");
		String sql = "insert into mvcboard(idx, name, subject, content, gup, lev, seq)" + 
				"values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, mvcBoardVO.getName());
				pstmt.setString(2, mvcBoardVO.getSubject());
				pstmt.setString(3, mvcBoardVO.getContent());
				pstmt.setInt(4, mvcBoardVO.getGup());
				pstmt.setInt(5, mvcBoardVO.getLev());
				pstmt.setInt(6, mvcBoardVO.getSeq());
			}
		});
		
	}
	
}
