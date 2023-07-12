package com.goodee.mvcboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Board;

@Mapper
public interface BoardMapper {
	// 추상메서드 선언
	List<Map<String, Object>> selectLocalNameList(); // local_name 컬럼과 count()를 반환
	
	List<Board> selectBoardListByPage(Map<String, Object> map);
	// mybatis 메서드는 매개값을 하나만 허용하므로 Map 사용 // param -> int beginRow, int rowPerPage
	
	int selectBoardCount(String localName); // 전체 행의 수
	
	Board selectBoardOne(int boardNo); // 해당 boardNo의 게시글 상세보기
	
	int insertBoard(Board board); // board insert row값 
	
	int deleteBoard(Board board); // board delete row값
	
	int updateBoard(Board board); // board update row값
	
	// 인터페이스는 구현체 없이 메소드에 대한 명세만 되어있다 (선언부만 가지고 있다) -> 추상메서드
	// 추상메서드 선언만 가능하기 때문에 생성자(new 연산자)를 가질 수 없다 -> 독립적으로 객체를 생성할 수 없다 (추상메서드의 특징)
	// '이러한 메서드를 쓸 것이다'라는 마음으로 인터페이스에 선언한 다음 선언된 그대로 구현해야하는 것이 인터페이스
	// 즉, 인터페이스는 상속 받아서 기능을 확장시키는 것이 목적인 추상클래스와 다르게 구현 객체의 동일한 실행 기능을 보장하기 위함이 목적이라고 할 수 있다!
	// 또한 일반 변수를 선언하여 사용이 가능한 추상클래스와 다르게 인터페이스는 상수만을 사용할 수 있다
	/*
		추상클래스 (extends) : 자신의 기능들을 하위로 확장, 같은 부모 클래스를 상속
		인터페이스 (implements) : 인터페이스에 정의된 메서드를 각 클래스의 목적에 맞게 동일한 기능으로 구현, 다른 부모 클래스를 상속하더라도 같은 기능
	*/
}
