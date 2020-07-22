package com.abc.asms;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Account;

public class LoginCheck {

	LoginCheck() {

	}
	//登録画面以外（権限が必要ではない場合）
	public void checkLoginAndTransition(HttpServletRequest request, HttpServletResponse response, String transitionTo)
			throws ServletException, IOException {
		ArrayList<String> errMsg = new ArrayList<String>();
		//ログインチェック
		Account account = (Account) request.getSession().getAttribute("accounts");
		if (account == null) {
			errMsg.add("ログインしてください。");
			request.setAttribute("errMsg", errMsg);
			request.getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher(transitionTo).forward(request, response);
		}
	}

	//登録画面（権限が必要な場合）
	public void checkLoginAndTransition(HttpServletRequest request, HttpServletResponse response, String transitionTo,
			String auth1, String auth2)
			throws ServletException, IOException {
		ArrayList<String> errMsg = new ArrayList<String>();
		//ログインチェック
		Account account = (Account) request.getSession().getAttribute("accounts");
		if (account == null) {
			errMsg.add("ログインしてください。");
			request.setAttribute("errMsg", errMsg);
			request.getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
		} else {
			//権限チェック
			String authority = account.getAuthority();
			if (authority.equals(auth1) || authority.equals(auth2)) {
				errMsg.add("不正なアクセスです。");
				request.setAttribute("errMsg", errMsg);
				request.getRequestDispatcher("/JSP/C0020.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher(transitionTo).forward(request, response);
			}
		}
	}
}
