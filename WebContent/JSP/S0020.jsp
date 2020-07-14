<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ja">
<head>
<!--  meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.2/css/all.css">
<link rel="stylesheet" href="/teamB/CSS/SalesTitle.css">
<link rel="stylesheet" href="/teamB/CSS/common.css">
<link rel="stylesheet" href="/teamB/CSS/S0020.css">
<title>売上検索条件入力</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<h2 id="title">売上検索条件入力</h2>
	<form name="form" action="S0021.html" method="post">
		<table class="tablePosition">
			<tr>
				<td align="right">販売日</td>
				<td><input type="text" name="saleDate" size="10"><span
					id="margin-77">～</span><input type="text" name="" size="10"></td>
			</tr>
			<tr>
				<td align="right">担当</td>
				<td><select name="responsible" class="salesFiledLength">
						<option value="" disabled selected>選択して下さい</option>
						<option value="Ichiro">イチロー</option>
						<option value="HondaKesuke">本田 圭佑</option>
						<option value="IkedaYuta">池田 勇太</option>
				</select></td>
			</tr>
			<tr>
				<td align="right">商品カテゴリ</td>
				<td><select name="puroductCategory" class="salesFiledLength">
						<option value="" disabled selected>選択して下さい</option>
						<option value="1">食料品</option>
						<option value="1">本・雑誌</option>
						<option value="1">飲料</option>
				</select></td>
			</tr>
			<tr>
				<td align="right">商品名<span
					class="badge badge-pill badge-secondary">部分一致</span></td>
				<td><input type="text" name="puroductName" placeholder="商品名"
					class="salesFiledLength"></td>
			</tr>
			<tr>
				<td align="right" valign="top">備考<span
					class="badge badge-pill badge-secondary">部分一致</span></td>
				<td><input type="text" placeholder="備考"
					class="salesFiledLength"></td>
			</tr>
		</table>
		<div>
			<button type="submit" class="btn btn-primary mainButton">
				<i class="fas fa-search"></i>検索
			</button>
			<button type="reset" class="btn btn-outline-dark mainButton">クリア</button>
		</div>
	</form>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
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