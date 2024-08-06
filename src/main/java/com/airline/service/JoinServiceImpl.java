
package com.airline.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.airline.mapper.JoinMapper;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.TermsVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class JoinServiceImpl implements JoinService {

	@Autowired
	private JoinMapper join;

	@Override
	public String confirmEmail(String email) {
		return join.checkEmail(email);
	}

	@Override
	public KakaoUserVO showUserId(String email, String mail_key) {
		return join.getUserId(email, mail_key);
	}

	@Override
	public KakaoUserVO confirmMember(KakaoUserVO vo) {
		return join.checkMember(vo);
	}
	

	@Override
	public String confirmUserIdAndEmail(String userId, String email) {
		return join.checkUserIdAndEmail(userId, email);
	}

	@Override
	public void modifyPwdByMailKey(String userId, String mail_key) {
		join.updatePwdByMailKey(userId, mail_key);
	}
	

	@Override //토큰 받아오기
	public String getAccessToken(String authorize_code) throws Throwable {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
//		Content-type: application/x-www-form-urlencoded;charset=utf-8 설정 어떻게 하는지..?

		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			
			sb.append("grant_type=authorization_code");

			sb.append("&client_id=607caeca9f2a0089b46f99c667e0dee3"); // REST_API키 본인이 발급받은 key 넣어주기
			sb.append("&redirect_uri=http://localhost:8081/join/kakao"); // REDIRECT_URI 본인이 설정한 주소 넣어주기

			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			log.info("response body : " + result);

			// jackson objectmapper 객체 생성
			//JSON 컨텐츠를 Java 객체로 deserialization 하거나 Java 객체를 JSON으로 serialization 할 때 사용하는 Jackson 라이브러리의 클래스
			//JSON 파일을 Java 객체로 deserialization 하기 위해서는 ObjectMapper의 readValue() 메서드를 이용
			ObjectMapper objectMapper = new ObjectMapper();
			
			// JSON String -> Map
			Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
			});
			//result(JSON 형태의 데이터)를 TypeReference로 끊어서 읽음
			//TypeReference는 그냥 런타임시에도 유형을 보존해주는 메소드..
			//결론적으로 result를 map으로 끊어서 map에 저장함
			

			access_Token = jsonMap.get("access_token").toString(); //map에 저장되어 있는 정보증에 "~~"를 포함한 값을 String으로 가져옴
			refresh_Token = jsonMap.get("refresh_token").toString();

			log.info("access_token : " + access_Token);
			log.info("refresh_token : " + refresh_Token);

			//작업끝났으면 연결끝내줌
			br.close(); // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기 BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			bw.close(); // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_Token; //aceess와 refresh토큰 값을 받을 수 있음

	}
	
	//토큰주고 정보받아오기
	@Override 
	public HashMap<String, Object> getUserInfo(String access_Token) throws Throwable {
		// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
				HashMap<String, Object> userInfo = new HashMap<String, Object>();
				String reqURL = "https://kapi.kakao.com/v2/user/me";

				try {
					//post타입으로 지정한 url에 접속
					URL url = new URL(reqURL);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");

					// 요청에 필요한 Header에 포함될 내용  (String key, String value) => kakao로부터 정보를 받아오기 위함
					conn.setRequestProperty("Authorization", "Bearer " + access_Token);
					

					int responseCode = conn.getResponseCode(); //200이 나오면 정상
					log.info("responseCode >> " + responseCode);

					//kakao가 주는 정보 담은 애
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

					String line = "";
					String result = "";

					//잘 연결이 되었다면(access Token conn 등에 오류가 없다면..) result에 결과를 전부 담음
					while ((line = br.readLine()) != null) {
						result += line;
					}
					log.info("response body >> " + result);
					log.info("result type >> " + result.getClass().getName()); // java.lang.String

					try {
						// jackson objectmapper 객체 생성(Json 타입의 데이터를 java 데이터로 바꾸기 위해)
						ObjectMapper objectMapper = new ObjectMapper();
						// JSON String -> Map
						Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
						});
						//result(JSON 형태의 데이터)를 TypeReference로 끊어서 읽음
						//TypeReference는 그냥 런타임시에도 유형을 보존해주는 메소드..
						//결론적으로 result를 map으로 끊어서 map에 저장함

						log.info("jsonMap Data >> " +jsonMap.get("properties")); //null error처리해주기
						//jsonMap에서 "properties"라는 이름을 가진 데이터를 출력
						
						// kakao는 kakao_account에 유저정보가 있다.
						//Map<String, Object> properties = (Map<String, Object>) jsonMap.get("properties");
						//"properties"라는 데이터를 map형태로 가져옴 아래도 마찬가지..
						Map<String, Object> kakao_account = (Map<String, Object>) jsonMap.get("kakao_account");
						Map<String, Object> kakao_profile =  (Map<String, Object>) kakao_account.get("profile");

						System.out.println("kakao_account >> " + kakao_account);
						//System.out.println("properties >> " + properties);
						
						log.info(kakao_profile.get("nickname"));
						log.info(kakao_account.get("email"));
						log.info(kakao_account.get("name"));
						log.info(kakao_account.get("gender"));
						log.info(kakao_account.get("age_range_needs_agreement"));
						//log.info(kakao_account.get("age_range")); //=> error
						log.info(kakao_account.get("birthday")); //탄생연도는 따로 권한이 필요해서 불가능-> age_rage로 대체하여 성인인지만 판단할예정... 그러나 age_range에서 에러발생
						log.info(kakao_account.get("phone_number"));
//						log.info(kakao_account.get("shipping_address")); =>error

						String nickname = kakao_profile.get("nickname").toString();
						String email = kakao_account.get("email").toString();
						String name = kakao_account.get("name").toString();
						String gender = kakao_account.get("gender").toString();
						String age_range_needs_agreement = kakao_account.get("age_range_needs_agreement").toString();
						//String age_range = kakao_account.get("age_range").toString();
						String birthday = kakao_account.get("birthday").toString();
						String phone_number = kakao_account.get("phone_number").toString();
//						String shipping_address = kakao_account.get("shipping_address").toString();

						userInfo.put("nickname", nickname);
						userInfo.put("email", email);
						userInfo.put("name", name);
						userInfo.put("gender", gender);
						userInfo.put("age_range_needs_agreement", age_range_needs_agreement);
						//userInfo.put("age_range", age_range);
						userInfo.put("birthday", birthday);
						userInfo.put("phone_number", phone_number);
//						userInfo.put("shipping_address", shipping_address);

						log.info("nickname >> " + nickname);
						log.info("email >> " + email);
						log.info("name >> " + name);
						log.info("gender >> " + gender);
						log.info("age_range_needs_agreement >> " + age_range_needs_agreement);
						//log.info("age_range >> " + age_range);
						log.info("birthday >> " + birthday);
						log.info("phone_number >> " + phone_number);
//						log.info("shipping_address >> " +shipping_address);
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				return userInfo;
			
	}
	
	
//	//토큰주고 정보받아오기
//		@Override 
//		public HashMap<String, Object> getKakaoLogout(String access_Token) throws Throwable {
//			// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
//					HashMap<String, Object> userInfo = new HashMap<String, Object>();
//					String reqURL = "https://kapi.kakao.com/v1/user/logout";
//
//					try {
//						//post타입으로 지정한 url에 접속
//						URL url = new URL(reqURL);
//						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//						conn.setRequestMethod("POST");
//
//						// 요청에 필요한 Header에 포함될 내용  (String key, String value) => kakao로부터 정보를 받아오기 위함
//						conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//						
//
//						int responseCode = conn.getResponseCode(); //200이 나오면 정상
//						log.info("responseCode >> " + responseCode);
//
//						//kakao가 주는 정보 담은 애
//						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//						String line = "";
//						String result = "";
//
//						//잘 연결이 되었다면(access Token conn 등에 오류가 없다면..) result에 결과를 전부 담음
//						while ((line = br.readLine()) != null) {
//							result += line;
//						}
//						log.info("response body >> " + result);
//						log.info("result type >> " + result.getClass().getName()); // java.lang.String
//
//						try {
//							// jackson objectmapper 객체 생성(Json 타입의 데이터를 java 데이터로 바꾸기 위해)
//							ObjectMapper objectMapper = new ObjectMapper();
//							// JSON String -> Map
//							Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
//							});
//							//result(JSON 형태의 데이터)를 TypeReference로 끊어서 읽음
//							//TypeReference는 그냥 런타임시에도 유형을 보존해주는 메소드..
//							//결론적으로 result를 map으로 끊어서 map에 저장함
//
//							log.info("jsonMap Data >> " +jsonMap.get("id")); //null error처리해주기
//							//jsonMap에서 "properties"라는 이름을 가진 데이터를 출력
//							
//							// kakao는 kakao_account에 유저정보가 있다.
//							//Map<String, Object> properties = (Map<String, Object>) jsonMap.get("properties");
//							//"properties"라는 데이터를 map형태로 가져옴 아래도 마찬가지..
//							Map<String, Object> kakao_account = (Map<String, Object>) jsonMap.get("id");
//
//							//System.out.println("properties >> " + properties);
//							
//							log.info(kakao_account.get("id"));
//
//							String id = kakao_account.get("id").toString();
//
//							log.info("id >> " + id);
//							
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					return userInfo;
//				
//		}
	
	
 
	@Override
	public TermsVO getTerms(int termsCode) {
		return join.getTerms(termsCode);
	}

	@Override
	public void registerMember(String userId, String userNick, String userNameK, String userNameE, String gender, String pwd,
			int userReginumFirst, int userReginumLast, int postCode, String phone, String mail, String address) {
		join.insertMember(userNameE, userNameK, gender, userReginumFirst, 
				userReginumLast, userId, userNick, pwd, 
				mail, phone, postCode, address);
	}
	
	@Override
	public void registerKakaoMember(KakaoUserVO vo) {
		join.insertkakaoMember(vo);
	}

	@Override
	public int userIdDuplicateCheck(String userId) {
		return join.userIdDuplicateCheck(userId);
	}

	@Override
	public int userNickDuplicateCheck(String userNick) {
		return join.userNickDuplicateCheck(userNick);
	}

	@Override
	public KakaoUserVO kakaoLoginCheck(String userNameK, String userId) {
		KakaoUserVO vo = join.kakaoLoginCheck(userNameK, userId);
		return vo;
	}

	@Override
	public void registerBasicTerms(String userId) {
		join.insertBasicTerms(userId);
	}

	@Override
	public void registerAllTerms(String userId) {
		join.insertAllTerms(userId);
	}

	@Override
	public void registerAuthorityMEMBER(String userId) {
		join.insertAuthorityMEMBER(userId);
	}

	@Override
	public List<SimpleGrantedAuthority> getAuthorities(String email) {
		return join.getAuthorities(email);
	}

	@Override
	public void registerUserlog(String userId) {
		join.insertUserlog(userId);
	}

	@Override
	public void registerGradelog(String userId) {
		join.insertGradelog(userId);
	}

	@Override
	public void registerUserPay(String userId) {
		join.insertUserPay(userId);
	}

	@Override
	public void registerPoint(String userId) {
		join.insertPoint(userId);
	}

	@Override
	public void modifyEnabled(String email, String mail_key) {
		join.updateEnabled(email, mail_key);
		
	}

	@Override
	public String getUserIdByMail(String email) {
		return join.getUserIdByMail(email);
	}

	
}
