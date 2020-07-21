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
<link rel="stylesheet" href="/teamB/CSS/C0010.css" type="text/css">

<title>パスワード再設定｜物品売上管理システム</title>
</head>

<body class="p-3 mb-2 bg-light text-dark m-lg-5">
	<div class="container-fluid">
		<h2 class="text-center">物品売上管理システム</h2>
		<h3 class="text-center">パスワード再設定</h3>
		<!-- エラーメッセージ -->
		<c:if test="${!empty(errMsg)  }">
			<div class="alert alert-danger alert-dismissible" role="alert"
				id="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong><i class="fas fa-times"></i> エラーが発生しました！</strong><br>
				<ul>
					<c:forEach var="err" items="${errMsg}">
						<li>${err}</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<!-- 成功メッセージ -->
		<c:if test="${success=='success' }">
		<div class="alert alert-success alert-dismissible" role="alert" id="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong><i class="fas fa-check"></i> 成功しました！</strong><br>
			<ul>
				<li>パスワード再設定メールを送信しました。</li>
			</ul>
		</div>
		</c:if>
		
		<div class="login-page">
			<div class="form">
				<form class="login-form" action="/teamB/S0045" method="post">
					<div class="text-center">
						<input type="text" name="mail" placeholder="メールアドレス"><br>
						<input type="submit" class="login btn btn-primary btn-lg"
							value="メール送信"><br>
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
