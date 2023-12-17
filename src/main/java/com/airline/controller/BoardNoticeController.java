package com.airline.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.service.BoardNoticeService;
import com.airline.vo.AuthorityVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.Criteria;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.PageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/notice/*")
@Log4j
@RequiredArgsConstructor
public class BoardNoticeController {

	@Autowired
	private BoardNoticeService service;
	
	@GetMapping("/list")
	public void getList(Model model, Criteria cri) {
		model.addAttribute("list", service.getPageList(cri));
		model.addAttribute("page", new PageDTO(cri, service.getTotal(cri)));
	} 
	 
	@GetMapping("/read")
	public void read(Model model, @Param("boardnum")int boardnum, @ModelAttribute("cri") Criteria cri) {
		model.addAttribute("board", service.getOne(boardnum));
		System.out.println(service.getOne(boardnum));
		service.updateReadCount(boardnum);
	}
	
	 
	@PostMapping("/delete")
	@PreAuthorize("isAuthenticated()")
	public String delete(@Param("boardnum") int boardnum, RedirectAttributes rttr,Model model ) {
		service.delete(boardnum);
		return "redirect:/notice/list";
	}
	
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void getRegister(Model model ,String userid) {
		model.addAttribute("user", service.getUser(userid));
	}
	
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register( BoardNoticeVO vo, RedirectAttributes rttr,Model model) throws Exception{
		System.out.println(vo);
		service.insert(vo);
		return "redirect:/notice/list";
	}

	@GetMapping("/modify")
	@PreAuthorize("isAuthenticated()")
	public void getModify(Model model, @Param("boardnum")int boardnum, @ModelAttribute("cri") Criteria cri ) {
		model.addAttribute("board", service.getOne(boardnum));
		System.out.println(service.getOne(boardnum));
	}
	
	@PostMapping("/modify")
	@PreAuthorize("isAuthenticated()")
	public String modify(BoardNoticeVO vo, RedirectAttributes rttr,Model model ) {
		service.modify(vo);
		System.out.println(">>>>>>>>>>>>>>>>"+vo);
		return "redirect:/notice/list";
	}   
	 
	@GetMapping("/popupList")
	@PreAuthorize("isAuthenticated()")
	public void getPopupList(Model model, Criteria cri) {
		model.addAttribute("list", service.noticePopup(cri));
		model.addAttribute("page", new PageDTO(cri, service.popupTotal()));
	}
	

}
