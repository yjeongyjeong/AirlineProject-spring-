package com.airline.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.service.BoardDiaryLikeService;
import com.airline.service.BoardDiaryService;
import com.airline.vo.BoardDiaryVO;
import com.airline.vo.Criteria;
import com.airline.vo.PageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/boardDiary/*")
@RequiredArgsConstructor
public class BoardDiaryController {

	private final BoardDiaryService service;
	
	private final BoardDiaryLikeService likeService;
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("list..." + cri);
		//cri.setPageNum(1);
		//cri.setAmount(2);
				
		model.addAttribute("diaryList", service.getListwithPaging(cri));
		model.addAttribute("paging", new PageDTO(cri, service.getTotalCount(cri)));
	}
	
	@GetMapping("/write")
	@PreAuthorize("isAuthenticated()")
	public void write() {
		log.info("insert...");
		
	}
	
	@PostMapping("/write")
	@PreAuthorize("isAuthenticated()")
	public String write(BoardDiaryVO board, RedirectAttributes rttr){	

		service.insert(board);
		rttr.addFlashAttribute("result", board.getBoardNum());	
		return "redirect:/boardDiary/list";
	}
	

	@GetMapping({"/view", "/update"})
	public void get(@RequestParam("boardNum") int boardNum, @ModelAttribute("cri") Criteria cri , Model model) {
		log.info("get/modify...");
		log.info(boardNum);
		BoardDiaryVO boardVO = service.get(boardNum);
		model.addAttribute("board", boardVO);
		
		int likeCount = likeService.likeCount(boardNum);
		model.addAttribute("likeCount", likeCount);
		
	}
	
//	@PreAuthorize("principal.username==#writer")
	@PostMapping("/delete")
	public String remove(int boardNum, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, String writer) {
		log.info("remove : " + boardNum);
		
		if(service.delete(boardNum)) {
			
			rttr.addFlashAttribute("result", "success");
		}
		
		
		return "redirect:/boardDiary/list" + cri.getListLink2();
	}
	
//	@PreAuthorize("principal.username==#writer")
	@PostMapping("/update")
	public String modify(BoardDiaryVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, String writer) {
		log.info("update...");
		
		if(service.update(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		
		return "redirect:/boardDiary/list" + cri.getListLink2();
	}
	
}
