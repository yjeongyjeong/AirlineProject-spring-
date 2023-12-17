package com.airline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.mapper.AdminMapper;
import com.airline.vo.BoardEventVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.CancelVO;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.UserPayVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminMapper mapper;

	@Override
	public List<CancelVO> getCancelList(Criteria cri) {
		return mapper.getCancelList(cri);
	}

	@Override
	public int getCancelCtn(Criteria cri) {
		return mapper.getCancelCnt(cri);
	}

	@Override
	public List<FlightResVO> getResInfo(Criteria cri) {
		return mapper.getResInfo(cri);
	}

	@Override
	public UserPayVO getPayInfo(String resno) {
		return mapper.getPayInfo(resno);
	}

	@Override
	public int updatePrice(String resno, int price, int mileage) {
		return mapper.updatePrice(resno,price,mileage);
	}

	@Override
	public int sumMile(String userid) {
		return mapper.sumMile(userid);
	}

	@Override
	public int sumPrice(String userid) {
		return mapper.sumPrice(userid);
	}

	@Override
	public int insertlog(String userid, int sumMile, int sumPrice) {
		return mapper.insertlog(userid, sumMile, sumPrice);
	}

	@Override
	public void updateCancel(String resno) {
		mapper.updateCancel(resno);
		
	}

	@Override
	public int getTicketCnt(Criteria cri) {
		return mapper.getTicketCnt(cri);
	}

	@Override
	public List<KakaoUserVO> getUserInfo(Criteria cri) {
		return mapper.getUserInfo(cri);
	}

	@Override
	public int getUserCnt(Criteria cri) {
		return mapper.getUserCnt(cri);
	}

	@Override
	public int updateUserInfo1(String userid) {
		return mapper.updateUserInfo1(userid);
	}

	@Override
	public int updateUserInfo0(String userid) {
		return mapper.updateUserInfo0(userid);
	}

	@Override
	public List<BoardNoticeVO> getNotice(Criteria cri) {
		return mapper.getNotice(cri);
	}

	@Override
	public int noticeCnt(Criteria cri) {
		return mapper.noticeCnt(cri);
	}

	@Override
	public int deleteNotice(int boardnum) {
		return mapper.deleteNotice(boardnum);
	}

	@Override
	public List<BoardEventVO> getEvent(Criteria cri) {
		return mapper.getEvent(cri);
	}

	@Override
	public int eventCtn(Criteria cri) {
		return mapper.eventCtn(cri);
	}
	
	@Override
	public List<FlightVO> getFlightList(Criteria cri) {
		return mapper.getFlightList(cri);
	}

	@Override
	public int getFlightListCnt(Criteria cri) {
		return mapper.getFlightListCnt(cri);
	}

	@Override
	public int getFno() {
		return mapper.getFno();
	}

	@Override
	public List<String> getDepcode() {
		return mapper.getDepCode();
	}

	@Override
	public List<String> getArrcode() {
		return mapper.getArrcode();
	}

	@Override
	public List<Integer> getdRCode() {
		return mapper.getdRCode();
	}

	@Override
	public List<Integer> getaRCode() {
		return mapper.getaRCode();
	}

	@Override
	public String getFullDeparture(String depCode) {
		return mapper.getFullDeparture(depCode);
	}

	@Override
	public String getFullArrival(String arrCode) {
		return mapper.getFullArrival(arrCode);
	}

	@Override
	public int insertFlight(FlightVO vo) {
		return mapper.insertFlight(vo);
	}

	@Override
	public FlightVO getFlightInfo(int fno) {
		return mapper.getFlightInfo(fno);
	}

	@Override
	public int modifyFlight(FlightVO vo) {
		return mapper.modifyFlight(vo);
	}

	@Override
	public int insertFlightLog(FlightVO vo) {
		return mapper.insertFlightLog(vo);
	}

	@Override
	public FlightVO flightNoticePopup() {
		return mapper.flightNoticePopup();
	}

	@Override
	public int deleteFlight(FlightVO vo) {
		return mapper.deleteFlight(vo);
	}

	@Override
	public int getRegionCode(String depName) {
		return mapper.getRegionCode(depName);
	}

	@Override
	public int getArrRegionCode(String arrName) {
		return mapper.getArrRegionCode(arrName);
	}
}
