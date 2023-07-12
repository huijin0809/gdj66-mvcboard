<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>게시글 작성하기</h1>
	<form action="/board/addBoard" method="post">
		<table>
			<tr>
				<th>localName</th>
				<td>
					<select name="localName">
						<c:forEach var="m" items="${localNameList}">
							<c:if test="${m.localName != '전체'}">
								<option value="${m.localName}">${m.localName}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td><input type="text" name="boardTitle"></td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td><textarea rows="5" cols="40" name="boardContent"></textarea></td>
			</tr>
		</table>
		<a href="/board/boardList">목록으로</a>
		<button type="submit">작성</button>
	</form>
</body>
</html>