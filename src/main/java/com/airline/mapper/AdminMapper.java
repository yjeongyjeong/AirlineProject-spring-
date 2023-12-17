package com.airline.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.airline.vo.BoardEventVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.CancelVO;
import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.FlightVO;
import com.airline.vo.KakaoUserVO;
import com.airline.vo.UserPayVO;

@Component
public interface AdminMapper {

	public List<CancelVO> getCancelList(@Param("cri")Criteria cri);

	public int getCancelCnt(@Param("cri")Criteria cri);

	public List<FlightResVO> getResInfo(@Param("cri")Criteria cri);

	public UserPayVO getPayInfo(String resno);

	public int updatePrice(@Param("resno")String resno,@Param("price") int price,@Param("mileage") int mileage);

	public int sumMile(String userid);

	public int sumPrice(String userid);

	public int insertlog(@Param("userid")String userid, @Param("sumMile")int sumMile,@Param("sumPrice") int sumPrice);

	public void updateCancel(String resno);

	public int getTicketCnt(Criteria cri);

	public List<KakaoUserVO> getUserInfo(@Param("cri")Criteria cri);

	public int getUserCnt(Criteria cri);

	public int updateUserInfo1(String userid);

	public int updateUserInfo0(String userid);

	public List<BoardNoticeVO> getNotice(@Param("cri")Criteria cri);

	public int noticeCnt(@Param("cri")Criteria cri);

	public int deleteNotice(int boardnum);

	public List<BoardEventVO> getEvent(@Param("cri")Criteria cri);

	public int eventCtn(@Param("cri")Criteria cri);
	
	public List<FlightVO> getFlightList(@Param("cri")Criteria cri);

	public int getFlightListCnt(@Param("cri")Criteria cri);

	public int getFno();

	@Select("SELECT DISTINCT depcode FROM airplaneschedule")
	public List<String> getDepCode();

	@Select("SELECT DISTINCT arrcode FROM airplaneschedule")
	public List<String> getArrcode();

	@Select("SELECT DISTINCT depregioncode FROM airplaneschedule")
	public List<Integer> getdRCode();

	@Select("SELECT DISTINCT arrregioncode FROM airplaneschedule")
	public List<Integer> getaRCode();
	
	@Select("SELECT DISTINCT fulldeparture FROM airplaneschedule where depcode=#{depCode}")
	public String getFullDeparture(String depCode);

	@Select("SELECT DISTINCT fullarrival FROM airplaneschedule where arrcode=#{arrCode}")
	public String getFullArrival(String arrCode);

	public int insertFlight(@Param("vo")FlightVO vo);

	public FlightVO getFlightInfo(int fno);

	public int modifyFlight(@Param("vo")FlightVO vo);

	public int insertFlightLog(@Param("vo")FlightVO vo);

	public FlightVO flightNoticePopup();

	public int deleteFlight(@Param("vo")FlightVO vo);

	public int getRegionCode(String depName);

	public int getArrRegionCode(String arrName);
	
	

}
