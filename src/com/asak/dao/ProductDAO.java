package com.asak.dao;

import java.sql.*;
import java.util.*;

import com.asak.dto.ProductVO;

import util.DBManager;

public class ProductDAO {
	private ProductDAO() {
		
	}
	
	private static ProductDAO instance = new ProductDAO();
	
	public static ProductDAO getInstance() {
		return instance;
	}
	
	/*
	 * 전체 게시글의 수 조회
	 */
	public int countProductList(String productName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM product " +
					" WHERE name LIKE '%'||?||'%'";
		int result = 0;
		
		try {
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			if (productName != "") {
				pstmt.setString(1, "");
			} else {
				pstmt.setString(1, productName);
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/*
	 * 뷰에서 신규상품 조회
	 */
	public ArrayList<ProductVO> listNewProduct() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM new_pro_view";
		ArrayList<ProductVO> productList = new ArrayList<>();
		
		try {
			conn = DBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				ProductVO product = new ProductVO();
				product.setPseq(rs.getInt("pseq"));
				product.setName(rs.getString("name"));
				product.setPrice2(rs.getInt("price2"));
				product.setImage(rs.getString("image"));
				
				productList.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, stmt, rs);
		}
		
		return productList;
	}
	
	/*
	 * 뷰에서 베스트상품 조회
	 */
	public ArrayList<ProductVO> listBestProduct() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM best_pro_view";
		ArrayList<ProductVO> productList = new ArrayList<>();
		
		try {
			conn = DBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				ProductVO product = new ProductVO();
				product.setPseq(rs.getInt("pseq"));
				product.setName(rs.getString("name"));
				product.setPrice2(rs.getInt("price2"));
				product.setImage(rs.getString("image"));
				
				productList.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, stmt, rs);
		}
		
		return productList;
	}
	
	
	/*
	 * 상품번호로 상품정보 하나를 조회하는 메소드
	 */
	public ProductVO getProduct(String pseq) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM product WHERE pseq=?";
		ProductVO product = new ProductVO();
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pseq);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				product.setPseq(rs.getInt("pseq"));
				product.setName(rs.getString("name"));
				product.setKind(rs.getString("kind"));
				product.setPrice1(rs.getInt("price1"));
				product.setPrice2(rs.getInt("price2"));
				product.setPrice3(rs.getInt("price3"));
				product.setContent(rs.getString("content"));
				product.setImage(rs.getString("image"));
				product.setUseyn(rs.getString("useyn"));
				product.setBestyn(rs.getString("bestyn"));
				product.setRegdate(rs.getTimestamp("regdate"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return product;
	}
	
	/*
	 * 상품 종류별로 상품정보를 조회하는 메소드
	 */
	public ArrayList<ProductVO> listKindProduct(String kind) {
	    ArrayList<ProductVO> productList = new ArrayList<ProductVO>();
	    String sql= "select * from product where kind=?";
	    
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	      conn = DBManager.getConnection();
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, kind);
	      rs = pstmt.executeQuery();
	      
	      while (rs.next()) {
	        ProductVO product = new ProductVO();
	        product.setPseq(rs.getInt("pseq"));
	        product.setName(rs.getString("name"));
	        product.setPrice2(rs.getInt("price2"));
	        product.setImage(rs.getString("image"));
	        productList.add(product);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      DBManager.close(conn, pstmt, rs);
	    }
	    return productList;
	 }
	
	/*
	 * 상품의 전체 목록 조회
	 */
	public ArrayList<ProductVO> listProduct(String productName) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT pseq, regdate, name, price1, price2, useyn, bestyn " +
	    			 "FROM product WHERE name LIKE '%'||?||'%' ORDER BY pseq DESC";
	    ArrayList<ProductVO> productList = new ArrayList<>(); // 결과 저장변수
	    
	    try {
	    	
	    	conn = DBManager.getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	
	    	// 전체상품 조회 와 상품명 조회를 구분하여 처리
	    	if (productName.equals("")) { // 전체상품 조회
	    		pstmt.setString(1, "");
	    	} else {	// 상품명에 의한 조회
	    		pstmt.setString(1, productName);
	    	}
	    	
	    	rs = pstmt.executeQuery();
	    	
	    	while(rs.next()) {
	    		ProductVO product = new ProductVO();
	    		
	    		// price1, price2, useyn, bestyn
	    		product.setPseq(rs.getInt("pseq"));
	    		product.setRegdate(rs.getTimestamp("regdate"));
	    		product.setName(rs.getString("name"));
	    		product.setPrice1(rs.getInt("price1"));
	    		product.setPrice2(rs.getInt("price2"));
	    		product.setUseyn(rs.getString("useyn"));
	    		product.setBestyn(rs.getString("bestyn"));
	    		
	    		productList.add(product);
	    	}
	    	
	    } catch (Exception e) {
		    e.printStackTrace();
	    } finally {
	      DBManager.close(conn, pstmt, rs);
	    }
	    
	    return productList;
	}
	
	
	
	public void insertProduct(ProductVO product) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "INSERT INTO product(" +
	    		"pseq, name, kind, price1, price2, price3, content, image) " +
	    		"VALUES(product_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try {
	    	
	    	conn = DBManager.getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setString(1, product.getName());
	    	pstmt.setString(2, product.getKind());
	    	pstmt.setInt(3, product.getPrice1());
	    	pstmt.setInt(4, product.getPrice2());
	    	pstmt.setInt(5, product.getPrice3());
	    	pstmt.setString(6, product.getContent());
	    	pstmt.setString(7, product.getImage());
	    	
	    	pstmt.executeUpdate();
	    	
	    } catch (Exception e) {
		    e.printStackTrace();
	    } finally {
	      DBManager.close(conn, pstmt);
	    }
	}
	
	public void updateProduct(ProductVO product) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "UPDATE product SET kind=?, name=?, price1=?, price2=?, price3=?, " + 
	    			"content=?, image=?, bestyn=?, useyn=? WHERE pseq=?";
	    
	    try {
	       	conn = DBManager.getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	
	    	pstmt.setString(1, product.getKind());
	    	pstmt.setString(2, product.getName());
	    	pstmt.setInt(3, product.getPrice1());
	    	pstmt.setInt(4, product.getPrice2());
	    	pstmt.setInt(5, product.getPrice3());
	    	pstmt.setString(6, product.getContent());
	    	pstmt.setString(7, product.getImage());
	    	pstmt.setString(8, product.getBestyn());
	    	pstmt.setString(9, product.getUseyn());
	    	pstmt.setInt(10, product.getPseq());
	    	
	    	pstmt.executeUpdate();    	
	    } catch (Exception e) {
		    e.printStackTrace();
	    } finally {
	      DBManager.close(conn, pstmt);
	    }
	}

}
