package com.abc.asms;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail { //クラスとメソッドインスタンス化する　メアド引数
	public void SendMailMethod(String address )throws Exception {
		try {
			// GmailのSMTPを使用する
			Properties property = new Properties();
			property.put("mail.smtp.host", "smtp.gmail.com");//予期しないえらーを発生させたい場合gggmailとかにする
			property.put("mail.smtp.auth", "true");
			property.put("mail.smtp.starttls.enable", "true");
			property.put("mail.smtp.port", "587");
			property.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(property, new javax.mail.Authenticator() {
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication("tsd.sq.sie@gmail.com", "u7YFemtf");//パスワードかえてみる
				}
			});

			// toアドレス
			InternetAddress toAddress = new InternetAddress(address);
			// fromアドレス
			InternetAddress fromAddress = new InternetAddress("tsd.sq.sie@gmail.com", "物品売上管理システム");

			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);
			mimeMessage.setFrom(fromAddress);

			//件名
			mimeMessage.setSubject("【物品売上管理システム】パスワード再設定", "ISO-2022-JP");

			//本文
			mimeMessage.setText("パスワード再設定を行います。\r\n"
					+ "以下のURLより新パスワードの入力・変更を行ってください。\r\n"
					+ "http://localhost:8080/teamB/S0045AddressCheck?mail="+address, "ISO-2022-JP");

			Transport.send(mimeMessage);

			System.out.println("メールを送信しました。");
		} catch (Exception e) {
			throw e;
		}
	}
}
