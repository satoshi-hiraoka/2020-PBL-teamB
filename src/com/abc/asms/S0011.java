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
 * Servlet implementation class S0011
 */
@WebServlet("/S0011")
public class S0011 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0011() {
		super();
	}

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

			StringBuilder isertSucCheckSql = new StringBuilder();
			isertSucCheckSql.append("SELECT");
			isertSucCheckSql.append("	sale_id");
			isertSucCheckSql.append(" FROM");
			isertSucCheckSql.append(" 	sales");
			isertSucCheckSql.append(" WHERE");
			isertSucCheckSql.append(" 	sale_date=?");
			isertSucCheckSql.append(" 	AND account_id=?");
			isertSucCheckSql.append(" 	AND category_id=?");
			isertSucCheckSql.append(" 	AND trade_name=?");
			isertSucCheckSql.append(" 	AND unit_price=?");
			isertSucCheckSql.append(" 	AND sale_number=?");
			isertSucCheckSql.append(" 	AND note=?");

			ps2 = cb.getCon().prepareStatement(isertSucCheckSql.toString());
			ps2.setString(1, sale.getSale_date());
			ps2.setInt(2, sale.getAccount_id());
			ps2.setInt(3, sale.getCategory_id());
			ps2.setString(4, sale.getTrade_name());
			ps2.setInt(5, sale.getUnit_price());
			ps2.setInt(6, sale.getSale_number());
			ps2.setString(7, sale.getNote());
			rs = ps2.executeQuery();

			System.out.println(isertSucCheckSql);

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

		SaleService saleservice = new SaleService();
		request.setAttribute("resposiblelist", saleservice.responsibleList(""));
		request.setAttribute("puroductCategorylist", saleservice.categoryList());
		this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request,
				response);
	}

}
