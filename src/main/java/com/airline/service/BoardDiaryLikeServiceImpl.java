package com.airline.service;

import org.springframework.stereotype.Service;

import com.airline.mapper.BoardDiaryLikeMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class BoardDiaryLikeServiceImpl implements BoardDiaryLikeService {
	
	private final BoardDiaryLikeMapper mapper;
	
	@Override
	public boolean insertLike(int boardNum, String userId) {
		log.info("insert like service");
		return mapper.insertLike(boardNum, userId)==1;
	}

	@Override
	public boolean deleteLike(int boardNum, String userId) {
		log.info("delete like service");
		return mapper.deleteLike(boardNum, userId)==1;
	}

	@Override
	public int checkLike(int boardNum, String userId) {
		log.info("check like service");
		return mapper.checkLike(boardNum, userId);
	}

	@Override
	public int likeCount(int boardNum) {
		log.info("like count service");
		return mapper.likeCount(boardNum);
	}

	
	
}
