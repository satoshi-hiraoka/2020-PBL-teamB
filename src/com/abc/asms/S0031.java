package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
			sql.append("INSERT INTO");
			sql.append(" 	accounts(");
			sql.append(" 		name,");
			sql.append(" 		mail,");
			sql.append(" 		password,");
			sql.append(" 		authority)");
			sql.append(" VALUES(");
			sql.append(" 		?,?,MD5(?),?)");

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ps.setString(1, request.getParameter("name"));
			ps.setString(2, request.getParameter("mail"));
			ps.setString(3, request.getParameter("password"));
			if(request.getParameter("authSales").equals("0") && request.getParameter("authAccount").equals("0")) {
				ps.setString(4, "0");
			}else if(request.getParameter("authSales").equals("1") && request.getParameter("authAccount").equals("0")) {
				ps.setString(4, "1");
			}else if(request.getParameter("authSales").equals("0") && request.getParameter("authAccount").equals("1")) {
				ps.setString(4, "10");
			}else if(request.getParameter("authSales").equals("1") && request.getParameter("authAccount").equals("1")) {
				ps.setString(4, "11");
			}

			int result = ps.executeUpdate();
			if (result == 0) {
				this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp?err=1").forward(request, response);
			} else {
				this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp?suc=1").forward(request, response);
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
