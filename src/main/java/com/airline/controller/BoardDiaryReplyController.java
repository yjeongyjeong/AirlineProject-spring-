package com.airline.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.airline.service.BoardDiaryReplyService;
import com.airline.vo.BoardDiaryReplyVO;
import com.airline.vo.Criteria;
import com.airline.vo.ReplyPageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequiredArgsConstructor
@RequestMapping("/boardDiary/replies")
public class BoardDiaryReplyController {

	private final BoardDiaryReplyService service;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody BoardDiaryReplyVO vo){
		log.info("ReplyVO : " + vo);
		
		return service.insertReply(vo) ? new ResponseEntity<String>("success", HttpStatus.OK) 	
								: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	@GetMapping(value = "/pages/{boardNum}/{page}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("boardNum") int boardNum,
													@PathVariable("page") int page){
		log.info("get List...boardNum : " + boardNum + " page : " +page);							
		
		Criteria cri = new Criteria(page, 10);
		
		return new ResponseEntity<ReplyPageDTO>(service.selectAllReply(cri, boardNum), HttpStatus.OK);	
	}
	
	
	@GetMapping(value = "/{replyNum}", produces = {MediaType.APPLICATION_JSON_VALUE})	
	public ResponseEntity<BoardDiaryReplyVO> get(@PathVariable("replyNum") int replyNum){
		log.info("get : " + replyNum);
		return new ResponseEntity<BoardDiaryReplyVO>(service.selectOneReply(replyNum), HttpStatus.OK);
	}
	
//	@PreAuthorize("principal.userName==#vo.userId")
	@DeleteMapping(value = "/{replyNum}")
	public ResponseEntity<String> remove(@RequestBody BoardDiaryReplyVO vo, @PathVariable("replyNum") int replyNum){		
		log.info("delete : " + replyNum);
		log.info("replyer : " + vo.getReplyWriter());
		return service.deleteReply(replyNum)? new ResponseEntity<String>("success", HttpStatus.OK) 
										: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	@PreAuthorize("principal.userName==#vo.userId")				
	@RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "/{replyNum}", consumes = "application/json")
	public ResponseEntity<String> modify(@RequestBody BoardDiaryReplyVO vo, @PathVariable("replyNum") int replyNum){
		log.info("modify >> " + replyNum);
		log.info("Reply VO >> " +vo);		
		
		//vo.setRno(rno);	//필요 없음?
		
		return service.updateReply(vo) ? new ResponseEntity<String>("success", HttpStatus.OK) 
										: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
