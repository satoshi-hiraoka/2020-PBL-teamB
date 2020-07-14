<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<tr>
			<td>
				<form action="S0022.html" method="post">
					<button type="submit" class="btn btn-primary">
						<i class="fas fa-check"></i>詳細
					</button>
				</form>
			</td>
			<td class="textAlign">1</td>
			<td>2015/1/15</td>
			<td>本田 圭佑</td>
			<td>その他</td>
			<td>Yシャツ</td>
			<td class="textAlign">1,200</td>
			<td class="textAlign">5</td>
			<td class="textAlign">6,000</td>
		</tr>
		<tr>
			<td>
				<form action="S0022.html" method="post">
					<button type="submit" class="btn btn-primary">
						<i class="fas fa-check"></i>詳細
					</button>
				</form>
			</td>
			<td class="textAlign">2</td>
			<td>2015/1/16</td>
			<td>池田 勇太</td>
			<td>本・雑誌</td>
			<td>週刊少年ジャンプ2020年10号</td>
			<td class="textAlign">280</td>
			<td class="textAlign">1</td>
			<td class="textAlign">280</td>
		</tr>
		<tr>
			<td><form action="S0022.html" method="post">
					<button type="submit" class="btn btn-primary">
						<i class="fas fa-check"></i>詳細
					</button>
				</form></td>
			<td class="textAlign">3</td>
			<td>2015/1/17</td>
			<td>イチロー</td>
			<td>食料品</td>
			<td>唐揚げ弁当</td>
			<td class="textAlign">450</td>
			<td class="textAlign">3</td>
			<td class="textAlign">1,350</td>
		</tr>
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
