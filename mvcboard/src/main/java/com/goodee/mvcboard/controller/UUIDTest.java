package com.goodee.mvcboard.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UUIDTest { // 파일 이름을 랜덤으로 부여하기 위해 UUID 사용 TEST
	@GetMapping("/uuidTest")
	public String uuidTest() {
		UUID uuid = UUID.randomUUID(); // UUID 클래스의 randomUUID() 메서드를 사용하여 유일한 식별자를 생성
		log.debug("\u001B[31m" + uuid.toString().replace("-", "") + "\u001B[0m"); // 콘솔창 출력 색상 변경
		return "";
	}
}
