package com.asak.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.dao.ProductDAO;
import com.asak.dto.ProductVO;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductDAO pDao = ProductDAO.getInstance();
		
		ArrayList<ProductVO> newProdList = pDao.listNewProduct();
		ArrayList<ProductVO> bestProdList = pDao.listBestProduct();
		
		request.setAttribute("newProductList", newProdList);
		request.setAttribute("bestProductList", bestProdList);
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

}
