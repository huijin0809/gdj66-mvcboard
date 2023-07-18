package com.goodee.mvcboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goodee.mvcboard.service.BoardfileService;

@CrossOrigin
@RestController
public class BoardfileController {
	@Autowired
	private BoardfileService boardfileService;
	
	@PostMapping("/removeBoardfile")
	public int removeBoardfile(HttpServletRequest request, int boardfileNo) {
		String path = request.getServletContext().getRealPath("/upload/");
		System.out.println("boardfileNo : " + boardfileNo);
		System.out.println("path : " + path);
		int row = boardfileService.removeBoardfile(boardfileNo, path);
		return row;
	}
}
