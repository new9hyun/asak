package com.asak.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.asak.dto.QnaVO;

import util.DBManager;

public class QnaDAO {
private QnaDAO() { }
	
	private static QnaDAO instance = new QnaDAO();
	
	public static QnaDAO getInstance() {
		return instance;
}
	public ArrayList<QnaVO> listQna(String id) {
		ArrayList<QnaVO> qnaList = new ArrayList<>();
		String sql = "SELECT * FROM qna WHERE id=? ORDER BY qseq DESC";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaVO qna = new QnaVO();
				
				
				qna.setQseq(rs.getInt("qseq"));
				qna.setSubject(rs.getString("subject"));
				qna.setContent(rs.getString("content"));
				qna.setReply(rs.getString("reply"));
				qna.setId(rs.getString("id"));
				qna.setRep(rs.getString("rep"));
				qna.setIndate(rs.getTimestamp("indate"));
				
				qnaList.add(qna);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return qnaList;
	}
	
	
	public QnaVO getQna(int qseq) {
		QnaVO qna = null;
		String sql = "SELECT * FROM qna WHERE qseq=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qseq);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				qna = new QnaVO();
				
				
				qna.setQseq(rs.getInt("qseq"));
				qna.setSubject(rs.getString("subject"));
				qna.setContent(rs.getString("content"));
				qna.setReply(rs.getString("reply"));
				qna.setId(rs.getString("id"));
				qna.setRep(rs.getString("rep"));
				qna.setIndate(rs.getTimestamp("indate"));
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return qna;
	}
	
	public void insertQna(QnaVO qna, String session_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO qna(qseq, subject, content, id) " +
					"VALUES(qna_seq.nextval, ?, ?, ?)";
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getSubject());
			pstmt.setString(2, qna.getContent());
			pstmt.setString(3, session_id);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	
	public ArrayList<QnaVO> listAllQna() {
		ArrayList<QnaVO> qnaList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM qna ORDER BY indate DESC";
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaVO qna = new QnaVO();
				
				
				qna.setQseq(rs.getInt("qseq"));
				qna.setSubject(rs.getString("subject"));
				qna.setContent(rs.getString("content"));
				qna.setReply(rs.getString("reply"));
				qna.setId(rs.getString("id"));
				qna.setRep(rs.getString("rep"));
				qna.setIndate(rs.getTimestamp("indate"));
				
				qnaList.add(qna);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return qnaList;	
	}
	
	public void updateQna(QnaVO qna) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE qna SET reply=?, rep='2' WHERE qseq=?";
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getReply());
			pstmt.setInt(2, qna.getQseq());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
}