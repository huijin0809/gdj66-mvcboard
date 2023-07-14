<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- JQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<!-- Chart.js -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
	<script>
		$(document).ready(function() {
			
			// 차트로 출력할 x축, y축 배열 선언
			const x = [];
			const y = [];
			// const barColors = ["red", "green","blue","orange","brown"];
			
			// 동기 호출로 x, y에 model값 셋팅 // 값을 불러오기도 전에 차트가 그려지면 안되므로 동기 방식으로 받기
			$.ajax({
				async : false, // true -> 비동기(기본값), false -> 동기
				url : '/rest/localNameList',
				type : 'get',
				success : function(model) { // JSON model
					// 실제 model은 List 형식 -> 자바스크립트에서 배열타입으로 바뀜
					/* 	예를 들면..
						{'model':[{localName:'부산', cnt:10},
								{localName:'서울', cnt:22}, .... ]}
					*/
					model.forEach(function(item, index) {
						$('#target').append('<tr>');
						$('#target').append('<td>' + item.localName + '</td>');
						$('#target').append('<td>' + item.cnt + '</td>');
						$('#target').append('</tr>');
						
						// chart 모델 생성해서 값 넣기
						x.push(item.localName);
						y.push(item.cnt);
					});
				}
			});
			
			// 셋팅한 값으로 차트 그리기
			new Chart("target2", {
			  type: "bar",
			  data: {
			    labels: x,
			    datasets: [{
			      // backgroundColor: barColors,
			      data: y
			    }]
			  },
			  // options: {...}
			});
			
		});
	</script>
</head>
<body>
	<h1>AJax API사용으로 localNameList 가져오기</h1>
	
	<table border="1">
		<thead>
			<tr>
				<th>지역명</th>
				<th>게시글수</th>
			</tr>
		</thead>
		<tbody id="target">
		</tbody>
	</table>
	
	<canvas id="target2" style="width:100%;max-width:700px"></canvas>
</body>
</html>