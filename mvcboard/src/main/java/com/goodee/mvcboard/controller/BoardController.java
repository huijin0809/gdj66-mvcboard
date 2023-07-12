package com.goodee.mvcboard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.mvcboard.service.BoardService;
import com.goodee.mvcboard.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log라는 필드를 호출할 수 있다, log필드는 디버깅용 메서드를 가지고 있다
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	// 게시판 목록 조회
	@GetMapping("/board/boardList")
	public String boardList(Model model,
							@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
							@RequestParam(name = "rowPerPage", defaultValue = "10") int rowPerPage,
							@RequestParam(name = "localName", required = false) String localName) {
		// RequestParam 어노테이션
		// -> request.getParameter와 같은 역할, 무조건 String타입으로 받아오는 것과 다르게 형변환을 할 필요가 없다는 편의성이 있다
		// 또한, defaultValue을 사용하면 기본값을 지정 가능하다 즉 null로 값이 넘어올 일이 없다
		
		Map<String, Object> resultMap = boardService.getBoardList(currentPage, rowPerPage, localName);
		
		model.addAttribute("localNameList", resultMap.get("localNameList")); // request.setAttribute와 비슷한 역할
		model.addAttribute("boardList", resultMap.get("boardList"));
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		
		model.addAttribute("currentPage", currentPage); // 페이징을 위해 현재 페이지의 값도 같이 넘겨줌
		model.addAttribute("localName", localName);
		
		return "/board/boardList";
	}
	
	// 게시글 작성 폼
	@GetMapping("/board/addBoard")
	public String addBoard(Model model, // 대신 ModelAndView 사용하기? 고민..
							@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
							@RequestParam(name = "rowPerPage", defaultValue = "10") int rowPerPage,
							@RequestParam(name = "localName", required = false) String localName) {
		// 아직 세션 기능 구현 전이므로.... // 로그인 유무에 따라 분기 필요
		
		// 카테고리 목록 조회 // 만을 위해서 매개값 두개나 받아오는거 킹받는다...
		Map<String, Object> resultMap = boardService.getBoardList(currentPage, rowPerPage, localName);
		model.addAttribute("localNameList", resultMap.get("localNameList"));
		
		return "/board/addBoard";
	}
	
	// 게시글 작성 액션
	@PostMapping("/board/addBoard")
	public String addBoard(Board board) { // Board 객체의 필드 이름에 자동으로 매핑된다
		// memberId 처리
		String memberId = "test";
		board.setMemberId(memberId);
		
		int row = boardService.addBoard(board);
		System.out.println("addBoard row: " + row);
		log.debug("\u001B[31m" + "addBoard row : " + row + "\u001B[0m"); // 콘솔창 출력 색상 지정
		
		return "redirect:/board/boardList";
	}
	
	// 게시글 상세보기
	@GetMapping("/board/boardOne")
	public String boardOne(Model model,
							@RequestParam(name = "boardNo", required = false) int boardNo) {
		Board board = boardService.selectBoardOne(boardNo);
		model.addAttribute("board", board);
		
		return "/board/boardOne";
	}
	
	// 게시글 수정 폼
	@GetMapping("/board/modifyBoard")
	public String modifyBoard(Model model,
								@RequestParam(name = "boardNo", required = false) int boardNo,
								@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
								@RequestParam(name = "rowPerPage", defaultValue = "10") int rowPerPage,
								@RequestParam(name = "localName", required = false) String localName) {
		// 수정 전 정보 조회
		Board board = boardService.selectBoardOne(boardNo);
		model.addAttribute("board", board);
		
		// 카테고리 목록 조회 // 만을 위해서 매개값 두개나 받아오는거 킹받는다...
		Map<String, Object> resultMap = boardService.getBoardList(currentPage, rowPerPage, localName);
		model.addAttribute("localNameList", resultMap.get("localNameList"));
				
		return "/board/modifyBoard";
	}
	
	// 게시글 수정 액션
	@PostMapping("/board/modifyBoard")
	public String modifyBoard(Board board) { // Board 객체의 필드 이름에 자동으로 매핑된다
		// memberId 처리
		String memberId = "test";
		board.setMemberId(memberId);
		
		int row = boardService.modifyBoard(board);
		System.out.println("modifyBoard row: " + row);
		log.debug("\u001B[31m"+"modifyBoard row : "+row+"\u001B[0m"); // 콘솔창 출력 색상 지정
		
		return "redirect:/board/boardOne?boardNo=" + board.getBoardNo();
	}
	
	// 게시글 삭제 폼
	@GetMapping("/board/removeBoard")
	public String removeBoard(Model model,
								@RequestParam(name = "boardNo", required = false) int boardNo) {
		// 세션 아이디에 따른 분기... 추가예정
		return "";
		
	}
	
	// 게시글 삭제 액션
	@PostMapping("/board/removeBoard")
	public String removeBoard() {
		return "";
	}
	
}
