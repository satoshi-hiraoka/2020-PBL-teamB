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

@WebServlet("/S0046")
public class S0046 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	private void checkPassword(String passWord, ArrayList<String> errMsg) {
		if (passWord.isEmpty()) {
			errMsg.add("パスワードを入力してください。");
		} else {
			if (!checkLength(passWord, 31)) {
				errMsg.add("パスワードが長すぎます。");
			}
		}
	}

	private void checkPassword1(String passWord1, ArrayList<String> errMsg) {
		if (passWord1.isEmpty()) {
			errMsg.add("確認用パスワードを入力してください。");
		}
	}

	private void checkPassword2(String passWord, String passWord1, ArrayList<String> errMsg) {
		if (!passWord.matches(passWord1)) {
			errMsg.add("新パスワードとパスワード(確認)が一致していません。");
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
		String passWord = request.getParameter("passWord");
		String passWord1 = request.getParameter("passWord1");
		String mail = request.getParameter("mail");
		
		ArrayList<String> errMsg = new ArrayList<String>();
		ArrayList<String> sucMsg = new ArrayList<String>();
		
		checkPassword(passWord, errMsg);
		checkPassword1(passWord1, errMsg);
		checkPassword2(passWord, passWord1,errMsg);

		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/S0046.jsp").forward(request, response);
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
			sql.append(" 	mail=?");

			ps = db.prepareStatement(sql.toString());//StringBuilderをStringに変換して渡す。上のsqlをpsにせっと0
			ps.setString(1, request.getParameter("mail"));
			rs = ps.executeQuery();//実行

			if (rs.next()) {
				StringBuilder sql2 = new StringBuilder();
				sql2.append(" Update");
				sql2.append("		accounts");
				sql2.append(" set");
				sql2.append("		PASSWORD=MD5(?)");
				sql2.append(" WHERE");
				sql2.append("		mail=?");

				ps = db.prepareStatement(sql2.toString());
				ps.setString(1, passWord);
				ps.setString(2, mail);
				int res = ps.executeUpdate();

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
