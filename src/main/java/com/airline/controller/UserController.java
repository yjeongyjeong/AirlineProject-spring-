package com.airline.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.mapper.UserMapper;
import com.airline.service.UserService;
import com.airline.vo.BoardDiaryVO;
import com.airline.vo.BoardQnaVO;
import com.airline.vo.CancelVO;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.GradeLogVO;
import com.airline.vo.PageDTO;
import com.airline.vo.PointVO;
import com.airline.vo.UserPayVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
//	private PayService pay;

	// 좌석 선택 후 결제창으로 넘어가기
//	@GetMapping("/flightRes")
//	public void flightRes(Model model,@Param("fno")int fno, @Param("seat")String seat) {
//		log.info("유저 결제창...");
//		//유저정보 가져오기
//		model.addAttribute("fno", fno);
//		model.addAttribute("seat",seat);
//	}

	//포인트 충전 view단
	@GetMapping("/chargePoint")
	public void chargePoint() {

	}
	
	//포인트 충전을 위한 url 받음
	@GetMapping("/chargePoint2")
	public void chargePoint2(int amount) {
		System.out.println("amount : " + amount);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			int result = service.chargePoint(userid, amount);
		}

	}
	
	//포인트 충전 상세조회
	@GetMapping("/kakaoDetail")
	public void kakaoDetail(Model model,Criteria cri) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			List<PointVO> vo = service.getKPoint(userid, cri);
			int sumK = service.getSumK(userid);
			model.addAttribute("vo",vo);
			model.addAttribute("sumK",sumK);
			System.out.println( new PageDTO(cri, service.getKTotal(userid, cri)));
			model.addAttribute("paging", new PageDTO(cri, service.getKTotal(userid, cri)));
		}
	}
	
	//마일리지 적립,사용 상세조회
	@GetMapping("/mileage")
	public void mileage(Model model, Criteria cri) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			List<UserPayVO> vo = service.getMPoint(userid, cri);
			System.out.println("vo : "+vo);
			int sumP= service.getSumP(userid);
			model.addAttribute("vo",vo);
			model.addAttribute("sumP",sumP);
			PageDTO dto = new PageDTO(cri, service.getMTotal(userid, cri));
			model.addAttribute("paging", new PageDTO(cri,  service.getMTotal(userid, cri)));
			System.out.println("dto>>>"+dto);
		}
	}
	
	//유저별 항공예약 상세조회
	@GetMapping("/userResDetail")
	public void userResDetail(Model model,Criteria cri) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			List<FlightResVO> vo = service.getUserRes(userid, cri);
			System.out.println(vo);
			//항공구매횟수, 총 구매금액
			int count= service.getCountBuy(userid);
			int totalPrice= service.getTotal(userid);
			model.addAttribute("vo",vo);
			model.addAttribute("count",count);
			model.addAttribute("totalPrice",totalPrice);
			model.addAttribute("paging", new PageDTO(cri, service.getFlightTotal(userid, cri)));
			System.out.println("test>>>>>"+new PageDTO(cri, service.getFlightTotal(userid, cri)));
		}
	}
	
	//항공취소
	@PostMapping(value="/userResDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	public void userResDetailPost(@RequestParam("resno")String data) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			System.out.println("data : "+data);
			FlightResVO resvo = mapper.findResByResNo(data);
			String userId = resvo.getUserid();
			mapper.insertCancel(userId,data);
			//항공취소 처리
			int result = service.cancelTicket(data);
		}
	}
	
	//체크인 처리
	@PostMapping(value="/userResDetail2", produces = MediaType.APPLICATION_JSON_VALUE)
	public void userResDetailPost2(@RequestParam("resno")String data) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			System.out.println("data : "+data);
			//체크인 처리
			int resultCheckin = service.checkin(data);
		}
	}
	
	//여행일기 상세조회
	@GetMapping("/diary")
	public void userDiary(Model model,Criteria cri) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			String username = service.getName(userid);
			List<BoardDiaryVO> vo = service.getUserDiary(username, cri);
			model.addAttribute("vo",vo);
			model.addAttribute("paging", new PageDTO(cri, service.getUserDiaryCnt(username, cri)));

		}
	}
	
	//문의게시판
	@GetMapping("/qna")
	public void useqna(Model model, Criteria cri) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			String username = service.getName(userid);
			List<BoardQnaVO> vo = service.getUserQna(username, cri);
			model.addAttribute("vo",vo);
			model.addAttribute("paging", new PageDTO(cri, service.getUserQnaCnt(username, cri)));

		}
	}
	

	//등급조회
	@GetMapping("/searchGrade")
	public void searchGrade(Model model, Criteria cri) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			model.addAttribute("userid",userid);
			List<GradeLogVO> vo = service.getGradeLog(userid,cri);
			model.addAttribute("vo",vo);
			model.addAttribute("paging", new PageDTO(cri, service.getGradeLogCnt(userid, cri)));

		}
		
	}
	
	//마이페이지 이동
  	@GetMapping("/myPage")
	public void myPage(Model model, HttpSession session) {
		KakaoUserVO vo = (KakaoUserVO) session.getAttribute("loginUser");
		model.addAttribute("userInfo", vo);
		log.info("마이페이지 겟컨트롤러 >> "+vo);
	}
  
  	//마이페이지 수정
	@GetMapping("/myInfoModify")
	public void myInfoModify(Model model, HttpSession session) {
		KakaoUserVO vo = (KakaoUserVO) session.getAttribute("loginUser");
		model.addAttribute("userInfo", vo);
		//010-0000-0000
		String phone_first = vo.getPhone().substring(0, 3);		
		String phone_middle = vo.getPhone().substring(4, 8);		
		String phone_last = vo.getPhone().substring(9, 13);		
		model.addAttribute("phone_first" , phone_first);
		model.addAttribute("phone_middle" , phone_middle);
		model.addAttribute("phone_last" , phone_last);
		
		String[] mail = vo.getMail().split("@");
		String email = mail[0];
		String mail_Domain = mail[1];
		model.addAttribute("email", email);
		model.addAttribute("mail_Domain", mail_Domain);
		
	}
	
	@PostMapping("/myInfoModify")
	public String myInfoModify(HttpSession session, String userId, String userNick,
			String userNameK, String userNameE, 
			String phone_first, String phone_middle, String phone_last, 
			int postCode, String addressDefault, String addressDetail) {
		// email phone address 합쳐줘야해서.. parameter로 받음....

		String phone = phone_first + "-" + phone_middle + "-" + phone_last;
		//String mail = email + "@" + mail_Domain;
		String address = addressDefault + addressDetail;
		userNameE = userNameE.toUpperCase(); 
		
		log.info("myinfomodify post controller >> "+userId);
		//log.info(userNick);
		log.info(userNameK);
		log.info(userNameE);
		log.info(phone);
		log.info(postCode);
		log.info(address);
		
		service.modifyUserInfo(userId, userNameK, userNameE, phone, postCode, address);
		//service.modifyUserNick(userId, userNick);
		KakaoUserVO vo = service.getUserInfo(userId);
		session.setAttribute("loginUser", vo);
		log.info("modified loginUser >> "+vo);
		
		return "redirect:/user/myPage";
	}
	
	@GetMapping("/myPwdModify") //mypage에서 get으로 링크타고 이동해서 다시 session가져옴..
	public void myPwdModify(Model model, HttpSession session) {
		KakaoUserVO vo = (KakaoUserVO) session.getAttribute("loginUser");
		model.addAttribute("userInfo", vo);
	}

	@PostMapping("/myPwdModify") //mypage에서 get으로 링크타고 이동해서 다시 session가져옴..
	public String myPwdModify(String userId, String pwd, RedirectAttributes attr) {
		pwd = passwordEncoder.encode(pwd);
		service.modifyUserPwd(userId, pwd);
		return "redirect:/user";
	}
	@GetMapping("/myNickModify")
	public void myNickModify(Model model, HttpSession session) {
		KakaoUserVO vo = (KakaoUserVO) session.getAttribute("loginUser");
		model.addAttribute("userInfo", vo);
	}
	@PostMapping("/myNickModify")
	public String myNickModify(String userId, String userNick, HttpSession session) {
		service.modifyUserNick(userId, userNick);
		KakaoUserVO vo = service.getUserInfo(userId);
		session.setAttribute("loginUser", vo);
		log.info("modified loginUser >> "+vo);
		return "redirect:/user";
	}
	
}
