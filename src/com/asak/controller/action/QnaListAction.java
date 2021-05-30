package com.asak.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.QnaDAO;
import com.asak.dto.MemberVO;
import com.asak.dto.QnaVO;

public class QnaListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		String url = "qna/qnaList.jsp";
		
		if (loginUser == null) {
			url = "AsakServlet?command=login_form";
		} else {
			QnaDAO qDao = QnaDAO.getInstance();
			ArrayList<QnaVO> qnaList = qDao.listQna(loginUser.getId());
			
			request.setAttribute("qnaList", qnaList);
		}
		
		request.getRequestDispatcher(url).forward(request, response);


	}

}
