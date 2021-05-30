package com.asak.controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.dao.OrderDAO;
import com.asak.dto.MemberVO;
import com.asak.dto.OrderVO;

public class MyPageAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		String url = "mypage/mypage.jsp";
		
		if (loginUser == null) {
			url = "AsakServlet?command=login_form";
		} else {
			
			ArrayList<OrderVO> orderList = new ArrayList<>();
			
			OrderDAO oDao = OrderDAO.getInstance();
			String userId = loginUser.getId();
			
			ArrayList<Integer> oseqList = oDao.selectSeqOrdering(userId, "1");
			
			
			for(int oseq : oseqList) {
				List<OrderVO> detailList = oDao.listOrderById(userId, "1", oseq);
				
				
				OrderVO order = detailList.get(0);
				int count = detailList.size();
				if (count > 1) {
					order.setPname(order.getPname() + " 외" + (count-1) + " 건");
				}
				
				
				int totalPrice = 0;
				for (OrderVO vo : detailList) {
					totalPrice += vo.getPrice2() * vo.getQuantity();
				}
				order.setPrice2(totalPrice);
				
				orderList.add(order);	// 주문 요약정보를 리스트에 저장
			}
			
			request.setAttribute("title", "진행중인 주문내역");
			request.setAttribute("orderList", orderList);
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}

}
