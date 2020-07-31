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

import com.abc.asms.dataset.Sale;

@WebServlet("/S0010")
public class S0010 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0010() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ログインと権限チェック
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, null,
				puf.getPermitList("sales"));
		SaleService saleservice = new SaleService();
		HttpSession session = request.getSession();
		session.setAttribute("resposiblelist", saleservice.responsibleList());
		session.setAttribute("puroductCategorylist", saleservice.categoryList("1"));
		this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ログインチェックと権限チェック
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, null,
				puf.getPermitList("sales"));

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
		Sale sale = saleservice.parse(request);
		//未入力かチェックpuroductUnitPrice
		System.out.println(saleservice.parse(request).getAccount_id());
		if (sale.getSale_date().isEmpty()) {
			errMsg.add("販売日を入力してください");
		}
		if (sale.getAccount_id() == null) {
			errMsg.add("担当者が未選択です。");
		}
		if (sale.getCategory_id() == null) {
			errMsg.add("商品カテゴリーが未選択です。");
		}
		if (sale.getTrade_name().isEmpty()) {
			errMsg.add("商品名を入力してください");
		}
		if (sale.getUnit_price().isEmpty()) {
			errMsg.add("単価をを入力してください");
		}
		if (sale.getSale_number().isEmpty()) {
			errMsg.add("個数を入力してください");
		}
		//文字数長さチェック
		if (!(errMsg.contains("商品名を入力してください"))) {

			if (!(checklength.checkLength(sale.getTrade_name(), 101))) {
				errMsg.add("商品名が長すぎます。");
			}
		}
		if (!(errMsg.contains("単価をを入力してください"))) {
			if (CheckInputValues.checkLength(String.valueOf(sale.getUnit_price()), 10)) {
				errMsg.add("単価が長すぎます。");
			}
		}
		if (!(errMsg.contains("個数を入力してください"))) {
			if (CheckInputValues.checkLength(sale.getSale_number(), 10)) {
				errMsg.add("個数が長すぎます。");
			}
		}
		if (CheckInputValues.checkLength(sale.getNote(), 400)) {
			errMsg.add("備考が長すぎます。");
		}

		//入力の形式チェック
		if (!(errMsg.contains("販売日を入力してください"))) {
			try {
				String saleDate = sale.getSale_date();

				localdate = LocalDate.parse(saleDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

			} catch (java.time.format.DateTimeParseException e) {
				errMsg.add("販売日を正しく入力してください。");
			}
		}
		if (!(errMsg.contains("単価が長すぎます。")) && !(errMsg.contains("単価をを入力してください"))) {
			try {

				if (Integer.valueOf(sale.getUnit_price()) <= 1) {
					errMsg.add("単価を正しく入力してください");
				}

			} catch (NumberFormatException e) {
				errMsg.add("単価を正しく入力してください");
			}
		}
		if (!(errMsg.contains("個数が長すぎます。")) && !(errMsg.contains("個数を入力してください"))) {
			try {

				if (Integer.valueOf(sale.getSale_number()) <= 1) {
					errMsg.add("個数を正しく入力してください");
				}

			} catch (NumberFormatException e) {
				errMsg.add("個価を正しく入力してください");
			}
		}

		if (!(errMsg.contains("担当者が未選択です。")) && !(errMsg.contains("商品カテゴリーが未選択です。"))) {
			try {

				StringBuilder findResponsibleSql = new StringBuilder();
				findResponsibleSql.append(" SELECT");
				findResponsibleSql.append("	*");
				findResponsibleSql.append(" FROM ");
				findResponsibleSql.append("	accounts");
				findResponsibleSql.append(" WHERE ");
				findResponsibleSql.append("	account_id=?");
				ps = cb.getCon().prepareStatement(findResponsibleSql.toString());
				ps.setString(1, saleservice.parse(request).getAccount_id());
				rs = ps.executeQuery();

				if (!rs.next()) {
					errMsg.add("アカウントテーブルに存在しません。");
				}

				StringBuilder findResonsibleSql = new StringBuilder();
				findResonsibleSql.append(" SELECT ");
				findResonsibleSql.append("	*");
				findResonsibleSql.append(" FROM ");
				findResonsibleSql.append("	categories ");
				findResonsibleSql.append(" WHERE active_flg=1");
				findResponsibleSql.append("	category_id=?");
				ps2 = cb.getCon().prepareStatement(findResonsibleSql.toString());
				ps.setString(1, saleservice.parse(request).getCategory_id());
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
		}
		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			request.setAttribute("responsible", request.getParameter("responsible"));
			request.setAttribute("puroductCategory", request.getParameter("puroductCategory"));
			request.setAttribute("sales", saleservice.parse(request));
			this.getServletContext().getRequestDispatcher("/JSP/S0010.jsp").forward(request, response);
		}
		session.setAttribute("responsible", request.getParameter("responsible"));
		session.setAttribute("puroductCategory", request.getParameter("puroductCategory"));
		session.setAttribute("sales", saleservice.parse(request));
		this.getServletContext().getRequestDispatcher("/JSP/S0011.jsp").forward(request, response);

	}
}
