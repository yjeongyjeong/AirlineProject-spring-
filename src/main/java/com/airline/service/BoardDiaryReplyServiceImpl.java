package com.airline.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.airline.mapper.BoardDiaryMapper;
import com.airline.mapper.BoardDiaryReplyMapper;
import com.airline.vo.BoardDiaryReplyVO;
import com.airline.vo.Criteria;
import com.airline.vo.ReplyPageDTO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class BoardDiaryReplyServiceImpl implements BoardDiaryReplyService{

	private final BoardDiaryReplyMapper mapper;
	private final BoardDiaryMapper diaryMapper;
	
	@Override
	public BoardDiaryReplyVO selectOneReply(int replyNum) {
		log.info("select all reply service");
		return mapper.selectOneReply(replyNum);
	}
	
	@Override
	public ReplyPageDTO selectAllReply(Criteria cri, int boardNum) {
		log.info("select all reply service");
		return new ReplyPageDTO(mapper.replyCount(boardNum), mapper.selectAllReply(cri, boardNum));
		//return mapper.selectAllReply(cri, boardNum);
	}
	
	@Override
	public boolean insertReply(BoardDiaryReplyVO vo) {
		log.info("insert reply service");
		int result = mapper.insertReply(vo);
		diaryMapper.updateReplyCount(vo.getBoardNum());
		return result==1;
	}

	@Override
	public boolean updateReply(BoardDiaryReplyVO vo) {
		log.info("update reply service");
		return mapper.updateReply(vo)==1;
	}

	@Override
	public boolean deleteReply(int replyNum) {
		log.info("delete reply service");
		BoardDiaryReplyVO vo = mapper.selectOneReply(replyNum);
		int boardNum = vo.getBoardNum();
		int result = mapper.deleteReply(replyNum);
		diaryMapper.updateReplyCount(boardNum);
		return result==1;
	}

	


	
	
}
