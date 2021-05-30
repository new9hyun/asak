package com.asak.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.controller.action.Action;

public class AdminProductWriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "admin/product/productWrite.jsp";
		String kindList[] = {"Salad", "Topping", "Sauce", "Yogurt", "Sale"};
		
		request.setAttribute("kindList", kindList);
		request.getRequestDispatcher(url).forward(request, response);
	}
	

}
