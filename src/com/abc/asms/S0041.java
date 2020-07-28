package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Account;

/**
 * Servlet implementation class S0041
 */
@WebServlet("/S0041")
public class S0041 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0041() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoginCheck login = new LoginCheck();
		login.checkLoginAndTransition(request, response, "/JSP/S0041.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoginCheck login = new LoginCheck();
		login.checkLoginAndTransition(request, response);

		request.setCharacterEncoding("UTF-8");

		ConnectionTeamB cb = new ConnectionTeamB();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT");
		sql.append(" 	name,");
		sql.append(" 	mail,");
		sql.append(" 	authority");
		sql.append(" FROM");
		sql.append(" 	accounts");
		sql.append(" WHERE");
		sql.append(" 	account_id = ?");

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cb.getCon().prepareStatement(sql.toString());
			//hiddenで送られたアカウントIDで検索
			ps.setString(1, request.getParameter("id"));

			rs = ps.executeQuery();

			if (rs.next()) {
				Account account = new Account();
				account.setName(rs.getString("name"));
				account.setMail(rs.getString("mail"));
				account.setAuthority(rs.getString("authority"));
				request.setAttribute("name", account.getName());
				request.setAttribute("mail", account.getMail());
				String authSales = null;
				String authAccount = null;
				if (account.getAuthority().equals("0")) {
					authSales = "0";
					authAccount = "0";
				} else if (account.getAuthority().equals("1")) {
					authSales = "1";
					authAccount = "0";
				} else if (account.getAuthority().equals("10")) {
					authSales = "0";
					authAccount = "1";
				} else {
					authSales = "1";
					authAccount = "1";
				}
				request.setAttribute("authSales", authSales);
				request.setAttribute("authAccount", authAccount);
				this.getServletContext().getRequestDispatcher("/JSP/S0042.jsp").forward(request, response);
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
}