package com.asak.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.controller.action.Action;
import com.asak.dao.WorkerDAO;

public class AdminLoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String workerId = request.getParameter("workerId");
		String workerPwd = request.getParameter("workerPwd");
		String url = "AsakServlet?command=admin_login_form";
		String message = "";
		
		WorkerDAO wDao = WorkerDAO.getInstance();
		
		int result = wDao.workerCheck(workerId, workerPwd);

		if (result == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("workerId", workerId);
			url = "AsakServlet?command=admin_product_list";
		} else if (result == 0) {
			message = "비밀번호를 확인하세요!";
		} else {
			message = "아이디를 확인하세요!";
		}
		
		request.setAttribute("message", message);
		
		request.getRequestDispatcher(url).forward(request, response);
	}

	

}
