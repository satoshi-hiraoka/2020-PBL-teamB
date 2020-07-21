package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Account;

/**
 * Servlet implementation class S0031
 */
@WebServlet("/S0031")
public class S0031 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0031() {
		super();
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
			} else {
				this.getServletContext().getRequestDispatcher("/JSP/S0031.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		ConnectionTeamB cb = new ConnectionTeamB();

		ArrayList<String> errMsg = new ArrayList<String>();
		ArrayList<String> sucMsg = new ArrayList<String>();

		Account account = new Account();

		String name = (String) request.getSession().getAttribute("name");
		String mail = (String) request.getSession().getAttribute("mail");
		String password = (String) request.getSession().getAttribute("password");
		String authSales = (String) request.getSession().getAttribute("authSales");
		String authAccount = (String) request.getSession().getAttribute("authAccount");
		String authority = null;
		if (authSales.equals("0")) {
			if (authAccount.equals("0")) {
				authority = "0";
			} else {
				authority = "10";
			}
		} else {
			if (authAccount.equals("0")) {
				authority = "1";
			} else {
				authority = "11";
			}
		}

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO");
		sql.append(" 	accounts(");
		sql.append(" 		name,");
		sql.append(" 		mail,");
		sql.append(" 		password,");
		sql.append(" 		authority)");
		sql.append(" VALUES(");
		sql.append(" 		?,?,MD5(?),?)");

		PreparedStatement ps = null;

		try {
			ps = cb.getCon().prepareStatement(sql.toString());

			ps.setString(1, name);
			ps.setString(2, mail);
			ps.setString(3, password);
			ps.setString(4, authority);

			int result = ps.executeUpdate();

			StringBuilder sql2 = new StringBuilder();
			sql2.append("SELECT");
			sql2.append(" 	account_id");
			sql2.append(" FROM");
			sql2.append(" 	accounts");
			sql2.append(" WHERE");
			sql2.append(" 	mail = ?");

			PreparedStatement ps2 = null;
			ResultSet rs = null;

			ps2 = cb.getCon().prepareStatement(sql2.toString());
			ps2.setString(1, mail);

			rs = ps2.executeQuery();

			if (rs.next()) {
				account.setAccount_id(rs.getString("account_id"));
			}

			if (result == 0) {
				errMsg.add("登録に失敗しました。");
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp").forward(request, response);
			} else {
				sucMsg.add("No" + account.getAccount_id() + "のアカウントを登録しました。");
				request.setAttribute("sucMsg", sucMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
			}
		}
	}

}
