package com.asak.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.MemberDAO;
import com.asak.dto.MemberVO;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "member/login_fail.jsp";
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		// 사용자 정보를 조회한다.
		MemberDAO mDao = MemberDAO.getInstance();
		MemberVO member = mDao.getMember(id);
		
		if (member != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", member);
			url = "AsakServlet?command=index";
		}
		
		request.getRequestDispatcher(url).forward(request, response);

	}

}
