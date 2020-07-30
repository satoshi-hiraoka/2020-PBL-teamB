package com.abc.asms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/S0045AddressCheck")
public class S0045AddressCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0045AddressCheck() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<String> errMsg = new ArrayList<String>();
		String mail = request.getParameter("mail");
		try {
			ChangePasswordService cps = new ChangePasswordService();

			if (!cps.isMailExist(mail)) {
				errMsg.add("アクセスしたURLが間違っています。");
				request.setAttribute("errMsg", errMsg);
			}
			request.setAttribute("mail", mail);
			this.getServletContext().getRequestDispatcher("/JSP/S0046.jsp").forward(request, response);

		} catch (NamingException e) {
			throw new ServletException(e);
		} catch (SQLException e) {
			throw new ServletException(e);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
