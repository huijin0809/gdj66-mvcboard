package com.goodee.mvcboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.vo.Board;

@Service
@Transactional // 서비스단에 위치하냐, 메소드단에 위치하냐에 따라 달라지므로 유의한다
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	// 게시글 목록 조회
	public Map<String, Object> getBoardList(int curretPage, int rowPerPage, String localName) {
		// service layer의 역할 1)
		// controller에서 넘겨준 매개값을 DAO의 매개값으로 넣기 위해 가공한다
		int beginRow = (curretPage - 1) * rowPerPage;
		
		// 반환값 1
		List<Map<String, Object>> localNameList = boardMapper.selectLocalNameList();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("beginRow", beginRow);
		paramMap.put("rowPerPage", rowPerPage);
		paramMap.put("localName", localName);
		// 반환값 2
		List<Board> boardList = boardMapper.selectBoardListByPage(paramMap);
		
		// service layer의 역할 2)
		// DAO에서 반환받은 값을 가공하여 controller로 반환한다
		// 즉, service는 (값을 가공하는) 중간 연결자 역할을 한다고 볼 수 있다!
		int totalCount = boardMapper.selectBoardCount(localName);
		// 반환값 3
		int lastPage = totalCount / rowPerPage;
		if(totalCount % rowPerPage != 0) {
			lastPage += 1;
		}
		
		// 반환값 1, 2, 3를 담을 Map 생성
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("localNameList", localNameList);
		resultMap.put("boardList", boardList);
		resultMap.put("lastPage", lastPage);
		
 		return resultMap;
	}
	
	// 게시글 상세보기
	public Board selectBoardOne(int boardNo) { 
		return boardMapper.selectBoardOne(boardNo);
	}
	
	// 게시글 작성
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
	
	// 게시글 삭제
	public int removeBoard(Board board) {
		return boardMapper.deleteBoard(board);
	}
	
	// 게시글 수정
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
}
