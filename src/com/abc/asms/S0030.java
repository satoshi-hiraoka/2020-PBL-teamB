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

/**
 * Servlet implementation class S0030
 */
@WebServlet("/S0030")
public class S0030 extends HttpServlet {

	CheckLength cl = new CheckLength();

	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#HttpServlet()
	 */
	public S0030() throws ServletException, IOException {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoginCheck login = new LoginCheck();
		login.checkLoginAndTransition(request, response, "/JSP/S0030.jsp", "0", "1");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		ConnectionTeamB cb = new ConnectionTeamB();

		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");
		String authSales = request.getParameter("authSales");
		String authAccount = request.getParameter("authAccount");

		ArrayList<String> errMsg = new ArrayList<String>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" 	account_id");
		sql.append(" FROM");
		sql.append(" 	accounts");
		sql.append(" WHERE");
		sql.append(" 	mail = ?");

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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

		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp").forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher("/JSP/S0031.jsp").forward(request, response);
		}

		HttpSession session = request.getSession();

		session.setAttribute("name", name);
		session.setAttribute("mail", mail);
		session.setAttribute("password", password);
		session.setAttribute("passwordCheck", passwordCheck);
		session.setAttribute("authSales", authSales);
		session.setAttribute("authAccount", authAccount);
	}

	private void checkName(String name, ArrayList<String> errMsg) {
		if (cl.inputEmptyCheck(name)) {
			errMsg.add("氏名を入力してください。");
		}
		if (cl.checkLength(name, 21)) {
			errMsg.add("氏名が長すぎます。");
		}
	}

	private void checkMail(String mail, ArrayList<String> errMsg) {
		if (cl.inputEmptyCheck(mail)) {
			errMsg.add("メールアドレスを入力してください。");
		}
		if (cl.checkLength(mail, 101)) {
			errMsg.add("メールアドレスが長すぎます。");
		}
		if (!(mail.matches(
				"^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$"))) {
			errMsg.add("メールアドレスを正しく入力してください。");
		}
	}

	private void checkPassword(String password, String passwordCheck, ArrayList<String> errMsg) {
		if (cl.inputEmptyCheck(password)) {
			errMsg.add("パスワードを入力してください。");
		}
		if (cl.checkLength(password, 31)) {
			errMsg.add("パスワードが長すぎます。");
		}
		if (cl.inputEmptyCheck(passwordCheck)) {
			errMsg.add("パスワード（確認）を入力してください。");
		}
		if (!(password.equals(passwordCheck))) {
			errMsg.add("パスワードとパスワード（確認）が一致していません。");
		}
	}

	private void checkAuthSales(String authSales, ArrayList<String> errMsg) {
		if (cl.inputEmptyCheck(authSales)) {
			errMsg.add("売上登録権限を入力してください。");
		}
		if (!(authSales.equals("0") || authSales.equals("1"))) {
			errMsg.add("売上登録権限に正しい値を入力してください。");
		}
	}

	private void checkAuthAccount(String authAccount, ArrayList<String> errMsg) {
		if (cl.inputEmptyCheck(authAccount)) {
			errMsg.add("アカウント登録権限を入力してください。");
		}
		if (!(authAccount.equals("0") || authAccount.equals("1"))) {
			errMsg.add("アカウント登録権限に正しい値を入力してください。");
		}
	}

}
