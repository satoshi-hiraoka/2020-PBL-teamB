package com.abc.asms;

import java.io.IOException;
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

@WebServlet("/S0021")
public class S0021 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0021() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ログインチェック
		//		LoginCheck.checkLoginAndTransition(request, response);

		request.setCharacterEncoding("UTF-8");

		List<String> errMsg = new ArrayList<String>();
		LocalDate previousLocalDate = null;
		LocalDate lateLocalDate = null;
		HttpSession session = request.getSession();
		//売上関連のサービスをインスタンス化
		SaleService service = new SaleService();

		//リクエストから検索条件を取得する
		getSearchCondition(request);
		Sale sale = (Sale) request.getSession().getAttribute("searchCondition");
		//検索時のチェック
		//	日付の形式(from to両方)
		//	日付の前後関係
		//エラーがあれば検索画面へ遷移
		//	sessionに格納した検索条件を画面に渡す事。

		String previousPeriod = sale.getPreviousPeriod();
		if (!(previousPeriod.isEmpty())) {
			try {

				previousLocalDate = LocalDate.parse(previousPeriod, DateTimeFormatter.ofPattern("yyyy/MM/d"));

			} catch (Exception e) {

				errMsg.add("販売日(検索開始日)を正しく入力してください。");
			}
		}

		String latePeriod = sale.getLatePeriod();
		if (!(latePeriod.isEmpty())) {

			try {

				lateLocalDate = LocalDate.parse(latePeriod, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

			} catch (Exception e) {
				errMsg.add("販売日(検索終了日)を正しく入力してください。");
			}
		}
		//販売日（開始日＜終了日）チェック
		if (!(previousLocalDate == null) && !(lateLocalDate == null)) {
			if (previousLocalDate.isAfter(lateLocalDate)) {
				errMsg.add("販売日(検索開始日)が販売日(検索終了日)より後の日付になっております。");
			}
		}

		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0020.jsp").forward(request, response);
		}

		request.setAttribute("saleslLst", service.find(sale));

		SaleService saleService = new SaleService();
		saleService.find(sale);
		if (saleService.find(sale).size() == 0) {
			errMsg.add("検索結果はありません");
			session.removeAttribute("searchCondition");
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0020.jsp").forward(request, response);
		}
		this.getServletContext().getRequestDispatcher("/JSP/S0021.jsp").forward(request, response);

	}

	private void getSearchCondition(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		SaleService saleservce = new SaleService();
		//requestから検索条件を取得しsessionへ格納しておく。
		session.setAttribute("searchCondition", saleservce.parse(request));
	}

}
