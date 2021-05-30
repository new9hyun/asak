package com.asak.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dto.MemberVO;

public class QnaWriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		String url = "qna/qnaWrite.jsp";
		
		if (loginUser == null) {
			url = "AsakServlet?command=login_form";
		} 
		
		request.getRequestDispatcher(url).forward(request, response);

	}

}
