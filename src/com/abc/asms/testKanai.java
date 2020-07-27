package com.abc.asms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Sale;

/**
 * Servlet implementation class testKanai
 */
@WebServlet("/testKanai")
public class testKanai extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public testKanai() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("テスト施行 ");
		SaleService saleService = new SaleService();
		Sale sale = new Sale();
		//		saleService.insert(sale);
		saleService.find(sale);
		System.out.print(">>>>");System.out.println(saleService.find(sale).size());System.out.print("<<<<<");
		for (int i = 0; i < saleService.find(sale).size(); i++) {
			System.out.print(saleService.find(sale).get(i).getName());

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
