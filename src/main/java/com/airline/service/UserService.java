package com.airline.service;

import java.util.List;

import com.airline.vo.BoardDiaryVO;
import com.airline.vo.BoardEventVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.BoardQnaVO;
import com.airline.vo.CancelVO;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.GradeLogVO;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.PointVO;
import com.airline.vo.UserPayVO;

public interface UserService {
	//유저 정보 불러오기
	KakaoUserVO getUserInfo(String userid);
	//등급조회(grade 테이블)
	String getGrade(int gradeCode);
	//유저별 포인트 내역 가져오기
	UserPayVO getPoint(String userid);
	//포인트 합
	int getSumP(String userid);
	//최근 마일리지 내역3개
	List<UserPayVO> getPoint3(String userid);
	//카카오포인트 합
	int getSumK(String userid);
	//최근 카카오내역3
	List<PointVO> getKakao3(String userid);
	//여행일기 게시글 3개
	List<BoardDiaryVO> getDiary(String userid);
	//전체 유저정보 가져오기(가입순서 desc)
	List<KakaoUserVO> getUserInfoAll();
	//한달동안 항공 결제내역
	List<UserPayVO> getSale();
	//티켓취소요청
	List<CancelVO> reqCancel();
	//항공권 구매/예약현황
	List<FlightResVO> getFlightres();
	//공지사항 
	List<BoardNoticeVO> getNotice();
	//이벤트
	List<BoardEventVO> getEvent();
	//카카오 포인트 충전
	int chargePoint(String userid, int amount);
	//유저별 카카오포인트 내역 가져오기
	List<PointVO> getKPoint(String userid, Criteria cri);
	//유저별 마일리지 적립내역 가져오기
	List<UserPayVO> getMPoint(String userid, Criteria cri);
	//카카오페이 게시글 수
	int getKTotal(String userid, Criteria cri);
	//마일리지 게시글 수
	int getMTotal(String userid, Criteria cri);
	//유저별 항공예약 상세
	List<FlightResVO> getUserRes(String userid, Criteria cri);
	//총 구매횟수
	int getCountBuy(String userid);
	//총 구매금액
	int getTotal(String userid);
	//항공예약 총 게시글수
	int getFlightTotal(String userid, Criteria cri);
	//항공취소
	int cancelTicket(String data);
	//체크인
	int checkin(String data);
	//유저페이지 항공내역3개
	List<FlightResVO> getFlight3(String userid);
	//유저이름 검색(닉네임)
	String getName(String userid);
	//qna 게시판3개 
	List<BoardQnaVO> getQna(String username);
	//여행일기 게시판
	List<BoardDiaryVO> getUserDiary(String username, Criteria cri);
	//여행일기 페이징 카운트
	int getUserDiaryCnt(String username, Criteria cri);
	//qna 게시판
	List<BoardQnaVO> getUserQna(String username, Criteria cri);
	//qna 페이징 카운트
	int getUserQnaCnt(String username, Criteria cri);
	//등급 페이징
	List<GradeLogVO> getGradeLog(String userid, Criteria cri);
	int getGradeLogCnt(String userid, Criteria cri);
	//항공운항내역
	List<FlightVO> getFlightList3();
	//mileage 가져오기
	public int getMileage(String userid);
	//마이페이지(유저) 정보수정 업데이트
	public void modifyUserInfo(String userId,
			String userNameK, String userNameE, 
			String phone, int postCode, String address);
	//마이페이지(유저) 비밀번호 업데이트
	public void modifyUserPwd(String userId, String pwd);
	//마이페이지(유저) 닉네임 업데이트
	public void modifyUserNick(String userId, String userNick);
	
	public int getEnabled(String userId);
}
