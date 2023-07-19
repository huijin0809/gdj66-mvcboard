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
			
			// 파일 삭제 ajax
	        $('.delBtn').click(function() {
	        	let confirmed = confirm('파일을 삭제하시겠습니까?');
	        	if(confirmed) {
	        		let fileNo = $(this).data('file-no'); // 클릭한 버튼의 data-file-no 속성 값을 가져온다
		            console.log('boardfileNo : ' + fileNo);
	        		
		            $.ajax({
		                url: '/removeBoardfile', // 파일 삭제를 처리하는 서버의 URL
		                type: 'post', // 요청 방식을 선택
		                data: { boardfileNo : fileNo }, // 삭제할 파일의 정보를 데이터로 전송
		                success: function() {
		                	console.log('파일 삭제 성공');
		                	 $(this).closest('div').remove();
		                     alert('파일이 삭제되었습니다.');
		                }.bind(this),
		                error: function(error) {
		                	console.error('파일 삭제 실패: ' + error);
		                	alert('파일 삭제에 실패하였습니다.');
		                }
		            });
	        	}
	        });

		});
	</script>
</head>
<body>
	<h1>게시글 수정하기</h1>
	<form action="/board/modifyBoard" enctype="multipart/form-data" method="post">
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
			<tr>
				<th>boardFile</th>
				<td>
					<c:if test="${boardfileList.size() != 0}"> <!-- 파일을 등록했을 경우 -->
						<c:forEach var="bf" items="${boardfileList}">
							<div>
								${bf.originFilename}
								<a href="/upload/${bf.saveFilename}" target="_blank">
									미리보기
								</a>
								<a href="/upload/${bf.saveFilename}" download="${bf.saveFilename}">
									다운로드
								</a>
								<button type="button" class="delBtn" data-file-no="${bf.boardfileNo}">파일 삭제</button>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${boardfileList.size() == 0}"> <!-- 등록한 파일이 없을 경우 -->
						첨부된 파일이 없습니다
					</c:if>
					<div><input type="file" name="multipartFile"></div>
				</td>
			</tr>
		</table>
		<a href="/board/boardOne?boardNo=${board.boardNo}">취소</a>
		<button type="button" id="addBtn">파일 선택 추가</button>
		<button type="submit">수정</button>
	</form>
</body>
</html>