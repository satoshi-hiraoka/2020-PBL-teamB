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
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.2/css/all.css">
<link rel="stylesheet" href="/teamB/CSS/SalesTitle.css">
<link rel="stylesheet" href="/teamB/CSS/common.css">
<link rel="stylesheet" href="/teamB/CSS/S0022.css">
<title>売上詳細表示</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<h2 id="title">売上詳細表示</h2>
	<jsp:include page="errOccur.jsp" />
		<jsp:include page="sucOccur.jsp" />
	<table class="tablePosition">
		<tr>
			<td align="right">販売日</td>
			<td class="leftTd">2015/1/17</td>
		</tr>
		<tr>
			<td align="right">担当</td>
			<td class="leftTd">イチロー</td>
		</tr>
		<tr>
			<td align="right">商品カテゴリー</td>
			<td class="leftTd">食料品</td>
		</tr>
		<tr>
			<td align="right">商品名</td>
			<td class="leftTd">唐揚げ弁当</td>
		</tr>
		<tr>
			<td align="right">単価</td>
			<td align="right" class="leftTd">450</td>
		</tr>
		<tr>
			<td align="right">個数</td>
			<td align="right" class="leftTd">3</td>
		</tr>
		<tr>
			<td align="right">小計</td>
			<td align="right" class="leftTd">1,350</td>
		</tr>
		<tr>
			<td align="right" valign="top">備考</td>
			<td align="right" class="leftTd">今日からの新商品</td>
		</tr>
	</table>
	<div>
		<form action="S0023.jsp">
			<button type="submit" class="btn btn-primary mainButton">
				<i class="fas fa-check"></i>編集
			</button>
			<button type="submit" class="btn btn-danger mainButton" formaction="S0025.jsp">
				<i class="fas fa-times"></i>削除
			</button>
			<button type="button" class="btn btn-outline-dark mainButton"
				onclick="history.back()">キャンセル</button>
		</form>
	</div>
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
