package sample;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSample {
	public static void main(String[] args) {
		try {
			// GmailのSMTPを使用する
			Properties property = new Properties();
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.smtp.auth", "true");
			property.put("mail.smtp.starttls.enable", "true");
			property.put("mail.smtp.port", "587");
			property.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(property, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("tsd.sq.sie@gmail.com", "u7YFemtf");
				}
			});

			// toアドレス
			InternetAddress toAddress = new InternetAddress("satoshi.hiraoka@sie.co.jp");
			// fromアドレス
			InternetAddress fromAddress = new InternetAddress("tsd.sq.sie@gmail.com", "物品売上管理システム");

			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);
			mimeMessage.setFrom(fromAddress);

			//件名
			mimeMessage.setSubject("テストメール", "ISO-2022-JP");

			//本文
			mimeMessage.setText("テストメール本文", "ISO-2022-JP");

			Transport.send(mimeMessage);

			System.out.println("メールを送信しました。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
