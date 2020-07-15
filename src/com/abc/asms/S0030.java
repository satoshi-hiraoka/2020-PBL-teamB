package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class S0030
 */
@WebServlet("/S0030")
public class S0030 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0030() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql/asms");
			con = ds.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT");
			sql.append(" 	*");
			sql.append(" FROM");
			sql.append(" 	accounts");
			sql.append(" WHERE");
			sql.append(" 	mail = ?");

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ps.setString(1, request.getParameter("mail"));

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp?err=1").forward(request, response);
			} else {
				request.setAttribute("name", rs.getString("name"));
				request.setAttribute("mail", rs.getString("mail"));
				request.setAttribute("password", rs.getString("password"));
				request.setAttribute("passwordCheck", rs.getString("passwordCheck"));
				if(request.getParameter("authSales").equals("0")) {
					request.setAttribute("authSales", "0");
				}else {
					request.setAttribute("authSales", "1");
				}
				if(request.getParameter("authAccount").equals("0")) {
					request.setAttribute("authAccount", "0");
				}else {
					request.setAttribute("authAccount", "1");
				}
				if(request.getParameter("password")!=request.getParameter("passwordCheck")) {
					this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp?err=2").forward(request, response);
				}
				this.getServletContext().getRequestDispatcher("/JSP/S0031.jsp").forward(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (con == null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}

}
