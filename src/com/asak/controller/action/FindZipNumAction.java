package com.asak.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import com.asak.dao.AddressDAO;
import com.asak.dto.AddressVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindZipNumAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dong = request.getParameter("dong");
		String url = "member/findZipNum.jsp";
		ArrayList<AddressVO> addressList = new ArrayList<>();
		
		AddressDAO aDao = AddressDAO.getInstance();
		addressList = aDao.selectAddressByDong(dong);
		request.setAttribute("addressList", addressList);
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);		
	}

}
