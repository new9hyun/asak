package com.asak.dao;

import java.sql.*;
import java.util.ArrayList;

import com.asak.dto.MemberVO;

import util.DBManager;

public class MemberDAO {
	
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() { }
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	
	public int confirmID(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT pwd FROM member WHERE id=?";
		int result = -1;
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = 1;
			} else {
				result = -1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return result;
	} 
	
	public MemberVO getMember(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM member WHERE id=?";
		MemberVO member = null;
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new MemberVO();
				
				member.setId(rs.getString("id"));
				member.setPwd(rs.getString("pwd"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setZip_num(rs.getString("zip_num"));
				member.setAddress(rs.getString("address"));
				member.setPhone(rs.getString("phone"));
				member.setUseyn(rs.getString("useyn"));
				member.setRegdate(rs.getTimestamp("regdate"));
			} 
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return member;
		
	}
	
		public void insertMember(MemberVO memberVO) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "INSERT INTO member(id, pwd, name, email, zip_num, address, phone) " +
						"VALUES(?, ?, ?, ?, ?, ?, ?)";
			
			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberVO.getId());
				pstmt.setString(2, memberVO.getPwd());
				pstmt.setString(3, memberVO.getName());
				pstmt.setString(4, memberVO.getEmail());
				pstmt.setString(5, memberVO.getZip_num());
				pstmt.setString(6, memberVO.getAddress());
				pstmt.setString(7, memberVO.getPhone());
				
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		}
	
		public ArrayList<MemberVO> listMember(String member_name) {
			ArrayList<MemberVO> memberList = new ArrayList<>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT * FROM member WHERE name LIKE '%'||?||'%' " +
						"ORDER BY regdate DESC";
			
			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				if (member_name == "") {
					pstmt.setString(1, "");
				} else {
					pstmt.setString(1, member_name);
				}
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					MemberVO member = new MemberVO();
					
					member.setId(rs.getString("id"));
					member.setPwd(rs.getString("pwd"));
					member.setName(rs.getString("name"));
					member.setEmail(rs.getString("email"));
					member.setZip_num(rs.getString("zip_num"));
					member.setAddress(rs.getString("address"));
					member.setPhone(rs.getString("phone"));
					member.setUseyn(rs.getString("useyn"));
					member.setRegdate(rs.getTimestamp("regdate"));
					
					memberList.add(member);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt, rs);
			}
			
			return memberList;
			
		}
	
}
