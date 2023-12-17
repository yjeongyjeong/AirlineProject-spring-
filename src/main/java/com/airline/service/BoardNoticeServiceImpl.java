package com.airline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.mapper.BoardNoticeMapper;
import com.airline.vo.AuthorityVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.Criteria;
import com.airline.vo.KakaoUserVO;

@Service
public class BoardNoticeServiceImpl implements BoardNoticeService{

	@Autowired
	private BoardNoticeMapper mapper;
	
	@Override
	public List<BoardNoticeVO> getList() {  
		return mapper.getList();
	}

	@Override 
	public List<BoardNoticeVO> getPageList(Criteria cri) {
		return mapper.getPageList(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}  
 
	@Override
	public BoardNoticeVO getOne(int boardnum) {
		return mapper.getOne(boardnum);
	} 

	@Override
	public void insert(BoardNoticeVO vo) {
		mapper.insert(vo);
	}

	@Override
	public void modify(BoardNoticeVO vo) {
		mapper.modify(vo);
	}

	@Override
	public void delete(int boardnum) {
		mapper.delete(boardnum);
	}

	@Override
	public void updateReadCount(int boardnum) {
		mapper.updateReadCount(boardnum);
	}

	@Override
	public List<KakaoUserVO> getUserList() {
		return mapper.getUserList();
	}

	@Override
	public AuthorityVO getAuthority(String userid) {
		return mapper.getAuthority(userid);
	}

	@Override
	public KakaoUserVO getUser(String userid) {
		return mapper.getUser(userid);
	}

	@Override
	public List<BoardNoticeVO> noticePopup(Criteria cri) {
		return mapper.noticePopup(cri);
	}

	@Override
	public int popupTotal() {
		return mapper.popupTotal();
	}
}