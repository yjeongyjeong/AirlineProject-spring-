package com.airline.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.service.AdminService;
import com.airline.vo.BoardEventVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.CancelVO;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.PageDTO;
import com.airline.vo.UserPayVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
	
	@Autowired
	private AdminService admin;
	
	//회원정보
	@GetMapping("/userInfo")
	public void userInfo(Model model, Criteria cri) {
		List<KakaoUserVO> vo = admin.getUserInfo(cri);
		System.out.println("pagenum>>"+cri.getPageNum());
		System.out.println("vo>>>"+vo);
		model.addAttribute("vo", vo);
		model.addAttribute("paging",new PageDTO(cri, admin.getUserCnt(cri)));
		
	}
	//휴먼회원 전환
	@PostMapping(value="/userInfo",produces = MediaType.APPLICATION_JSON_VALUE)
	public void userDelete(@RequestParam("userid")String userid, @RequestParam("enabled")int enabled) {
		System.out.println("userid : "+userid);
		System.out.println("enabled : "+enabled);
		//enabled = 0 -> 휴먼해제 1->휴먼전환
		if(enabled == 0)admin.updateUserInfo1(userid); 
		if(enabled == 1)admin.updateUserInfo0(userid); 
		
	}
	
	
	//티켓취소
	@GetMapping("/cancelTicket")
	public void cancelTicket(Model model, Criteria cri) {
		//전체 데이터 가져오기
		List<CancelVO> vo = admin.getCancelList(cri);
		System.out.println("admin>>>>"+vo);
		model.addAttribute("vo",vo);
		//페이징
		model.addAttribute("paging",new PageDTO(cri,admin.getCancelCtn(cri)));
		//버튼 클릭 시 가격 및 마일리지 취소처리 -> ajax

		
	}
	
	@PostMapping(value = "cancelTicket",produces = MediaType.APPLICATION_JSON_VALUE)
	public void cancelTicekt(@RequestParam("resno")String resno, @RequestParam("userid")String userid) {
		System.out.println("resno : "+resno);
		System.out.println("userid : "+userid);
		//예약정보 불러오기
		UserPayVO pvo = admin.getPayInfo(resno);
		int price  = -pvo.getPrice();
		int mileage = -pvo.getMileage();
		
		//가격, 마일리지 취소처리
		int result = admin.updatePrice(resno,price, mileage);
		//로그 취소처리(insert)
		if(result == 1) {
			int sumMile  = admin.sumMile(userid);
			int sumPrice  = admin.sumPrice(userid);
			admin.insertlog(userid, sumMile, sumPrice);
			//cancel 테이블 취소여부 update
			admin.updateCancel(resno);
			//등급계산 다시해야함
		}
		
	}
	
	//항공권 구입 현황
	@GetMapping("/buyTicket")
	public void buyTicket(Model model, Criteria cri) {
		List<FlightResVO> vo = admin.getResInfo(cri);
		model.addAttribute("vo",vo);
		model.addAttribute("paging",new PageDTO(cri, admin.getTicketCnt(cri)));
		
	}
	
	//공지사항 
	@GetMapping("/notice")
	public void notice(Model model, Criteria cri) {
		List<BoardNoticeVO> vo = admin.getNotice(cri);
		model.addAttribute("vo",vo);
		model.addAttribute("paging",new PageDTO(cri, admin.noticeCnt(cri)));
	}
	
	@PostMapping(value = "notice",produces = MediaType.APPLICATION_JSON_VALUE)
	public void noticePost(@RequestParam("boardnum")int boardnum) {
		System.out.println("boardnum>>"+boardnum);
		int delete = admin.deleteNotice(boardnum);
		
	}
	
	//이벤트 게시판
	@GetMapping("/event")
	public void event(Model model,Criteria cri) {
		List<BoardEventVO> vo = admin.getEvent(cri);
		model.addAttribute("vo",vo);
		model.addAttribute("paging",new PageDTO(cri, admin.eventCtn(cri)));
	}
	
	//항공스케줄 조회 및 업데이트
	@GetMapping("/flightSchedule")
	public void flightSchedule(Model model, Criteria cri) {
		List<FlightVO> vo = admin.getFlightList(cri);
		model.addAttribute("vo",vo);
		model.addAttribute("paging",new PageDTO(cri, admin.getFlightListCnt(cri)));
	}
	
	//항공스케줄 인서트 뷰 페이지
	@GetMapping("/flightCreate")
	public void flightCreate(Model model) {
		List<String> depCode = admin.getDepcode();
		System.out.println("depCode>>>>"+depCode);
		model.addAttribute("depCode",depCode);
		List<String> arrCode = admin.getArrcode();
		model.addAttribute("arrCode",arrCode);
		List<Integer> dRCode = admin.getdRCode();
		List<Integer> aRCode = admin.getaRCode();
		model.addAttribute("dRCode",dRCode);
		model.addAttribute("aRCode",aRCode);
		/*
		 * String arrCode = admin.arrDepcode(); model.addAttribute("depCode",depCode);
		 */
		
	}
	
	@PostMapping(value="/flightCreate",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String postFlightSchedule(@RequestBody FlightVO vo, RedirectAttributes rttr) {
		System.out.println("vo>>>"+vo);
		String depDay = vo.getFullDeptime().substring(0, 11)+"00:00:00";
		String arrDay = vo.getFullArrtime().substring(0, 11)+"00:00:00";
		System.out.println(depDay+" : "+arrDay);
		String depTime = vo.getFullDeptime().substring(11);
		String arrTime = vo.getFullArrtime().substring(11);
		System.out.println(depTime+" : "+arrTime);
		String fullDeparture = admin.getFullDeparture(vo.getDepCode());
		String fullArrival = admin.getFullArrival(vo.getArrCode());
		System.out.println(fullDeparture+" : "+fullArrival);
		int depRegionCode = admin.getRegionCode(vo.getDepName());
		int arrRegionCode = admin.getArrRegionCode(vo.getArrName());
		System.out.println(depRegionCode+" : "+arrRegionCode);
		//인서트
		vo.setDepDay(depDay);
		vo.setArrDay(arrDay);
		vo.setDepTime(depTime);
		vo.setArrTime(arrTime);
		vo.setFullDeparture(fullDeparture);
		vo.setFullArrival(fullArrival);
		vo.setDepRegionCode(depRegionCode);
		vo.setArrRegionCode(arrRegionCode);
		System.out.println("vo출력>>"+vo);
		int result = admin.insertFlight(vo);
		if(result == 1) rttr.addFlashAttribute(result);
		return "redirect:/admin/flightSchedule";
	}
	
	//항공스케줄 수정 뷰 페이지
	@GetMapping("/flightModify")
	public void flightModify(Model model,@Param("fno")int fno) {
		System.out.println("fno>>"+fno);
		FlightVO vo = admin.getFlightInfo(fno);
		System.out.println("vo>>>"+vo);
		model.addAttribute("vo",vo);

		
	}
	
	@PostMapping(value="/flightModify",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String postflightModify(@RequestBody FlightVO vo, RedirectAttributes rttr) {
		System.out.println("modify vo>>>"+vo);
		int isdelete = vo.getIsDelete();
		System.out.println("delete>>"+isdelete);
		String depDay = vo.getFullDeptime().substring(0, 11)+"00:00:00";
		String arrDay = vo.getFullArrtime().substring(0, 11)+"00:00:00";
		System.out.println(depDay+" : "+arrDay);
		String depTime = vo.getFullDeptime().substring(11);
		String arrTime = vo.getFullArrtime().substring(11);
		System.out.println(depTime+" : "+arrTime);
		String fullDeparture = admin.getFullDeparture(vo.getDepCode());
		String fullArrival = admin.getFullArrival(vo.getArrCode());
		System.out.println(fullDeparture+" : "+fullArrival);
		System.out.println(vo.getIsDelete());
		//수정
		vo.setDepDay(depDay);
		vo.setArrDay(arrDay);
		vo.setDepTime(depTime);
		vo.setArrTime(arrTime);
		vo.setFullDeparture(fullDeparture);
		vo.setFullArrival(fullArrival);
		System.out.println("vo출력>>"+vo);
		int result = admin.modifyFlight(vo);
		if(result == 1) {
			//메인화면 공지 띄우기
			//공지관련 테이블 저장 후 home 에 모달띄우기
			int fResult = admin.insertFlightLog(vo);
			rttr.addFlashAttribute(result);
		}
		
		return "redirect:/admin/flightSchedule";
	}
	
	//항공스케줄 삭제 뷰 페이지
	/*
	 * @GetMapping("/flightDelete") public void flightDelete(Model
	 * model,@Param("fno")int fno) { System.out.println("fno>>"+fno); FlightVO vo =
	 * admin.getFlightInfo(fno); System.out.println("vo>>>"+vo);
	 * model.addAttribute("vo",vo);
	 * 
	 * 
	 * }
	 */
		
		@PostMapping(value="/flightDelete",consumes = MediaType.APPLICATION_JSON_VALUE)
		public String postflightDelete(@RequestBody FlightVO vo, RedirectAttributes rttr) {
			System.out.println("modify vo>>>"+vo);
			int isdelete = vo.getIsDelete();
			System.out.println("delete>>"+isdelete);

			 FlightVO flightInfo = admin.getFlightInfo(vo.getFno());
			 flightInfo.setReason(vo.getReason());
			 int fResult = admin.insertFlightLog(flightInfo);
			//삭제(운항테이블)+인서트(로그)
//			vo.setDepDay(depDay);
//			vo.setArrDay(arrDay);
//			vo.setDepTime(depTime);
//			vo.setArrTime(arrTime);
//			vo.setFullDeparture(fullDeparture);
//			vo.setFullArrival(fullArrival);
//			System.out.println("vo출력>>"+vo);
			int result = admin.deleteFlight(vo);
			if(result == 1) {
				//메인화면 공지 띄우기
				//공지관련 테이블 저장 후 home 에 모달띄우기
				
				rttr.addFlashAttribute(result);
			}
			
			return "redirect:/admin/flightSchedule";
		}

}
