<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	<c:if test="${!empty(sucMsg)}">
		<div class="alert alert-success alert-dismissible" role="alert"
			id="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong><i class="fas fa-check"></i> 成功しました！</strong><br>
			<ul>
				<c:forEach var="suc" items="${sucMsg}">
					<li>${suc}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
</body>
</html>