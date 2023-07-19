package com.goodee.mvcboard.restapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goodee.mvcboard.service.BoardfileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BoardfileRest {
	@Autowired
	private BoardfileService boardfileService;
	
	@PostMapping("/removeBoardfile")
	public String removeBoardfile(HttpServletRequest request,
								@RequestParam(name="boardfileNo") int boardfileNo) {
		String path = request.getServletContext().getRealPath("/upload/");
		
		log.debug("\u001B[31m" + "BoardfileController.removeBoardfile() param boardfileNo : " + boardfileNo + "\u001B[0m");
		log.debug("\u001B[31m" + "BoardfileController.removeBoardfile() : " + path + "\u001B[0m");

		boardfileService.removeBoardfile(boardfileNo, path);
		
		return "";
	}
}
