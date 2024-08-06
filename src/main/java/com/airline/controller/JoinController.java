package com.airline.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.mail.MailHandler;
import com.airline.mail.TempKey;
import com.airline.security.CustomUser;
import com.airline.service.JoinService;
import com.airline.service.MailSendService;
import com.airline.service.UserService;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.TermsVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
@RequestMapping("/join/*")
public class JoinController {
	@Autowired
	private JoinService join;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@GetMapping("/joinTerms")
	public void joinTermsGet(Model model) {
		TermsVO terms1 = join.getTerms(1);
		TermsVO terms2 = join.getTerms(2);
		TermsVO terms3 = join.getTerms(3);
		TermsVO terms4 = join.getTerms(4);

		model.addAttribute("terms1", terms1);
		model.addAttribute("terms2", terms2);
		model.addAttribute("terms3", terms3);
		model.addAttribute("terms4", terms4);

		log.info("JoinController >> joinTerms [get]");
	}

	@PostMapping("/joinTerms")
	public String joinTerms(Model model, String terms) {
		log.info("terms 이름에 담아온 값 >> " + terms);
		log.info("JoinController >> joinTerms [post]");

		model.addAttribute("termsAgree", terms);

		return "/join/checkMember";
	}

	@GetMapping("/findId")
	public void findId() {
		log.info("JoinController >> findId");
	}

	@PostMapping("/findId") // 여유가 있다면.. 랜덤키생성/메일보내는 메서드를 따로 뺄까 생각중...
	public String findId(String email, Model model, RedirectAttributes attr) {
		String result = join.confirmEmail(email);
		model.addAttribute("email", result); // 필요한가

		log.info("email >> " + email);
		log.info("result >> " + result);

		if (result == null) {
			model.addAttribute("joinMessage", "입력하신 정보를 다시 확인해주시기 바랍니다.");
			return "/join/findId";
		} else {
			try {
				String mail_key = new TempKey().getKey(); // 랜덤키 생성

				Map<String, String> params = new HashMap<String, String>();
				params.put("email", email);
				params.put("mail_key", mail_key);

				mailSendService.modifyMailKey(params); // email을 기준으로 컬럼에 랜덤키 저장
				log.info("입력받은 이메일 >> " + email + "생성된 key >> " + mail_key);

				MailHandler sendMail = new MailHandler(mailSender);
				sendMail.setSubject("카카오 항공 인증 메일입니다.");
				sendMail.setText("<h3>카카오 항공을 찾아주셔서 감사합니다.</h3>" + "<br>아래 확인 버튼을 눌러서 인증을 완료해 주시기 바랍니다."
						+ "<br><br><a href='http://localhost:8081/join/getUserId" + "/" + email + "/" + mail_key
						+ "' target='_blank'>이메일 인증 확인</a>");
				sendMail.setFrom("systemlocal99@gmail.com", "카카오 항공");
				sendMail.setTo(email);
				sendMail.send();

				log.info("controller에서 아아디 찾기 메일 보냄 완료");

				return "redirect:/join/mailSended";

			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/error/accessError";
			}
		}

	}

	@GetMapping("/mailSended")
	public void mailSended() {
		log.info("JoinController >> mailSended");
	}

	@GetMapping("/getUserId/{email}/{mail_key}") // 근데 get이라서 전부 url에 노출됨..
	public String getUserId(@PathVariable("email") String email, @PathVariable("mail_key") String mail_key, Model model)
			throws Exception {
		log.info("JoinController >> getUserId");
		KakaoUserVO vo = join.showUserId(email, mail_key);
		mailSendService.removeMailKey(email);
		model.addAttribute("user", vo);
		return "/join/getUserId"; // 다시 클릭하면 아이디값이 나오지 않음 따라서 다른 페이지로 이동시키는것도 나쁘지 않을 듯..
	}

	@GetMapping("/findPwd")
	public void findPwd() {
		log.info("JoinController >> findPwd");
	}

	@PostMapping("/findPwd") // 여유가 있다면.. 랜덤키생성/메일보내는 메서드를 따로 뺄까 생각중...
	public String findPwd(String userId, String email, Model model, RedirectAttributes attr) {

		String result = join.confirmUserIdAndEmail(userId, email);

		log.info("userId >> " + userId);
		log.info("email >> " + email);
		log.info("result >> " + result);

		if (result == null) {
			model.addAttribute("joinMessage", "입력하신 정보를 다시 확인해주시기 바랍니다.");
			return "/join/findPwd";
		} else {
			try {
				String mail_key = new TempKey().getKey(); // 랜덤키 생성

				Map<String, String> params = new HashMap<String, String>();
				// params.put("userId", userId);
				params.put("email", email);
				params.put("mail_key", mail_key);

				mailSendService.modifyMailKey(params); // email을 기준으로 컬럼에 랜덤키 저장
				log.info("입력받은 아이디 >> " + userId + " 입력받은 이메일 >> " + email + " 생성된 key >> " + mail_key);

				MailHandler sendMail = new MailHandler(mailSender);
				sendMail.setSubject("카카오 항공 인증 메일입니다.");
				sendMail.setText("<h3>카카오 항공을 찾아주셔서 감사합니다.</h3>" + "<br>아래의 임시 비밀번호로 로그인 후 비밀번호 변경 부탁드립니다." + "<h3>"
						+ mail_key + "</h3>" + "<br><br><a href='http://localhost:8081/login"
						+ "' target='_blank'>로그인</a>");
				sendMail.setFrom("systemlocal99@gmail.com", "카카오 항공");
				sendMail.setTo(email);
				sendMail.send();

				log.info("controller에서 아아디 찾기 메일 보냄 완료");

				log.info("raw mail_key >> " + mail_key);
				// password 암호화...;
				mail_key = passwordEncoder.encode(mail_key);
				log.info("encoded password >> " + mail_key);

				join.modifyPwdByMailKey(userId, mail_key);

				return "redirect:/join/mailSended";

			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/error/accessError";
			}
		}

	}

	@GetMapping("/checkMember") // 약관동의 후 기존멤버 체크(아직 약관동의 저장, 유효성 구현하지 않음)
	public void checkMember(Model model) {
		log.info("JoinController >> checkMember [get]");
	}

	@PostMapping("/checkMember") // 약관동의 후 기존멤버 체크
	public String checkMember(Model model, KakaoUserVO vo, String termsAgree) {
		log.info("JoinController >> checkMember [post]");
		log.info(vo);

		model.addAttribute("userInfo", vo);
		model.addAttribute("termsAgree", termsAgree);

		KakaoUserVO result = join.confirmMember(vo);
		log.info(vo);
		if (result == null) {
			return "/join/memberInfo"; // 정보조회가 되지않으면 신규회원
		} else {
			model.addAttribute("joinMessage", "이미 가입된 회원입니다.");
			return "/login"; // uri가 http://localhost:8081/join/checkMember인채로 이동함(post라서..)
		}

	}

	@PostMapping("/userIdDuplicateCheck")
	@ResponseBody
	public int userIdDuplicateCheck(@RequestParam("userId") String userId) {
		// ajax 아이디 체크
		int userIdCnt = join.userIdDuplicateCheck(userId);
		return userIdCnt;
	}

	@PostMapping("/userNickDuplicateCheck")
	@ResponseBody
	public int userNickDuplicateCheck(@RequestParam("userNick") String userNick) {
		// ajax 아이디 체크
		int userNickCnt = join.userNickDuplicateCheck(userNick);
		return userNickCnt;
	}

	@PostMapping("/userPwdCheck")
	@ResponseBody
	public int userPwdDuplicateCheck(@RequestParam("pwd") String pwd, @RequestParam("pwd_check") String pwd_check) {
		// ajax 비밀번호 일치 체크
		int userPwdCnt = -1;
		log.info("pwd >> " + pwd);
		log.info("pwd_check >> " + pwd_check);
		// select count(pwd) from kakaouser where pwd = #{pwd_check} => DB에 입력받는 pwd가
		// 없으니 당연함..
		// 1이 안나옴..
		if (pwd.equals(pwd_check)) { // 입력값과 equals로 직접비교
			userPwdCnt = 1;
			log.info("result userPwdCnt >> " + userPwdCnt);
		} else {
			userPwdCnt = 0;
			log.info("result userPwdCnt >> " + userPwdCnt);
		}
		return userPwdCnt;
	}

	@GetMapping("/memberInfo") // 약관동의 후 기존멤버 체크
	public void memberInfoGet(Model model, KakaoUserVO vo) {
		model.addAttribute("userInfo", vo);
		log.info("JoinController >>  [get]");

	}

	@PostMapping("/memberInfo")
	public String memberInfo(RedirectAttributes attr, String termsAgree, String userId, String userNick,
			String userNameK, String userNameE, String gender, String pwd, int userReginumFirst, int userReginumLast,
			String phone_first, String phone_middle, String phone_last, String email, String mail_Domain, int postCode,
			String addressDefault, String addressDetail, Model model) {

		// email phone address 합쳐줘야해서.. parameter로 받음....
		// memberInfo에 바로 접근하는 경우 접근 막음
		if (termsAgree == "" || termsAgree == null) {
			log.info("termsAgree >> " + termsAgree);
			model.addAttribute("joinMessage", "잘못된 접근입니다.");
			return "/login";
		}

		userNameE = userNameE.toUpperCase();
		String phone = phone_first + "-" + phone_middle + "-" + phone_last;
		String address = addressDefault + addressDetail;
		String mail = email + "@" + mail_Domain;
		log.info("입력된 이메일 >> " + mail);

		log.info("raw password >> " + pwd);
		// password 암호화
		pwd = passwordEncoder.encode(pwd);
		log.info("encoded password >> " + pwd);

		String[] userTermsAgree = termsAgree.split(","); // selectall,selectall,selectall,terms4 이런식으로 저장되어 있음

		if (mail.contains(",")) {
			String[] splitMail = mail.split(",");
			mail = splitMail[0] + splitMail[1];
			log.info("splitMail >> " + mail);
		}
		if (join.confirmEmail(mail) != null) {
			model.addAttribute("joinMessage", "이미 가입된 회원입니다.");
			return "/login";
		}

		try {

			// String mail_key = new TempKey().getKey(); // 랜덤키 생성

			Map<String, String> params = new HashMap<String, String>();
			params.put("email", mail);
			// params.put("mail_key", mail_key);

//			mailSendService.updateMailKey(params); // email을 기준으로 컬럼에 랜덤키 저장
			log.info("메일 보내질 이메일 >> " + mail);

			MailHandler sendMail = new MailHandler(mailSender);
			sendMail.setSubject("카카오 항공 가입을 환영합니다.");
			sendMail.setText("<h3>카카오 항공을 찾아주셔서 감사합니다.</h3>" + "<br>언제나 회원님을 생각하는 카카오 항공이 되겠습니다." + "<br><br>");
			sendMail.setFrom("systemlocal99@gmail.com", "카카오 항공");
			sendMail.setTo(mail);
			sendMail.send();

			log.info("controller에서 가입완료 메일 보냄 완료");

			join.registerMember(userId, userNick, userNameK, userNameE, gender, pwd, userReginumFirst, userReginumLast,
					postCode, phone, mail, address);

			// userTermsAgree가 0 1 2 3으로 들어가서 3번째에 값이 있으면 전체동의, 3번째에 값이 없으면 기본동의 하려고하는데 에러남
			// -> length로 바꿈(선택약관 늘어나면 다시 고려해봐야함..)
			if (userTermsAgree.length == 4) {
				join.registerAllTerms(userId);
			} else {
				join.registerBasicTerms(userId);
			}

			join.registerAuthorityMEMBER(userId);
			join.registerUserlog(userId);
			join.registerGradelog(userId);
			join.registerUserPay(userId);
			join.registerPoint(userId);

			return "redirect:/join/joinSuccess";

		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/accessError";
		}

	}

	@GetMapping("/joinSuccess")
	public void joinSuccess() {

	}

	// 카카오 로그인 구현
	@GetMapping("/kakao")
	@CrossOrigin(origins = "http://localhost:8081/join/kakao")
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code, Model model,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) throws Throwable {
		System.out.println("kakao controller타는중~~~(join에서 get)");
		// 1번 카카오톡에 사용자 코드 받기(jsp의 a태그 href에 경로 있음)
		log.info("code:" + code);

		// 2번 받은 code를 iKakaoS.getAccessToken로 보냄 ###access_Token###로 찍어서 잘 나오면은 다음단계진행
		String access_Token = join.getAccessToken(code); // url에 있는 code를 넣어서 access_Token을 가져옴
		log.info("###access_Token#### : " + access_Token);
		// 위의 access_Token 받는 걸 확인한 후에 밑에 진행

		// 3번 받은 access_Token를 iKakaoS.getUserInfo로 보냄 userInfo받아옴
		HashMap<String, Object> userInfo = join.getUserInfo(access_Token);
		log.info("###nickname#### : " + userInfo.get("nickname"));
		log.info("###email#### : " + userInfo.get("email"));
		log.info("###name### : " + userInfo.get("name"));
		log.info("###gender### : " + userInfo.get("gender"));
		log.info("###age_range_needs_agreement### : " + userInfo.get("age_range_needs_agreement"));
		log.info("###age_range### : " + userInfo.get("age_range"));
		log.info("###birthday### : " + userInfo.get("birthday"));
		log.info("###phone_number### : " + userInfo.get("phone_number"));

		// userNameK와 mail로 DB를 조회하여 결과가 있으면 마이페이지(혹은 로그인 선택 전의 페이지)
		// 결과가 없으면 model에 정보를 담아서 추가입력정보 페이지 (kakaoMemberInfo)로 이동
		String email = (String) userInfo.get("email");

		String userNameK = (String) userInfo.get("name");
		String userId = (String) userInfo.get("email");
		KakaoUserVO vo = join.kakaoLoginCheck(userNameK, userId);

		log.info("vo 결과 >>> " + vo);

		if (vo == null) {

			String mail_key = new TempKey().getKey(); // 랜덤키 생성

			// 일단 model에 담아서 값을 이동시킴
			model.addAttribute("userNick", (String) userInfo.get("nickname"));
			model.addAttribute("mail", (String) userInfo.get("email"));
			model.addAttribute("userNameK", (String) userInfo.get("name"));
			model.addAttribute("gender", (String) userInfo.get("gender"));
			model.addAttribute("birthday", (String) userInfo.get("birthday"));
			model.addAttribute("phone", (String) userInfo.get("phone_number"));
			model.addAttribute("pwd", mail_key);
			return "/join/kakaoMemberInfo";

		} else if (vo.getEnabled() == 0) {
			String[] cookiesToKeep = { "maindiv_flight", "maindiv_notice", "Cookie_userId" };

			request.getSession();
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (!Arrays.asList(cookiesToKeep).contains(cookie.getName())) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
			log.info("카카오로그인 시 휴면계정 처리 >>> ");
			return "redirect:/join/checkEnabled";
		}

		else {
			// 사용자 정보로 Authentication 객체 생성
			List<SimpleGrantedAuthority> userAuthorities = join.getAuthorities(email);
			List<GrantedAuthority> authorities = new ArrayList<>(userAuthorities);

			CustomUser customuser = new CustomUser(vo);
			log.info("customuser >> " + customuser);

			// (Object principal, Object credentials, Collection<? extends GrantedAuthority>
			// authorities)
			Authentication authentication = new UsernamePasswordAuthenticationToken(customuser, null, authorities);
			log.info("authentication >> " + authentication);

			// SecurityContext에 설정
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// 세션에 사용자 정보 저장
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", vo);

			// 로그인 후의 페이지로 리다이렉트
			log.warn("login success [join controller]===========");
			List<String> roleNames = new ArrayList<>();
			authentication.getAuthorities().forEach(authority -> {
				roleNames.add(authority.getAuthority());
				log.info("authority >> " + authority);
				log.info("vo.getAuthority >> " + vo.getAuthority());
			});

			log.warn("role names : " + roleNames);

//			if (roleNames.contains("ROLE_ADMIN")) {
//			model.addAttribute("vo", vo);
//			return "redirect:/user";
//		}
//
//		if (roleNames.contains("ROLE_MEMBER")) {
//			model.addAttribute("vo", vo);
//			return "redirect:/user";
//		}

			// 로그인 성공 시 타겟url 으로 리다이렉트
			RequestCache requestCache = new HttpSessionRequestCache();
			RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

			SavedRequest savedRequest = requestCache.getRequest(request, response);
			String uri = "/";

			String prevPage = (String) request.getSession().getAttribute("prevPage");
			if (prevPage != null) {
				request.getSession().removeAttribute("prevPage");
			}

			if (savedRequest != null) {
				String targetUrl = savedRequest.getRedirectUrl();
				redirectStrategy.sendRedirect(request, response, targetUrl);
			} else if (prevPage != null && !prevPage.equals("")) {
				// 회원가입 - 로그인으로 넘어온 경우 "/"로 redirect
				if (prevPage.contains("/join")) {
					uri = "/";
					redirectStrategy.sendRedirect(request, response, uri);
				} else {
					uri = prevPage;
					redirectStrategy.sendRedirect(request, response, uri);
				}
			} else {
				redirectStrategy.sendRedirect(request, response, "/");
			}

			uri = request.getHeader("Referer");
			if (uri != null && !uri.contains("/login")) {
				request.getSession().setAttribute("prevPage", uri);
			}

			return uri;

		}
	}

	@PostMapping("/kakaoMemberInfo")
	public String kakaoMemberInfo(RedirectAttributes attr, String userId, String userNick, String userNameK,
			String userNameE, String gender_kakao, String pwd, int userReginumFirst, int userReginumLast,
			String phone_kakao, String mail, int postCode, String addressDefault, String addressDetail, Model model) {
		// ###gender### : female
		// ###phone_number### : +82 10-0000-0000 값 처리해야함....... vo말고 파라미터로 받아야함...
		log.info("기존의 phone >> " + phone_kakao);
		log.info("기존의 gender_kakao >> " + gender_kakao);

		String gender = gender_kakao;
		String phone = "0" + phone_kakao.substring(4);

		log.info("raw password >> " + pwd);
		// password 암호화
		pwd = passwordEncoder.encode(pwd);
		log.info("encoded password >> " + pwd);

		if (gender_kakao.equals("female")) {
			gender = "W";
		} else {
			gender = "M";
		}
		userNameE = userNameE.toUpperCase();

		log.info("가공된 phone >> " + phone);
		log.info("가공된 gender >> " + gender);

		KakaoUserVO vo = new KakaoUserVO();
		vo.setUserNameE(userNameE);
		vo.setUserNameK(userNameK);
		vo.setGender(gender);
		vo.setUserReginumFirst(userReginumFirst);
		vo.setUserReginumLast(userReginumLast);

		vo = join.confirmMember(vo);
		log.info("vo >> " + vo);

		if ((vo != null) || (join.confirmEmail(mail) != null)) { // 값이 반환된다면 기존멤버
			model.addAttribute("joinMessage", "이미 가입된 회원입니다.");
			return "/login"; // uri가 http://localhost:8081/join/checkMember인채로 이동함(post라서..)
		}

		try {

			// String mail_key = new TempKey().getKey(); // 랜덤키 생성

			Map<String, String> params = new HashMap<String, String>();
			params.put("email", mail);
			// params.put("mail_key", mail_key);

			// mailSendService.updateMailKey(params); // email을 기준으로 컬럼에 랜덤키 저장
			// log.info("입력받은 이메일 >> " + mail + "생성된 key >> " + mail_key);

			MailHandler sendMail = new MailHandler(mailSender);
			sendMail.setSubject("카카오 항공 가입을 환영합니다.");
			sendMail.setText("<h3>카카오 항공을 찾아주셔서 감사합니다.</h3>" + "<br>언제나 회원님을 생각하는 카카오 항공이 되겠습니다." + "<br><br>");
			sendMail.setFrom("systemlocal99@gmail.com", "카카오 항공");
			sendMail.setTo(mail);
			sendMail.send();

			log.info("controller에서 가입완료 메일 보냄 완료");

			join.registerMember(userId, userNick, userNameK, userNameE, gender, pwd, userReginumFirst, userReginumLast,
					postCode, phone, mail, addressDetail);

			join.registerAllTerms(userId);
			join.registerAuthorityMEMBER(userId);
			join.registerUserlog(userId);
			join.registerGradelog(userId);
			join.registerUserPay(userId);
			join.registerPoint(userId);

			return "redirect:/join/joinSuccess";

		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/accessError";
		}

	}

//http://192.168.0.19:8081/login
	@GetMapping("/error/accessError")
	@CrossOrigin("http://localhost:8081/error/accessError")
	public void accessError() {

	}

	@GetMapping("/checkEnabled")
	public void checkEnabled() {
//	    String[] cookiesToKeep = {"maindiv_flight", "maindiv_notice", "Cookie_userId"};
//		
//	    //remember-me 쿠키 제거
//		request.getSession();
//		Cookie[] cookies = request.getCookies();
//		if(cookies != null) {
//            for (Cookie cookie : cookies) {
//            	if(!Arrays.asList(cookiesToKeep).contains(cookie.getName())) {
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            	}
//            }
//		}

	}

	// @PreAuthorize("isAnonymous()")
	@PostMapping("/checkEnabled") // 여유가 있다면.. 랜덤키생성/메일보내는 메서드를 따로 뺄까 생각중...
	public String checkEnabled(String email, Model model, RedirectAttributes attr) {

		String result = join.confirmEmail(email);
		model.addAttribute("email", result); // 필요한가
		log.info("email >> " + email);
		log.info("result >> " + result);

		String userId = join.getUserIdByMail(email); //입력받은 이메일로 아이디를 가져옴
		int userEnabled = userService.getEnabled(userId);
		
		if (result == null) { //등록된 이메일이 없는 경우
			model.addAttribute("joinMessage", "입력하신 정보를 다시 확인해주시기 바랍니다.");
			return "/join/checkEnabled";
			
		} else if (userEnabled != 0) { //이메일로 유저의 아이디를 조회, 해당 아이디의 enabled가 0이 아닌 경우
			log.info("이메일로 유저의 아이디를 조회, 해당 아이디의 enabled가 0이 아닌 경우 >>> ");
			model.addAttribute("joinMessage", "입력하신 정보를 다시 확인해주시기 바랍니다.");
			return "/join/checkEnabled";
		}

		else {
			try {
				String mail_key = new TempKey().getKey(); // 랜덤키 생성

				Map<String, String> params = new HashMap<String, String>();
				params.put("email", email);
				params.put("mail_key", mail_key);

				mailSendService.modifyMailKey(params); // email을 기준으로 컬럼에 랜덤키 저장
				log.info("입력받은 이메일 >> " + email + "생성된 key >> " + mail_key);

				MailHandler sendMail = new MailHandler(mailSender);
				sendMail.setSubject("카카오 항공 인증 메일입니다.");
				sendMail.setText("<h3>카카오 항공을 찾아주셔서 감사합니다.</h3>" + "<br>아래 확인 버튼을 눌러서 인증을 완료해 주시기 바랍니다."
						+ "<br><br><a href='http://localhost:8081/join/updateEnabled" + "/" + email + "/" + mail_key
						+ "' target='_blank'>이메일 인증 확인</a>");
				sendMail.setFrom("systemlocal99@gmail.com", "카카오 항공");
				sendMail.setTo(email);
				sendMail.send();

				log.info("controller에서 enabled 메일 보냄 완료");

				return "redirect:/join/mailSended";

			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/error/accessError";
			}
		}

	}

	@GetMapping("/updateEnabled/{email}/{mail_key}") // 근데 get이라서 전부 url에 노출됨..
	public String updateEnabled(@PathVariable("email") String email, @PathVariable("mail_key") String mail_key,
			Model model) throws Exception {
		log.info("JoinController >> updateEnabled");
		join.modifyEnabled(email, mail_key);
		mailSendService.removeMailKey(email);

		return "/join/updateEnabled";
	}

}
