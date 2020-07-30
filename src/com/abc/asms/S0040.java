package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.dataset.Account;

/**
 * Servlet implementation class S0040
 */
@WebServlet("/S0040")
public class S0040 extends HttpServlet {

	CheckLength cl = new CheckLength();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0040() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, "/JSP/S0040.jsp",
				puf.getPermitList("all"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PermitUseFunction puf = new PermitUseFunction();
		LoginCheck.checkLoginAndTransition(request, response);
		AuthCheck.checkAuthandTransition(request, response, null,
				puf.getPermitList("all"));

		request.setCharacterEncoding("UTF-8");

		ConnectionTeamB cb = new ConnectionTeamB();

		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String authSales = request.getParameter("authSales");
		String authAccount = request.getParameter("authAccount");

		SearchAuthorityFunction saf = new SearchAuthorityFunction();
		ArrayList<String> authList = new ArrayList<String>();
		//検索するauthorityの値
		if (authSales.equals("all")) {
			if (authAccount.equals("all")) {
				authList.addAll(saf.getAuthList("all"));
			} else if (authAccount.equals("0")) {
				authList.addAll(saf.getAuthList("noAccount"));
			} else {
				authList.addAll(saf.getAuthList("account"));
			}
		} else if (authSales.equals("0")) {
			if (authAccount.equals("all")) {
				authList.addAll(saf.getAuthList("noSales"));
			} else if (authAccount.equals("0")) {
				authList.addAll(saf.getAuthList("noAuth"));
			} else {
				authList.addAll(saf.getAuthList("onlyAccount"));
			}
		} else {
			if (authAccount.equals("all")) {
				authList.addAll(saf.getAuthList("sales"));
			} else if (authAccount.equals("0")) {
				authList.addAll(saf.getAuthList("onlySales"));
			} else {
				authList.addAll(saf.getAuthList("salesAndAccount"));
			}
		}

		ArrayList<String> errMsg = new ArrayList<String>();
		List<Account> list = new ArrayList<Account>();
		LinkedHashMap<String, String> lHMap = new LinkedHashMap<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBuilder sql = new StringBuilder();
			//検索条件に合うアカウントを取得
			sql.append("SELECT");
			sql.append(" 	account_id,");
			sql.append(" 	name,");
			sql.append(" 	mail,");
			sql.append(" 	authority");
			sql.append(" FROM");
			sql.append(" 	accounts");
			sql.append(" WHERE");
			sql.append(" 	authority IN (");
			sql.append(" 				?");
			//検索するauthorityの数は1つは確定
			lHMap.put("auth1", authList.get(0));
			//検索するauthorityの数が2つの場合
			if (authList.size() >= 2) {
				sql.append(" 			,?");
				lHMap.put("auth2", authList.get(1));
			}
			//検索するauthorityの数が4つの場合
			if (authList.size() == 4) {
				sql.append(" 			,?,?");
				lHMap.put("auth3", authList.get(2));
				lHMap.put("auth4", authList.get(3));
			}
			sql.append(" 				)");
			//氏名入力時には氏名を部分一致で検索
			if (!(cl.inputEmptyCheck(name))) {
				sql.append(" 	AND name LIKE ?");
				lHMap.put("name", "%" + name + "%");
			}
			//メールアドレス入力時にはメールアドレスを完全一致で検索
			if (!(cl.inputEmptyCheck(mail))) {
				sql.append(" 	AND mail = ?");
				lHMap.put("mail", mail);
			}

			int idx = 1;

			ps = cb.getCon().prepareStatement(sql.toString());

			//プレースホルダーに値を格納
			for (Entry<String, String> entry : lHMap.entrySet()) {
				ps.setString(idx, entry.getValue());
				idx++;
			}

			rs = ps.executeQuery();

			//取得したアカウントをlistに格納
			while (rs.next()) {
				Account resultAccount = new Account();
				resultAccount.setAccount_id(rs.getString("account_id"));
				resultAccount.setName(rs.getString("name"));
				resultAccount.setMail(rs.getString("mail"));
				resultAccount.setAuthority(rs.getString("authority"));
				list.add(resultAccount);
			}
			if (list.size() == 0) {
				errMsg.add("検索結果はありません。");
			}
			if (!(cl.inputEmptyCheck(name))) {
				checkName(name, errMsg);
			}
			if (!(cl.inputEmptyCheck(mail))) {
				checkMail(mail, errMsg);
			}

			if (errMsg.size() > 0) {
				request.setAttribute("errMsg", errMsg);
				this.getServletContext().getRequestDispatcher("/JSP/S0040.jsp").forward(request, response);
			} else {
				Account account = (Account) request.getSession().getAttribute("accounts");
				//ログイン中のアカウントの権限
				request.setAttribute("authority", account.getAuthority());
				request.setAttribute("list", list);
				this.getServletContext().getRequestDispatcher("/JSP/S0041.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException e) {

			}
		}
	}

	private void checkName(String name, ArrayList<String> errMsg) {
		if (cl.checkLength(name, 21)) {
			errMsg.add("氏名が長すぎます。");
		}
	}

	private void checkMail(String mail, ArrayList<String> errMsg) {
		if (cl.checkLength(mail, 101)) {
			errMsg.add("メールアドレスが長すぎます。");
		}
		if (!(mail.matches(
				"^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$"))) {
			errMsg.add("メールアドレスの形式が誤っています。");
		}
	}
}