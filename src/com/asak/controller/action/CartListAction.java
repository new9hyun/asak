package com.asak.controller.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.CartDAO;
import com.asak.dto.CartVO;
import com.asak.dto.MemberVO;

public class CartListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = "mypage/cartList.jsp";
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {	
			url = "AsakServlet?command=login_form";
		} else {
			CartDAO cartDao = CartDAO.getInstance();
			List<CartVO> cartList = cartDao.listCart(loginUser.getId());
			
			int totalPrice = 0;
			
			for (CartVO cart : cartList) {
				totalPrice += cart.getPrice2() * cart.getQuantity();
			}
			
			request.setAttribute("cartList", cartList);
			request.setAttribute("totalPrice", totalPrice);
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}

}
