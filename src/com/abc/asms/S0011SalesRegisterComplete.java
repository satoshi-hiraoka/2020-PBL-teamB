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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class salesComplete
 */
@WebServlet("/S0011SalesRegisterComplete")
public class S0011SalesRegisterComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String saleDate = (String) session.getAttribute("saleDate");
		String responsibleData = (String) session.getAttribute("responsibleData");
		String puroductCategory = (String) session.getAttribute("puroductCategory");
		String puroductName = (String) session.getAttribute("puroductName");
		String puroductUnitPrice = (String) session.getAttribute("puroductUnitPrice");
		String puroductNumber = (String) session.getAttribute("puroductNumber");
		String remark = (String) session.getAttribute("remark");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql/asms");

			con = ds.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO");
			sql.append("	sales(sale_date,");
			sql.append("	account_id,");
			sql.append("	category_id, ");
			sql.append("	trade_name,");
			sql.append("	unit_price,");
			sql.append("	sale_number,");
			sql.append("	note)");
			sql.append(" VALUES");
			sql.append("	(?,");
			sql.append("	?,");
			sql.append("	?,");
			sql.append("	?,");
			sql.append("	?,");
			sql.append("	?,");
			sql.append("	?)");

			ps = con.prepareStatement(sql.toString());
			ps.setString(1, saleDate);
			ps.setString(2, responsibleData);
			ps.setString(3, puroductCategory);
			ps.setString(4, puroductName);
			ps.setString(5, puroductUnitPrice);
			ps.setString(6, puroductNumber);
			ps.setString(7, remark);
			ps.executeUpdate();
			this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request, response);
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

}
