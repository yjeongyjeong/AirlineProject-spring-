package com.airline.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.service.FlightService;
import com.airline.service.PayService;
import com.airline.vo.BoardEventFileVO;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResDTO;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.PageDTO;
import com.airline.vo.PointVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
@RequestMapping("/flight/*")
public class FlightController {

	@Autowired
	private FlightService flights;
	
	@Autowired
	private PayService pservice;
	
	@GetMapping("/list")
	public void getList(Model model, Criteria cri) {
		model.addAttribute("list",flights.getList(cri));
		model.addAttribute("pageMaker", new PageDTO(cri, flights.getTotal(cri)));
	}
	
	@GetMapping(value="/search", produces = {MediaType.APPLICATION_JSON_VALUE})
	public void getSearch(Model model, Criteria cri, @Param("dep") String dep,@Param("arr") String arr,@Param("arrDate") String arrDate, @Param("depDate") String depDate, @Param("depRegionCode") String depRegionCode, @Param("arrRegionCode") String arrRegionCode ) throws ParseException {
		System.out.println("dep : "+dep+" arr : "+arr+" time : "+ arrDate);
		
		if(dep!=null||arr!=null) {	
			List<FlightVO> listSearch = flights.getListSearch(cri,dep,arr,depDate);
			System.out.println(listSearch.size());
			for(FlightVO vo : listSearch) {
				System.out.println("vo : "+vo);
			}
			
			cri.setAmount(50);
			model.addAttribute("list", flights.getListSearch(cri,dep,arr,depDate));
			//model.addAttribute("pageMaker", new PageDTO(cri, flights.getTotalSearch(cri,dep,arr,depDate)));
			
			model.addAttribute("arrlist", flights.getListSearch(cri,arr,dep,arrDate));
			//model.addAttribute("arrPageMaker", new PageDTO(cri, flights.getTotalSearch(cri,arr,dep,arrDate)));
		}
		//검색창 반환값
		model.addAttribute("dep", dep);
		model.addAttribute("arr", arr);
		model.addAttribute("arrDate", arrDate);
		model.addAttribute("depDate", depDate);
		model.addAttribute("depRegionCode", depRegionCode);
		model.addAttribute("arrRegionCode", arrRegionCode);
		
		//가격정보 설정
		if(flights.getPrice(dep, arr)!=null) {
			model.addAttribute("depPrice", flights.getPrice(dep, arr));
		}
		if(flights.getPrice(arr, dep)!=null) {
			model.addAttribute("arrPrice", flights.getPrice(arr, dep));
		}
		
		//날짜계산
		if(depDate!=null) {
			LocalDate depDateCal = LocalDate.parse(depDate);
			model.addAttribute("nextDepDay", depDateCal.plusDays(1));
			model.addAttribute("prevDepDay", depDateCal.plusDays(-1));
		}
		if(arrDate!=null) {
			LocalDate arrDateCal = LocalDate.parse(arrDate);
			model.addAttribute("nextArrDay", arrDateCal.plusDays(1));
			model.addAttribute("prevArrDay", arrDateCal.plusDays(-1));			
		}
		
		//해당 일자의 항공편 부재시, 가장 가까운 일정 찾기
		if(depDate!=null) {
			model.addAttribute("closestFlightPrev", flights.getClosestFlightPrev(dep, arr, depDate));
			model.addAttribute("closestFlightAfter", flights.getClosestFlightAfter(dep, arr, depDate));
		}
		if(arrDate!=null) {
			model.addAttribute("closestFlightPrevArr", flights.getClosestFlightPrev(arr, dep, arrDate));
			model.addAttribute("closestFlightAfterArr", flights.getClosestFlightAfter(arr, dep, arrDate));			
		}
		
	}
	
	@GetMapping("/flightDepArrSearch")
	public void getflightDepArrSearch(Model model, Criteria cri, @Param("dep") String dep,@Param("arr") String arr, @Param("targetDate") String targetDate, @Param("flightName") String flightName, @Param("depRegionCode") String depRegionCode, @Param("arrRegionCode") String arrRegionCode ) {
		System.out.println("dep : " + dep + " arr : " + arr + " target : " + targetDate + " flightName : " + flightName);
		if(dep!=null||arr!=null) {			
			List<FlightVO> listSearch = flights.getListSearch(cri,dep,arr,targetDate);
			System.out.println("노선 검색>>"+listSearch.size());
			model.addAttribute("list", listSearch);
			model.addAttribute("pageMaker", new PageDTO(cri, flights.getTotalSearch(cri, dep, arr, targetDate)));
		}
		
		if(flightName!=null) {
			List<FlightVO> listByFlightName = flights.getListSearchByFlightName(cri, flightName, targetDate);
			System.out.println("편명 검색"+listByFlightName.size());
			model.addAttribute("list", listByFlightName);
			model.addAttribute("pageMaker", new PageDTO(cri, flights.getTotalSearchByFlightName(cri, flightName, targetDate)));
		}
		
		//검색창 반환값
		model.addAttribute("dep", dep);
		model.addAttribute("arr", arr);
		model.addAttribute("targetDate", targetDate);
		model.addAttribute("flightName", flightName);
		model.addAttribute("depRegionCode", depRegionCode);
		model.addAttribute("arrRegionCode", arrRegionCode);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
		Date now = new Date();         
		String today = sdf.format(now);
		System.out.println("현재 시간>>>" + today);
		model.addAttribute("now", today);
		
		if(targetDate!=null) {
			LocalDate dateCal = LocalDate.parse(targetDate);
			model.addAttribute("nextDay", dateCal.plusDays(1));
			model.addAttribute("prevDay", dateCal.plusDays(-1));
		}
		
	}
	
	
	@GetMapping("/flight/reservation")
	public void getReservation(Model model,@Param("fno")int fno) {
		log.info("res....");
		FlightVO vo = flights.getFlightInfo(fno);
		model.addAttribute("vo",vo);
		model.addAttribute("fno",fno);
		String flightName = vo.getFlightName();
		System.out.println("flightName>>"+flightName);
		//예약된 좌석 명단
		List<FlightResVO> rvo = flights.getResAll(flightName, fno);
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        String json = objectMapper.writeValueAsString(rvo);
	        model.addAttribute("json", json);
	        //System.out.println("json>> "+json);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace(); // 또는 예외 처리 로직 추가
	    }
	    model.addAttribute("rvo",rvo);
		//list.add(flights.getResAll(flightName));

		System.out.println("list>>"+flights.getResAll(flightName,fno));
		
	}
	
	//좌석 선택 후 결제창으로 넘어가기
	@GetMapping("/flightRes")
	@PreAuthorize("isAuthenticated()")
	public void flightRes(Model model,@Param("fno")int fno, @Param("seat")String seat) {
		log.info("유저 결제창...");
		//예약할 항공정보
		FlightVO vo = flights.getFlightInfo(fno);
		System.out.println("vo : "+vo);
		//가격구간 검색
		int price = flights.getPrice(vo.getDepName(),vo.getArrName());
		float pc = 0;
		System.out.println("price : "+price);

		//좌석별 가격구간 검색
		float seatPc = 0;
		String originSeat = seat;
		String className = seat.charAt(0)+"";
		if(className == "A") {
			seat = "비지니스";
			seatPc = flights.getSeatPc(seat);
		}else if(className == "B") {
			seat = "이코노미";
			seatPc = flights.getSeatPc(seat);
		}else {
			seat = "일등석";
			seatPc = flights.getSeatPc(seat);
		}
		
		System.out.println("seat : "+seat);
		model.addAttribute("seat",seatPc);
		model.addAttribute("className",originSeat);
				
		//유저정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
			String userid = userDetails.getUsername();
			
			System.out.println("id : "+userid);
			model.addAttribute("userid",userid);
			//유저이메일
			String email = flights.getEmail(userid);
			model.addAttribute("email",email);
			
			//유저나이 검색
			String dbage = flights.getUserAge(userid)+"";
			int age = 0;
			if(Integer.parseInt(dbage.substring(0, 2))<11 || Integer.parseInt(dbage.substring(0, 2))>59) {
				age = 12;
				//age = 2023-Integer.parseInt(19+dbage.substring(0, 2));
			}else {
				//나잇값 다시 고쳐야함...;;
				age = 2023-Integer.parseInt(20+dbage.substring(0, 2));
			}
			System.out.println("age : "+age);
			
			//나이별 할인구간 검색
			  pc = flights.getAgeDiscount(age); 
			  System.out.println(pc);
			  
			//카카오 포인트 검색
				int kpoint = flights.getKcount(userid);
				int kakaoP = 0;
				if(kpoint ==0) {
					kakaoP = 0;
				}else {
					kakaoP = flights.getKakaoPoint(userid);
				}
				
				System.out.println(kakaoP);
				model.addAttribute("kakaoP", kakaoP);
			//유저 마일리지 검색 
			int count = flights.getcount(userid);
			System.out.println(count);
			int point = 0;
			if(count == 0) {
				point = 0;
			}else {
				point = flights.getPoint(userid);
			}

			System.out.println(point);
			model.addAttribute("point",point);
			
			
			//int ageP =flights.getAgePercent()
			
			
			//pay.chargePoint(userid,amount);
			} else { 
				String userid = authentication.getPrincipal().toString(); 
				System.out.println("id : "+userid);
			} 
		model.addAttribute("fno",fno);
		model.addAttribute("seat",seat);
		model.addAttribute("vo", vo);
		model.addAttribute("price", price);
		model.addAttribute("pc", pc);
		model.addAttribute("total", Math.round(price*pc*seatPc));
		
		
	}
		
	@PostMapping(value="/rescomplete" )
	@PreAuthorize("isAuthenticated()")
	public @ResponseBody void rescomplete(@RequestBody FlightResDTO flight, RedirectAttributes rttr, HttpServletResponse res) throws IOException {
		/*
		 * System.out.println(flight.getUserid());
		 * System.out.println(flight.getPoint()); System.out.println(flight.getKakao());
		 * System.out.println(flight.getSeat()); System.out.println(flight.getTotal());
		 * System.out.println(flight.getFno());
		 */
		log.info("결제완료.. post");
		String userid = flight.getUserid();
		//db에 집어넣기
		//항공정보 가져오기 + user정보 가져오기
		FlightVO vo = flights.getFlightInfo(flight.getFno());
		//KakaoUserVO kvo = flights.getUserInfo(flight.getUserid());
		String uName = flights.getUserName(userid);
		//1.예약 테이블
		String rno = UUID.randomUUID().toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("fno", String.valueOf(flight.getFno()));
		map.put("resno", rno);
		map.put("userid", userid);
		map.put("username", uName);
		map.put("flightname", vo.getFlightName());
		map.put("departure", vo.getDepName());
		map.put("arrival", vo.getArrName());
		map.put("arrtime", vo.getFullArrtime());
		map.put("deptime", vo.getFullDeptime());
		map.put("seatid", flight.getSeat());
		System.out.println(map);
		/*
		 * FlightResVO resvo = FlightResVO.builder() .resno(rno)
		 * .userid(flight.getUserid()) .username(uName) .flightname(vo.getFlightName())
		 * .departure(vo.getDepName()) .arrival(vo.getArrName())
		 * .arrtime(vo.getArrTime()) .deptime(vo.getArrTime())
		 * .seatid(flight.getSeat()).build();
		 */
		int resResult = flights.insertRes(map);
		//2.userpay 테이블 업데이트
		if(resResult == 1) {
			//2-1.마일리지 및 카카오페이 사용내역 업데이트
			if(flight.getPoint()!=0 ) {
				int usePoint = pservice.updatePoint(-flight.getPoint(), userid);
			}
			if(flight.getKakao() != 0) {
				int useKakao = pservice.updateKakaoPoint(-flight.getKakao(), userid);
			}
			//2-2. 구매관련 마일리지 및 금액 적립
			int total = flight.getTotal();
			int mileage = (int) (Math.round(flight.getTotal()*0.1));
			int payResult = flights.insertPay(rno, userid, total, mileage);
			System.out.println("payResult>>"+payResult);
			//2-3. 유저로그 업데이트(비행내역, 구매총금액, 총 마일리지)
			int flightCount = flights.getBuyCount(userid); //예약내역으로 구매횟수 조회
			System.out.println("flightCount>>"+flightCount);
			int flightSum = flights.getTotalBuy(userid);//총 구매금액 조회 ->등급업데이트에 이용
			System.out.println("flightSum>>"+flightSum);
			int userPoint = flights.getCurMileage(userid);//현재 마일리지 금액		
			System.out.println("userPoint>>"+userPoint);
			int logresult = flights.logUpdate(userid,flightCount, flightSum, userPoint);
			System.out.println("logresult>>"+logresult);
			//2-4. 등급 업데이트
			int flightSum1 = 0;
			if(flightSum<300000) { flightSum1 = 0;}
			else if(flightSum>=300000 && flightSum<500000) {flightSum1 = 300000;}
			else if(flightSum>=500000 && flightSum<1000000) {flightSum1 = 500000;}
			else{flightSum1 = 1000000;}
			System.out.println("등급코드기준금액>>>>"+flightSum1);
			int getCode = flights.getGradeCode(flightSum1);
			//원래 등급과 비교해서 변동사항이 잇을시 로우 인서트
			int oriCode = flights.getOriCode(userid);
			if(oriCode != getCode) {
				//kakaouser + log 테이블 바꿈
				int codeUpdate = flights.updateGrade(userid,getCode);
				int logInsert = flights.insertGradeUpdate(userid,flightCount,flightSum,userPoint);
			}
			System.out.println("oriCode>>"+oriCode + ":" +getCode );
			//flightSum이 grade 테이블의 gradeStandard 이상인 gradeCode를 가져와 kakaoUser 테이블에 업데이트
			
			
			flights.updateSeatCount(vo.getFlightName(), vo.getDepDay().substring(0,10));
		}
		
		//getRescomplete으로 리다이렉트(예약정보 가져오기)
		FlightResVO resVo = flights.getResInfo(rno);
		System.out.println("resVo >>>"+resVo);
		
		rttr.addAttribute("userid", userid);
		//rttr.addAttribute("resVo",resVo);
		//rttr.addAttribute("rno",rno);
		res.sendRedirect("/flight/rescompleteMeg");
	}
	
	@GetMapping("/rescompleteMeg")
	@PreAuthorize("isAuthenticated()")
	public void getRescomplete(Model model, @RequestParam("userid")String userid) {
		log.info("결제완료.. get");
		
		System.out.println("id>>>>"+userid);
		//비행기 예약내역 최신순 1 찾아오기
		FlightResVO vo = flights.getResFirst(userid);
		//결제자 정보
		KakaoUserVO kvo = flights.getUserInfo(userid);
		model.addAttribute("userid",userid);
		model.addAttribute("vo",vo);
		model.addAttribute("kvo",kvo);
		//총 결제금액
		int usePoint = flights.usePoint(userid);
		model.addAttribute("point",usePoint);


		}
	
	//카카오메세지 토큰값 가져오기
	@GetMapping(value="/oath")
	public String oath(@RequestParam("code")String code, Model model, RedirectAttributes rttr) {
		System.out.println(code);
		String getCode = code;
		String grant_type = code;
		String url = "https://kauth.kakao.com/oauth/token";
		//String url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
		String redirect_url = "http://192.168.0.19:8081/flight/oath";
		String rest_api_key="607caeca9f2a0089b46f99c667e0dee3";
//		Map<String, String> jsonData = new HashMap<String, String>();
//		jsonData.put("grant_type", grant_type);
//		jsonData.put("client_id", rest_api_key);
//		jsonData.put("redirect_url", redirect_url);
//		jsonData.put("code", code);
		
		//전역변수 생성
		String userid = "";
		String flightName="";
		String arrival = "";
		String arrTime = "";
		String departure = "";
		String depTime = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			userid = userDetails.getUsername();
			System.out.println("id : " + userid);
			FlightResVO vo = flights.getResFirst(userid); //최신내역
			flightName = vo.getFlightname();
			arrival = vo.getArrival();
			arrTime = vo.getArrtime();
			departure = vo.getDeparture();
			depTime= vo.getDeptime();
		}
		
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // 형식변환
        String access_Token = "";
		
        try {
            URL reqUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) reqUrl.openConnection();
            
          //필수 헤더 세팅
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            conn.setRequestMethod("POST");
            
            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            //필수 쿼리 파라미터 세팅
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(rest_api_key);
            sb.append("&redirect_uri=").append(redirect_url);
            sb.append("&code=").append(code);
            
            bw.write(sb.toString());
            bw.flush();
            
            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while((line = br.readLine()) != null){
                responseSb.append(line);
            }
            
            String result = responseSb.toString();
            System.out.println("result : " + result);
            
//          Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            String refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            model.addAttribute("access_Token",access_Token);
            model.addAttribute("refresh_token",refresh_Token);
            br.close();
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        //메세지 보내기
        try {
            URL reqUrl = new URL("https://kapi.kakao.com/v2/api/talk/memo/default/send");
            HttpURLConnection conn = (HttpURLConnection) reqUrl.openConnection();
            String templateObject = "{ \"object_type\": \"text\", \"text\": \"%s님 예약이 완료되었습니다. 출발지 : %s, 출발시간 : %s, 도착지 : %s, 도착시간 : %s  \", \"link\": { \"web_url\": \"http://www.daum.net\", \"mobile_web_url\": \"http://m.daum.net\" }, \"button_title\" : \"바로 확인\" }";
            // String templateObject = "{ \"object_type\": \"text\", \"text\": { \"text 전송 테스트\": \"오늘의 디저트\", \"description\": \"아메리카노, 빵, 케익\", \"image_url\": \"https://mud-kage.kakao.com/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg\", \"image_width\": 640, \"image_height\": 640, \"link\": { \"web_url\": \"http://www.daum.net\", \"mobile_web_url\": \"http://m.daum.net\", \"android_execution_params\": \"contentId=100\", \"ios_execution_params\": \"contentId=100\" } }, \"item_content\" : { \"profile_text\" :\"Kakao\", \"profile_image_url\" :\"https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png\", \"title_image_url\" : \"https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png\", \"title_image_text\" :\"Cheese cake\", \"title_image_category\" : \"Cake\", \"items\" : [ { \"item\" :\"Cake1\", \"item_op\" : \"1000원\" }, { \"item\" :\"Cake2\", \"item_op\" : \"2000원\" }, { \"item\" :\"Cake3\", \"item_op\" : \"3000원\" }, { \"item\" :\"Cake4\", \"item_op\" : \"4000원\" }, { \"item\" :\"Cake5\", \"item_op\" : \"5000원\" } ], \"sum\" :\"Total\", \"sum_op\" : \"15000원\" }, \"social\": { \"like_count\": 100, \"comment_count\": 200, \"shared_count\": 300, \"view_count\": 400, \"subscriber_count\": 500 }, \"buttons\": [ { \"title\": \"웹으로 이동\", \"link\": { \"web_url\": \"http://www.daum.net\", \"mobile_web_url\": \"http://m.daum.net\" } }, { \"title\": \"앱으로 이동\", \"link\": { \"android_execution_params\": \"contentId=100\", \"ios_execution_params\": \"contentId=100\" } } ] }";
            String formattedJson = String.format(templateObject, userid, departure, depTime, arrival, arrTime);
            String encodedTemplateObject = URLEncoder.encode(formattedJson, StandardCharsets.UTF_8.toString());
            
            //필수 헤더 세팅
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = ("template_object=" + encodedTemplateObject).getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);


            // 연결 종료
            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }

		FlightResVO vo = flights.getResFirst(userid);
		//결제자 정보
		KakaoUserVO kvo = flights.getUserInfo(userid);
		model.addAttribute("userid",userid);
		model.addAttribute("vo",vo);
		model.addAttribute("kvo",kvo);
		//총 결제금액
		int usePoint = flights.usePoint(userid);
		model.addAttribute("point",usePoint);

        
        return "/flight/rescompleteMeg";
	}
	
	//검색어 자동완성 - 출발지
	@GetMapping(value = "/getDistinctDep")
	@ResponseBody
	public List<String> getDistinctDep(String searchValue, String depRegionCode){
		log.info("getDistinctDep... ");
		List<String> list = flights.getDistinctDep(searchValue, depRegionCode);
		for(String str : list) {
			log.info(str);
		}
		
		return list;
	}
	
	//검색어 자동완성 - 도착지
	@PostMapping(value = "/getDistinctArrByDep1")
	@ResponseBody
	public List<String> getDistinctArrByDep1( String depName, String searchValue){
		log.info("getDistinctArrByDep... ");
		List<String> list = flights.getDistinctArrByDep1(depName, searchValue);
		for(String str : list) {
			log.info(str);
		}
		
		return list;
	}
	
	//검색어 자동완성 - 도착지
	@PostMapping(value = "/getDistinctArrByDep2")
	@ResponseBody
	public List<String> getDistinctArrByDep2( String depName, String searchValue, String arrRegionCode){
		log.info("getDistinctArrByDep2... ");
		log.info("depName" +depName);
		log.info("arrName" +searchValue);
		log.info("arrRegionCode" +arrRegionCode);
		List<String> list = flights.getDistinctArrByDep2(depName, searchValue, arrRegionCode);
		for(String str : list) {
			log.info(str);
		}
		
		//Gson gson = new Gson();
		//return gson.toJson(list);
		return list;
	}
	
	//검색어 자동완성 - 항공편명
	@GetMapping(value = "/getDistinctFlightName")
	@ResponseBody
	public String getDistinctFlightName(String searchValue){
		log.info("getDistinctFlightName... ");
		List<String> list = flights.getDistinctFlightName(searchValue);
//		for(String str : list) {
//			log.info(str);
//		}
		
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	//검색어 자동완성 - 대륙코드
	@GetMapping(value = "/getDistinctArrRegionCode")
	@ResponseBody
	public String getDistinctArrRegionCode(String depName){
		log.info("getDistinctArrRegionCode... ");
		List<String> list = flights.getDistinctArrRegionCode(depName);
//			for(String str : list) {
//				log.info(str);
//			}
		
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
}
