package com.asak.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.QnaDAO;
import com.asak.dto.MemberVO;
import com.asak.dto.QnaVO;

public class QnaWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		String url = "AsakServlet?command=qna_list";
		
		if (loginUser == null) {
			url = "AsakServlet?command=login_form";
		} else { 
			QnaVO qna = new QnaVO();
			qna.setSubject(request.getParameter("subject"));
			qna.setContent(request.getParameter("content"));
			
			QnaDAO qDao = QnaDAO.getInstance();
			qDao.insertQna(qna, loginUser.getId());
		}
		
		response.sendRedirect(url);

	}

}
