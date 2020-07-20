package com.abc.asms;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.abc.asms.dataset.Account;

public class AccountService {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Account parse(HttpServletRequest request) {
		Account account = new Account();
		//requestから売上のデータ取り出してSale型の変数に格納
		return account;
	}

	public Account parse(ResultSet rs) throws SQLException {
		Account account = new Account();
		//ResultSetから売上のデータ取り出してSale型の変数に格納
		return account;
	}

	public void registCheck(Account key) {
		//登録時に必要なチェック
	}

	public void updateCheck(Account key) {
		registCheck(key);
	}

	//特定の売上データを取得するメソッド
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

	//売上検索用メソッド
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

	//売上登録する用のメソッド
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

	//売上削除する用のメソッド
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
