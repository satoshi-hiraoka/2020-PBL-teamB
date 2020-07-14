package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/C0010")
public class C0010 extends HttpServlet {

	///public void doGet(HttpServletRequest request, HttpServletResponse response)
	//		throws ServletException, IOException {

	//}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String paswd = request.getParameter("passWord");

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
			sql.append(" 	mail");
			sql.append(" FROM");
			sql.append(" 	accounts");
			sql.append(" WHERE");
			sql.append(" 	mail=?");//?で可変にしている
			sql.append(" 	AND PASSWORD=MD5(?)");
			sql.append("");
			sql.append("");

			ps = db.prepareStatement(sql.toString());//StringBuilderをStringに変換して渡す。上のsqlをpsにせっと0
			ps.setString(1, mail);
			ps.setString(2, paswd); //mail PASSWORDに値を設定
			rs = ps.executeQuery();//実行

			//HttpSession session = request.getSession();
			if (rs.next()) {
				response.sendRedirect("/teamB/JSP/C0020.jsp");
				return;
			} else {
				request.setAttribute("Err", "メールアドレス、パスワードを正しく入力してください");
				RequestDispatcher dis = request.getRequestDispatcher("/JSP/C0010.jsp");
				dis.forward(request, response);
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