package com.goodee.mvcboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.mapper.BoardfileMapper;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

@Service
@Transactional // 서비스단에 위치하냐, 메소드단에 위치하냐에 따라 달라지므로 유의한다
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardfileMapper boardfileMapper;
	
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
	
	// 게시글 상세보기 // + 파일 첨부시 같이 조회
	public Map<String, Object> selectBoardOne(int boardNo) {
		// 게시글 조회
		Board board = boardMapper.selectBoardOne(boardNo);
		// 파일 정보 조회
		List<Boardfile> boardfileList = boardfileMapper.selectBoardfileOne(boardNo);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("board", board);
		resultMap.put("boardfileList", boardfileList);
		
		return resultMap;
	}
	
	// 게시글 작성
	public int addBoard(Board board, String path) { // path 값 같이 받기
		int row = boardMapper.insertBoard(board);
		int boardNo = 0;
		
		// 키 값을 가져올 board의 입력이 성공 했고 (row == 1), 사용자가 첨부한 파일이 1개 이상이라면...
		List<MultipartFile> fileList = board.getMultipartFile(); // Board vo에서 입력폼 속성값 가져오기
		if(row == 1 && fileList != null && fileList.size() >= 1) {
			boardNo = board.getBoardNo(); // 부모키 값 가져오기
			
			// 파일 업로드 작업 시작
			// 첨부한 파일의 갯수에 따라.. 1번 진행 or 여러번 진행 (반복)
			for(MultipartFile mf : fileList) {
				if(mf.getSize() > 0) {
					Boardfile bf = new Boardfile(); // Boardfile vo에 값 셋팅
					bf.setBoardNo(boardNo); // 가져온 부모키 값
					bf.setOriginFilename(mf.getOriginalFilename()); // 파일 원본이름
					bf.setFilesize(mf.getSize()); // 파일 크기
					bf.setFiletype(mf.getContentType()); // 파일 타입
					// 저장할 파일 이름 구하기 (+ 확장자명을 포함)
					// 1) 파일의 확장자
					// lastIndexOf() 메서드를 이용하여 "."을 기준으로 확장자명을 잘라낸다
					String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
					// 2) 파일의 이름
					// 중복되지 않는 유일한 식별자를 랜덤으로 생성하기 위해 UUID 클래스의 randomUUID() 메서드 사용
					// UUID로 식별자 생성시 중간에 "-"가 들어가기 때문에 replace() 메서드를 이용하여 없애준다
					String newFilename = UUID.randomUUID().toString().replace("-", "") + ext;
					bf.setSaveFilename(newFilename);
					
					// 최종적으로 (1) 데이터 테이블에 저장
					boardfileMapper.insertBoardfile(bf);
					
					// 최종적으로 (2) 실제 폴더에 데이터 저장 (저장위치인 path 필요)
					File f = new File(path + bf.getSaveFilename()); // path에 저장된 파일 이름으로 빈 파일을 생성
					// transferTo() 메서드를 이용하여 생성한 빈 파일에 첨부된 파일의 스트림을 주입한다
					try {
						mf.transferTo(f);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
						
						// 트랜잭션이 정상적으로 작동할 수 있도록 try..catch를 강요하지 않는 예외 발생이 필요하다
						throw new RuntimeException();
					}
				}
			}
		}
		
		return row;
	}
	
	// 게시글 삭제
	public int removeBoard(Board board) {
		// boardfile 테이블의 db 먼저 삭제
		Boardfile boardfile = new Boardfile();
		boardfile.setBoardNo(board.getBoardNo()); // boardNo를 넘긴다
		boardfileMapper.deleteBoardfile(boardfile);
		
		// board 테이블 db 삭제
		int row = boardMapper.deleteBoard(board);
		
		return row;
	}
	
	// 게시글 수정
	public int modifyBoard(Board board, String path) {
		int row = boardMapper.updateBoard(board);
		
		// 키 값을 가져올 board의 입력이 성공 했고 (row == 1), 사용자가 첨부한 파일이 1개 이상이라면...
		List<MultipartFile> fileList = board.getMultipartFile(); // Board vo에서 입력폼 속성값 가져오기
		if(row == 1 && fileList != null && fileList.size() >= 1) {
			int boardNo = board.getBoardNo(); // 부모키 값 가져오기
			
			// 파일 업로드 작업 시작
			// 첨부한 파일의 갯수에 따라.. 1번 진행 or 여러번 진행 (반복)
			for(MultipartFile mf : fileList) {
				if(mf.getSize() > 0) {
					Boardfile bf = new Boardfile(); // Boardfile vo에 값 셋팅
					bf.setBoardNo(boardNo); // 가져온 부모키 값
					bf.setOriginFilename(mf.getOriginalFilename()); // 파일 원본이름
					bf.setFilesize(mf.getSize()); // 파일 크기
					bf.setFiletype(mf.getContentType()); // 파일 타입
					// 저장할 파일 이름 구하기 (+ 확장자명을 포함)
					// 1) 파일의 확장자
					// lastIndexOf() 메서드를 이용하여 "."을 기준으로 확장자명을 잘라낸다
					String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
					// 2) 파일의 이름
					// 중복되지 않는 유일한 식별자를 랜덤으로 생성하기 위해 UUID 클래스의 randomUUID() 메서드 사용
					// UUID로 식별자 생성시 중간에 "-"가 들어가기 때문에 replace() 메서드를 이용하여 없애준다
					String newFilename = UUID.randomUUID().toString().replace("-", "") + ext;
					bf.setSaveFilename(newFilename);
					
					// 최종적으로 (1) 데이터 테이블에 저장
					boardfileMapper.insertBoardfile(bf);
					
					// 최종적으로 (2) 실제 폴더에 데이터 저장 (저장위치인 path 필요)
					File f = new File(path + bf.getSaveFilename()); // path에 저장된 파일 이름으로 빈 파일을 생성
					// transferTo() 메서드를 이용하여 생성한 빈 파일에 첨부된 파일의 스트림을 주입한다
					try {
						mf.transferTo(f);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
						
						// 트랜잭션이 정상적으로 작동할 수 있도록 try..catch를 강요하지 않는 예외 발생이 필요하다
						throw new RuntimeException();
					}
				}
			}
		}
		
		return row;
	}
	
	// REST API chart 호출
	// REST(Representational State Transfer) API란, URI와 HTTP Method를 이용하여 외부에서 만들어둔 기능들을 제어하는 것
	// HTTP Method -> GET(데이터 조회, SELECT), POST(데이터 생성, INSERT), DELETE(데이터 삭제), PUT(데이터 수정, UPDATE)
	// 원래는 서버에서 처리된 데이터를 view에 담아 클라이언트에게 응답했지만, REST API구조에서는 데이터(Model)만 응답하면 된다
	public List<Map<String, Object>> getLocalNameList() {
		return boardMapper.selectLocalNameList();
	}
}
