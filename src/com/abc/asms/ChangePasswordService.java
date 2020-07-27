package com.abc.asms;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;

public class ChangePasswordService extends ConnectionTeamB {
	ConnectionTeamB cb = new ConnectionTeamB();

	ChangePasswordService() throws ServletException, IOException {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public boolean checkMail(String mail) {

		boolean isMailExist = false;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			//SQL作って結果を取得する
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT");
			sql.append(" 	mail,");
			sql.append("		account_id,");
			sql.append("		name,");
			sql.append("		authority");
			sql.append(" FROM");
			sql.append(" 	accounts");
			sql.append(" WHERE");
			sql.append(" 	mail=?");
			ps = cb.getCon().prepareStatement(sql.toString());//StringBuilderをStringに変換して渡す。上のsqlをpsにせっと0
			ps.setString(1, mail);
			rs = ps.executeQuery();//実行
			if (rs.next()) {
				isMailExist = true;
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

		return isMailExist;

	}
}
