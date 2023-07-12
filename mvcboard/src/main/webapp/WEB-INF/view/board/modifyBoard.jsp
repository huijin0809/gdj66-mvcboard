<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>게시글 수정하기</h1>
	<form action="/board/modifyBoard" method="post">
	<input type="hidden" name="boardNo" value="${board.boardNo}">
		<table>
			<tr>
				<th>boardNo</th>
				<td>${board.boardNo}</td>
			</tr>
			<tr>
				<th>localName</th>
				<td>
					<select name="localName">
						<c:forEach var="m" items="${localNameList}">
							<option value="${m.localName}">${m.localName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td><input type="text" name="boardTitle" value="${board.boardTitle}"></td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td><textarea rows="5" cols="40" name="boardContent">${board.boardContent}</textarea></td>
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
		<a href="/board/boardOne?boardNo=${board.boardNo}">취소</a>
		<button type="submit">수정</button>
	</form>
</body>
</html>