package com.airline.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.airline.vo.BoardEventVO;
import com.airline.vo.Criteria;
@Component
public interface BoardEventMapper {

	public List<BoardEventVO> getList();

	public List<BoardEventVO> getListwithPaging(Criteria cri);

	public List<BoardEventVO> getListPastEvent(Criteria cri);

	public BoardEventVO get(int boardNum);

	public int insert(BoardEventVO vo);
	
	public int delete(int boardNum);
	
	public int update(BoardEventVO vo);
	
	public int getTotalCount(Criteria cri);

	public int getTotalCountPastEvent(Criteria cri);

	public int updateReadCount(int boardNum);
	
	int updateRepImg(@Param("repImg") String repImg, @Param("filePath") String filePath, @Param("boardNum") int boardNum);
	
	public List<BoardEventVO> getListOverDue(String time);
	
	public int updateOngoing(int boardNum);
	
}
