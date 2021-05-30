package com.asak.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.controller.action.Action;
import com.asak.dao.ProductDAO;
import com.asak.dto.ProductVO;

public class AdminProductDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "admin/product/productDetail.jsp";
		String pseq = request.getParameter("pseq");
		
		ProductDAO pDao = ProductDAO.getInstance();
		ProductVO product = pDao.getProduct(pseq);

		request.setAttribute("productVO", product);
		
		String kindList[] = {"", "Salad", "Topping", "Sauce", "Yogurt", "Sale"};
		int kind = Integer.parseInt(product.getKind());
		request.setAttribute("kind", kindList[kind]);
		
		request.getRequestDispatcher(url).forward(request, response);
	}

}
