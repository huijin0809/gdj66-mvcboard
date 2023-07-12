package com.goodee.mvcboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 이 어노테이이션을 붙임으로써 서블릿을 생성한 것과 같아지는 것
public class HomeController {
	@GetMapping("/home") // web.xml의 url 패턴매팅 or 어노테이션 WebServlet
	public String home() {
		return "home"; // RequestDispatcher.forward()
	}
}