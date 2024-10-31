package com.airline.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.service.AdminService;
import com.airline.service.BoardEventService;
import com.airline.service.BoardNoticeService;
import com.airline.service.BoardQnaService;
import com.airline.service.FlightService;
import com.airline.service.UserService;
import com.airline.vo.BoardDiaryVO;
import com.airline.vo.BoardEventVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.BoardQnaVO;
import com.airline.vo.CancelVO;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.PointVO;
import com.airline.vo.UserPayVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@Log4j
public class HomeController {
	
	@Autowired
	private UserService user;
	
	@Autowired
	private BoardEventService eventService;	
	
	@Autowired
	private BoardQnaService qnaService;
	
	@Autowired
	private FlightService flightService;
		
    @Autowired
 	private BoardNoticeService noticeService;
    
    @Autowired
 	private AdminService adminService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;
    
	
	//메인화면
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, Criteria cri) {
		
		List<BoardNoticeVO> emer = noticeService.noticePopup(cri);
		if(emer != null) {
			model.addAttribute("emer", noticeService.noticePopup(cri));
		}
		FlightVO popupVO = adminService.flightNoticePopup();
		if(popupVO != null) {
			model.addAttribute("modi", popupVO);
			model.addAttribute("info", adminService.getFlightInfo(popupVO.getFno()));			
		}
    
		//이벤트 슬라이더용 8개만 출력.
		Criteria criEvent = new Criteria();
		criEvent.setAmount(8);
		List<BoardEventVO> EventImglist = eventService.getListwithPaging(criEvent);
		model.addAttribute("EventList", EventImglist);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");         
		Date now = new Date();         
		String today = sdf.format(now);
		model.addAttribute("today", today);

		LocalDate date = LocalDate.now();
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String fdate = date.format(fm);
		
		model.addAttribute("nextWeek", date.plusWeeks(1));
		
		//국내선
		model.addAttribute("GMPtoCJU", flightService.getRoundTripPrice("김포", "제주"));
		model.addAttribute("KWJtoCJU", flightService.getRoundTripPrice("광주", "제주"));
		model.addAttribute("GMPtoRSU", flightService.getRoundTripPrice("김포", "여수"));
		
		//동북아
		model.addAttribute("ICNtoKIX", flightService.getRoundTripPrice("인천", "오사카"));
		model.addAttribute("ICNtoFUK", flightService.getRoundTripPrice("인천", "후쿠오카"));
		model.addAttribute("ICNtoTPE", flightService.getRoundTripPrice("인천", "타이베이"));
		model.addAttribute("ICNtoHKG", flightService.getRoundTripPrice("인천", "홍콩"));

		//동남아/서남아
		model.addAttribute("ICNtoSGN", flightService.getRoundTripPrice("인천", "호찌민"));
		model.addAttribute("ICNtoMNL", flightService.getRoundTripPrice("인천", "마닐라"));
		model.addAttribute("ICNtoSIN", flightService.getRoundTripPrice("인천", "싱가포르"));
		model.addAttribute("ICNtoBKK", flightService.getRoundTripPrice("인천", "방콕"));
		
		//중앙아시아
		model.addAttribute("ICNtoUBN", flightService.getRoundTripPrice("인천", "울란바타르"));
		model.addAttribute("ICNtoALA", flightService.getRoundTripPrice("인천", "알마티"));
		model.addAttribute("ICNtoTAS", flightService.getRoundTripPrice("인천", "타슈켄트"));

		//유럽
		model.addAttribute("ICNtoFRA", flightService.getRoundTripPrice("인천", "프랑크푸르트"));
		model.addAttribute("ICNtoLHR", flightService.getRoundTripPrice("인천", "런던히드로"));
		model.addAttribute("ICNtoCDG", flightService.getRoundTripPrice("인천", "파리"));
		model.addAttribute("ICNtoFCO", flightService.getRoundTripPrice("인천", "로마"));
		
		//미주
		model.addAttribute("ICNtoLAX", flightService.getRoundTripPrice("인천", "로스앤젤레스"));
		model.addAttribute("ICNtoJFK", flightService.getRoundTripPrice("인천", "뉴욕"));
		model.addAttribute("ICNtoSFO", flightService.getRoundTripPrice("인천", "샌프란시스코"));
		model.addAttribute("ICNtoHNL", flightService.getRoundTripPrice("인천", "호놀룰루"));

		//대양주
		model.addAttribute("ICNtoSYD", flightService.getRoundTripPrice("인천", "시드니"));
		model.addAttribute("ICNtoSPN", flightService.getRoundTripPrice("인천", "사이판"));

		//공지사항 3개 출력
		Criteria nCri = new Criteria();
		nCri.setAmount(3);
		model.addAttribute("noticeBoard", noticeService.getPageList(nCri));
		
		
		return "home";
	}
	
	@GetMapping("/login")
	@PreAuthorize("isAnonymous()") 
	public void login(String error, String logout, Model model, HttpServletRequest request, HttpSession session) {
		log.info("error>>"+error);
		log.info("logout>>"+logout);
		log.info("login page");
		
		if(error != null) {
			model.addAttribute("error","아이디 또는 비밀번호를 잘못 입력하였습니다.");
		}
		
		if(model != null) {
			model.addAttribute("logout", "logout");
		}
		
		//이전 페이지로 되돌아가기 위한 Referer 헤더값을 세션의 prevPage attribute로 저장 
	    String uri = request.getHeader("Referer");
	    if (uri != null && !uri.contains("/login")) {
	        request.getSession().setAttribute("prevPage", uri);
	    }

		
	}
		
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		log.info("logout..post");
		
		String access_token = (String) session.getAttribute("access_token");

		//if(access_token != null)
		Map<String, String> map = new HashMap<String, String>();
		map.put("Authorization", "Bearer " + access_token);
		
		
	    try {
	        log.info("logout..post");
	        request.getSession();
	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                cookie.setMaxAge(0);
	                response.addCookie(cookie);
	            }
	        }
	        return ResponseEntity.ok("Logout success");
	    } catch (Exception e) {
	        log.error("Logout failed", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
	    }
	}
	
	//마이페이지(유저)
	@GetMapping("/user")
	public void userPage(Model model) {
		log.info("user page");
		//유저정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
			String userid = userDetails.getUsername();

			KakaoUserVO vo = user.getUserInfo(userid);
			//등급조회
			String getGrade = user.getGrade(vo.getGradeCode());
			model.addAttribute("vo",vo);
			model.addAttribute("grade",getGrade);
			//마일리지 가져오기
			//UserPayVO pvo = user.getPoint(userid); //마일리지 내역
			int sumP = user.getSumP(userid);
			model.addAttribute("sumP",sumP);
			//model.addAttribute("pvo",pvo);
			//마일리지 내역 최근 3개 가져오기
			List<UserPayVO> pvo3 = user.getPoint3(userid);
			model.addAttribute("pvo3",pvo3);
			//카카오페이 가져오기
			int sumK = user.getSumK(userid);
			model.addAttribute("sumK",sumK);
			//카카오페이 최근내역 3개
			List<PointVO> kvo3 = user.getKakao3(userid);
			model.addAttribute("kvo3",kvo3);
			//예약내역 3개
			List<FlightResVO> rvo3 = user.getFlight3(userid);
			model.addAttribute("rvo3",rvo3);
			//여행일기 게시글 최근3개
			List<BoardDiaryVO> dvo = user.getDiary(userid);
			model.addAttribute("dvo",dvo);
			//문의게시글 최근3개
			String username = user.getName(userid);
			List<BoardQnaVO> qvo = user.getQna(username);
			model.addAttribute("qvo",qvo);
			 
			
		}
		
	}
	

	
	//마이페이지(관리자)
	@GetMapping(value="/admin", produces = MediaType.APPLICATION_JSON_VALUE)
	public void adminPage(Model model)throws Exception {
		log.info("admin page");
		//유저정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
			String userid = userDetails.getUsername();
			//회원정보 조회
		}
		//회원정보 가져오기
		List<KakaoUserVO> vo = user.getUserInfoAll();
		model.addAttribute("vo",vo);
		//매출현황(카카오페이+항공결제내역 월별/년도별)
		//일별 항공매출현황
        List<UserPayVO> pvo = user.getSale();
       // System.out.println("pvo>> "+pvo);
	    // 데이터를 JSON 문자열로 변환하여 모델에 추가
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        String json = objectMapper.writeValueAsString(pvo);
	        model.addAttribute("json", json);
	        //System.out.println("json>> "+json);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace(); // 또는 예외 처리 로직 추가
	    }
		
	    System.out.println(pvo);
        model.addAttribute("pvo", pvo);
        //취소요청
        List<CancelVO> cvo = user.reqCancel();
        model.addAttribute("cvo",cvo);
        //항공권 구매/예약 현황
        List<FlightResVO> fvo = user.getFlightres();
        System.out.println(fvo);
        model.addAttribute("fvo",fvo);
        //공지사항
        List<BoardNoticeVO> nvo = user.getNotice();
        model.addAttribute("nvo",nvo);
        //이벤트 게시판
        List<BoardEventVO> evo = user.getEvent();
        model.addAttribute("evo",evo);
        //항공운항내역3
        List<FlightVO> avo = user.getFlightList3();
        model.addAttribute("avo",avo);
        

		
	}
	

	
	@GetMapping("/contact")
	public void contact() {
		
	}
	

	@GetMapping("/memberGrade")
	public void grade(Model model) {
		//유저정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
			String userid = userDetails.getUsername();

			KakaoUserVO vo = user.getUserInfo(userid);
			//등급조회
			String getGrade = user.getGrade(vo.getGradeCode());
			model.addAttribute("vo",vo);
			model.addAttribute("grade",getGrade);
			//마일리지 가져오기
			//UserPayVO pvo = user.getPoint(userid); //마일리지 내역
			int mile = user.getMileage(userid);
			model.addAttribute("mile", mile);
		}
	}
	
	@GetMapping("/priceInform")
	public void priceInform() {
	}
	
	@GetMapping("/error/accessError")
	@CrossOrigin("http://localhost:8081/error/accessError")
	public String accessError(RedirectAttributes attr, HttpSession session) {
		if(session.getAttribute("loginUser") != null)
			return "redirect:/";
		return "/error/accessError";
	}

	
}
