package com.asak.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.controller.action.Action;
import com.asak.dao.QnaDAO;
import com.asak.dto.QnaVO;

public class AdminQnaDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "admin/qna/qnaDetail.jsp";

		String qseq = request.getParameter("qseq");
		
		QnaDAO qDao = QnaDAO.getInstance();
		QnaVO qna = qDao.getQna(Integer.parseInt(qseq));
		
		request.setAttribute("qnaVO", qna);
		
		request.getRequestDispatcher(url).forward(request, response);

	}

}
