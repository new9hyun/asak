package com.asak.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asak.controller.action.Action;
import com.asak.dao.QnaDAO;
import com.asak.dto.QnaVO;

public class AdminQnaRepsaveAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "AsakServlet?command=admin_qna_list";

		String qseq = request.getParameter("qseq");
		String reply = request.getParameter("reply").trim(); // 앞, 뒤 공백을 제거한 답변내용
		
		QnaVO qna = new QnaVO();
		qna.setQseq(Integer.parseInt(qseq));
		qna.setReply(reply);
		
		QnaDAO qDao = QnaDAO.getInstance();
		qDao.updateQna(qna);
		
		response.sendRedirect(url);

	}

}
