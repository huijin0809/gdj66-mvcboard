<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- JQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<script>
		$(document).ready(function() {
			
			// 파일 선택 태그 추가
			$('#addBtn').click(function() {
				let fileInputRow = '<div><input type="file" name="multipartFile"></div>';
				$('input[type="file"]').last().after(fileInputRow);
			});
			
		});
	</script>
</head>
<body>
	<h1>게시글 작성하기</h1>
	<form action="/board/addBoard" enctype="multipart/form-data" method="post"> <!-- 파일 업로드를 위해 mutipart form 사용 -->
		<table>
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
				<td><input type="text" name="boardTitle"></td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td><textarea rows="5" cols="40" name="boardContent"></textarea></td>
			</tr>
			<tr>
				<th>boardFile</th>
				<td>
					<div><input type="file" name="multipartFile"></div>
				</td>
			</tr>
		</table>
		<a href="/board/boardList">목록으로</a>
		<button type="button" id="addBtn">파일 선택 추가</button>
		<button type="submit">작성</button>
	</form>
</body>
</html>