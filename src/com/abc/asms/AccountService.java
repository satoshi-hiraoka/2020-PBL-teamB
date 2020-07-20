package com.abc.asms;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.abc.asms.dataset.Account;

public class AccountService implements Service<Account> /* コネクションプールを持っているクラスを継承 */ {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	List<String> errMsg = new ArrayList<String>();

	public Account parse(HttpServletRequest request) {
		Account account = new Account();
		//requestからアカウントのデータ取り出してAccount型の変数に格納

		return account;
	}

	public Account parse(ResultSet rs) throws SQLException {
		Account account = new Account();
		//ResultSetからアカウントのデータ取り出してAccount型の変数に格納



		return account;
	}

	public void registCheck(Account key) {
		//登録時に必要なチェック
		CheckLength cl = new CheckLength();
		if (cl.inputEmptyCheck(key.getName())) {
			errMsg.add("氏名を入力してください。");
		}
		if (cl.checkLength(key.getName(), 21)) {
			errMsg.add("氏名が長すぎます。");
		}
		if (cl.inputEmptyCheck(key.getMail())) {
			errMsg.add("メールアドレスを入力してください。");
		}
		if (cl.checkLength(key.getMail(), 101)) {
			errMsg.add("メールアドレスが長すぎます。");
		}
		if (!(key.getMail().matches(
				"^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$"))) {
			errMsg.add("メールアドレスを正しく入力してください。");
		}
	}

	public void updateCheck(Account key) {
		registCheck(key);
	}

	//特定のアカウントデータを取得するメソッド
	public Account findAsKey(Account key) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int idx = 1;
		Account bean = null;

		try {
			//SQL作って結果を取得する
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		int idx = 1;
		List<Account> list = new ArrayList<Account>();
		try {
			//SQLを生成する。
			//ただし検索条件は常にすべて入力されているわけではないことに注意

			//プレースホルダ(?)に値を設定していく。
			//?の個数は流動的だが「?の個数=入力された検索条件であることがヒント
			while (rs.next()) {
				list.add(parse(rs));
			}
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
		return list;
	}

	//アカウント登録する用のメソッド
	public void insert(Account bean) {
		PreparedStatement ps = null;
		int idx = 1;
		try {
			//ここでinsertする



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

	//売上編集するようのメソッド
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