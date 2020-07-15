package com.abc.asms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class S0011
 */
@WebServlet("/S0011")
public class S0011 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0011() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		S0010 s0010 = new S0010();
		s0010.doGet(request, response);
		int number = Integer.valueOf(request.getParameter("puroductNumber"));
		int praice = Integer.valueOf(request.getParameter("puroductUnitPrice"));
		int subtotal = number * praice;
		String commaNumer = String.format("%,d", number);
		String commaPrice = String.format("%,d", praice);
		String commaSubtotal = String.format("%,d", subtotal);
		request.setAttribute("commaNumer", commaNumer);
		request.setAttribute("commaPrice", commaPrice);
		request.setAttribute("commaSubtotal", commaSubtotal);
		this.getServletContext().getRequestDispatcher("/JSP/S0011.jsp").forward(request, response);
	}

}
