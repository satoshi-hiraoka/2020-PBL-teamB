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

@WebServlet("/S0045")
public class S0045 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mail = request.getParameter("mail");
		SendMail sendmail = new SendMail();
		ArrayList<String> errMsg = new ArrayList<String>();
		ArrayList<String> sucMsg = new ArrayList<String>();
		checkMail(mail, errMsg);

		//errMsgの要素が1以上であれば何かしらの入力エラー。
		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);
		}

		try {
			ChangePasswordService cps = new ChangePasswordService();
			//共通
			if (cps.isMailExist(mail)) {
				sendmail.SendMailMethod(mail);
				sucMsg.add("パスワード再設定メールを送信しました。");
				request.setAttribute("sucMsg", sucMsg);//左は変数名右は中身
			} else {
				errMsg.add("メールアドレスを正しく入力してください");
				request.setAttribute("errMsg", errMsg);
			}
			this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);

		} catch (NamingException e) {
			throw new ServletException(e);
		} catch (SQLException e) {
			errMsg.add("予期しないエラーが発生しました");
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);
		} catch (Exception e) {
			errMsg.add("予期しないエラーが発生しました");
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);
		}
	}

	private void checkMail(String mail, ArrayList<String> errMsg) {
		new CheckInputValues();
		//		CheckLength cl = new CheckLength();
//		String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
		if (mail.isEmpty()) {
			errMsg.add("メールアドレスを入力してください。");
		} else {
			if (!CheckInputValues.checkLength(mail, 100)) {
				errMsg.add("メールアドレスが長すぎます。");
			} else {
				if (!CheckInputValues.mailFormatCheck(mail)) {
					errMsg.add("メールアドレスを正しく入力してください。");
				}
			}
		}
	}
}
