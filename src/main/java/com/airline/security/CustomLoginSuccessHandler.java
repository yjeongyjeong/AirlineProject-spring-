package com.airline.security;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.airline.mapper.UserMapper;
import com.airline.vo.KakaoUserVO;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
	
	//매퍼 추가
	@Autowired
	private UserMapper mapper;
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		
		//세션에 선언하는 부분 추가
		KakaoUserVO vo = mapper.getUser(auth.getName());
		HttpSession session = request.getSession();	
		session.setAttribute("loginUser", vo); 
		//일반로그인이랑 카카오랑 구별해서 따로 선언해서 가져오기...
		//카카오로하면 세션값 못얻어와서 연결안되는중.. => controller에서 직접 시큐리티 핸들링....
		log.info("CUSTOM LOGIN SUCCESS ===============================");
		log.warn("login success");
		List<String> roleNames = new ArrayList<>();
		auth.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
	
		log.warn("role names : " + roleNames);
//		if(roleNames.contains("ROLE_ADMIN")) {
//			resultRedirectStrategy(request, response, auth);
//			//response.sendRedirect("/admin");
//			return ;
//		}
//		
//		if(roleNames.contains("ROLE_MEMBER")) {
//			resultRedirectStrategy(request, response, auth);
//			//response.sendRedirect("/user");
//			return;
//		}
		
		clearAuthenticationAttributes(request);
		
		String userId = vo.getUserId();
		
		if(mapper.getEnabled(userId) == 0) {
			String access_token = (String) session.getAttribute("access_token");

			//if(access_token != null)
			Map<String, String> map = new HashMap<String, String>();
			map.put("Authorization", "Bearer " + access_token);
			
		    String[] cookiesToKeep = {"maindiv_flight", "maindiv_notice", "Cookie_userId"};

		    try {
		        log.info("loginSuccessHandler ... ");
		        request.getSession();
		        Cookie[] cookies = request.getCookies();
		        if (cookies != null) {
		            for (Cookie cookie : cookies) {
		            	if(!Arrays.asList(cookiesToKeep).contains(cookie.getName())) {
		                cookie.setMaxAge(0);
		                response.addCookie(cookie);
		            	}
		            }
		        }
		        log.info("loginsuccesshandler에서 session만료 ");
		    }finally {		
		    	log.info("loginsuccesshandler에서 checkEnabled 하는중.. ");
		    	redirectStrategy.sendRedirect(request, response, "/join/checkEnabled");
			}
		} else {
		//response.sendRedirect("/");
		resultRedirectStrategy(request, response, auth);
		}
	}

	//로그인 실패 에러세션 지우기
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) return;
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		
	}

	//로그인 성공 시 타겟url 으로 리다이렉트
	private void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		log.info("로그인 성공");
		
		// 기본 URI
        String uri = "/";
		
		String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null) {
            request.getSession().removeAttribute("prevPage");
        }
		
		if(savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			redirectStrategy.sendRedirect(request, response, targetUrl);
		} else if(prevPage != null && !prevPage.equals("")){
			// 회원가입 - 로그인으로 넘어온 경우 "/"로 redirect
            if (prevPage.contains("/join")) {
                uri = "/";
                redirectStrategy.sendRedirect(request, response, uri);
            } else {
                uri = prevPage;
                redirectStrategy.sendRedirect(request, response, uri);
            }
		} else  {
			redirectStrategy.sendRedirect(request, response, "/");
		}
		
	}

}
