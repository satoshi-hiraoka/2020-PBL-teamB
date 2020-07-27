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
import javax.servlet.http.HttpSession;

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
		LoginCheck login = new LoginCheck();
		login.checkLoginAndTransition(request, response, "/JSP/S0031.jsp", "0", "1");
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

		HttpSession sessionS0030 = request.getSession();

		String name = (String) sessionS0030.getAttribute("name");
		String mail = (String) sessionS0030.getAttribute("mail");
		String password = (String) sessionS0030.getAttribute("password");
		String authSales = (String) sessionS0030.getAttribute("authSales");
		String authAccount = (String) sessionS0030.getAttribute("authAccount");
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
		PreparedStatement ps2 = null;
		ResultSet rs = null;

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
				sessionS0030.invalidate();
				this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
		}
	}

}
