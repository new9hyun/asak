package com.asak.admin.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.controller.action.Action;
import com.asak.dao.QnaDAO;
import com.asak.dto.QnaVO;

public class AdminQnaListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String url = "admin/qna/qnaList.jsp";
		
		QnaDAO qDao = QnaDAO.getInstance();
		ArrayList<QnaVO> qnaList = qDao.listAllQna();

		request.setAttribute("qnaList", qnaList);
		
		request.getRequestDispatcher(url).forward(request, response);

	}

}
