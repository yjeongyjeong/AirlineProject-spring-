package com.airline.mapper;

import java.util.List;

import com.airline.vo.AuthorityVO;
import com.airline.vo.BoardNoticeVO;
import com.airline.vo.Criteria;
import com.airline.vo.KakaoUserVO;

public interface BoardNoticeMapper {
	
	public List<BoardNoticeVO> getList();

	public List<BoardNoticeVO> getPageList(Criteria cri);
	
	public int getTotal(Criteria cri);
	
	public BoardNoticeVO getOne(int boardnum);
	
	public void insert(BoardNoticeVO vo);
	
	public void modify(BoardNoticeVO vo);
	
	public void updateReadCount(int boardnum); 
	   
	public void delete(int boardnum); 
  
	public List<KakaoUserVO> getUserList();
	
	public AuthorityVO getAuthority(String userid);
	
	public KakaoUserVO getUser(String userid); 
	
	public List<BoardNoticeVO> noticePopup(Criteria cri);
	
	public int popupTotal();

}
