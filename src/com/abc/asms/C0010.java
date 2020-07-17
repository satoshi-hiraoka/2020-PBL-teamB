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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.abc.asms.dataset.Account;

@WebServlet("/C0010")
public class C0010 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		checkLoginAndTransition(request, response, "/JSP/C0020.jsp");

	}
//共通クラスに書く。32～41
	public void checkLoginAndTransition(HttpServletRequest request, HttpServletResponse response,String transitiontTo) throws ServletException, IOException{
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
			errMsg.add("メールアドレスが入力されていません。");
		} else {
			if (!checkLength(mail, 101)) {
				errMsg.add("文字が長すぎます。");
			}
			if (!mail.matches(mailFormat)) {
				errMsg.add("メールアドレスを正しく入力してください。");
			}
		}
	}

	private void checkPassword(String passWord, ArrayList<String> errMsg) {
		if (passWord.isEmpty()) {
			errMsg.add("パスワードが入力されていません。");
		}
		if (!checkLength(passWord, 31)) {
			errMsg.add("パスワードが長すぎます。");
		}
	}

	//	private boolean checkEmpty(String value) {
	//		if (value.isEmpty())
	//			return false;
	//		return true;
	//	}

	private boolean checkLength(String value, int max) {
		int length = value.getBytes().length;
		if (length < max)
			return true;
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String passWord = request.getParameter("passWord");

		ArrayList<String> errMsg = new ArrayList<String>();

		checkMail(mail, errMsg);
		checkPassword(passWord, errMsg);

		//errMsgの要素が1以上であれば何かしらの入力エラー。
		//エラーがあればログイン画面に飛ばす。
		//ログイン画面でerrmsgを全部出力する。
		if (errMsg.size() > 0) {
			request.setAttribute("errMsg", errMsg);
			this.getServletContext().getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
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
			sql.append(" 	AND PASSWORD=MD5(?)");

			ps = db.prepareStatement(sql.toString());//StringBuilderをStringに変換して渡す。上のsqlをpsにせっと0
			ps.setString(1, mail);
			ps.setString(2, passWord); //mail PASSWORDに値を設定
			rs = ps.executeQuery();//実行

			//共通
			HttpSession session = request.getSession(); //セッションにログインしたときの情報をいれる。権限が一番大事p288参考
			if (rs.next()) {
				Account account = new Account();
				account.setAccount_id(rs.getString("account_id"));
				account.setMail(rs.getString("mail"));
				account.setName(rs.getString("name"));
				account.setAuthority(rs.getString("authority"));
				session.setAttribute("accounts", account);
				this.getServletContext().getRequestDispatcher("/JSP/C0020.jsp").forward(request, response);
				return;
			} else {
				//request.setAttribute("Err", "メールアドレス、パスワードを正しく入力してください");
				errMsg.add("メールアドレス、パスワードを正しく入力してください");
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher("/JSP/C0010.jsp").forward(request, response);
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