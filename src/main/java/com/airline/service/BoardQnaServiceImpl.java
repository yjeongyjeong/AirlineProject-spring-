package com.airline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.mapper.BoardQnaMapper;
import com.airline.vo.BoardQnaVO;
import com.airline.vo.Criteria;
import com.airline.vo.KakaoUserVO;

@Service
public class BoardQnaServiceImpl implements BoardQnaService{

	@Autowired
	private BoardQnaMapper mapper;
	
	@Override
	public List<BoardQnaVO> getList() {
		return mapper.getList();
	} 

	@Override
	public BoardQnaVO readOne(int boardnum) {
		return mapper.readOne(boardnum);
	} 
  
	@Override
	public void deleteQna(int boardnum) {
		mapper.deleteQna(boardnum);
	}

	@Override
	public void updateQna(BoardQnaVO vo) {
		mapper.updateQna(vo);
	}

	@Override
	public void registerQna(BoardQnaVO vo) {
		mapper.registerQna(vo);
	}

	@Override
	public List<BoardQnaVO> getPageList(Criteria cri) {
		return mapper.getPageList(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}

	@Override
	public void updateReadCount(int boardnum) {
		mapper.updateReadCount(boardnum);
	}

	@Override
	public void replyQna(BoardQnaVO vo) { 
		mapper.replyQna(vo);
	}

	@Override
	public BoardQnaVO selectBoardreref(int boardnum) {
		return mapper.selectBoardreref(boardnum);
	}

	@Override
	public void updateRepAdmin(int boardreref) {
		mapper.updateRepAdmin(boardreref);
	}

	@Override
	public List<BoardQnaVO> mynotAnwList(KakaoUserVO vo) {
		return mapper.mynotAnwList(vo);
	}

	@Override
	public List<BoardQnaVO> myAnsweredList(KakaoUserVO vo) {
		return mapper.myAnsweredList(vo);
	}

	@Override
	public List<BoardQnaVO> myAllList(KakaoUserVO vo) {
		return mapper.myAllList(vo);
	}

	@Override
	public List<BoardQnaVO> questionList(int boardnum) {
		return mapper.questionList(boardnum);
	}


}
