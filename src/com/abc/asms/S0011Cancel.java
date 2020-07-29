package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Account;
import com.abc.asms.dataset.Category;

/**
 * Servlet implementation class S0011Cancel
 */
@WebServlet("/S0011Cancel")
public class S0011Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0011Cancel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ConnectionTeamB cb = new ConnectionTeamB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<Account> resposiblelist = new ArrayList<>();
		List<Category> puroductCategorylist = new ArrayList<>();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT");
			sql.append("	*");
			sql.append(" FROM ");
			sql.append("	accounts");
			ps = cb.getCon().prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				Account responsibleUser = new Account();
				responsibleUser.setName(rs.getString("name"));
				responsibleUser.setAccount_id(rs.getString("account_id"));
				resposiblelist.add(responsibleUser);

			}
			request.setAttribute("resposiblelist", resposiblelist);

			StringBuilder sql2 = new StringBuilder();
			sql2.append(" SELECT ");
			sql2.append("	*");
			sql2.append(" FROM ");
			sql2.append("	categories ");
			sql2.append(" WHERE active_flg=1");
			ps2 = cb.getCon().prepareStatement(sql2.toString());
			rs2 = ps2.executeQuery();

			while (rs2.next()) {
				Category puroductCategoryData = new Category();
				puroductCategoryData.setCategory_name(rs2.getString("category_name"));
				puroductCategoryData.setCategory_id(rs2.getString("category_id"));
				puroductCategorylist.add(puroductCategoryData);

			}
			request.setAttribute("puroductCategorylist", puroductCategorylist);

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
				if (cb.getCon() != null) {
					cb.getCon().close();
				}
			} catch (Exception e) {

			}
		}
	}

}
