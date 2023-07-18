package com.goodee.mvcboard.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.mvcboard.mapper.BoardfileMapper;

@Service
@Transactional
public class BoardfileService {
	@Autowired
	private BoardfileMapper boardfileMapper;
	
	// 파일 삭제 ajax로 요청
	public int removeBoardfile(int boardfileNo, String path) {
		
		// db에서 파일 삭제
		int row = boardfileMapper.deleteBoardfile(boardfileNo);
				
		// 실제 파일 삭제
		String saveFilename = boardfileMapper.selectBoardfileName(boardfileNo);
		System.out.println(saveFilename);
		File f = new File(path + saveFilename);
		if (f.exists()) { // 파일이 존재하는지 확인합니다.
            if (f.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("파일 삭제 실패");
            }
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
		
		return row;
	}
}
