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
			
			// orders 테이블에서 최대 주문번호를 조회
			String selectMaxOseq = "SELECT max(oseq) FROM orders";
			pstmt = conn.prepareStatement(selectMaxOseq);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxOseq = rs.getInt(1); 
				System.out.println("Order 테이블 최대 주문번호: " + maxOseq);
			}
			
			pstmt.close();
			
			// 최대 주문번호를 1증가
			// 새로운 주문번호를 할당하기 위함
			maxOseq += 1;
			
			String insertOrder = "INSERT INTO orders(oseq, id) VALUES(?, ?)";
			pstmt = conn.prepareStatement(insertOrder);
			pstmt.setInt(1, maxOseq);
			pstmt.setString(2, userid);
			
			pstmt.executeUpdate();	// orders 테이블에 주문정보 insert함.
			
			// 장바구니 목록을 읽어와서 주문상세 정보를 insert한다.
			for ( CartVO cart : cartList) {
				// 장바구니의 각 상품정보와 할당된 주문번호를 인스로 주문상세 정보 insert
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
			
			// 주문상세 정보를 order_detail에 insert
			String insertOrderDetail = "INSERT INTO ORDER_DETAIL(odseq, oseq, pseq, quantity) " +
									   "VALUES(order_detail_seq.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(insertOrderDetail);
			pstmt.setInt(1, maxOseq);
			pstmt.setInt(2, cart.getPseq());
			pstmt.setInt(3, cart.getQuantity());
			
			pstmt.executeUpdate();
			pstmt.close();
			
			// cart 테이블에 처리결과 업데이트
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
	 * 사용자가 주문한 내역을 조회
	 * 입력 파라미터:
	 * 		userId: 사용자id
	 * 	 	result: 주문처리결과(1:배송미처리, 2:배송처리)
	 * 		oseq: 주문 저장시에 할당받은 주문번호
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
		ArrayList<Integer> oseqList = new ArrayList<>();	// 지정된 사용자의 모든 주문번호 목록
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
	 * 주문의 전체 목록을 조회
	 * 관리자 모드에서 사용
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

