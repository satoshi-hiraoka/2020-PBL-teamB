<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>アカウント検索|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<form action="/teamB/S0040" method="POST" name="searchAccount">
		<h1 id="title">アカウント検索</h1>
		<jsp:include page="errOccur.jsp" />
		<table class="tablePosition">
			<tr>
				<td>
					<div class="text-right">
						氏名<span class="badge badge-pill badge-secondary">部分一致</span>
					</div>
				</td>
				<td>
					<input type="text" name="name" size="40" placeholder="氏名" />
				</td>
			</tr>
			<tr>
				<td>
					<div class="text-right">メールアドレス</div>
				</td>
				<td>
					<input type="text" name="mail" size="40" placeholder="メールアドレス" />
				</td>
			</tr>
			<tr>
				<td>
					<div class="text-right">売上登録権限</div>
				</td>
				<td>
					<label><input type="radio" name="authSales" value="all" checked />全て</label>
					<label><input type="radio" name="authSales" value="0" />権限なし</label>
					<label><input type="radio" name="authSales" value="1" />権限あり</label>
				</td>
			</tr>
			<tr>
				<td>
					<div class="text-right">アカウント登録権限</div>
				</td>
				<td>
					<label><input type="radio" name="authAccount" value="all" checked />全て</label>
					<label><input type="radio" name="authAccount" value="0" />権限なし</label>
					<label><input type="radio" name="authAccount" value="1" />権限あり</label>
				</td>
			</tr>
		</table>
		<button type="submit" class="btn btn-primary mainButton">
			<i class="fas fa-search"></i>検 索
		</button>
		<button type="reset" class="btn btn-outline-dark mainButton">
			クリア
		</button>
	</form>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

</body>
</html>