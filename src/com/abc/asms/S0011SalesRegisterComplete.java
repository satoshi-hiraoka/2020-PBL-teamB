package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.dataset.Sale;

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
		Sale sale = (Sale) session.getAttribute("sales");
		ConnectionTeamB cb = new ConnectionTeamB();
		ArrayList<String> errMsg = new ArrayList<String>();
		ArrayList<String> sucMsg = new ArrayList<String>();

		String saleDate = sale.getSale_date();
		String responsibleData = sale.getAccount_id();
		String puroductCategory = sale.getCategory_id();
		String puroductName = sale.getTrade_name();
		String puroductUnitPrice = sale.getUnit_price();
		String puroductNumber = sale.getSale_number();
		String remark = sale.getNote();

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
			ps.setString(1, saleDate);
			ps.setString(2, responsibleData);
			ps.setString(3, puroductCategory);
			ps.setString(4, puroductName);
			ps.setString(5, puroductUnitPrice);
			ps.setString(6, puroductNumber);
			ps.setString(7, remark);
			int result = ps.executeUpdate();

			session.removeAttribute("sales");
			session.removeAttribute("puroductCategory");
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
				sale.setSale_id(rs.getString("sale_id"));
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

}
