package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public class ConnectionTeamB implements interfaceConnectionTeamB {
	private Connection con;

	ConnectionTeamB() throws ServletException, IOException {
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

	public Connection getCon() {
		return con;
	}
}
interface interfaceConnectionTeamB{
	public Connection getCon();
}

//サーブレットに以下のソースコードを記述
//	public クラス名 extends HttpServlet implements interfaceConnectionTeamB {
//		private ConnectionTeamB cb;
//	}
//	public クラス名() throws ServletException, IOException {
//		super();
//		this.cb = new ConnectionTeamB();
//	}
//	public Connection getCon() {
//		return this.cb.getCon();
//	}
//以降ConnectionはgetCon()で記述
