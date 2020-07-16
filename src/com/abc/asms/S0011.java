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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import asms_Dateset.Account;
import asms_Dateset.Category;

/**
 * Servlet implementation class S0011
 */
@WebServlet("/S0011")
public class S0011 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0011() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<Account> resposiblelist = new ArrayList<>();
		List<Category> puroductCategorylist = new ArrayList<>();

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql/asms");

			con = ds.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT");
			sql.append("	*");
			sql.append(" FROM ");
			sql.append("	accounts");
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				Account responsibleUser = new Account();
				responsibleUser.setName(rs.getString("name"));
				responsibleUser.setAccount_id(rs.getString("account_id"));
				resposiblelist.add(responsibleUser);
				request.setAttribute("resposiblelist", resposiblelist);
			}

			con2 = ds.getConnection();

			StringBuilder sql2 = new StringBuilder();
			sql2.append(" SELECT ");
			sql2.append("	*");
			sql2.append(" FROM ");
			sql2.append("	categories ");
			ps2 = con2.prepareStatement(sql2.toString());
			rs2 = ps2.executeQuery();

			while (rs2.next()) {
				Category puroductCategoryData = new Category();
				puroductCategoryData.setCategory_name(rs2.getString("category_name"));
				puroductCategoryData.setCategory_id(rs2.getString("category_id"));
				puroductCategorylist.add(puroductCategoryData);
				request.setAttribute("puroductCategorylist", puroductCategorylist);

			}

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
		//ここにセッションにセットする
		HttpSession session = request.getSession();
		session.setAttribute("saleDate", request.getParameter("saleDate"));
		session.setAttribute("responsible", request.getParameter("responsible"));
		session.setAttribute("puroductCategory", request.getParameter("puroductCategory"));
		session.setAttribute("puroductName", request.getParameter("puroductName"));
		session.setAttribute("puroductUnitPrice", request.getParameter("puroductUnitPrice"));
		session.setAttribute("puroductNumber", request.getParameter("puroductNumber"));
		session.setAttribute("remark", request.getParameter("remark"));

		int number = Integer.valueOf(request.getParameter("puroductNumber"));
		int praice = Integer.valueOf(request.getParameter("puroductUnitPrice"));
		int subtotal = number * praice;
		session.setAttribute("commaNumer", String.format("%,d", number));
		session.setAttribute("commaPrice", String.format("%,d", praice));
		session.setAttribute("commaSubtotal", String.format("%,d", subtotal));

		this.getServletContext().getRequestDispatcher("/JSP/S0011.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ゲットだよ");
	}
}
