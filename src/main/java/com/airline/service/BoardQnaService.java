package com.airline.service;

import java.util.List;

import com.airline.vo.BoardQnaVO;
import com.airline.vo.Criteria;
import com.airline.vo.KakaoUserVO;

public interface BoardQnaService {

	public List<BoardQnaVO> getList();
	
	public List<BoardQnaVO> getPageList(Criteria cri);
	
	public List<BoardQnaVO> questionList(int boardnum);	
	
	public int getTotal(Criteria cri);
	
	public BoardQnaVO readOne(int boardnum); 
	
	public void deleteQna(int boardnum);
	
	public void updateQna(BoardQnaVO vo);
	
	public void updateReadCount(int boardnum);
   
	public void registerQna(BoardQnaVO vo); 
	
	public void replyQna(BoardQnaVO vo);
	 
	public BoardQnaVO selectBoardreref(int boardnum);
	
	public void updateRepAdmin(int boardreref);
	
	public List<BoardQnaVO> mynotAnwList(KakaoUserVO vo);
	
	public List<BoardQnaVO> myAnsweredList(KakaoUserVO vo);
	
	public List<BoardQnaVO> myAllList(KakaoUserVO vo);
	

}
