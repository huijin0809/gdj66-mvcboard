<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>게시글 삭제하기</h1>
	정말 삭제하시겠습니까?
	<form action="/board/removeBoard" method="post">
	<input type="hidden" name="boardNo" value="${boardNo}">
		<a href="/board/boardOne?boardNo=${boardNo}">취소</a>
		<button type="submit">삭제</button>
	</form>
</body>
</html>