package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

@WebServlet("/S0046")
public class S0046 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String passWord = request.getParameter("passWord");
		String CheckPassWord = request.getParameter("CheckPassWord");
		String mail = request.getParameter("mail");

		ArrayList<String> errMsg = new ArrayList<String>();
		ArrayList<String> sucMsg = new ArrayList<String>();

		checkPassword(passWord, errMsg);
		checkPassword(CheckPassWord, errMsg, "確認用");
		checkPasswordMatch(passWord, CheckPassWord, errMsg);

		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0046.jsp").forward(request, response);
		}
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ChangePasswordService cps = new ChangePasswordService();
			if (cps.isMailExist(mail)) {
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				DataSource ds = (DataSource) envContext.lookup("jdbc/mysql/asms");
				db = ds.getConnection();

				StringBuilder updateSql = new StringBuilder();
				updateSql.append(" UPDATE");
				updateSql.append("		accounts");
				updateSql.append(" SET");
				updateSql.append("		password=MD5(?)");
				updateSql.append(" WHERE");
				updateSql.append("		mail=?");

				ps = db.prepareStatement(updateSql.toString());
				ps.setString(1, passWord);
				ps.setString(2, mail);
				ps.executeUpdate();

				sucMsg.add("パスワードを再設定しました");
				request.setAttribute("sucMsg", sucMsg);
				this.getServletContext().getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
				return;
			} else {
				errMsg.add("メールアドレスが存在しません");
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0046.jsp").forward(request, response);
				return;
			}
		} catch (NamingException e) {
			throw new ServletException(e);
		} catch (SQLException e) {
			throw new ServletException(e);
		} catch (Exception e) {
			throw new ServletException(e);
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

	private void checkPassword(String target, ArrayList<String> errMsg) {
		checkPassword(target, errMsg, "");
	}

	private void checkPassword(String target, ArrayList<String> errMsg, String prefix) {
		CheckLength cl = new CheckLength();
		if (target.isEmpty()) {
			errMsg.add(prefix + "パスワードを入力してください。");
		} else {
			if (!cl.checkLength(target, 30)) {
				errMsg.add(prefix + "パスワードが長すぎます。");
			}
		}
	}

	private void checkPasswordMatch(String passWord, String CheckPassWord, ArrayList<String> errMsg) {
		if (!passWord.equals(CheckPassWord)) {
			errMsg.add("新パスワードとパスワード(確認)が一致していません。");
		}
	}
}
