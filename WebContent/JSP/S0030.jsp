<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css">
<link rel="stylesheet" href="/teamB/CSS/common.css">
<link rel="stylesheet" href="/teamB/CSS/radioButton.css">
<link rel="stylesheet" href="/teamB/CSS/alert.css">
<title>アカウント登録|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<h1 id="title">アカウント登録</h1>
	<c:if test="${param['err'] == 1}">
		<div class="alert alert-danger alert-dismissible" role="alert" id="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong><i class="fas fa-times"></i> エラーが発生しました！</strong><br>
		<c:forEach var="err" items="${errMsg}">
			<ul>
				<li>${errMsg}</li>
			</ul>
		</c:forEach>
		</div>
	</c:if>
	<c:if test="${param['suc'] == 1}">
		<div class="alert alert-success alert-dismissible" role="alert" id="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong><i class="fas fa-check"></i> 成功しました！</strong><br>
			<ul>
				<li>アカウントを登録しました。</li>
			</ul>
		</div>
	</c:if>
	<form action="/teamB/S0030" method="POST" name="registerAccount">
		<table class="tablePosition" >
			<tr>
				<td align="right">
					氏名<span class="badge badge-pill badge-secondary">必須</span>
				</td>
				<td>
					<input type="text" name="name" size="40" placeholder="氏名" />
				</td>
			</tr>
			<tr>
				<td align="right">
					メールアドレス<span class="badge badge-pill badge-secondary">必須</span>
				</td>
				<td>
					<input type="text" name="mail" size="40" placeholder="メールアドレス" />
				</td>
			</tr>
			<tr>
				<td align="right">
					パスワード<span class="badge badge-pill badge-secondary">必須</span>
				</td>
				<td>
					<input type="password" name="password" size="40" placeholder="パスワード" />
				</td>
			</tr>
			<tr>
				<td align="right">
					パスワード（確認）<span class="badge badge-pill badge-secondary">必須</span>
				</td>
				<td>
					<input type="password" name="passwordCheck" size="40" placeholder="パスワード（確認）" />
				</td>
			</tr>
			<tr>
				<td align="right">
					売上登録権限<span class="badge badge-pill badge-secondary">必須</span>
				</td>
				<td>
					<label><input type="radio" name="authSales" value="0" checked />権限なし</label>
					<label><input type="radio" name="authSales" value="1" />権限あり</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					アカウント登録権限<span class="badge badge-pill badge-secondary">必須</span>
				</td>
				<td>
					<label><input type="radio" name="authAccount" value="0" checked />権限なし</label>
					<label><input type="radio" name="authAccount" value="1" />権限あり</label>
				</td>
			</tr>
		</table>
		<button type="submit" class="btn btn-primary mainButton">
			<i class="fas fa-check"></i>登 録
		</button>
	</form>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
	<script	src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

</body>
</html>