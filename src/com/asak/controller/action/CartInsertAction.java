package com.asak.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.CartDAO;
import com.asak.dto.CartVO;
import com.asak.dto.MemberVO;

public class CartInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = "AsakServlet?command=cart_list";
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {	
			url = "AsakServlet?command=login_form";
		} else {
			CartVO cart = new CartVO();
			cart.setId(loginUser.getId()); 
			cart.setPseq(Integer.parseInt(request.getParameter("pseq")));
			cart.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			
			CartDAO cartDao = CartDAO.getInstance();
			cartDao.insertCart(cart);
		}
		
		response.sendRedirect(url);

	}

}
