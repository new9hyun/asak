package com.asak.dao;

import java.sql.*;
import java.util.ArrayList;

import com.asak.dto.AddressVO;

import util.DBManager;


public class AddressDAO {
	private static AddressDAO instance = new AddressDAO();
	
	private AddressDAO() {  }
	
	public static AddressDAO getInstance() {
		return instance;
	}
	
	public ArrayList<AddressVO> selectAddressByDong(String dong) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<AddressVO> addrList = new ArrayList<>();
		String sql = "SELECT * FROM address WHERE dong LIKE '%'||?||'%'";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dong);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AddressVO address = new AddressVO();
				address.setZipnum(rs.getString("zipnum"));
				address.setSido(rs.getString("sido"));
				address.setGugun(rs.getString("gugun"));
				address.setDong(rs.getString("dong"));
				address.setZipcode(rs.getString("zipcode"));
				address.setBunji(rs.getString("bunji"));
				
				addrList.add(address);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return addrList;
		
	}
	
}
