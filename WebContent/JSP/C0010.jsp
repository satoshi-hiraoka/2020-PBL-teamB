<%@page import="org.apache.jasper.tagplugins.jstl.core.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ja">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link rel="stylesheet" href="/teamB/CSS/logpass.css" type="text/css">

<title>ログイン|物品売上管理システム</title>
</head>

<body class="p-3 mb-2 bg-light text-dark m-lg-5">
	<div class="container-fluid">
		<h2 class="text-center">物品売上管理システム</h2>
		<!-- エラー、成功メッセージ -->
		<jsp:include page="Popup.jsp" ></jsp:include>
		<div class="login-page">
			<div class="form">
				<form class="login-form" action="/teamB/C0010" method="post">
					<div class="text-center">
						<input type="text" placeholder="メールアドレス" name="mail" value="${mail }"/><br>
						<input type="password" placeholder="パスワード" name="passWord"/><br>
						<input type="submit" class="login btn btn-primary btn-lg"
							value="ログイン"><br>
						<p class="message">
							<a href="/teamB/JSP/S0045.jsp">パスワードを忘れた方はこちら</a><br>
						</p>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>
</body>
</html>