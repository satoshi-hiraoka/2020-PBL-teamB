package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class S0010
 */
@WebServlet("/S0010")
public class S0010 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0010() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ログインと権限チェック
		LoginCheck logincheck = new LoginCheck();
		logincheck.checkLoginAndTransition(request, response, "0", "10");

		SaleService saleservice = new SaleService();
		HttpSession session = request.getSession();
		session.setAttribute("resposiblelist", saleservice.responsibleList(""));
		session.setAttribute("puroductCategorylist", saleservice.categoryList());
		this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request,
				response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ログインチェックと権限チェック
		LoginCheck logincheck = new LoginCheck();
		logincheck.checkLoginAndTransition(request, response, "0", "10");

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		SaleService saleservice = new SaleService();
		ConnectionTeamB cb = new ConnectionTeamB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		LocalDate localdate;
		List<String> errMsg = new ArrayList<String>();

		CheckLength checklength = new CheckLength();
		//未入力かチェック

		if (checklength.inputEmptyCheck(saleservice.parse(request).getSale_date())) {
			errMsg.add("販売日を入力してください");
		}
		if (Integer.valueOf(saleservice.parse(request).getAccount_id()) == null) {
			errMsg.add("担当者が未選択です。");
		}
		if (Integer.valueOf(saleservice.parse(request).getCategory_id()) == null) {
			errMsg.add("商品カテゴリーが未選択です。");
		}
		if (checklength.inputEmptyCheck(String.valueOf(saleservice.parse(request).getTrade_name()))) {
			errMsg.add("商品名を入力してください");
		}
		if (checklength.inputEmptyCheck(String.valueOf(saleservice.parse(request).getUnit_price()))) {
			errMsg.add("単価をを入力してください");
		}
		if (checklength.inputEmptyCheck(String.valueOf(saleservice.parse(request).getSale_number()))) {
			errMsg.add("個数を入力してください");
		}
		//文字数長さチェック
		if (errMsg.size() < 0) {

			//			if (checklength.checkLength(saleservice.parse(request).getTrade_name(), 101)) {
			//				errMsg.add("商品名が長すぎます。");
			//			}
			//			if (checklength.checkLength(saleservice.parse(request).getUnit_price(), 10)) {
			//				errMsg.add("単価が長すぎます。");
			//			}
			//			if (checklength.checkLength(saleservice.parse(request).getSale_number(), 10)) {
			//				errMsg.add("個数が長すぎます。");
			//			}
			//			if (checklength.checkLength(saleservice.parse(request).getNote(), 400)) {
			//				errMsg.add("備考が長すぎます。");
			//			}
		}
		//入力の形式チェック
		if (errMsg.size() < 0) {
			try {
				String saleDate = saleservice.parse(request).getSale_date();

				if (saleDate.length() == 8) {

					localdate = LocalDate.parse(saleDate, DateTimeFormatter.ofPattern("yyyy/M/d"));

				} else if (saleDate.length() == 10) {
					localdate = LocalDate.parse(saleDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
				} else {

					if ((saleDate.charAt(5) == '0' && saleDate.charAt(8) == '0')) {
						localdate = LocalDate.parse(saleDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
					} else if (saleDate.charAt(7) == '0' || saleDate.charAt(8) == '0') {
						localdate = LocalDate.parse(saleDate, DateTimeFormatter.ofPattern("yyyy/M/dd"));
					} else if (saleDate.charAt(5) == '0' || saleDate.charAt(5) == '1') {
						localdate = LocalDate.parse(saleDate, DateTimeFormatter.ofPattern("yyyy/MM/d"));
					} else {
						localdate = LocalDate.parse(saleDate, DateTimeFormatter.ofPattern("yyyy/M/dd"));

					}
				}
			} catch (java.time.format.DateTimeParseException e) {
				errMsg.add("販売日を正しく入力してください。");
			}
		}

		try {
			if (saleservice.parse(request).getUnit_price() <= 1) {
				errMsg.add("単価を正しく入力してください");
			}

		} catch (NumberFormatException e) {
			errMsg.add("単価を正しく入力してください");
		} finally {
			try {
				if (saleservice.parse(request).getSale_number() <= 1) {
					errMsg.add("個数を正しく入力してください");
				}
			} catch (NumberFormatException e) {
				errMsg.add("個価を正しく入力してください");
			} finally {

				//errMsgに何か入っていればs0010.jspに飛ばす。
				if (errMsg.size() > 0) {
					request.setAttribute("errMsg", errMsg);
					request.setAttribute("responsible", request.getParameter("responsible"));
					request.setAttribute("puroductCategory", request.getParameter("puroductCategory"));
					request.setAttribute("sales", saleservice.parse(request));
					this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request, response);
				}

				try {

					StringBuilder sql = new StringBuilder();
					sql.append(" SELECT");
					sql.append("	*");
					sql.append(" FROM ");
					sql.append("	accounts");
					sql.append(" WHERE ");
					sql.append("	account_id=?");
					ps = cb.getCon().prepareStatement(sql.toString());
					ps.setInt(1, saleservice.parse(request).getAccount_id());
					rs = ps.executeQuery();

					if (!rs.next()) {
						errMsg.add("アカウントテーブルに存在しません。");
					}

					StringBuilder sql2 = new StringBuilder();
					sql2.append(" SELECT ");
					sql2.append("	*");
					sql2.append(" FROM ");
					sql2.append("	categories ");
					sql2.append(" WHERE active_flg=1");
					sql.append("	category_id=?");
					ps2 = cb.getCon().prepareStatement(sql2.toString());
					ps.setInt(1, saleservice.parse(request).getCategory_id());
					rs2 = ps2.executeQuery();

					if (!rs2.next()) {
						request.setAttribute("errMsg", errMsg);
						errMsg.add("商品カテゴリーテーブルに存在しません。");
						this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request, response);
					}

				} catch (Exception e) {
					throw new ServletException(e);

				} finally {
					try {
						if (rs != null) {
							rs.close();
							rs2.close();
						}
						if (ps != null) {
							ps.close();
							ps2.close();
						}
						if (cb.getCon() != null) {
							cb.getCon().close();
						}
					} catch (Exception e) {

					}
				}

				session.setAttribute("responsible", request.getParameter("responsible"));
				session.setAttribute("puroductCategory", request.getParameter("puroductCategory"));
				session.setAttribute("sales", saleservice.parse(request));
				this.getServletContext().getRequestDispatcher("/JSP/S0011.jsp").forward(request, response);
			}
		}
	}
}
