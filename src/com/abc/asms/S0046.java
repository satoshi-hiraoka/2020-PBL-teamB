package com.abc.asms;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Account;

@WebServlet("/S0046")
public class S0046 extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkLoginAndTransition(request, response, "/JSP/S0045.jsp");
	}
	public void checkLoginAndTransition(HttpServletRequest request, HttpServletResponse response, String transitiontTo)
			throws ServletException, IOException {
		//ログインチェック
		Account account = (Account) request.getSession().getAttribute("accounts");

		if (account == null) {
			this.getServletContext().getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher(transitiontTo).forward(request, response);
		}
	}
	private void checkPassword(String passWord, ArrayList<String> errMsg) {
		if (passWord.isEmpty()) {
			errMsg.add("パスワードが入力されていません。");
		}
		if (!checkLength(passWord, 31)) {
			errMsg.add("パスワードが長すぎます。");
		}
	}
	
	private void checkPassword1(String passWord1, ArrayList<String> errMsg) {
		if (passWord1.isEmpty()) {
			errMsg.add("パスワードが入力されていません。");
		}
		if (!checkLength(passWord1, 31)) {
			errMsg.add("パスワードが長すぎます。");
		}
	}
	private boolean checkLength(String value, int max) {
		int length = value.getBytes().length;
		if (length < max)
			return true;
		return false;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String passWord = request.getParameter("passWord");
		String passWord1 = request.getParameter("passWord1");
	}

}
