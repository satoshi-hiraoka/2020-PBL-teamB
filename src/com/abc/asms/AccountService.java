package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.abc.asms.dataset.Account;

public class AccountService implements Service<Account> /* コネクションプールを持っているクラスを継承 */ {
	List<String> errMsg = new ArrayList<String>();
	List<String> sucMsg = new ArrayList<String>();
	ConnectionTeamB cb = new ConnectionTeamB();

	AccountService() throws ServletException, IOException {
		super();
	}

	public Account parse(HttpServletRequest request) {
		Account account = new Account();
		//requestからアカウントのデータ取り出してAccount型の変数に格納
		account.setAccount_id(request.getParameter("account_id"));
		account.setName(request.getParameter("name"));
		account.setMail(request.getParameter("mail"));
		account.setPassword(request.getParameter("password"));
		account.setPasswordCheck(request.getParameter("passwordCheck"));
		String authSales = request.getParameter("authSales");
		String authAccount = request.getParameter("authAccount");
		String authority = null;
		if (authSales.equals("0")) {
			if (authAccount.equals("0")) {
				authority = "0";
			} else {
				authority = "10";
			}
		} else {
			if (authAccount.equals("0")) {
				authority = "1";
			} else {
				authority = "11";
			}
		}
		account.setAuthority(authority);

		return account;
	}

	public Account parse(ResultSet rs) throws SQLException {
		Account account = new Account();
		//ResultSetからアカウントのデータ取り出してAccount型の変数に格納
		account.setAccount_id(rs.getString("account_id"));
		account.setName(rs.getString("name"));
		account.setMail(rs.getString("mail"));
		account.setPassword(rs.getString("password"));
		account.setAuthority(rs.getString("authority"));

		return account;
	}

	public void registCheck(Account key) {
		//登録時に必要なチェック
		//		CheckLength cl = new CheckLength();
		//		if (cl.inputEmptyCheck(key.getName())) {
		//			errMsg.add("氏名を入力してください。");
		//		}
		//		if (cl.checkLength(key.getName(), 21)) {
		//			errMsg.add("氏名が長すぎます。");
		//		}
		//		if (cl.inputEmptyCheck(key.getMail())) {
		//			errMsg.add("メールアドレスを入力してください。");
		//		}
		//		if (cl.checkLength(key.getMail(), 101)) {
		//			errMsg.add("メールアドレスが長すぎます。");
		//		}
		//		if (!(key.getMail().matches(
		//				"^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$"))) {
		//			errMsg.add("メールアドレスを正しく入力してください。");
		//		}
		//		if (cl.inputEmptyCheck(key.getPassword())) {
		//			errMsg.add("パスワードを入力してください。");
		//		}
		//		if (cl.checkLength(key.getPassword(), 31)) {
		//			errMsg.add("パスワードが長すぎます。");
		//		}
		//		if (cl.inputEmptyCheck(key.getPasswordCheck())) {
		//			errMsg.add("パスワード（確認）を入力してください。");
		//		}
		//		if (!(key.getPassword().equals(key.getPasswordCheck()))) {
		//			errMsg.add("パスワードとパスワード（確認）が一致していません。");
		//		}
		//		String authSales = null;
		//		String authAccount = null;
		//		if (key.getAuthority().equals("0")) {
		//			authSales = "0";
		//			authAccount = "0";
		//		} else if (key.getAuthority().equals("1")) {
		//			authSales = "1";
		//			authAccount = "0";
		//		} else if (key.getAuthority().equals("10")) {
		//			authSales = "0";
		//			authAccount = "1";
		//		} else {
		//			authSales = "1";
		//			authAccount = "1";
		//		}
		//		if (cl.inputEmptyCheck(authSales)) {
		//			errMsg.add("売上登録権限を入力してください。");
		//		}
		//		if (!(authSales.equals("0") || authSales.equals("1"))) {
		//			errMsg.add("売上登録権限に正しい値を入力してください。");
		//		}
		//		if (cl.inputEmptyCheck(authAccount)) {
		//			errMsg.add("アカウント登録権限を入力してください。");
		//		}
		//		if (!(authAccount.equals("0") || authAccount.equals("1"))) {
		//			errMsg.add("アカウント登録権限に正しい値を入力してください。");
		//		}
	}

	public void updateCheck(Account key) {
		registCheck(key);
	}

	//特定のアカウントデータを取得するメソッド
	public Account findAsKey(Account key) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account bean = null;

		try {
			//SQL作って結果を取得する

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT");
			sql.append(" 	account_id");
			sql.append(" FROM");
			sql.append(" 	accounts");
			sql.append(" WHERE");
			sql.append(" 	mail = ?");

			ps = cb.getCon().prepareStatement(sql.toString());

			ps.setString(1, key.getMail());

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	//アカウント検索用メソッド
	public List<Account> find(Account key) {
		CheckLength cl = new CheckLength();
		List<Account> list = new ArrayList<Account>();
		LinkedHashMap<String, String> lHMap = new LinkedHashMap<>();

		//		List<String> authList = new ArrayList<String>();
		//検索するauthorityの値
		//		if (authSales.equals("all")) {
		//			if (authAccount.equals("all")) {
		//				authList.add("0");
		//				authList.add("1");
		//				authList.add("10");
		//				authList.add("11");
		//			} else if (authAccount.equals("0")) {
		//				authList.add("0");
		//				authList.add("1");
		//			} else {
		//				authList.add("10");
		//				authList.add("11");
		//			}
		//		} else if (authSales.equals("0")) {
		//			if (authAccount.equals("all")) {
		//				authList.add("0");
		//				authList.add("10");
		//			} else if (authAccount.equals("0")) {
		//				authList.add("0");
		//			} else {
		//				authList.add("10");
		//			}
		//		} else {
		//			if (authAccount.equals("all")) {
		//				authList.add("1");
		//				authList.add("11");
		//			} else if (authAccount.equals("0")) {
		//				authList.add("1");
		//			} else {
		//				authList.add("11");
		//			}
		//		}
		//
		//		PreparedStatement ps = null;
		//		ResultSet rs = null;
		//		int idx = 1;
		//		try {
		//			//SQLを生成する。
		//			//ただし検索条件は常にすべて入力されているわけではないことに注意
		//			//プレースホルダ(?)に値を設定していく。
		//			//?の個数は流動的だが「?の個数=入力された検索条件であることがヒント
		//			StringBuilder sql = new StringBuilder();
		//			//検索条件に合うアカウントを取得
		//			sql.append("SELECT");
		//			sql.append(" 	account_id,");
		//			sql.append(" 	name,");
		//			sql.append(" 	mail,");
		//			sql.append(" 	authority");
		//			sql.append(" FROM");
		//			sql.append(" 	accounts");
		//			sql.append(" WHERE");
		//			sql.append(" 	authority IN (");
		//			sql.append(" 				?");
		//			//検索するauthorityの数は1つは確定
		//			lHMap.put("auth1", authList.get(0));
		//			//検索するauthorityの数が2つの場合
		//			if (authList.size() >= 2) {
		//				sql.append(" 			,?");
		//				lHMap.put("auth2", authList.get(1));
		//			}
		//			//検索するauthorityの数が4つの場合
		//			if (authList.size() == 4) {
		//				sql.append(" 			,?,?");
		//				lHMap.put("auth3", authList.get(2));
		//				lHMap.put("auth4", authList.get(3));
		//			}
		//			sql.append(" 				)");
		//			//氏名入力時には氏名を部分一致で検索
		//			if (!(cl.inputEmptyCheck(key.getName()))) {
		//				sql.append(" 	AND name LIKE ?");
		//				lHMap.put("name", "%" + key.getName() + "%");
		//			}
		//			//メールアドレス入力時にはメールアドレスを完全一致で検索
		//			if (!(cl.inputEmptyCheck(key.getMail()))) {
		//				sql.append(" 	AND mail = ?");
		//				lHMap.put("mail", key.getMail());
		//			}
		//
		//			ps = cb.getCon().prepareStatement(sql.toString());
		//
		//			//プレースホルダーに値を格納
		//			for (Entry<String, String> entry : lHMap.entrySet()) {
		//				ps.setString(idx, entry.getValue());
		//				idx++;
		//			}
		//
		//			rs = ps.executeQuery();
		//			while (rs.next()) {
		//				list.add(parse(rs));
		//			}
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		} finally {
		//			try {
		//				if (rs != null)
		//					rs.close();
		//				if (ps != null)
		//					ps.close();
		//			} catch (Exception e) {
		//			}
		//		}
		return list;
	}

	//アカウント登録する用のメソッド
	public void insert(Account bean) {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		int idx = 1;
		try {
			ConnectionTeamB cb = new ConnectionTeamB();
			//ここでinsertする
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO");
			sql.append(" 	accounts(");
			sql.append(" 		name,");
			sql.append(" 		mail,");
			sql.append(" 		password,");
			sql.append(" 		authority)");
			sql.append(" VALUES(");
			sql.append(" 		?,?,MD5(?),?)");
			ps = cb.getCon().prepareStatement(sql.toString());

			ps.setString(1, bean.getName());
			ps.setString(2, bean.getMail());
			ps.setString(3, bean.getPassword());
			ps.setString(4, bean.getAuthority());

			idx = ps.executeUpdate();

			StringBuilder sql2 = new StringBuilder();
			sql2.append("SELECT");
			sql2.append(" 	account_id");
			sql2.append(" FROM");
			sql2.append(" 	accounts");
			sql2.append(" WHERE");
			sql2.append(" 	mail = ?");

			ps2 = cb.getCon().prepareStatement(sql2.toString());
			ps2.setString(1, bean.getMail());

			rs = ps2.executeQuery();

			if (rs.next()) {
				bean.setAccount_id(rs.getString("account_id"));
			}

			if (idx == 0) {
				errMsg.add("登録に失敗しました。");
			} else {
				sucMsg.add("No" + bean.getAccount_id() + "のアカウントを登録しました。");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
		}
	}

	//アカウント編集するようのメソッド
	public void update(Account bean) {
		PreparedStatement ps = null;
		int idx = 1;
		try {
			//ここでupdateする
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
		}
	}

	//アカウント削除する用のメソッド
	public void delete(Account bean) {
		PreparedStatement ps = null;
		int idx = 1;
		try {
			//ここでdeleteする
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
		}
	}
}