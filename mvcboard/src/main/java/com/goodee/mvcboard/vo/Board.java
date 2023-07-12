package com.goodee.mvcboard.vo;

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
}
