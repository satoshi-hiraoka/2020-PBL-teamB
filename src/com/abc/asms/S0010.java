package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
		List<User> resposible_puroductCategory = new ArrayList<>();
		User user = new User();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql/asms");

			con = ds.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT");
			sql.append("	a.name,");
			sql.append("	c.category_name");
			sql.append(" FROM");
			sql.append("	sales s");
			sql.append("	LEFT JOIN");
			sql.append("		accounts a ON s.account_id=a.account_id");
			sql.append("	 LEFT JOIN");
			sql.append("		categories c ON s.category_id=c.category_id");
			sql.append(" WHERE");
			sql.append("	c.active_flg=0;");
			sql.append("");
			sql.append("");

			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {

				user.setName(rs.getString("name"));
				resposible_puroductCategory.add(user);
			}
			for (int i = 0; i < resposible_puroductCategory.size(); i++) {
				System.out.println(resposible_puroductCategory);
			}
			this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request,
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
