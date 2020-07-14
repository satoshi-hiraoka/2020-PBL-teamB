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
 * Servlet implementation class S0010
 */
@WebServlet("/S0010")
public class S0010 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0010() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql/login");

			con = ds.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("	SELECT");
			sql.append("		u.id,");
			sql.append("		u.loginID,");
			sql.append("		u.delete_flag, ");
			sql.append("		u.create_date,");
			sql.append("		u.update_date,");
			sql.append("		au.auth_id,");
			sql.append("		a.name");
			sql.append("	FROM");
			sql.append("		auth_user au");
			sql.append("		LEFT JOIN");
			sql.append("			users u ON au.user_id=u.id");
			sql.append("		LEFT JOIN");
			sql.append("			auth a ON au.auth_id=a.id");
			sql.append("	WHERE");
			sql.append("		loginID='kanai'");
			sql.append("		and password = MD5('shota')");
			sql.append("		and u.delete_flag=0");
			sql.append("		and au.delete_flag=0");
			sql.append("		and a.delete_flag=0");

			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			this.getServletContext().getRequestDispatcher("/UseManagement/searchUser.jsp").forward(request,
					response);
		} catch (Exception e) {
			throw new ServletException(e);

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {

			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
