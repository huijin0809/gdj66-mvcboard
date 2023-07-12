<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>게시글 상세보기</h1>
	<table>
		<tr>
			<th>boardNo</th>
			<td>${board.boardNo}</td>
		</tr>
		<tr>
			<th>localName</th>
			<td>${board.localName}</td>
		</tr>
		<tr>
			<th>boardTitle</th>
			<td>${board.boardTitle}</td>
		</tr>
		<tr>
			<th>boardContent</th>
			<td>${board.boardContent}</td>
		</tr>
		<tr>
			<th>memberId</th>
			<td>${board.memberId}</td>
		</tr>
		<tr>
			<th>createdate</th>
			<td>${board.createdate}</td>
		</tr>
		<tr>
			<th>updatedate</th>
			<td>${board.updatedate}</td>
		</tr>
	</table>
	<a href="/board/boardList">목록으로</a>
	<a href="/board/modifyBoard?boardNo=${board.boardNo}">수정</a>
	<a href="/board/removeBoard?boardNo=${board.boardNo}">삭제</a>
</body>
</html>