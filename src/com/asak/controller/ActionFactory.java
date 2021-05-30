package com.asak.controller;

import com.asak.admin.controller.action.AdminIndexAction;
import com.asak.admin.controller.action.AdminLoginAction;
import com.asak.admin.controller.action.AdminLogoutAction;
import com.asak.admin.controller.action.AdminMemberListAction;
import com.asak.admin.controller.action.AdminOrderListAction;
import com.asak.admin.controller.action.AdminOrderSaveAction;
import com.asak.admin.controller.action.AdminProductDetailAction;
import com.asak.admin.controller.action.AdminProductListAction;
import com.asak.admin.controller.action.AdminProductUpdateAction;
import com.asak.admin.controller.action.AdminProductUpdateFormAction;
import com.asak.admin.controller.action.AdminProductWriteAction;
import com.asak.admin.controller.action.AdminProductWriteFormAction;
import com.asak.admin.controller.action.AdminQnaDetailAction;
import com.asak.admin.controller.action.AdminQnaListAction;
import com.asak.admin.controller.action.AdminQnaRepsaveAction;
import com.asak.controller.action.Action;
import com.asak.controller.action.CartDeleteAction;
import com.asak.controller.action.CartInsertAction;
import com.asak.controller.action.CartListAction;
import com.asak.controller.action.ContractAction;
import com.asak.controller.action.FindZipNumAction;
import com.asak.controller.action.IdCheckFormAction;
import com.asak.controller.action.IndexAction;
import com.asak.controller.action.InsertOrderAction;
import com.asak.controller.action.JoinAction;
import com.asak.controller.action.JoinFormAction;
import com.asak.controller.action.LoginAction;
import com.asak.controller.action.LoginFormAction;
import com.asak.controller.action.LogoutAction;
import com.asak.controller.action.MyPageAction;
import com.asak.controller.action.OrderAllAction;
import com.asak.controller.action.OrderDetailAction;
import com.asak.controller.action.OrderListAction;
import com.asak.controller.action.ProductDetailAction;
import com.asak.controller.action.ProductKindAction;
import com.asak.controller.action.QnaListAction;
import com.asak.controller.action.QnaViewAction;
import com.asak.controller.action.QnaWriteAction;
import com.asak.controller.action.QnaWriteFormAction;


public class ActionFactory {
	
	private static ActionFactory instance = new ActionFactory();
	
	private ActionFactory() { }
	
	public static ActionFactory getInstance() {
		return instance;
	}
	
	public Action getAction(String command) {
		Action action = null;
		
		System.out.println("ActionFactory : " + command);
		
		if (command.equals("index")) {
			action = new IndexAction();
		} else if (command.equals("product_detail")) {
			action = new ProductDetailAction();
		} else if (command.equals("category")) {
			action = new ProductKindAction();
		} else if (command.equals("contract")) {
			action = new ContractAction();
		} else if (command.equals("join_form")) {
			action = new JoinFormAction();
		} else if (command.equals("id_check_form")) {
			action = new IdCheckFormAction();
		} else if (command.equals("find_zip_num")) {
			action = new FindZipNumAction();
		} else if (command.equals("join")) {
			action = new JoinAction();
		} else if (command.equals("login_form")) {
			action = new LoginFormAction();
		} else if (command.equals("login")) {
			action = new LoginAction();
		} else if (command.equals("logout")) {
			action = new LogoutAction();
		} else if (command.equals("cart_insert")) {
			action = new CartInsertAction();
		} else if (command.equals("cart_list")) {
			action = new CartListAction();
		} else if (command.equals("cart_delete")) {
			action = new CartDeleteAction();
		} else if (command.equals("order_insert")) {
			action = new InsertOrderAction();
		} else if (command.equals("order_list")) {
			action = new OrderListAction();
		} else if (command.equals("mypage")) {
			action = new MyPageAction();
		} else if (command.equals("order_detail")) {
			action = new OrderDetailAction();
		} else if (command.equals("order_all")) {
			action = new OrderAllAction();
		} else if (command.equals("qna_list")) {
			action = new QnaListAction();
		} else if (command.equals("qna_write_form")) {
			action = new QnaWriteFormAction();
		} else if (command.equals("qna_write")) {
			action = new QnaWriteAction();
		} else if (command.equals("qna_view")) {
			action = new QnaViewAction();
		} else if (command.equals("admin_login_form")) {
			action = new AdminIndexAction();
		} else if (command.equals("admin_login")) {
			action = new AdminLoginAction();
		} else if (command.equals("admin_logout")) {
			action = new AdminLogoutAction();
		} else if (command.equals("admin_product_list")) {
			action = new AdminProductListAction();
		} else if (command.equals("admin_product_write_form")) {
			action = new AdminProductWriteFormAction();
		} else if (command.equals("admin_product_write")) {
			action = new AdminProductWriteAction();
		} else if (command.equals("admin_product_detail")) {
			action = new AdminProductDetailAction();
		} else if (command.equals("admin_product_update_form")) {
			action = new AdminProductUpdateFormAction();
		} else if (command.equals("admin_product_update")) {
			action = new AdminProductUpdateAction();
		} else if (command.equals("admin_order_list")) {
			action = new AdminOrderListAction();
		} else if (command.equals("admin_order_save")) {
			action = new AdminOrderSaveAction();
		} else if (command.equals("admin_member_list")) {
			action = new AdminMemberListAction();
		} else if (command.equals("admin_qna_list")) {
			action = new AdminQnaListAction();
		} else if (command.equals("admin_qna_detail")) {
			action = new AdminQnaDetailAction();
		} else if (command.equals("admin_qna_repsave")) {
			action = new AdminQnaRepsaveAction();
		} 
		return action;
	}
	
}
