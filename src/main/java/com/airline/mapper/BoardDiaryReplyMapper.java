package com.airline.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.airline.vo.BoardDiaryReplyVO;
import com.airline.vo.Criteria;

public interface BoardDiaryReplyMapper {
	
	public int insertReply(BoardDiaryReplyVO vo);
	
	public int replyCount(int boardNum);
	
	public List<BoardDiaryReplyVO> selectAllReply(@Param("cri") Criteria cri, @Param("boardNum") int boardNum);
	
	public int updateReply(BoardDiaryReplyVO vo);
	
	public int deleteReply(int replyNum);
	
	public BoardDiaryReplyVO selectOneReply(int replyNum);
	
	
	
}
