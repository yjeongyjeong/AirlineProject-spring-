package com.airline.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.service.BoardEventService;
import com.airline.vo.BoardEventFileVO;
import com.airline.vo.BoardEventVO;
import com.airline.vo.Criteria;
import com.airline.vo.PageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/boardEvent/*")
@RequiredArgsConstructor
public class BoardEventController {

	private final BoardEventService service;
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("list..." + cri);
		//cri.setPageNum(1);
		//cri.setAmount(2);
				
		model.addAttribute("EventList", service.getListwithPaging(cri));
		model.addAttribute("paging", new PageDTO(cri, service.getTotalCount(cri)));
	}
	
	@GetMapping("/gridList")
	public void gridList(Model model, Criteria cri) {
		log.info("grid list..." + cri);
		//cri.setPageNum(1);
		//cri.setPageNum(3);
		log.info("페이지>>"+cri.getPageNum());
		cri.setAmount(12);
				
		model.addAttribute("EventList", service.getListwithPaging(cri));
		model.addAttribute("paging", new PageDTO(cri, service.getTotalCount(cri)));
	}
	
	@GetMapping("/pastEventList")
	public void pastEventList(Model model, Criteria cri) {
		log.info("list..." + cri);
		//cri.setPageNum(1);
		//cri.setAmount(2);
				
		model.addAttribute("EventList", service.getListPastEvent(cri));
		model.addAttribute("paging", new PageDTO(cri, service.getTotalCountPastEvent(cri)));
	}
	
	@GetMapping({"/write", "/gridWrite"})
	@PreAuthorize("isAuthenticated()")
	public void write() {
		log.info("insert...");
		
		String tempFile = "C:\\upload\\temp";
		File tempFilePath = new File(tempFile);
		if(!tempFilePath.exists()) {
			System.out.println(tempFilePath+"가 없으므로 생성합니다.");
			tempFilePath.mkdirs();
		}
		
	}
	
	@PostMapping("/write")
	@PreAuthorize("isAuthenticated()")
	public String write(BoardEventVO board, RedirectAttributes rttr, HttpServletRequest request){	
		log.info("write controller");

		log.info("register post : " + board);
		if(board.getAttachList()!=null) {
			board.getAttachList().forEach(attach->log.info(attach));
		}

		service.insert(board);
		rttr.addFlashAttribute("result", board.getBoardNum());	
		
		return "redirect:/boardEvent/list";
	}
	
	@PostMapping("/gridWrite")
	@PreAuthorize("isAuthenticated()")
	public String gridWrite(BoardEventVO board, RedirectAttributes rttr, HttpServletRequest request){	
		log.info("write controller");

		log.info("register post : " + board);
		if(board.getAttachList()!=null) {
			board.getAttachList().forEach(attach->log.info(attach));
		}

		service.insert(board);
		rttr.addFlashAttribute("result", board.getBoardNum());	
		
		return "redirect:/boardEvent/gridList";
	}

	@GetMapping({"/view", "/gridView", "/update", "/gridUpdate"})
	public void get(@RequestParam("boardNum") int boardNum, @ModelAttribute("cri") Criteria cri , Model model) {
		log.info("get/modify...");
		log.info(boardNum);
		BoardEventVO eventVO = service.get(boardNum);
		model.addAttribute("board", eventVO);
		
	}
	
	@PostMapping("/delete")
	public String remove(int boardNum, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove : " + boardNum);
		List<BoardEventFileVO> attachList = service.getFileList(boardNum);
		
		if(service.delete(boardNum)) {
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/boardEvent/list" + cri.getListLink();
	}
	
	@PostMapping("/gridDelete")
	public String gridRemove(int boardNum, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove : " + boardNum);
		List<BoardEventFileVO> attachList = service.getFileList(boardNum);
		
		if(service.delete(boardNum)) {
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/boardEvent/gridList" + cri.getListLink();
	}
	
	@PostMapping("/update")
	public String modify(BoardEventVO vo, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("update...");
		
		if(service.update(vo)) {
			rttr.addFlashAttribute("result", "success");
		}
			
		return "redirect:/boardEvent/list" + cri.getListLink();
	}
	
	@PostMapping("/gridUpdate")
	public String gridModify(BoardEventVO vo, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("update...");
		
		if(service.update(vo)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/boardEvent/gridList" + cri.getListLink();
	}
	
	
	@GetMapping(value = "/getAttachList", produces =MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardEventFileVO>> getAttachList(int boardNum){
		log.info("getAttachList : " + boardNum);
		List<BoardEventFileVO> fileList = service.getFileList(boardNum);
		for(BoardEventFileVO vo : fileList) {
			log.info(vo);
		}
		return new ResponseEntity<>(service.getFileList(boardNum), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getRepImgList", produces =MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
//	public ResponseEntity<List<BoardEventFileVO>> getRepImgList(){
		public ResponseEntity<List<BoardEventVO>> getRepImgList(){
		log.info("getRepImg... " );
//		List<BoardEventFileVO> fileList = service.getRepImgList();
//		for(BoardEventFileVO vo : fileList) {
//			log.info(vo);
//		}
//		return new ResponseEntity<>(service.getRepImgList(), HttpStatus.OK);
		
		Criteria cri = new Criteria();
		cri.setAmount(8);
		
		return new ResponseEntity<>(service.getListwithPaging(cri), HttpStatus.OK);
	}
	
	private void deleteFiles(List<BoardEventFileVO> attachList) {
		if(attachList==null||attachList.size()==0) {
			return ;
		}
		log.info("delete attach files...");
		log.info(attachList);
		
		attachList.forEach(attach->{
			try {
				Path file = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\" 
							+ attach.getUuid() + "_" + attach.getFileName());
				Files.deleteIfExists(file);
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\s_" 
							+ attach.getUuid() + "_" + attach.getFileName());
					Files.delete(thumbNail);
				}
			} catch (Exception e) {
				log.error("error: " + e.getMessage());
			}
		});
	}
	
}
