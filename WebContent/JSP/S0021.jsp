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
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.2/css/all.css">
<link rel="stylesheet" href="/teamB/CSS/common.css">
<link rel="stylesheet" href="/teamB/CSS/searchResultList.css">
<title>売上検索結果次画面</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<h2 id="title">売上検索結果表示</h2>
	<table class="tablePosition">
		<tr>
			<th>操作</th>
			<th>No</th>
			<th>販売日</th>
			<th>担当</th>
			<th>商品カテゴリー</th>
			<th>商品名</th>
			<th>単価</th>
			<th>個数</th>
			<th>小計</th>
		</tr>
		<c:forEach var="sale" items="${saleslLst}">
			<tr>
				<td>
					<form action="S0022.html" method="post">
						<button type="submit" class="btn btn-primary">
							<i class="fas fa-check"></i>詳細
						</button>
					</form>
				</td>
				<td class="textAlign">${sale.sale_id}</td>
				<td>${sale.sale_date}</td>
				<td>${sale.name}</td>
				<td>${sale.category_name}</td>
				<td>${sale.trade_name}</td>
				<td class="textAlign">${sale.commaPrice}</td>
				<td class="textAlign">${ sale.commaNumer}</td>
				<td class="textAlign">${sale.commaSubtotal }</td>
			</tr>
		</c:forEach>
	</table>
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
