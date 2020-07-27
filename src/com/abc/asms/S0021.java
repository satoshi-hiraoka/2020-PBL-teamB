package com.abc.asms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.dataset.Account;
import com.abc.asms.dataset.Sale;

/**
 * Servlet implementation class S0021
 */
@WebServlet("/S0021")
public class S0021 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0021() {
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
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		checkLoginAndTransition(request, response, "/JSP/C0020.jsp");
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

		//入力された条件に問題がなければ
		/* 入力された検索条件 */
		request.setAttribute("saleslLst", service.find(sale));
		System.out.println(service.find(sale).size());
		SaleService saleService = new SaleService();
		saleService.find(sale);
		//		Sale sale = new Sale();
		//		saleService.insert(sale);

		//		System.out.println(saleService.find(sale).size());
		//		for (int i = 0; i < saleService.find(sale).size(); i++) {
		//			System.out.print(saleService.find(sale).get(i).getName());
		//
		//		}
		this.getServletContext().getRequestDispatcher("/JSP/S0021.jsp").forward(request, response);
	}

	private void getSearchCondition(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		SaleService saleservce = new SaleService();
		//requestから検索条件を取得しsessionへ格納しておく。
		session.setAttribute("searchCondition", saleservce.parse(request));
	}

}
