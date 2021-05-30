package com.asak.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.asak.dto.CartVO;
import com.asak.dto.OrderVO;

import util.DBManager;

public class OrderDAO {
private OrderDAO() { }
	
	private static OrderDAO instance = new OrderDAO();
	
	public static OrderDAO getInstance() {
		return instance;
	}
	
	public int insertOrder(List<CartVO> cartList, String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxOseq = 0;
		
		try {
			conn = DBManager.getConnection();
			
			// orders ���̺��� �ִ� �ֹ���ȣ�� ��ȸ
			String selectMaxOseq = "SELECT max(oseq) FROM orders";
			pstmt = conn.prepareStatement(selectMaxOseq);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxOseq = rs.getInt(1); 
				System.out.println("Order ���̺� �ִ� �ֹ���ȣ: " + maxOseq);
			}
			
			pstmt.close();
			
			// �ִ� �ֹ���ȣ�� 1����
			// ���ο� �ֹ���ȣ�� �Ҵ��ϱ� ����
			maxOseq += 1;
			
			String insertOrder = "INSERT INTO orders(oseq, id) VALUES(?, ?)";
			pstmt = conn.prepareStatement(insertOrder);
			pstmt.setInt(1, maxOseq);
			pstmt.setString(2, userid);
			
			pstmt.executeUpdate();	// orders ���̺� �ֹ����� insert��.
			
			// ��ٱ��� ����� �о�ͼ� �ֹ��� ������ insert�Ѵ�.
			for ( CartVO cart : cartList) {
				// ��ٱ����� �� ��ǰ������ �Ҵ�� �ֹ���ȣ�� �ν��� �ֹ��� ���� insert
				insertOrderDetail(cart, maxOseq);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return maxOseq;
	}

	private void insertOrderDetail(CartVO cart, int maxOseq) {
		Connection conn = null;
		PreparedStatement pstmt = null;	
		
		try {
			conn = DBManager.getConnection();
			
			// �ֹ��� ������ order_detail�� insert
			String insertOrderDetail = "INSERT INTO ORDER_DETAIL(odseq, oseq, pseq, quantity) " +
									   "VALUES(order_detail_seq.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(insertOrderDetail);
			pstmt.setInt(1, maxOseq);
			pstmt.setInt(2, cart.getPseq());
			pstmt.setInt(3, cart.getQuantity());
			
			pstmt.executeUpdate();
			pstmt.close();
			
			// cart ���̺� ó����� ������Ʈ
			String updateCart = "UPDATE cart SET result=2 WHERE cseq=?";
			pstmt = conn.prepareStatement(updateCart);
			pstmt.setInt(1, cart.getCseq());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
	}
	
	/*
	 * ����ڰ� �ֹ��� ������ ��ȸ
	 * �Է� �Ķ����:
	 * 		userId: �����id
	 * 	 	result: �ֹ�ó�����(1:��۹�ó��, 2:���ó��)
	 * 		oseq: �ֹ� ����ÿ� �Ҵ���� �ֹ���ȣ
	 */
	public List<OrderVO> listOrderById(String userId, String result, int oseq){
		ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM order_view WHERE id=? "
				    + "AND result LIKE '%'||?||'%' AND oseq=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, result);
			pstmt.setInt(3, oseq);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderVO order = new OrderVO();
				
				order.setOdseq(rs.getInt("odseq"));
				order.setOseq(rs.getInt("oseq"));
				order.setId(rs.getString("ID"));
				order.setIndate(rs.getTimestamp("indate"));
				order.setMname(rs.getString("mname"));
				order.setZipnum(rs.getString("zip_num"));
				order.setAddress(rs.getString("address"));
				order.setPhone(rs.getString("phone"));
				order.setPseq(rs.getInt("pseq"));
				order.setPname(rs.getString("pname"));
				order.setQuantity(rs.getInt("quantity"));
				order.setPrice2(rs.getInt("price2"));
				order.setResult(rs.getString("result"));
				
				orderList.add(order);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return orderList;
	}
	
	public ArrayList<Integer> selectSeqOrdering(String userid, String result) {
		ArrayList<Integer> oseqList = new ArrayList<>();	// ������ ������� ��� �ֹ���ȣ ���
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT oseq FROM order_view " +
					 "WHERE id=? AND result LIKE '%'||?||'%' ORDER BY oseq DESC";
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, result);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				oseqList.add(rs.getInt("oseq"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return oseqList;
	}
	
	/*
	 * �ֹ��� ��ü ����� ��ȸ
	 * ������ ��忡�� ���
	 */
	public ArrayList<OrderVO> listOrder(String member_name) {
		ArrayList<OrderVO> orderList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM order_view WHERE mname LIKE '%'||?||'%' " +
					"ORDER BY result, oseq DESC";
		
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
				OrderVO order = new OrderVO();
				
				order.setOdseq(rs.getInt("odseq"));
				order.setOseq(rs.getInt("oseq"));
				order.setId(rs.getString("ID"));
				order.setIndate(rs.getTimestamp("indate"));
				order.setMname(rs.getString("mname"));
				order.setZipnum(rs.getString("zip_num"));
				order.setAddress(rs.getString("address"));
				order.setPhone(rs.getString("phone"));
				order.setPseq(rs.getInt("pseq"));
				order.setPname(rs.getString("pname"));
				order.setQuantity(rs.getInt("quantity"));
				order.setPrice2(rs.getInt("price2"));
				order.setResult(rs.getString("result"));
				
				orderList.add(order);	
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return orderList;
	}
	
	public void updateOrderResult(String odseq) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE order_detail SET result='2' WHERE odseq=?";
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, odseq);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
	
}

