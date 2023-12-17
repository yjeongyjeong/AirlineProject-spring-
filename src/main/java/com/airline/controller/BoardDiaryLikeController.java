package com.airline.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.mapper.BoardDiaryMapper;
import com.airline.service.BoardDiaryLikeService;
import com.airline.vo.BoardDiaryLikeDTO;
import com.airline.vo.BoardDiaryLikeVO;
import com.airline.vo.BoardDiaryReplyVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequiredArgsConstructor
@RequestMapping("/boardDiary/like")
public class BoardDiaryLikeController {

	private final BoardDiaryLikeService service;

	private final BoardDiaryMapper diaryMapper;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/{boardNum}/{userId}")
	public ResponseEntity<BoardDiaryLikeDTO> updateLike(@PathVariable("boardNum") int boardNum, @PathVariable("userId") String userId){
		log.info("insert like controller ");
		
		int checkLike = service.checkLike(boardNum, userId);
		log.info("checklike : "+checkLike);
		
		diaryMapper.updateLikeCount(boardNum);
		int likeCount = service.likeCount(boardNum);
		
		BoardDiaryLikeDTO dto = new BoardDiaryLikeDTO();
		
		if(checkLike==0) {
			log.info("add like>>> ");
			boolean insertLike = service.insertLike(boardNum, userId);
			diaryMapper.updateLikeCount(boardNum);
			
			dto.setMessage("success");
			dto.setCheckLike(checkLike);
			dto.setLikeCount(likeCount);
			
			return insertLike ? new ResponseEntity<BoardDiaryLikeDTO>(dto, HttpStatus.OK) 	
					: new ResponseEntity<BoardDiaryLikeDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR); 
			
		} else if(checkLike==1) {
			log.info("delete like>>> ");
			boolean deleteLike = service.deleteLike(boardNum, userId);
			diaryMapper.updateLikeCount(boardNum);
			
			dto.setMessage("success");
			dto.setCheckLike(checkLike);
			dto.setLikeCount(likeCount);
			
			return deleteLike ? new ResponseEntity<BoardDiaryLikeDTO>(dto, HttpStatus.OK) 	
					: new ResponseEntity<BoardDiaryLikeDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<BoardDiaryLikeDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/check/{boardNum}")
	public int checkLike(@PathVariable("boardNum") int boardNum, @RequestBody BoardDiaryLikeVO vo){
		log.info("check like controller ");
		log.info("boardNum : " + boardNum);
		log.info("board like vo : " + vo);
		
		int checkLike = service.checkLike(boardNum, vo.getUserId());
		log.info("checklike : "+checkLike);

		return checkLike; 
	}
	
}
