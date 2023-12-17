package com.airline.service;

import java.util.List;

import com.airline.vo.BoardDiaryVO;
import com.airline.vo.Criteria;


public interface BoardDiaryService {
	
	public List<BoardDiaryVO> getListwithPaging(Criteria criteria);
	
	public BoardDiaryVO get(int boardNum);
	
	public boolean insert(BoardDiaryVO board);	
	
	public boolean delete(int boardNum);		
	
	public boolean update(BoardDiaryVO board);	
	
	public int getTotalCount(Criteria cri);
	
}
