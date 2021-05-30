package com.asak.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.QnaDAO;
import com.asak.dto.MemberVO;
import com.asak.dto.QnaVO;

public class QnaViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		String url = "qna/qnaView.jsp";
		
		if (loginUser == null) {
			url = "AsakServlet?command=login_form";
		} else {
			int qseq = Integer.parseInt(request.getParameter("qseq"));
			
			QnaDAO qDao = QnaDAO.getInstance();
			QnaVO qna = qDao.getQna(qseq);
			
			request.setAttribute("qnaVO", qna);
		}
		
		request.getRequestDispatcher(url).forward(request, response);


	}

}
