package com.goodee.mvcboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Boardfile;

@Mapper
public interface BoardfileMapper {
	int insertBoardfile(Boardfile boardFile);
}
