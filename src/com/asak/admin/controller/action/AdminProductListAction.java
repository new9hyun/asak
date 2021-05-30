package com.asak.admin.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.controller.action.Action;
import com.asak.dao.ProductDAO;
import com.asak.dto.ProductVO;

public class AdminProductListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "admin/product/productList.jsp";
		String key = "";

		
		if (request.getParameter("key") != null) {
			key = request.getParameter("key");
		}
		
		
		ProductDAO pDao = ProductDAO.getInstance();
		
		ArrayList<ProductVO> productList = pDao.listProduct(key);
		
		
		
		
	
		
		request.setAttribute("productList", productList);
		
		
		request.getRequestDispatcher(url).forward(request, response);
	}




}
