package com.asak.controller.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.CartDAO;
import com.asak.dao.OrderDAO;
import com.asak.dto.CartVO;
import com.asak.dto.MemberVO;

public class InsertOrderAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			url = "AsakServlet?command=login_form";
		} else {
			CartDAO cartDao = CartDAO.getInstance();
			List<CartVO> cartList = cartDao.listCart(loginUser.getId());
			
			OrderDAO orderDao = OrderDAO.getInstance();
			int maxOseq = orderDao.insertOrder(cartList, loginUser.getId());
			
			url = "AsakServlet?command=order_list&oseq="+maxOseq;
		}
		
		response.sendRedirect(url);
		
	}

}
