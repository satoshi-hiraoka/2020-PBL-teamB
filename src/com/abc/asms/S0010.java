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

import com.abc.asms.dataset.Account;
import com.abc.asms.dataset.Category;

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

	public void checkLoginAndTransition(HttpServletRequest request, HttpServletResponse response, String transitiontTo)
			throws ServletException, IOException {
		//ログインチェック
		List<String> errMsg = new ArrayList<String>();

		Account account = (Account) request.getSession().getAttribute("accounts");

		if (account == null) {
			errMsg.add("ログインしてください");
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
		} else {
			String authority = account.getAuthority();
			if (authority.equals("0") || authority.equals("1")) {
				errMsg.add("不正なアクセスです。");
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher(transitiontTo).forward(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		checkLoginAndTransition(request, response, "/JSP/C0020.jsp");

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
			HttpSession session = request.getSession();
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
				session.setAttribute("resposiblelist", resposiblelist);
			}

			con2 = ds.getConnection();

			StringBuilder sql2 = new StringBuilder();
			sql2.append(" SELECT ");
			sql2.append("	*");
			sql2.append(" FROM ");
			sql2.append("	categories ");
			sql2.append(" WHERE active_flg=1");
			ps2 = con2.prepareStatement(sql2.toString());
			rs2 = ps2.executeQuery();

			while (rs2.next()) {
				Category puroductCategoryData = new Category();
				puroductCategoryData.setCategory_name(rs2.getString("category_name"));
				puroductCategoryData.setCategory_id(rs2.getString("category_id"));
				puroductCategorylist.add(puroductCategoryData);
				session.setAttribute("puroductCategorylist", puroductCategorylist);

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
