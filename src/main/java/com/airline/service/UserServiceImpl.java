package com.airline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.mapper.UserMapper;
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

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper mapper;
	
	
	@Override
	public KakaoUserVO getUserInfo(String userid) {
		return mapper.getUser(userid);
	}


	@Override
	public String getGrade(int gradeCode) {
		return mapper.getGrade(gradeCode);
	}


	@Override
	public UserPayVO getPoint(String userid) {
		return mapper.getPoint(userid);
	}


	@Override
	public int getSumP(String userid) {
		return mapper.getSumP(userid);
	}


	@Override
	public List<UserPayVO> getPoint3(String userid) {
		return mapper.getPoint3(userid);
	}


	@Override
	public int getSumK(String userid) {
		return mapper.getSumK(userid);
	}


	@Override
	public List<PointVO> getKakao3(String userid) {
		return mapper.getKakao3(userid);
	}


	@Override
	public List<BoardDiaryVO> getDiary(String userid) {
		return mapper.getDiary(userid);
	}


	@Override
	public List<KakaoUserVO> getUserInfoAll() {
		return mapper.getUserInfoAll();
	}


	@Override
	public List<UserPayVO> getSale() {
		return mapper.getSale();
	}


	@Override
	public List<CancelVO> reqCancel() {
		return mapper.reqCancel();
	}


	@Override
	public List<FlightResVO> getFlightres() {
		return mapper.getFlightres();
	}


	@Override
	public List<BoardNoticeVO> getNotice() {
		return mapper.getNotice();
	}


	@Override
	public List<BoardEventVO> getEvent() {
		return mapper.getEvent();
	}


	@Override
	public int chargePoint(String userid, int amount) {
		return mapper.chargePoint(userid,amount);
	}


	@Override
	public List<PointVO> getKPoint(String userid, Criteria cri) {
		return mapper.getKPoint(userid,cri);
	}


	@Override
	public List<UserPayVO> getMPoint(String userid, Criteria cri) {
		return mapper.getMPoint(userid,cri);
	}


	@Override
	public int getKTotal(String userid, Criteria cri) {
		return mapper.getKTotal(userid, cri);
	}


	@Override
	public int getMTotal(String userid, Criteria cri) {
		return mapper.getMTotal(userid, cri);
	}


	@Override
	public List<FlightResVO> getUserRes(String userid, Criteria cri) {
		return mapper.getUserRes(userid,cri);
	}


	@Override
	public int getCountBuy(String userid) {
		return mapper.getCountBuy(userid);
	}


	@Override
	public int getTotal(String userid) {
		return mapper.getTotal(userid);
	}


	@Override
	public int getFlightTotal(String userid, Criteria cri) {
		return mapper.getFlightTotal(userid,cri);
	}


	@Override
	public int cancelTicket(String data) {
		return mapper.cancelTicket(data);
	}


	@Override
	public int checkin(String data) {
		return mapper.checkin(data);
	}


	@Override
	public List<FlightResVO> getFlight3(String userid) {
		return mapper.getFlight3(userid);
	}


	@Override
	public String getName(String userid) {
		return mapper.getUserName(userid);
	}


	@Override
	public List<BoardQnaVO> getQna(String username) {
		return mapper.getQna(username);
	}


	@Override
	public List<BoardDiaryVO> getUserDiary(String username, Criteria cri) {
		return mapper.getUserDiary(username, cri);
	}


	@Override
	public int getUserDiaryCnt(String username, Criteria cri) {
		return mapper.getUserDiaryCnt(username, cri);
	}


	@Override
	public List<BoardQnaVO> getUserQna(String username, Criteria cri) {
		return mapper.getUserQna(username,cri);
	}


	@Override
	public int getUserQnaCnt(String username, Criteria cri) {
		return mapper.getUserQnaCnt(username,cri);
	}
	
	@Override
	public List<GradeLogVO> getGradeLog(String userid, Criteria cri) {
		return mapper.getGradeLog(userid, cri);
	}


	@Override
	public int getGradeLogCnt(String userid, Criteria cri) {
		return mapper.getGradeLogCnt(userid, cri);
	}


	@Override
	public List<FlightVO> getFlightList3() {
		return mapper.getFlightList3();
	}


	@Override
	public int getMileage(String userid) {
		return mapper.getMileage(userid);
	}


	@Override
	public void modifyUserInfo(String userId, String userNameK, String userNameE, String phone,
			int postCode, String address) {
		mapper.updateUserInfo(userId, userNameK, userNameE, phone, phone, postCode, address);
	}


	@Override
	public void modifyUserPwd(String userId, String pwd) {
		mapper.updateUserPwd(userId, pwd);
	}


	@Override
	public void modifyUserNick(String userId, String userNick) {
		mapper.updateUserNick(userId, userNick);
	}


	@Override
	public int getEnabled(String userId) {
		return mapper.getEnabled(userId);
	}


	

}
