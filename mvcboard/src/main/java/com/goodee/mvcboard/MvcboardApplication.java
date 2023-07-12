package com.goodee.mvcboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // 추가
public class MvcboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcboardApplication.class, args);
	}

}
