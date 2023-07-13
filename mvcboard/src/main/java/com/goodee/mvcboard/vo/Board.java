package com.goodee.mvcboard.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data // setter, getter를 자동으로 생성해준다
public class Board {
	private int boardNo;
	private String localName;
	private String boardTitle;
	private String boardContent;
	private String memberId;
	private String createdate;
	private String updatedate;
	
	// table 속성이 아닌 입력폼의 속성
	// -> BoardForm.class(DTO) 와 Board.class(도메인)으로 분리해서도 사용 가능
	private List<MultipartFile> multipartFile;
}
