package com.airline.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.airline.vo.BoardEventFileVO;

@Component
public interface BoardEventFileMapper {
	
	public List<BoardEventFileVO> searchFileByBoardNum(int boardNum);
	
	public int fileCount(int boardNum);
	
	public int insert(BoardEventFileVO vo); 
	
	public void delete(String uuid);
	
	public int deleteAll(int boardNum);
	
	public BoardEventFileVO findRepImg(int boardNum);
	
	public List<BoardEventFileVO> findRepImgFiles();
	
	public List<BoardEventFileVO> getOldFiles();
	
}
