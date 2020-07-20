package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
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
public class S0030 extends HttpServlet implements InterfaceConnectionTeamB, InterfaceCheckLength {

	private ConnectionTeamB cb;
	private CheckLength cl;

	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#HttpServlet()
	 */
	public S0030() throws ServletException, IOException {
		super();
		this.cb = new ConnectionTeamB();
		this.cl = new CheckLength();
	}

	public Connection getCon() {
		return this.cb.getCon();
	}

	public boolean checkLength(String value, int max) {
		return this.cl.checkLength(value, max);
	}

	public boolean inputEmptyCheck(String value) {
		return this.cl.inputEmptyCheck(value);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		checkLoginAndTransition(request, response, "/JSP/C0020.jsp");
	}

	public void checkLoginAndTransition(HttpServletRequest request, HttpServletResponse response, String transitionTo)
			throws ServletException, IOException {
		ArrayList<String> errMsg = new ArrayList<String>();
		//ログインチェック
		Account account = (Account) request.getSession().getAttribute("accounts");
		if (account == null) {
			errMsg.add("ログインしてください。");
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
		} else {
			String authority = account.getAuthority();
			if (authority.equals("0") || authority.equals("1")) {
				errMsg.add("不正なアクセスです。");
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher(transitionTo).forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		doGet(request, response);

		ArrayList<String> errMsg = new ArrayList<String>();

		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");
		String authSales = request.getParameter("authSales");
		String authAccount = request.getParameter("authAccount");

		checkName(name, errMsg);
		checkMail(mail, errMsg);
		checkPassword(password, passwordCheck, errMsg);
		checkAuthSales(authSales, errMsg);
		checkAuthAccount(authAccount, errMsg);

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
			ps = getCon().prepareStatement(sql.toString());

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

		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp").forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher("/JSP/S0031.jsp").forward(request, response);
		}

		HttpSession sessionS0030 = request.getSession();
		sessionS0030.setAttribute("name", name);
		sessionS0030.setAttribute("mail", mail);
		sessionS0030.setAttribute("password", password);
		sessionS0030.setAttribute("passwordCheck", passwordCheck);
		sessionS0030.setAttribute("authSales", authSales);
		sessionS0030.setAttribute("authAccount", authAccount);
	}

	private void checkName(String name, ArrayList<String> errMsg) {
		if (inputEmptyCheck(name)) {
			errMsg.add("氏名を入力してください。");
		}
		if (checkLength(name, 21)) {
			errMsg.add("氏名が長すぎます。");
		}
	}

	private void checkMail(String mail, ArrayList<String> errMsg) {
		if (inputEmptyCheck(mail)) {
			errMsg.add("メールアドレスを入力してください。");
		}
		if (checkLength(mail, 101)) {
			errMsg.add("メールアドレスが長すぎます。");
		}
		if (!(mail.matches(
				"^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$"))) {
			errMsg.add("メールアドレスを正しく入力してください。");
		}
	}

	private void checkPassword(String password, String passwordCheck, ArrayList<String> errMsg) {
		if (inputEmptyCheck(password)) {
			errMsg.add("パスワードを入力してください。");
		}
		if (checkLength(password, 31)) {
			errMsg.add("パスワードが長すぎます。");
		}
		if (inputEmptyCheck(passwordCheck)) {
			errMsg.add("パスワード（確認）を入力してください。");
		}
		if (!(password.equals(passwordCheck))) {
			errMsg.add("パスワードとパスワード（確認）が一致していません。");
		}
	}

	private void checkAuthSales(String authSales, ArrayList<String> errMsg) {
		if (inputEmptyCheck(authSales)) {
			errMsg.add("売上登録権限を入力してください。");
		}
		if (!(authSales.equals("0") || authSales.equals("1"))) {
			errMsg.add("売上登録権限に正しい値を入力してください。");
		}
	}

	private void checkAuthAccount(String authAccount, ArrayList<String> errMsg) {
		if (inputEmptyCheck(authAccount)) {
			errMsg.add("アカウント登録権限を入力してください。");
		}
		if (!(authAccount.equals("0") || authAccount.equals("1"))) {
			errMsg.add("アカウント登録権限に正しい値を入力してください。");
		}
	}

}
