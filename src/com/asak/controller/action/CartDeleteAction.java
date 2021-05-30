package com.asak.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.dao.CartDAO;

public class CartDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "AsakServlet?command=cart_list";
		String[] cseqArr = request.getParameterValues("cseq");
		
		CartDAO cartDAO = CartDAO.getInstance();
		for (String cseq : cseqArr) {
			cartDAO.deleteCart(Integer.parseInt(cseq));
		}
	
		request.getRequestDispatcher(url).forward(request, response);
		
	}

}
