package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.dataset.Category;

@WebServlet("/S0020")
public class S0020 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public S0020() {
		//		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//		ログインチェック
		LoginCheck logincheck = new LoginCheck();
		logincheck.checkLoginAndTransition(request, response);

		ConnectionTeamB cb = new ConnectionTeamB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Category> puroductCategorylist = new ArrayList<>();
		SaleService saleservice = new SaleService();
		HttpSession session = request.getSession();
		session.setAttribute("resposiblelist", saleservice.responsibleList(null));
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ");
			sql.append("	*");
			sql.append(" FROM ");
			sql.append("	categories ");
			ps = cb.getCon().prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				Category puroductCategoryData = new Category();
				puroductCategoryData.setCategory_name(rs.getString("category_name"));
				puroductCategoryData.setCategory_id(rs.getString("category_id"));
				puroductCategorylist.add(puroductCategoryData);
				session.setAttribute("puroductCategorylist", puroductCategorylist);

			}
			this.getServletContext().getRequestDispatcher("/JSP/S0020.jsp").forward(request,
					response);
		} catch (Exception e) {
			throw new ServletException(e);

		} finally {
			try {
				if (cb.getCon() != null) {
					cb.getCon().close();
				}
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {

			}
		}

		//			//session内から検索条件を取得し検索画面に渡す。
		//			//	初回遷移時には意味をなさないが
		//			//	検索ボタンクリック時、検索条件にエラーがあった場合
		//			//	検索条件入力画面に戻ってくるので
		//			//	検索条件を保持しておく必要があるため。
		//			if(/* session内に検索条件があるかどうか */) {
		//				request.setAttribute("salesSearchCondition", /* sessionから検索条件を取り出す */);
		//			}
		//			request.setAttribute("accounts", getAccounts());
		//			request.setAttribute("categories", getCategories());
		//			request.getRequestDispatcher("S0020.jsp").forward(request, response);
		//		}
	}
}

//担当者一覧取得用メソッド
//	private ArrayList<Account> getAccounts() {
//		ArrayList<Account> accounts = null;
//		//DBから担当者一覧を取得する
//			return accounts;
//		}
//	}
//}

//商品カテゴリ取得用メソッド
//	private ArrayList<Categories> getCategories() {
//			ArrayList<Categories> categories = null;
//			//DBから商品カテゴリ一覧を取得する
//
//			return categories;
//		}
//}
