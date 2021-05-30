package com.asak.controller.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.dao.ProductDAO;
import com.asak.dto.ProductVO;

public class ProductKindAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "product/productKind.jsp";
		String kind = request.getParameter("kind");
		
		ProductDAO pDao = ProductDAO.getInstance();
		ArrayList<ProductVO> prodKindList = pDao.listKindProduct(kind);
		
		request.setAttribute("productKindList", prodKindList);
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);

	}

}
