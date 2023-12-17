package com.airline.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.KakaoUserVO;

@Component
public interface FlightMapper {

	public List<FlightVO> getList(@Param("cri")Criteria cri);
	
	public List<FlightVO> getListSearch(@Param("cri")Criteria cri,@Param("dep") String dep,@Param("arr") String arr,@Param("time") String time);

	public List<FlightVO> getListSearchByFlightName(@Param("cri")Criteria cri, @Param("flightName") String flightName, @Param("time") String time);

	//public List<FlightVO> getListSearch2(@Param("dep") String dep,@Param("arr") String arr,@Param("time") String time);
	
	public FlightVO getClosestFlightPrev(@Param("dep") String dep,@Param("arr") String arr, @Param("time") String time);

	public FlightVO getClosestFlightAfter(@Param("dep") String dep,@Param("arr") String arr, @Param("time") String time);
	
	public int updateSeatCount(@Param("flightName") String flightName, @Param("time") String time);
	
	public int getTotal(Criteria cri);

	public int getTotalSearch(@Param("cri")Criteria cri,@Param("dep") String dep,@Param("arr") String arr,@Param("time") String time);
	
	public int getTotalSearchByFlightName(@Param("cri")Criteria cri, @Param("flightName") String flightName, @Param("time") String time);	

	public FlightVO getFlightInfo(int fno);
	
	public List<String> getDistinctDep(@Param("depName") String depName, @Param("depRegionCode") String depRegionCode);

	public List<String> getDistinctArrRegionCode(String depName);

	public List<String> getDistinctArrByDep1(@Param("depName")String depName, @Param("arrName")String arrName);

	public List<String> getDistinctArrByDep2(@Param("depName")String depName, @Param("arrName")String arrName, @Param("arrRegionCode") String arrRegionCode);

	public List<String> getDistinctFlightName(String flightName);

	public Integer getPrice(@Param("depname")String depName,@Param("arrname") String arrName);

	public int getRoundTripPrice(@Param("depname")String depName,@Param("arrname") String arrName);

	public int getUserAge(String userid);

	public float getAgeDiscount(@Param("age")int age);

	public int getKakaoPoint(String userid);

	public int getPoint(String userid);

	public int getCount(String userid);

	public String getEmail(String userid);
	
	public int getKCount(String userid);
	
	public float getSeatPc(String seat);

	public String getUserName(String userid);

	public int insertRes(Map<String, String> map);

	public int insertPay(@Param("rno")String rno,@Param("userid")String userid, @Param("total") Integer total,@Param("mileage") int mileage);

	public int getBuyCount(String userid);

	public int getTotalBuy(String userid);

	public int getCurrentMileage(String userid);

	public int logUpdate(@Param("userid")String userid,@Param("flightCount") int flightCount,@Param("flightSum") int flightSum,@Param("userPoint") int userPoint);

	public int getCode(int flightSum);

	public int updateGrade(@Param("userid")String userid,@Param("getCode") int getCode);

	public int getOriCode(String userid);

	public int insertGradeUpdate(@Param("userid")String userid,@Param("flightCount") int flightCount, @Param("flightSum")int flightSum, @Param("userPoint")int userPoint);

	public FlightResVO getResInfo(String rno);

	public KakaoUserVO getUserInfo(String userid);
	
	public FlightResVO getResFirst(String userid);

	public int usePoint(String userid);

	public List<FlightResVO> getResAll(@Param("flightName")String flightName,@Param("fno") int fno);

	public int ResCnt(String flightName);
}
