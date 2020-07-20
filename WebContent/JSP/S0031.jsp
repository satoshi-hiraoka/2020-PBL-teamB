<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css">
<link rel="stylesheet" href="/teamB/CSS/common.css">
<link rel="stylesheet" href="/teamB/CSS/radioButton.css">
<title>アカウント登録確認|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<form action="/teamB/S0031" method="POST" name="registerAccountCheck">
		<h1 id="title">アカウントを登録してよろしいですか？</h1>
		<table class="tablePosition">
			<tr>
				<td>
					<div class="text-right">
						氏名<span class="badge badge-pill badge-secondary">必須</span>
					</div>
				</td>
				<td>
					<input type="text" name="name" size="40" placeholder="${param['name']}" disabled />
				</td>
			</tr>
			<tr>
				<td>
					<div class="text-right">
						メールアドレス<span class="badge badge-pill badge-secondary">必須</span>
					</div>
				</td>
				<td>
					<input type="text" name="mail" size="40" placeholder="${param['mail']}" disabled />
				</td>
			</tr>
			<tr>
				<td>
					<div class="text-right">
						パスワード<span class="badge badge-pill badge-secondary">必須</span>
					</div>
				</td>
				<td>
					<input type="password" name="password" size="40" value="${param['password']}" disabled />
				</td>
			</tr>
			<tr>
				<td>
					<div class="text-right">
						パスワード（確認）<span class="badge badge-pill badge-secondary">必須</span>
					</div>
				</td>
				<td>
					<input type="password" name="passwordCheck" size="40" value="${param['passwordCheck']}" disabled />
				</td>
			</tr>
			<tr>
				<td>
					<div class="text-right">
						売上登録権限<span class="badge badge-pill badge-secondary">必須</span>
					</div>
				</td>
				<td>
					<label><input type="radio" name="authSales" value="0" disabled <c:if test="${param['authSales'] == '0'}">checked</c:if> />権限なし</label>
					<label><input type="radio" name="authSales" value="1" disabled <c:if test="${param['authSales'] == '1'}">checked</c:if> />権限あり</label>
				</td>
			</tr>
			<tr>
				<td>
					<div class="text-right">
						アカウント登録権限<span class="badge badge-pill badge-secondary">必須</span>
					</div>
				</td>
				<td>
					<label><input type="radio" name="authAccount" value="0" disabled <c:if test="${param['authAccount'] == '0'}">checked</c:if> />権限なし</label>
					<label><input type="radio" name="authAccount" value="1" disabled <c:if test="${param['authAccount'] == '1'}">checked</c:if> />権限あり</label>
				</td>
			</tr>
		</table>
		<button type="submit" class="btn btn-primary mainButton">
			<i class="fas fa-check"></i>OK
		</button>
		<button type="button" class="btn btn-outline-dark mainButton"onclick="history.back()">
			キャンセル
		</button>
	</form>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
	<script	src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

</body>
</html>