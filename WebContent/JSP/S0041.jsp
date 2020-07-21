<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.2/css/all.css">
<link rel="stylesheet" href="/teamB/CSS/common.css">
<link rel="stylesheet" href="/teamB/CSS/searchResultList.css">
<title>アカウント検索|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<form action="S0042.html" method="POST" name="searchAccountResult">
		<h1 id="title">アカウント検索結果一覧</h1>
		<table class="tablePosition">
			<tr>
				<c:if test="${authority == 10 || authority == 11}">
					<th>操作</th>
				</c:if>
				<th>No</th>
				<th>氏名</th>
				<th>メールアドレス</th>
				<th>権限</th>
			</tr>
			<c:forEach var="item" items="${list}">
				<tr>
					<c:if test="${param['authority']}">
						<td>
							<button type="submit" class="btn btn-primary">
								<i class="fas fa-check"></i>編集
							</button>
							<button type="submit" class="btn btn-danger"
								formaction="S0044.html">
								<i class="fas fa-times"></i>削除
							</button>
						</td>
					</c:if>
					<td class="textAlign">${item.account_id}</td>
					<td>${item.name}</td>
					<td>${item.mail}</td>
					<td>${item.authority}</td>
				</tr>
			</c:forEach>
		</table>
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