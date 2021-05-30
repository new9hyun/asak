package com.asak.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DBManager;

public class WorkerDAO {
private WorkerDAO() { }
	
	private static WorkerDAO instance = new WorkerDAO();
	
	public static WorkerDAO getInstance() {
		return instance;
	}

	public int workerCheck(String userId, String userPwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT pwd FROM worker WHERE id=?";
		int result = -1;
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = 0;
				String pwd_in_db = rs.getString(1);
				if (userPwd.equals(pwd_in_db)) { // 입력한 암호화 dB에서 조회한 암호가 같으면
					result = 1;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
}
