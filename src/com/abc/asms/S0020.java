package com.abc.asms;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class S0020
 */
@WebServlet("/S0020")
public class S0020 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public S0020() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//sessionでログイン状態化どうかを確認する。
		if(/*ログイン状態のチェク*/) {
			//未ログインであればログイン画面へ
			request.getRequestDispatcher(/*ログイン画面のサーブレットのパス*/).forward(request, response);
		} else {
			//ログイン済みであれば検索画面へ

			//session内から検索条件を取得し検索画面に渡す。
			//	初回遷移時には意味をなさないが
			//	検索ボタンクリック時、検索条件にエラーがあった場合
			//	検索条件入力画面に戻ってくるので
			//	検索条件を保持しておく必要があるため。
			if(/* session内に検索条件があるかどうか */) {
				request.setAttribute("salesSearchCondition", /* sessionから検索条件を取り出す */);
			}
			request.setAttribute("accounts", getAccounts());
			request.setAttribute("categories", getCategories());
			request.getRequestDispatcher("S0020.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	//担当者一覧取得用メソッド
	private ArrayList<Account> getAccounts() {
		ArrayList<Account> accounts = null;
		//DBから担当者一覧を取得する

		return accounts;
	}

	//商品カテゴリ取得用メソッド
	private ArrayList<Categories> getCategories() {
		ArrayList<Categories> categories = null;
		//DBから商品カテゴリ一覧を取得する

		return categories;
	}
}
