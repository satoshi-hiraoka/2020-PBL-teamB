<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${!empty(errMsg)}">
		<div class="alert alert-danger alert-dismissible" role="alert"
			id="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong><i class="fas fa-times"></i> エラーが発生しました！</strong><br>
			<ul>
				<c:forEach var="err" items="${errMsg}">
					<li>${err}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
</body>
</html>