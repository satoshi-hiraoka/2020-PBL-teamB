package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.abc.asms.dataset.Account;
import com.abc.asms.dataset.Category;
import com.abc.asms.dataset.Sale;

public class SaleService extends ConnectionTeamB implements Service<Sale> /* コネクションプールを持っているクラスを継承 */ {
	ConnectionTeamB cb = new ConnectionTeamB();

	SaleService() throws ServletException, IOException {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Sale parse(HttpServletRequest request) {
		Sale sale = new Sale();
		//requestから売上のデータ取り出してSale型の変数に格納
		sale.setSale_date(request.getParameter("saleDate"));
		if (!(request.getParameter("responsible") == null)) {
			sale.setAccount_id(Integer.parseInt(request.getParameter("responsible")));
		} else {
			sale.setAccount_id(0);
		}
		if (!(request.getParameter("puroductCategory") == null)) {
			sale.setCategory_id(Integer.parseInt(request.getParameter("puroductCategory")));
		}else {
			sale.setCategory_id(0);
		}

		if ( !(request.getParameter("puroductUnitPrice").isEmpty())) {
			sale.setUnit_price(Integer.parseInt(request.getParameter("puroductUnitPrice")));
		}
		if (!(request.getParameter("puroductNumber").isEmpty())) {
			sale.setSale_number(Integer.parseInt(request.getParameter("puroductNumber")));
		}
		sale.setTrade_name(request.getParameter("puroductName"));
		sale.setNote(request.getParameter("remark"));
		sale.setPreviousPeriod(request.getParameter("previousPeriod"));
		sale.setLatePeriod(request.getParameter("latePeriod"));
		if(false){
		sale.setSubtotal(sale.getSale_number() * sale.getUnit_price());
		}
		return sale;
	}

	public Sale parse(ResultSet rs) throws SQLException {
		Sale sale = new Sale();
		//ResultSetから売上のデータ取り出してSale型の変数に格納

		sale.setSale_id(Integer.parseInt(rs.getString("sale_id")));
		sale.setSale_date(rs.getString("sale_date"));
		sale.setName(rs.getString("name"));
		sale.setCategory_name(rs.getString("category_name"));
		sale.setTrade_name(rs.getString("trade_name"));
		sale.setUnit_price(Integer.parseInt(rs.getString("unit_price")));
		sale.setSale_number(Integer.parseInt(rs.getString("sale_number")));
		sale.setNote(rs.getString("note"));
		int number = sale.getSale_number();
		int praice = sale.getUnit_price();
		sale.setSubtotal(number * praice);

		sale.setCommaNumer(String.format("%,d", number));
		sale.setCommaPrice(String.format("%,d", praice));

		return sale;
	}

	public void registCheck(Sale key) {
		List<String> errMsg = new ArrayList<String>();
		//登録時に必要なチェック
		CheckLength checklength = new CheckLength();
		//		if (key.getSale_date())) {
		//			errMsg.add("販売日を入力してください");
		//		}
		//		if (key.getAccount_id() == null) {
		//			errMsg.add("担当者が未選択です。");
		//		}
		//		if (key.getSale_number() == null) {
		//			errMsg.add("商品カテゴリーが未選択です。");
		//		}
		//		if (key.getTrade_name())) {
		//			errMsg.add("商品名を入力してください");
		//		}
		//		if (key.getUnit_price())) {
		//			errMsg.add("単価をを入力してください");
		//		}
		//		if (key.getSale_number())) {
		//			errMsg.add("個数を入力してください");
		//		}
		//		//文字数長さチェック
		//		if (checklength.checkLength(key.getTrade_name(), 101)) {
		//			errMsg.add("商品名が長すぎます。");
		//		}
		//		if (checklength.checkLength(key.getUnit_price(), 10)) {
		//			errMsg.add("単価が長すぎます。");
		//		}
		//		if (checklength.checkLength(key.getSale_number(), 10)) {
		//			errMsg.add("個数が長すぎます。");
		//		}
		//		if (checklength.checkLength(key.getNote(), 400)) {
		//			errMsg.add("備考が長すぎます。");
		//		}
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
		CheckLength checklength = new CheckLength();

		PreparedStatement ps = null;
		ResultSet rs = null;
		int idx = 1;
		List<Sale> list = new ArrayList<Sale>();
		LinkedHashMap<String, String> lHMap = new LinkedHashMap<>();

		try {

			//SQLを生成する。
			//ただし検索条件は常にすべて入力されているわけではないことに注意
			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT");
			sql.append("	s.sale_id,");
			sql.append("	s.sale_date,");
			sql.append("	a.name,");
			sql.append("	c.category_name,");
			sql.append("	s.trade_name,");
			sql.append("	s.unit_price,");
			sql.append("	s.sale_number,");
			sql.append("    s.note");
			sql.append(" FROM");
			sql.append("	sales s");
			sql.append("	LEFT JOIN accounts a");
			sql.append("		ON  s.account_id=a.account_id");
			sql.append("	LEFT JOIN categories c");
			sql.append("		ON s.category_id=c.category_id");
			sql.append(" WHERE");
			sql.append("	 s.note LIKE ?");

			//備考
			lHMap.put("note", "%" + key.getNote() + "%");
			//from日付
			if (!(key.getPreviousPeriod().isEmpty())) {
				sql.append("	and s.sale_date>=?");
				lHMap.put("previousPeriod", key.getPreviousPeriod());
			}
			//to日付
			if (!(key.getLatePeriod().isEmpty())) {
				sql.append("	and s.sale_date<=?");
				lHMap.put("latePeriod", key.getLatePeriod());
			}
			//担当
			if (!(Integer.valueOf(key.getAccount_id()) == null)) {
				sql.append("	 and s.account_id=?");
				lHMap.put("account_id", String.valueOf(key.getAccount_id()));
			}
			//商品カテゴリー
			if (!(Integer.valueOf(key.getCategory_id()) == null)) {
				sql.append("	 and s.category_id=?");
				lHMap.put("category_id", String.valueOf(key.getCategory_id()));
			}
			//商品名
			if (!(key.getTrade_name().isEmpty())) {
				sql.append("	 and s.trade_name LIKE ?");
				lHMap.put("trade_name", "%" + key.getTrade_name() + "%");
			}

			ps = cb.getCon().prepareStatement(sql.toString());

			for (Entry<String, String> entry : lHMap.entrySet()) {

				ps.setString(idx, entry.getValue());
				idx++;
			}

			rs = ps.executeQuery();
			//プレースホルダ(?)に値を設定していく。a
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

			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO");
			sql.append("	sales(sale_date,");
			sql.append("	account_id,");
			sql.append("	category_id, ");
			sql.append("	trade_name,");
			sql.append("	unit_price,");
			sql.append("	sale_number,");
			sql.append("	note)");
			sql.append(" VALUES");
			sql.append("	(?,");
			sql.append("	?,");
			sql.append("	?,");
			sql.append("	?,");
			sql.append("	?,");
			sql.append("	?,");
			sql.append("	?)");

			ps = cb.getCon().prepareStatement(sql.toString());
			ps.setString(1, bean.getSale_date());
			ps.setInt(2, bean.getAccount_id());
			ps.setInt(3, bean.getCategory_id());
			ps.setString(4, bean.getTrade_name());
			ps.setInt(5, bean.getUnit_price());
			ps.setInt(6, bean.getSale_number());
			ps.setString(7, bean.getNote());
			ps.executeUpdate();

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

	public List<Account> responsibleList(String account_id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Account> resposiblelist = new ArrayList<>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT");
			sql.append("	*");
			sql.append(" FROM ");
			sql.append("	accounts");
			if (!(account_id.equals(""))) {
				sql.append(" WHERE ");
				sql.append("	account_id=?");
			}
			ps = cb.getCon().prepareStatement(sql.toString());
			if (!(account_id.equals(""))) {
				ps.setString(1, account_id);
			}
			rs = ps.executeQuery();

			while (rs.next()) {

				Account responsibleUser = new Account();
				responsibleUser.setName(rs.getString("name"));
				responsibleUser.setAccount_id(rs.getString("account_id"));
				resposiblelist.add(responsibleUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
			}
		}
		return resposiblelist;
	}

	public List<Category> categoryList() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Category> puroductCategorylist = new ArrayList<>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ");
			sql.append("	*");
			sql.append(" FROM ");
			sql.append("	categories ");
			sql.append(" WHERE active_flg=1");
			ps = cb.getCon().prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {

				Category puroductCategoryData = new Category();
				puroductCategoryData.setCategory_name(rs.getString("category_name"));
				puroductCategoryData.setCategory_id(rs.getString("category_id"));
				puroductCategorylist.add(puroductCategoryData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
			}
		}
		return puroductCategorylist;
	}

}
