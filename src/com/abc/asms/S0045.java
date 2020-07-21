package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.abc.asms.dataset.Account;

@WebServlet("/S0045")
public class S0045 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		checkLoginAndTransition(request, response, "/JSP/S0045.jsp");
	}

	public void checkLoginAndTransition(HttpServletRequest request, HttpServletResponse response, String transitiontTo)
			throws ServletException, IOException {
		//ログインチェック
		Account account = (Account) request.getSession().getAttribute("accounts");

		if (account == null) {
			this.getServletContext().getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher(transitiontTo).forward(request, response);
		}
	}

	private void checkMail(String mail, ArrayList<String> errMsg) {
		String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
		if (mail.isEmpty()) {
			errMsg.add("メールアドレスを入力してください。");
		} else {
			if (!checkLength(mail, 101)) {
				errMsg.add("メールアドレスが長すぎます。");
			} else {
				if (!mail.matches(mailFormat)) {
					errMsg.add("メールアドレスを正しく入力してください。");
				}
			}
		}
	}

	private boolean checkLength(String value, int max) {
		int length = value.getBytes().length;
		if (length < max)
			return true;
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mail = request.getParameter("mail");
		SendMail sendmail=new SendMail();
		ArrayList<String> errMsg = new ArrayList<String>();

		checkMail(mail, errMsg);

		//errMsgの要素が1以上であれば何かしらの入力エラー。
		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);
		}

		Connection db = null;
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql/asms");
			db = ds.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT");
			sql.append(" 	mail,");
			sql.append("		account_id,");
			sql.append("		name,");
			sql.append("		authority");
			sql.append(" FROM");
			sql.append(" 	accounts");
			sql.append(" WHERE");
			sql.append(" 	mail=?");//?で可変にしている5

			ps = db.prepareStatement(sql.toString());//StringBuilderをStringに変換して渡す。上のsqlをpsにせっと0
			ps.setString(1, mail);
			rs = ps.executeQuery();//実行

			//共通
			if (rs.next()) {
				sendmail.SendMailMethod(mail);
				request.setAttribute("success", "success");//左は変数名右は中身
				this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);

				return;
			} else {
				//request.setAttribute("Err", "メールアドレス、パスワードを正しく入力してください");
				errMsg.add("メールアドレスを正しく入力してください");
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);
				return;
			}

		} catch (NamingException e) {
			throw new ServletException(e);
		} catch (SQLException e) {
			errMsg.add("予期しないエラーが発生しました");
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);
		}catch(Exception e) {
			errMsg.add("予期しないエラーが発生しました");
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0045.jsp").forward(request, response);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (db != null) {
					db.close();
				}
			} catch (SQLException e) {

			}
		}
	}
}
