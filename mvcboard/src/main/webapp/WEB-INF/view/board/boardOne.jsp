<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<tr>
			<th>boardFile</th>
			<td>
				<c:if test="${boardfileList.size() != 0}"> <!-- 파일을 등록했을 경우 -->
					<c:forEach var="bf" items="${boardfileList}">
						<a href="/upload/${bf.saveFilename}" download="${bf.saveFilename}"> <!-- 이름 클릭시 다운로드 -->
							${bf.originFilename}<br>
						</a>
					</c:forEach>
				</c:if>
				<c:if test="${boardfileList.size() == 0}"> <!-- 등록한 파일이 없을 경우 -->
					첨부된 파일이 없습니다
				</c:if>
			</td>
		</tr>
	</table>
	<a href="/board/boardList">목록으로</a>
	<a href="/board/modifyBoard?boardNo=${board.boardNo}">수정</a>
	<a href="/board/removeBoard?boardNo=${board.boardNo}">삭제</a>
</body>
</html>