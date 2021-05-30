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

public class AdminProductUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "AsakServlet?command=admin_product_list";
		
		int sizeLimit = 5 * 1024 * 1024;  // 5MB
		String savePath = "product_images";	// ��ǰ�̹��� ���� ����
		// ���� ������ ã��
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
		
		// ȭ�鿡�� ���۵� �Է� �����͸� �����ͺ��̽��� ����ó��
		ProductVO product = new ProductVO();
		product.setPseq(Integer.parseInt(multi.getParameter("pseq")));
		product.setKind(multi.getParameter("kind"));
		product.setName(multi.getParameter("name"));
		product.setPrice1( Integer.parseInt(multi.getParameter("price1")));
		product.setPrice2( Integer.parseInt(multi.getParameter("price2")));
		product.setPrice3( Integer.parseInt(multi.getParameter("price3")));
		product.setContent(multi.getParameter("content"));
		if (multi.getFilesystemName("image") == null) {
			product.setImage(multi.getParameter("nonmakeImg"));
		} else {
			product.setImage(multi.getFilesystemName("image")); 
		}
		product.setBestyn(multi.getParameter("bestyn"));
		product.setUseyn(multi.getParameter("useyn"));
		
		ProductDAO pDao = ProductDAO.getInstance();
		pDao.updateProduct(product);

		response.sendRedirect(url);
	

	}

}
