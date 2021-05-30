package com.asak.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.controller.action.Action;
import com.asak.dao.OrderDAO;

public class AdminOrderSaveAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] resultArr = request.getParameterValues("result");
		String url = "AsakServlet?command=admin_order_list";
		
		for(String odseq : resultArr) {
			System.out.println(odseq);
			OrderDAO oDao = OrderDAO.getInstance();
			oDao.updateOrderResult(odseq);  // 주문완료 업데이트
		}
		
		request.getRequestDispatcher(url).forward(request, response);

	}

}
