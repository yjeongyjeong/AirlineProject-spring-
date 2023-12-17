package com.airline.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.airline.vo.BoardDiaryReplyVO;
import com.airline.vo.Criteria;
import com.airline.vo.ReplyPageDTO;

public interface BoardDiaryReplyService {
	
	public BoardDiaryReplyVO selectOneReply(int replyNum);
	
	public ReplyPageDTO selectAllReply(@Param("cri") Criteria cri, @Param("boardNum") int boardNum);

	public boolean insertReply(BoardDiaryReplyVO vo);
	
	public boolean updateReply(BoardDiaryReplyVO vo);
	
	public boolean deleteReply(int replyNum);
	
//	public int replyCount(int boardNum);
}
