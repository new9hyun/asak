package com.asak.controller.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.OrderDAO;
import com.asak.dto.MemberVO;
import com.asak.dto.OrderVO;

public class OrderListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "mypage/orderList.jsp";
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			url = "AsakServlet?command=login_form";
		} else {
			int oseq = Integer.parseInt(request.getParameter("oseq"));
			
			OrderDAO oDao = OrderDAO.getInstance();
			List<OrderVO> orderList 
				=  oDao.listOrderById(loginUser.getId(), "1", oseq);
			
			
			int totalPrice = 0;
			for (OrderVO order: orderList) {
				totalPrice += order.getPrice2() * order.getQuantity();
			}
			
			request.setAttribute("orderList", orderList);
			request.setAttribute("totalPrice", totalPrice);
		}
		
		request.getRequestDispatcher(url).forward(request, response);

	}
		
}
