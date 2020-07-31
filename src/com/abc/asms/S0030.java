package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.dataset.Account;

/**
 * Servlet implementation class S0030
 */
@WebServlet("/S0030")
public class S0030 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#HttpServlet()
	 */
	public S0030() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, "/JSP/S0030.jsp",
				puf.getPermitList("account"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, null, puf.getPermitList("account"));

		request.setCharacterEncoding("UTF-8");

		ConnectionTeamB cb = new ConnectionTeamB();

		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");
		String authSales = request.getParameter("authSales");
		String authAccount = request.getParameter("authAccount");
		String authority = "";
		if (authSales.equals("0")) {
			if (authAccount.equals("0")) {
				authority = "0";
			} else if (authAccount.equals("1")) {
				authority = "10";
			}
		} else if (authSales.equals("1")) {
			if (authAccount.equals("0")) {
				authority = "1";
			} else if (authAccount.equals("1")) {
				authority = "11";
			}
		}

		HttpSession sessionS0030 = request.getSession();
		AccountService as = new AccountService();
		Account user = as.parse(request);
		user.setName(name);
		user.setMail(mail);
		user.setPassword(password);
		user.setPasswordCheck(passwordCheck);
		user.setAuthSales(authSales);
		user.setAuthAccount(authAccount);
		user.setAuthority(authority);

		ArrayList<String> errMsg = new ArrayList<String>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBuilder sql = new StringBuilder();
			//メールアドレスが登録済みかチェック
			sql.append("SELECT");
			sql.append(" 	account_id");
			sql.append(" FROM");
			sql.append(" 	accounts");
			sql.append(" WHERE");
			sql.append(" 	mail = ?");

			ps = cb.getCon().prepareStatement(sql.toString());

			ps.setString(1, mail);

			rs = ps.executeQuery();

			if (rs.next()) {
				errMsg.add("メールアドレスが既に登録されています。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException e) {

			}
		}

		checkName(name, errMsg);
		checkMail(mail, errMsg);
		checkPassword(password, passwordCheck, errMsg);
		checkAuthSales(authSales, errMsg);
		checkAuthAccount(authAccount, errMsg);

		sessionS0030.setAttribute("user", user);
		request.setAttribute("user", sessionS0030.getAttribute("user"));
		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp").forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher("/JSP/S0031.jsp").forward(request, response);
		}

	}

	private void checkName(String name, ArrayList<String> errMsg) {
		if (CheckInputValues.inputEmptyCheck(name)) {
			errMsg.add("氏名を入力してください。");
		}
		if (CheckInputValues.checkLength(name, 20)) {
			errMsg.add("氏名が長すぎます。");
		}
	}

	private void checkMail(String mail, ArrayList<String> errMsg) {
		if (CheckInputValues.inputEmptyCheck(mail)) {
			errMsg.add("メールアドレスを入力してください。");
		} else if (CheckInputValues.mailFormatCheck(mail)) {
			errMsg.add("メールアドレスを正しく入力してください。");
		}
		if (CheckInputValues.checkLength(mail, 100)) {
			errMsg.add("メールアドレスが長すぎます。");
		}

	}

	private void checkPassword(String password, String passwordCheck, ArrayList<String> errMsg) {
		if (CheckInputValues.inputEmptyCheck(password)) {
			errMsg.add("パスワードを入力してください。");
		}
		if (CheckInputValues.checkLength(password, 30)) {
			errMsg.add("パスワードが長すぎます。");
		}
		if (CheckInputValues.inputEmptyCheck(passwordCheck)) {
			errMsg.add("パスワード（確認）を入力してください。");
		}
		if (CheckInputValues.checkLength(passwordCheck, 30)) {
			errMsg.add("パスワード（確認）が長すぎます。");
		}
		if (CheckInputValues.passwordCheck(password, passwordCheck)) {
			errMsg.add("パスワードとパスワード（確認）が一致していません。");
		}
	}

	private void checkAuthSales(String authSales, ArrayList<String> errMsg) {
		if (CheckInputValues.inputEmptyCheck(authSales)) {
			errMsg.add("売上登録権限を入力してください。");
		} else if (CheckInputValues.radioButtonCheck(authSales)) {
			errMsg.add("売上登録権限に正しい値を入力してください。");
		}
	}

	private void checkAuthAccount(String authAccount, ArrayList<String> errMsg) {
		if (CheckInputValues.inputEmptyCheck(authAccount)) {
			errMsg.add("アカウント登録権限を入力してください。");
		} else if (CheckInputValues.radioButtonCheck(authAccount)) {
			errMsg.add("アカウント登録権限に正しい値を入力してください。");
		}
	}

}
