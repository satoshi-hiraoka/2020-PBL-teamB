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
import javax.servlet.http.HttpSession;

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
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, "/JSP/S0040.jsp",
				puf.getPermitList("all"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, null,
				puf.getPermitList("all"));

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

				HttpSession sessionS0041 = request.getSession();
				AccountService as = new AccountService();
				Account user = as.parse(rs);
				user.setName(rs.getString("name"));
				user.setMail(rs.getString("mail"));
				if (rs.getString("authority").equals("0")) {
					user.setAuthSales("0");
					user.setAuthAccount("0");
				} else if (rs.getString("authority").equals("1")) {
					user.setAuthSales("1");
					user.setAuthAccount("0");
				} else if (rs.getString("authority").equals("10")) {
					user.setAuthSales("0");
					user.setAuthAccount("1");
				} else {
					user.setAuthSales("1");
					user.setAuthAccount("1");
				}
				user.setAuthority(rs.getString("authority"));
				sessionS0041.setAttribute("user", user);
				request.setAttribute("user", sessionS0041.getAttribute("user"));
				if (request.getParameter("edit") != null) {
					this.getServletContext().getRequestDispatcher("/JSP/S0042.jsp").forward(request, response);
				} else if (request.getParameter("delete") != null) {
					this.getServletContext().getRequestDispatcher("/JSP/S0044.jsp").forward(request, response);
				}
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
