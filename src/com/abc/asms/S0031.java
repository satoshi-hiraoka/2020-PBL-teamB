package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.dataset.Account;

/**
 * Servlet implementation class S0031
 */
@WebServlet("/S0031")
public class S0031 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0031() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, "/JSP/S0030.jsp",
				puf.getPermitList("account"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, null,
				puf.getPermitList("account"));

		request.setCharacterEncoding("UTF-8");

		//セッションを取得
		HttpSession sessionS0030 = request.getSession();

		if (request.getParameter("OK") != null) {
			ConnectionTeamB cb = new ConnectionTeamB();

			ArrayList<String> errMsg = new ArrayList<String>();
			ArrayList<String> sucMsg = new ArrayList<String>();

			Account user = (Account) sessionS0030.getAttribute("user");

			PreparedStatement psInsert = null;
			PreparedStatement psSelectId = null;
			ResultSet rs = null;

			try {
				StringBuilder insertSql = new StringBuilder();
				insertSql.append("INSERT INTO");
				insertSql.append(" 	accounts(");
				insertSql.append(" 		name,");
				insertSql.append(" 		mail,");
				insertSql.append(" 		password,");
				insertSql.append(" 		authority)");
				insertSql.append(" VALUES(");
				insertSql.append(" 		?,?,MD5(?),?);");

				psInsert = cb.getCon().prepareStatement(insertSql.toString());

				psInsert.setString(1, user.getName());
				psInsert.setString(2, user.getMail());
				psInsert.setString(3, user.getPassword());
				psInsert.setString(4, user.getAuthority());

				int result = psInsert.executeUpdate();

				StringBuilder selectIdSql = new StringBuilder();
				//登録したアカウントのアカウントIDを取得
				selectIdSql.append("SELECT");
				selectIdSql.append(" 	LAST_INSERT_ID()");

				psSelectId = cb.getCon().prepareStatement(selectIdSql.toString());

				rs = psSelectId.executeQuery();


				if (result == 0) {
					errMsg.add("登録に失敗しました。");
					request.setAttribute("errMsg", errMsg);
				} else {
					rs.next();
					sucMsg.add("No" +rs.getInt(1) + "のアカウントを登録しました。");
					request.setAttribute("sucMsg", sucMsg);
					//セッションオブジェクトの削除
					sessionS0030.removeAttribute("user");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (psInsert != null) {
						psInsert.close();
					}
					if (psSelectId != null) {
						psSelectId.close();
					}
					if (rs != null) {
						rs.close();
					}
				} catch (Exception e) {
				}
			}
		} else if (request.getParameter("cancel") != null) {
			request.setAttribute("user", sessionS0030.getAttribute("user"));
		}
		this.getServletContext().getRequestDispatcher("/JSP/S0030.jsp").forward(request, response);
	}
}
