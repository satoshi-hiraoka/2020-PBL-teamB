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
<!--CSS 外部ファイル-->
<link rel="stylesheet" href="/teamB/CSS/logpass.css" type="text/css">
</head>
<title>ダッシュボード|物品売上管理システム</title>
<body>

	<jsp:include page="menu.jsp" />
	<div class="m-lg-5">
		<h1>ダッシュボード</h1>
		<jsp:include page="Popup.jsp" />
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-4">
					<table border="1">
						<tr>
							<th width="570" height="50" class="lead table-light">前月(5月)の売上合計</th>
						</tr>
						<tr>
							<td height="70">1,000,000円</td>
						</tr>
					</table>
				</div>
				<div class="col-md-4">
					<table border="1">
						<tr>
							<th width="570" height="50" class="lead table-light">今月(6月)の売上合計</th>
						</tr>
						<tr>
							<td height="70">1,200,000円</td>
						</tr>
					</table>
				</div>
				<div class="col-md-4">
					<table border="1">
						<tr>
							<th width="570" height="50" class="lead table-light">前月比</th>
						</tr>
						<tr>
							<td height="70">↑120.00%</td>
						</tr>
					</table>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12">
					<table border="1" width="100%" class="table" rules="rows">
						<tr>
							<th colspan="7" height="60" class="lead table-light">今月のイチローさんの売上</th>
						</tr>
						<tr height="10">
							<th>No</th>
							<th>発売日</th>
							<th>商品カテゴリ</th>
							<th>商品名</th>
							<th>単価</th>
							<th>個数</th>
							<th>小計</th>
						</tr>
						<tr height="10">
							<td>1</td>
							<td>2020/7/07</td>
							<td>食料品</td>
							<td>唐揚げ弁当</td>
							<td>450</td>
							<td>3</td>
							<td>1,350</td>
						</tr>
						<tr height="20">
							<td>2</td>
							<td>2020/7/07</td>
							<td>食料品</td>
							<td>あんぱん</td>
							<td>120</td>
							<td>10</td>
							<td>1,200</td>
						</tr>
						<tr height="20">
							<td>3</td>
							<td>2020/7/07</td>
							<td>飲料</td>
							<td>コカ・コーラ500ml</td>
							<td>130</td>
							<td>5</td>
							<td>650</td>
						</tr>
						<tr height="20">
							<td colspan="5"></td>
							<td>合計</td>
							<td>3,200</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
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
	<!--CSS 外部ファイル-->
	<link rel="stylesheet" href="/teamB/CSS/C0010.css" type="text/css">

</body>
</html>