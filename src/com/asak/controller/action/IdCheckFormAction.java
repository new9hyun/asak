package com.asak.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.dao.MemberDAO;

public class IdCheckFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "member/idcheck.jsp";
		String id = request.getParameter("id").trim();
		
		MemberDAO mDao = MemberDAO.getInstance();
		int result = mDao.confirmID(id);
		
		request.setAttribute("message", result);
		request.setAttribute("id", id);
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
