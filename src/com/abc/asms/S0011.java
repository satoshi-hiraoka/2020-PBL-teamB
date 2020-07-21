package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		SaleService saleservice = new SaleService();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<String> errMsg = new ArrayList<String>();

		//		String saleDate = request.getParameter("saleDate");
		//		String puroductName = request.getParameter("puroductName");
		//		String puroductUnitPrice = request.getParameter("puroductUnitPrice");
		//		String puroductNumber = request.getParameter("puroductNumber");
		//		String remark = request.getParameter("remark");
		session.setAttribute("responsible", request.getParameter("responsible"));
		session.setAttribute("puroductCategory", request.getParameter("puroductCategory"));
		//		List<Sale> sales=(List<Sale>) saleservice.parse(request);
		session.setAttribute("sales", saleservice.parse(request));

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
			sql.append(" WHERE ");
			sql.append("	account_id=?");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, saleservice.parse(request).getAccount_id());
			rs = ps.executeQuery();

			if (!rs.next()) {
				errMsg.add("アカウントテーブルに存在しません。");
			}

			con2 = ds.getConnection();

			StringBuilder sql2 = new StringBuilder();
			sql2.append(" SELECT ");
			sql2.append("	*");
			sql2.append(" FROM ");
			sql2.append("	categories ");
			sql2.append(" WHERE active_flg=1");
			sql.append("	category_id=?");
			ps2 = con2.prepareStatement(sql2.toString());
			ps.setString(1, saleservice.parse(request).getCategory_id());
			rs2 = ps2.executeQuery();

			if (!rs2.next()) {
				errMsg.add("商品カテゴリーテーブルに存在しません。");
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

						CheckLength checklength = new CheckLength();
			//未入力かチェック
						if (checklength.inputEmptyCheck(saleservice.parse(request).getSale_date())) {
							errMsg.add("販売日を入力してください");
						}
						if (saleservice.parse(request).getAccount_id() == null) {
							errMsg.add("担当者が未選択です。");
						}
						if (saleservice.parse(request).getCategory_id() == null) {
							errMsg.add("商品カテゴリーが未選択です。");
					}
				if (checklength.inputEmptyCheck(saleservice.parse(request).getTrade_name())) {
						errMsg.add("商品名を入力してください");
						}
					if (checklength.inputEmptyCheck(saleservice.parse(request).getUnit_price())) {
							errMsg.add("単価をを入力してください");
						}
					if (checklength.inputEmptyCheck(saleservice.parse(request).getSale_number())) {
						errMsg.add("個数を入力してください");
					}
					//文字数長さチェック
					if (checklength.checkLength(saleservice.parse(request).getTrade_name(), 101)) {
						errMsg.add("商品名が長すぎます。");
						}
						if (checklength.checkLength(saleservice.parse(request).getUnit_price(), 10)) {
						errMsg.add("単価が長すぎます。");
					}
						if (checklength.checkLength(saleservice.parse(request).getSale_number(), 10)) {
							errMsg.add("個数が長すぎます。");
					}
					if (checklength.checkLength(saleservice.parse(request).getNote(), 400)) {
						errMsg.add("備考が長すぎます。");
					}
			//入力の形式チェック
						LocalDate localdate;
						try {
							localdate = LocalDate.parse(saleservice.parse(request).getSale_date(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
						} catch (java.time.format.DateTimeParseException e) {
							errMsg.add("販売日を正しく入力してください。");
						} finally {

							try {
								if (Integer.parseInt(saleservice.parse(request).getUnit_price()) <= 1) {
									errMsg.add("単価を正しく入力してください");
								}

							} catch (NumberFormatException e) {
								errMsg.add("単価を正しく入力してください");
							} finally {
						try {
									if (Integer.parseInt(saleservice.parse(request).getSale_number()) <= 1) {
										errMsg.add("個数を正しく入力してください");
		 					}
								} catch (NumberFormatException e) {
									errMsg.add("個価を正しく入力してください");
								} finally {

			//ここにセッションにセットする

//															session.setAttribute("saleDate", saleDate);
//															session.setAttribute("responsible", responsible);
//															session.setAttribute("puroductCategory", puroductCategory);
//															session.setAttribute("puroductName", puroductName);
//															session.setAttribute("puroductUnitPrice", puroductUnitPrice);
//															session.setAttribute("puroductNumber", puroductNumber);
//														session.setAttribute("remark", remark);
			//errMsgに何か入っていればs0010.jspに飛ばす。
			if (errMsg.size() > 0) {
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request, response);
			}

			int number = Integer.valueOf(saleservice.parse(request).getSale_number());
			int praice = Integer.valueOf(saleservice.parse(request).getUnit_price());
			int subtotal = number * praice;
			session.setAttribute("commaNumer", String.format("%,d", number));
			session.setAttribute("commaPrice", String.format("%,d", praice));
			session.setAttribute("commaSubtotal", String.format("%,d", subtotal));

			this.getServletContext().getRequestDispatcher("/JSP/S0011.jsp").forward(request, response);
		}
	}
				}
			}
		}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
