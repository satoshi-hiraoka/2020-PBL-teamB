package com.abc.asms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/S0020")
public class S0020 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0020() {
		//		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//		ログインチェック
		LoginCheck.checkLoginAndTransition(request, response);

		SaleService saleservice = new SaleService();
		HttpSession session = request.getSession();
		session.setAttribute("resposiblelist", saleservice.responsibleList());
		session.setAttribute("puroductCategorylist", saleservice.categoryList(""));
		this.getServletContext().getRequestDispatcher("/JSP/S0020.jsp").forward(request, response);

	}
}
