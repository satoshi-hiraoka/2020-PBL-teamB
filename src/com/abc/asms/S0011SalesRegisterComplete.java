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
import javax.servlet.http.HttpSession;

import com.abc.asms.dataset.Account;
import com.abc.asms.dataset.Category;
import com.abc.asms.dataset.Sale;

/**
 * Servlet implementation class salesComplete
 */
@WebServlet("/S0011SalesRegisterComplete")
public class S0011SalesRegisterComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ログインチェックと権限チェック
		LoginCheck logincheck = new LoginCheck();
		logincheck.checkLoginAndTransition(request, response, "0", "10");

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Sale sale = (Sale) session.getAttribute("sales");
		ConnectionTeamB cb = new ConnectionTeamB();
		ArrayList<String> errMsg = new ArrayList<String>();
		ArrayList<String> sucMsg = new ArrayList<String>();

		//		String saleDate = sale.getSale_date();
		//		String responsibleData = sale.getAccount_id();
		//		String puroductCategory = sale.getCategory_id();
		//		String puroductName = sale.getTrade_name();
		//		String puroductUnitPrice = sale.getUnit_price();
		//		String puroductNumber = sale.getSale_number();
		//		String remark = sale.getNote();

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		//ログイン状態のチェック

		try {

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

			ps = cb.getCon().prepareStatement(sql.toString());
			ps.setString(1, sale.getSale_date());
			ps.setInt(2, sale.getAccount_id());
			ps.setInt(3, sale.getCategory_id());
			ps.setString(4, sale.getTrade_name());
			ps.setInt(5, sale.getUnit_price());
			ps.setInt(6, sale.getSale_number());
			ps.setString(7, sale.getNote());
			int result = ps.executeUpdate();

			session.removeAttribute("sales");
			session.removeAttribute("puroductCategory");
			//個別で持っているのはおかしい。
			session.removeAttribute("responsible");
			session.removeAttribute("commaNumer");

			session.removeAttribute("commaPrice");
			session.removeAttribute("commaSubtotal");

			StringBuilder sql2 = new StringBuilder();
			sql2.append("SELECT");
			sql2.append("	sale_id");
			sql2.append(" FROM");
			sql2.append(" 	sales");
			sql2.append(" WHERE");
			sql2.append(" 	sale_id = (select max(sale_id) from sales)");

			ps2 = cb.getCon().prepareStatement(sql2.toString());

			rs = ps2.executeQuery();

			if (rs.next()) {
				sale.setSale_id(rs.getInt("sale_id"));
			}

			if (result == 0) {
				errMsg.add("登録に失敗しました。");
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request, response);
			} else {
				sucMsg.add("No" + sale.getSale_id() + "のアカウントを登録しました。");
				request.setAttribute("sucMsg", sucMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request, response);
			}

			this.getServletContext().getRequestDispatcher("/S0010").forward(request, response);
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
