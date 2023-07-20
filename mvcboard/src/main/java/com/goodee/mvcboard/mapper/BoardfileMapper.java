package com.goodee.mvcboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Boardfile;

@Mapper
public interface BoardfileMapper {
	int insertBoardfile(Boardfile boardFile);
	
	List<Boardfile> selectBoardfileOne(int boardNo);
	
	String selectBoardfileName(int boardfileNo);
	
	int deleteBoardfile(Boardfile boardFile); // boardNo 혹은 boardfileNo 중 하나가 넘어오기 때문에 매개값으로 boardfile 객체를 받는다
}
