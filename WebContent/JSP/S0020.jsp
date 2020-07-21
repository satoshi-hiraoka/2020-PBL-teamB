<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<form name="form" action="/teamB/S0021" method="post">
		<table class="tablePosition">
			<tr>
				<td align="right">販売日</td>
				<td><input type="text" name="previousPeriod" size="10"><span
					id="margin-77">～</span><input type="text" name="latePeriod" size="10"></td>
			</tr>
			<tr>
				<td align="right">担当</td>
				<td><select name="responsible" class="salesFiledLength">
						<c:if test="${not empty responsible}">
							<c:forEach var="responsibleData" items="${resposiblelist}">
								<c:choose>
									<c:when test="${responsibleData.account_id==responsible}">
										<option value="${responsibleData.account_id}" selected>${responsibleData.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${responsibleData.account_id}">${responsibleData.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
						<c:if test="${ empty responsible}">
							<option value="" disabled selected>選択して下さい</option>
							<c:forEach var="responsibleData" items="${resposiblelist}">
								<option value="${responsibleData.account_id}">${responsibleData.name}</option>
							</c:forEach>
						</c:if>
				</select></td>
			</tr>
			<tr>
				<td align="right">商品カテゴリ</td>
				<td><select name="puroductCategory" class="salesFiledLength">
						<c:if test="${not empty puroductCategory}">
							<c:forEach var="puroductCategoryData"
								items="${puroductCategorylist}">
								<c:choose>
									<c:when
										test="${puroductCategoryData.category_id==puroductCategory}">
										<option value="${puroductCategoryData.category_id}" selected>${puroductCategoryData.category_name}</option>
									</c:when>
									<c:otherwise>
										<option value="${puroductCategoryData.category_id}">${puroductCategoryData.category_name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
						<c:if test="${ empty puroductCategory}">
							<option value="" disabled selected>選択して下さい</option>
							<c:forEach var="puroductCategoryData"
								items="${puroductCategorylist}">
								<option value="${puroductCategoryData.category_id}">${puroductCategoryData.category_name}</option>
							</c:forEach>
						</c:if>
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
