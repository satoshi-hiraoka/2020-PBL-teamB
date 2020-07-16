package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public class LoginConnection {
	private Connection con;

	LoginConnection() throws ServletException, IOException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql/asms");
			con = ds.getConnection();

		} catch (Exception e) {
			System.out.println("データベースへの接続に失敗しました。");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return con;
	}
}
