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
<link rel="stylesheet" href="/teamB/CSS/SalesTitle.css">
<link rel="stylesheet" href="/teamB/CSS/common.css">
<title>売上登録確認画面</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<h2 id="title">売上登録を登録してよろしいですか？</h2>
	<form name="form" action="/teamB/SalesRegisterComplete" method="post">
		<table class="tablePosition">
			<tr>
				<td class="textAlign">販売日</td>
				<td><input type="text" name="ConSaleDate" size="10"
					value="${param['saleDate']}" disabled></td>
			</tr>
			<tr>
				<td class="textAlign">担当</td>
				<td><select name="responsibleData" class="salesFiledLength"
					disabled>
						<c:forEach var="responsibleData" items="${resposiblelist}">
							<c:if
								test="${responsibleData.account_id==param['responsibleData']}">
								<option value="${responsibleData.account_id}" selected>${responsibleData.name}</option>
							</c:if>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="textAlign">商品カテゴリー</td>
				<td><select name="puroductCategory" class="salesFiledLength"
					disabled>
						<c:forEach var="puroductCategoryData"
							items="${puroductCategorylist}">
							<c:if
								test="${puroductCategoryData.category_id==param['puroductCategory']}">
								<option value="${puroductCategoryData.category_id}" selected>${puroductCategoryData.category_name}</option>
							</c:if>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="textAlign">商品名</td>
				<td><input type="text" name="puroductName"
					value="${param['puroductName']}" class="salesFiledLength" disabled></td>
			</tr>
			<tr>
				<td class="textAlign">単価</td>
				<td><input type="text" name="puroductUnitPrice"
					value="${requestScope['commaPrice']}" class="salesInputIntText"
					size="10" disabled></td>
			</tr>
			<tr>
				<td class="textAlign">個数</td>
				<td><input type="text" name="puroductNumber" size="10"
					value="${requestScope['commaNumer']}" class="salesInputIntText"
					disabled></td>
			</tr>
			<tr>
				<td class="textAlign">小計</td>
				<td><input type="text" name="puroductSubtotal"
					value="${requestScope['commaSubtotal']}" size="10"
					class="salesInputIntText" disabled></td>
			</tr>
			<tr>
				<td class="textAlign textBoxAlignRemark">備考</td>
				<td><textarea rows="4" cols="40" placeholder="備考"
						class="salesFiledLength" name="
remark" disabled>${param['remark']}</textarea></td>
			</tr>
		</table>
		<div>
			<button type="submit" class="btn btn-primary mainButton">
				<i class="fas fa-check"></i>OK
			</button>
			<button type="submit" class="btn btn-outline-dark mainButton"
				formaction="S0010.html">キャンセル</button>
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
