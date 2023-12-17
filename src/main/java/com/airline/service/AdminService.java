package com.airline.service;

import java.util.List;

import com.airline.vo.BoardEventVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.CancelVO;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.UserPayVO;

public interface AdminService {
	//취소요청 리스트 가져오기
	public List<CancelVO> getCancelList(Criteria cri);
	//취소요청 리스트 페이징
	public int getCancelCtn(Criteria cri);
	//예약정보 불러오기
	public List<FlightResVO> getResInfo(Criteria cri);
	//가격, 마일리지 정보 불러오기
	public UserPayVO getPayInfo(String resno);
	//가격 마일리지 취소처리
	public int updatePrice(String resno, int price, int mileage);
	//마일리지 합
	public int sumMile(String userid);
	//구매가격 합
	public int sumPrice(String userid);
	//로그 인서트
	public int insertlog(String userid, int sumMile, int sumPrice);
	//cancel table 취소여부 업데이트
	public void updateCancel(String resno);
	//예약 개수
	public int getTicketCnt(Criteria cri);
	//회원정보 불러오기
	public List<KakaoUserVO> getUserInfo(Criteria cri);
	//회원정보 카운트
	public int getUserCnt(Criteria cri);
	//휴먼회원처리
	public int updateUserInfo1(String userid);
	public int updateUserInfo0(String userid);
	//공지사항
	public List<BoardNoticeVO> getNotice(Criteria cri);
	public int noticeCnt(Criteria cri);
	//공지사항 삭제
	public int deleteNotice(int boardnum);
	//이벤트 게시판
	public List<BoardEventVO> getEvent(Criteria cri);
	public int eventCtn(Criteria cri);
	//항공운항 리스트 조회
	public List<FlightVO> getFlightList(Criteria cri);
	public int getFlightListCnt(Criteria cri);
	//fno 가져오기
	public int getFno();
	//depCode 가져오기
	public List<String> getDepcode();
	public List<String> getArrcode();
	public List<Integer> getdRCode();
	public List<Integer> getaRCode();
	//출발지 도착지 풀네임 가져오기
	public String getFullDeparture(String depCode);
	public String getFullArrival(String arrCode);
	//항공스케줄 인서트
	public int insertFlight(FlightVO vo);
	//항공스케줄 불러오기(수정페이지)
	public FlightVO getFlightInfo(int fno);
	////항공스케줄 수정
	public int modifyFlight(FlightVO vo);
	//모달용 데이터 인서트
	public int insertFlightLog(FlightVO vo);
	//모달정보불러오기
	public FlightVO flightNoticePopup();
	//항공스케줄 삭제
	public int deleteFlight(FlightVO vo);
	//지역코드 받아오기
	public int getRegionCode(String depName);
	public int getArrRegionCode(String arrName);

}
