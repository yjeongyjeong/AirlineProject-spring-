package com.airline.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.mapper.FlightMapper;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.KakaoUserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService{

	@Autowired
	private FlightMapper mapper;
	
	@Override
	public List<FlightVO> getList(Criteria cri) {
		return mapper.getList(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}

	@Override
	public List<FlightVO> getListSearch(Criteria cri, String dep, String arr, String time) {
		log.info("dep serviceImpl"+dep);
		log.info("arr serviceImpl"+arr);
		log.info("time serviceImpl"+time);
		System.out.println("dep serviceImpl"+dep);
		System.out.println("arr serviceImpl"+arr);
		System.out.println("time serviceImpl"+time);
		return mapper.getListSearch(cri, dep, arr, time);
	}
	
	@Override
	public List<FlightVO> getListSearchByFlightName(Criteria cri, String flightName, String time) {
		
		return mapper.getListSearchByFlightName(cri, flightName, time);
	}

	@Override
	public int getTotalSearch(Criteria cri, String dep, String arr, String time) {
		return mapper.getTotalSearch(cri, dep, arr, time);
	}

	@Override
	public int getTotalSearchByFlightName(Criteria cri, String flightName, String time) {
		return mapper.getTotalSearchByFlightName(cri, flightName, time);
	}
	
	@Override
	public List<String> getDistinctDep(String depName, String depRegionCode) {
		return mapper.getDistinctDep(depName, depRegionCode);
	}

	@Override
	public List<String> getDistinctArrRegionCode(String depName) {
		return mapper.getDistinctArrRegionCode(depName);
	}
	
	@Override
	public List<String> getDistinctArrByDep1(String depName, String arrName) {
		return mapper.getDistinctArrByDep1(depName, arrName);
	}

	@Override
	public List<String> getDistinctArrByDep2(String depName, String arrName, String arrRegionCode) {
		return mapper.getDistinctArrByDep2(depName, arrName, arrRegionCode);
	}

	@Override
	public List<String> getDistinctFlightName(String flightName) {
		return mapper.getDistinctFlightName(flightName);
	}
	
	@Override
	public FlightVO getClosestFlightPrev(String dep, String arr, String time) {
		return mapper.getClosestFlightPrev(dep, arr, time);
	}
	
	@Override
	public FlightVO getClosestFlightAfter(String dep, String arr, String time) {
		return mapper.getClosestFlightAfter(dep, arr, time);
	}

	@Override
	public int updateSeatCount(String flightName, String time) {
		return mapper.updateSeatCount(flightName, time);
	}
	
	@Override
	public FlightVO getFlightInfo(int fno) {
		return mapper.getFlightInfo(fno);
	}

	@Override
	public Integer getPrice(String depName, String arrName) {
		return mapper.getPrice(depName,arrName);
	}
	
	@Override
	public int getRoundTripPrice(String depName, String arrName) {

		return mapper.getRoundTripPrice(depName, arrName);
	}

	@Override
	public int getUserAge(String userid) {
		return mapper.getUserAge(userid);
	}

	@Override
	public float getAgeDiscount(int age) {
		return mapper.getAgeDiscount(age);
	}

	@Override
	public int getKakaoPoint(String userid) {
		return mapper.getKakaoPoint(userid);
	}

	@Override
	public int getPoint(String userid) {
		return mapper.getPoint(userid);
	}

	@Override
	public int getcount(String userid) {
		return mapper.getCount(userid);
	}

	@Override
	public String getEmail(String userid) {
		return mapper.getEmail(userid);
	}

	@Override
	public int getKcount(String userid) {
		return mapper.getKCount(userid);
	}

	@Override
	public float getSeatPc(String seat) {
		return mapper.getSeatPc(seat);
	}

	@Override
	public String getUserName(String userid) {
		return mapper.getUserName(userid);
	}

	@Override
	public int insertRes(Map<String, String> map) {
		return mapper.insertRes(map);
	}

	@Override
	public int insertPay(String resno, String userid, Integer total, int mileage) {
		return mapper.insertPay(resno,userid, total,mileage);
	}

	@Override
	public int getBuyCount(String userid) {
		return mapper.getBuyCount(userid);
	}

	@Override
	public int getTotalBuy(String userid) {
		return mapper.getTotalBuy(userid);
	}

	@Override
	public int getCurMileage(String userid) {
		return mapper.getCurrentMileage(userid);
	}

	@Override
	public int logUpdate(String userid, int flightCount, int flightSum, int userPoint) {
		return mapper.logUpdate(userid, flightCount, flightSum, userPoint);
	}

	@Override
	public int getGradeCode(int flightSum) {
		return mapper.getCode(flightSum);
	}

	@Override
	public int updateGrade(String userid, int getCode) {
		return mapper.updateGrade(userid, getCode);
	}

	@Override
	public int getOriCode(String userid) {
		return mapper.getOriCode(userid);
	}

	@Override
	public int insertGradeUpdate(String userid, int flightCount, int flightSum, int userPoint) {
		return mapper.insertGradeUpdate(userid, flightCount, flightSum, userPoint);
	}

	@Override
	public FlightResVO getResInfo(String rno) {
		return mapper.getResInfo(rno);
	}

	@Override
	public KakaoUserVO getUserInfo(String userid) {
		return mapper.getUserInfo(userid);
	}

	@Override
	public FlightResVO getResFirst(String userid) {
		return mapper.getResFirst(userid);
	}

	@Override
	public int usePoint(String userid) {
		return mapper.usePoint(userid);
	}

	@Override
	public List<FlightResVO> getResAll(String flightName, int fno) {
		return mapper.getResAll(flightName, fno);
	}

	@Override
	public int ResCnt(String flightName) {
		return mapper.ResCnt(flightName);
	}
	//

}
