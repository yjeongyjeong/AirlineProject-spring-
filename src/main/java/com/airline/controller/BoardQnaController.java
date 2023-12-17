package com.airline.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.service.BoardQnaService;
import com.airline.service.UserService;
import com.airline.vo.BoardQnaVO;
import com.airline.vo.Criteria;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.PageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/qna/*")
@Log4j
@RequiredArgsConstructor
public class BoardQnaController {

	@Autowired
	private BoardQnaService service;
	
	@Autowired
	private UserService user;
	 
	@GetMapping("/list")
	public void getList(Model model, Criteria cri) {
		model.addAttribute("list", service.getPageList(cri));
		model.addAttribute("page", new PageDTO(cri, service.getTotal(cri)));
	} 
	  
	
	@GetMapping("/read")
	public void read(Model model, @Param("boardnum")int boardnum , @ModelAttribute("cri") Criteria cri) {
		model.addAttribute("board", service.readOne(boardnum));
		model.addAttribute("auth", service.selectBoardreref(boardnum));
		System.out.println(service.readOne(boardnum));
		model.addAttribute("list", service.questionList(boardnum));
		service.updateReadCount(boardnum);
	}
	
	@PostMapping("/delete")
	@PreAuthorize("isAuthenticated()")
	public String delete(@Param("boardnum") int boardnum, RedirectAttributes rttr,Model model ) {
		service.deleteQna(boardnum);
		return "redirect:/qna/list";
	}
	
	
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void getRegister(Model model ,String userid) {
	}
	
	
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register( BoardQnaVO vo, RedirectAttributes rttr,Model model) throws Exception{
		System.out.println(vo);
		service.registerQna(vo);
		return "redirect:/qna/list";
	}
	
	
	@GetMapping({"/modify", "/reply"})
	@PreAuthorize("isAuthenticated()")
	public void getModify(Model model, @Param("boardnum")int boardnum, @ModelAttribute("cri") Criteria cri ) {
		model.addAttribute("board", service.readOne(boardnum));
		System.out.println(service.readOne(boardnum));
	}
	
	@PostMapping("/modify")
	@PreAuthorize("isAuthenticated()")
	public String modify(BoardQnaVO vo, RedirectAttributes rttr,Model model ) {
		service.updateQna(vo);
		System.out.println(">>>>>>>>>>>>>>>>"+vo);
		return "redirect:/qna/list";
	}
	 
	@PostMapping("/reply")
	@PreAuthorize("isAuthenticated()")
	public String reply(Model model,BoardQnaVO vo, @Param("boardnum")int boardnum, @ModelAttribute("cri") Criteria cri ) {
		service.replyQna(vo);
		service.updateRepAdmin(boardnum);
		return "redirect:/qna/list";
	}
	
	@GetMapping("/myAns")
	@PreAuthorize("isAuthenticated()")
	public void myAns(Model model, Criteria cri) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
			String userid = userDetails.getUsername();

			KakaoUserVO vo = user.getUserInfo(userid);
			//등급조회
			String getGrade = user.getGrade(vo.getGradeCode());
			model.addAttribute("vo",vo);
		model.addAttribute("ans", service.myAnsweredList(vo));
		model.addAttribute("notAns", service.mynotAnwList(vo));
		model.addAttribute("all", service.myAllList(vo));
		}
	}
}