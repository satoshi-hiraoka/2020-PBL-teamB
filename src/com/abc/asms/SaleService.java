package com.abc.asms;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.abc.asms.dataset.Sale;

public class SaleService implements Service<Sale> /* コネクションプールを持っているクラスを継承 */ {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	List<String> errMsg = new ArrayList<String>();

	public Sale parse(HttpServletRequest request) {
		Sale sale = new Sale();
		//requestから売上のデータ取り出してSale型の変数に格納
		sale.setSale_date(request.getParameter("saleDate"));
		sale.setAccount_id(request.getParameter("responsible"));
		sale.setCategory_id(request.getParameter("puroductCategory"));
		sale.setTrade_name(request.getParameter("puroductName"));
		sale.setUnit_price(request.getParameter("puroductUnitPrice"));
		sale.setSale_number(request.getParameter("puroductNumber"));
		sale.setNote(request.getParameter("remark"));

		return sale;
	}

	public Sale parse(ResultSet rs) throws SQLException {
		Sale sale = new Sale();
		//ResultSetから売上のデータ取り出してSale型の変数に格納

		sale.setSale_date(rs.getString("saleDate"));
		sale.setAccount_id(rs.getString("responsible"));
		sale.setCategory_id(rs.getString("puroductCategory"));
		sale.setTrade_name(rs.getString("puroductName"));
		sale.setUnit_price(rs.getString("puroductUnitPrice"));
		sale.setSale_number(rs.getString("puroductNumber"));
		sale.setNote(rs.getString("remark"));

		return sale;
	}

	public void registCheck(Sale key) {
		//登録時に必要なチェック
		CheckLength checklength = new CheckLength();
		if (checklength.inputEmptyCheck(key.getSale_date())) {
			errMsg.add("販売日を入力してください");
		}
		if (key.getAccount_id() == null) {
			errMsg.add("担当者が未選択です。");
		}
		if (key.getSale_number() == null) {
			errMsg.add("商品カテゴリーが未選択です。");
		}
		if (checklength.inputEmptyCheck(key.getTrade_name())) {
			errMsg.add("商品名を入力してください");
		}
		if (checklength.inputEmptyCheck(key.getUnit_price())) {
			errMsg.add("単価をを入力してください");
		}
		if (checklength.inputEmptyCheck(key.getSale_number())) {
			errMsg.add("個数を入力してください");
		}
		//文字数長さチェック
		if (checklength.checkLength(key.getTrade_name(), 101)) {
			errMsg.add("商品名が長すぎます。");
		}
		if (checklength.checkLength(key.getUnit_price(), 10)) {
			errMsg.add("単価が長すぎます。");
		}
		if (checklength.checkLength(key.getSale_number(), 10)) {
			errMsg.add("個数が長すぎます。");
		}
		if (checklength.checkLength(key.getNote(), 400)) {
			errMsg.add("備考が長すぎます。");
		}
	}

	public void updateCheck(Sale key) {
		registCheck(key);
	}

	//特定の売上データを取得するメソッド
	public Sale findAsKey(Sale key) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int idx = 1;
		Sale bean = null;

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
	public List<Sale> find(Sale key) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int idx = 1;
		List<Sale> list = new ArrayList<Sale>();
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
	public void insert(Sale bean) {
		PreparedStatement ps = null;
		int idx = 1;
		try {
			//ここでinsertする よくわかってない

//			StringBuilder sql = new StringBuilder();
//			sql.append(" INSERT INTO");
//			sql.append("	sales(sale_date,");
//			sql.append("	account_id,");
//			sql.append("	category_id, ");
//			sql.append("	trade_name,");
//			sql.append("	unit_price,");
//			sql.append("	sale_number,");
//			sql.append("	note)");
//			sql.append(" VALUES");
//			sql.append("	(?,");
//			sql.append("	?,");
//			sql.append("	?,");
//			sql.append("	?,");
//			sql.append("	?,");
//			sql.append("	?,");
//			sql.append("	?)");
//
//			ps = con.prepareStatement(sql.toString());
//			ps.setString(1, bean.getSale_date());
//			ps.setString(2, bean.getAccount_id());
//			ps.setString(3, bean.getCategory_id());
//			ps.setString(4, bean.getTrade_name());
//			ps.setString(5, bean.getUnit_price());
//			ps.setString(6, bean.getSale_number());
//			ps.setString(7, bean.getNote());
//			ps.executeUpdate();

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
	public void update(Sale bean) {
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
	public void delete(Sale bean) {
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
