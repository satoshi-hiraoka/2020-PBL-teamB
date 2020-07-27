package com.abc.asms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class S0031Cancel
 */
@WebServlet("/S0031Cancel")
public class S0031Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0031Cancel() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoginCheck login = new LoginCheck();
		login.checkLoginAndTransition(request, response, "/JSP/S0031.jsp", "0", "1");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession sessionS0030 = request.getSession();
		String name = (String) sessionS0030.getAttribute("name");
		String mail = (String) sessionS0030.getAttribute("mail");
		String password = (String) sessionS0030.getAttribute("password");
		String passwordCheck = (String) sessionS0030.getAttribute("passwordCheck");
		String authSales = (String) sessionS0030.getAttribute("authSales");
		String authAccount = (String) sessionS0030.getAttribute("authAccount");

		this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp").forward(request, response);
	}

}
