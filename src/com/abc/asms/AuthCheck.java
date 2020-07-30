package com.abc.asms;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Account;

public class AuthCheck {

	public static void checkAuthandTransition(HttpServletRequest request, HttpServletResponse response,
			String transitionTo, ArrayList<String> permitList) throws ServletException, IOException {
		ArrayList<String> errMsg = new ArrayList<String>();
		Account account = (Account) request.getSession().getAttribute("accounts");
		//権限チェック
		String authority = account.getAuthority();
		if (!(permitList.contains(authority))) {
			errMsg.add("不正なアクセスです。");
			request.setAttribute("errMsg", errMsg);
			request.getRequestDispatcher("/JSP/C0020.jsp").forward(request, response);
		}
		if (transitionTo != null) {
			request.getRequestDispatcher(transitionTo).forward(request, response);
		}
	}
}
