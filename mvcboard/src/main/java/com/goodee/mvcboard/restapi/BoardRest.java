package com.goodee.mvcboard.restapi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goodee.mvcboard.service.BoardService;

@CrossOrigin
// 리소스를 요청한 위치와 응답할 위치(외부)가 서로 다른 경우(프로토콜, 도메인, 포트 등..) -> Cross-Site HTTP Requests라고 부른다
// HTTP 요청은 기본적으로 Cross-Site HTTP Requests가 가능하지만 스크립트는 Same Origin Policy를 적용받기 때문에 불가능
// 즉, 보통 api를 사용할때 쓰는 AJAX도 마찬가지.. 이를 해결하기 위해 CORS(Cross-Origin Resource Sharing)가 가능하도록 하는 어노테이션을 적용!
// 요약하자면 다른 도메인, 다른 포트에서도 자원을 받아오기 위한 방법임
@RestController // 기존의 Controller은 view를 반환했다면 RestController은 JSON을 반환
public class BoardRest {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/rest/localNameList")
	public List<Map<String, Object>> getLocalNameList() {
		return boardService.getLocalNameList();
	}
}
