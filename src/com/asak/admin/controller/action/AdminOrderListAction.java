package com.asak.admin.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.controller.action.Action;
import com.asak.dao.OrderDAO;
import com.asak.dto.OrderVO;

public class AdminOrderListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url="admin/order/orderList.jsp";
		String key = "";
		
		if (request.getParameter("key") != null) {
			key = request.getParameter("key");
		}
		OrderDAO oDao = OrderDAO.getInstance();
		ArrayList<OrderVO> orderList = oDao.listOrder(key);
		
		request.setAttribute("orderList", orderList);

		request.getRequestDispatcher(url).forward(request, response);

	}

}
