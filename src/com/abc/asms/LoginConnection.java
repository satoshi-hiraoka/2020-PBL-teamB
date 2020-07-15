package com.abc.asms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginConnection {
	private Connection con;

	LoginConnection() {
		String url = "jdbc:mysql://localhost/asms";
		String parameter = "?serverTimezone=JST&useUnicode=true&characterEncoding=utf8";
		String user = "root";
		String pass = "";

		try {
			con = DriverManager.getConnection(url + parameter, user, pass);
		} catch (SQLException e) {
			System.out.println("データベースへの接続に失敗しました。");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return con;
	}
}
