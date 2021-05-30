package com.asak.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asak.controller.action.Action;
import com.asak.dao.ProductDAO;
import com.asak.dto.ProductVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class AdminProductWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "AsakServlet?command=admin_product_list";
		
		int sizeLimit = 5 * 1024 * 1024;  
		String savePath = "product_images";	
		
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String uploadPath = context.getRealPath(savePath);
		
		MultipartRequest multi = new MultipartRequest(
				request,       
				uploadPath,    
				sizeLimit,	   
				"UTF-8",	   
				new DefaultFileRenamePolicy()	
				);
		
		// 화면에서 전송된 입력 데이터를 데이터베이스에 저장처리
		ProductVO product = new ProductVO();
		product.setKind(multi.getParameter("kind"));
		product.setName(multi.getParameter("name"));
		product.setPrice1( Integer.parseInt(multi.getParameter("price1")));
		product.setPrice2( Integer.parseInt(multi.getParameter("price2")));
		product.setPrice3( Integer.parseInt(multi.getParameter("price3")));
		product.setContent(multi.getParameter("content"));
		product.setImage(multi.getFilesystemName("image"));	// 이미지의 파일명을 저장
		
		ProductDAO pDao = ProductDAO.getInstance();
		pDao.insertProduct(product);

		response.sendRedirect(url);

	}

}
