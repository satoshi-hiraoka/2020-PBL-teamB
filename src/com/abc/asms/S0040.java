package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Account;

/**
 * Servlet implementation class S0040
 */
@WebServlet("/S0040")
public class S0040 extends HttpServlet {

	CheckLength cl = new CheckLength();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0040() throws ServletException, IOException {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		checkLoginAndTransition(request, response);
	}

	public void checkLoginAndTransition(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> errMsg = new ArrayList<String>();
		//ログインチェック
		Account account = (Account) request.getSession().getAttribute("accounts");
		if (account == null) {
			errMsg.add("ログインしてください。");
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher("/JSP/S0040.jsp").forward(request, response);
		}
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
		String authSales = request.getParameter("authSales");
		String authAccount = request.getParameter("authAccount");
		String authority = null;

		if (authSales.equals("all")) {
			if (authAccount.equals("all")) {
				authority = "0 OR 1 OR 10 OR 11";
			} else if (authAccount.equals("0")) {
				authority = "0 OR 1";
			} else {
				authority = "10 OR 11";
			}
		} else if (authSales.equals("0")) {
			if (authAccount.equals("all")) {
				authority = "0 OR 10";
			} else if (authAccount.equals("0")) {
				authority = "0";
			} else {
				authority = "10";
			}
		} else {
			if (authAccount.equals("all")) {
				authority = "1 OR 11";
			} else if (authAccount.equals("0")) {
				authority = "1";
			} else {
				authority = "11";
			}
		}

		ArrayList<String> errMsg = new ArrayList<String>();
		List<Account> list = new ArrayList<Account>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" 	account_id,");
		sql.append(" 	name,");
		sql.append(" 	mail,");
		sql.append(" 	authority");
		sql.append(" FROM");
		sql.append(" 	accounts");
		sql.append(" WHERE");
		sql.append(" 	authority = ?");
		if (!(cl.inputEmptyCheck(name))) {
			sql.append(" 	AND name LIKE '%?%'");
		}
		if (!(cl.inputEmptyCheck(mail))) {
			sql.append(" 	AND mail = ?");
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cb.getCon().prepareStatement(sql.toString());

			ps.setString(1, authority);

			if (!(cl.inputEmptyCheck(name))) {
				ps.setString(2, name);
				if (!(cl.inputEmptyCheck(mail))) {
					ps.setString(3, mail);
				}
			} else {
				if (!(cl.inputEmptyCheck(mail))) {
					ps.setString(2, mail);
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				Account resultAccount = new Account();
				resultAccount.setAccount_id(rs.getString("account_id"));
				resultAccount.setName(rs.getString("name"));
				resultAccount.setMail(rs.getString("mail"));
				resultAccount.setAuthority(rs.getString("authority"));
				list.add(resultAccount);
			}
			if (list.size() == 0) {
				errMsg.add("検索結果はありません。");
			}
			if (!(cl.inputEmptyCheck(name))) {
				checkName(name, errMsg);
			}
			if (!(cl.inputEmptyCheck(mail))) {
				checkMail(mail, errMsg);
			}

			if (errMsg.size() > 0) {
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0040.jsp").forward(request, response);
			} else {
				Account account = (Account) request.getSession().getAttribute("accounts");
				request.setAttribute("authority", account.getAuthority());
				request.setAttribute("list", list);
				this.getServletContext().getRequestDispatcher("/JSP/S0041.jsp").forward(request, response);
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
	}

	private void checkName(String name, ArrayList<String> errMsg) {
		if (cl.checkLength(name, 21)) {
			errMsg.add("氏名が長すぎます。");
		}
	}

	private void checkMail(String mail, ArrayList<String> errMsg) {
		if (cl.checkLength(mail, 101)) {
			errMsg.add("メールアドレスが長すぎます。");
		}
		if (!(mail.matches(
				"^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$"))) {
			errMsg.add("メールアドレスの形式が誤っています。");
		}
	}
}