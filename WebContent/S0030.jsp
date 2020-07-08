<!DOCTYPE html>
<html lang="ja">
	<head>
		<!-- Required meta tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" >
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" >

		<title>アカウント登録|物品売上管理システム</title>
	</head>
	<body>
		<div class="m-5">
			<form action="S03002.html" method="POST" name="register-account">
				<table class="table table-borderless">
					<tr><td colspan="2"><h2>アカウント登録</h2></td></tr>
					<tr>
						<td><div class="text-right">氏名<span class="badge badge-pill badge-secondary">必須</span></div></td>
						<td>
							<input type="text" name="name" size="40" placeholder="氏名" required />
						</td>
					</tr>
					<tr>
						<td><div class="text-right">メールアドレス<span class="badge badge-pill badge-secondary">必須</span></div></td>
						<td>
							<input type="text" name="email" size="40" placeholder="メールアドレス" required />
						</td>
					</tr>
					<tr>
						<td><div class="text-right">パスワード<span class="badge badge-pill badge-secondary">必須</span></div></td>
						<td>
							<input type="password" name="password" size="40" placeholder="パスワード" required />
						</td>
					</tr>
					<tr>
						<td><div class="text-right">パスワード（確認）<span class="badge badge-pill badge-secondary">必須</span></div></td>
						<td>
							<input type="password" name="password" size="40" placeholder="パスワード（確認）" required />
						</td>
					</tr>
					<tr>
						<td><div class="text-right">売上登録権限<span class="badge badge-pill badge-secondary">必須</span></div></td>
						<td>
							<input type="radio" name="auth-sales" value="権限なし" required  checked/>権限なし
							<input type="radio" name="auth-sales" value="権限あり" required  />権限あり
						</td>
					</tr>
					<tr>
						<td><div class="text-right">アカウント登録権限<span class="badge badge-pill badge-secondary">必須</span></div></td>
						<td>
							<input type="radio" name="auth-account" value="権限なし" required  checked/>権限なし
							<input type="radio" name="auth-account" value="権限あり" required  />権限あり
						</td>
					</tr>
				</table>
				<div class="text-center"><button type="submit" class="btn btn-primary" ><i class="fas fa-check" ></i>登 録</button></div>
			</form>
		</div>
		<!-- Optional JavaScript -->
		<!-- jQuery first, then Popper.js, then Bootstrap JS -->
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>

	</body>
	
</html>
