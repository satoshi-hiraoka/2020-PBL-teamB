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
	<h1 id="title">アカウント検索結果一覧</h1>
	<jsp:include page="errOccur.jsp" />
	<jsp:include page="sucOccur.jsp" />
	<table class="tablePosition">
		<tr>
			<c:if test="${authority == '10' || authority == '11'}">
				<th>操作</th>
			</c:if>
			<th>No</th>
			<th>氏名</th>
			<th>メールアドレス</th>
			<th>権限</th>
		</tr>
		<c:forEach var="account" items="${accountList}">
			<tr>
				<c:if test="${authority == '10' || authority == '11'}">
					<td>
						<form action="/teamB/S0041" method="POST" name="searchAccountResult">
							<input type="hidden" name="id" value="${account.account_id}" />
							<button type="submit" name="edit" value="edit" class="btn btn-primary">
								<i class="fas fa-check"></i>編集
							</button>
							<button type="submit" name="delete" value="delete" class="btn btn-danger">
								<i class="fas fa-times"></i>削除
							</button>
						</form>
					</td>
				</c:if>
				<td class="textAlign">${account.account_id}</td>
				<td>${account.name}</td>
				<td>${account.mail}</td>
				<td>
					<c:choose>
						<c:when test="${account.authority == 0}">権限なし</c:when>
						<c:when test="${account.authority == 1}">売上登録</c:when>
						<c:when test="${account.authority == 10}">アカウント登録</c:when>
						<c:when test="${account.authority == 11}">売上登録/アカウント登録</c:when>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</table>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</body>
</html>