package com.airline.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airline.mapper.BoardEventFileMapper;
import com.airline.mapper.BoardEventMapper;
import com.airline.vo.BoardEventFileVO;
import com.airline.vo.BoardEventVO;
import com.airline.vo.Criteria;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class BoardEventServiceImpl implements BoardEventService{

	private final BoardEventMapper mapper;
	
	private final BoardEventFileMapper fileMapper;
	
	@Override
	public List<BoardEventVO> getListwithPaging(Criteria criteria) {
		log.info("get list with paging service");
		return mapper.getListwithPaging(criteria);
	}

	@Override
	public List<BoardEventVO> getListPastEvent(Criteria criteria) {
		log.info("get list with paging service");
		return mapper.getListPastEvent(criteria);
	}

	@Transactional
	@Override
	public BoardEventVO get(int boardNum) {
		log.info("get service");
		mapper.updateReadCount(boardNum);
		return mapper.get(boardNum);
	}

	@Transactional
	@Override
	public void insert(BoardEventVO board) {
		log.info("insert service");
		
		mapper.insert(board);
		
		if(board.getAttachList()==null||board.getAttachList().size()<=0) {
			return;
		}
		
		board.getAttachList().forEach(attach->{
			attach.setBoardNum(board.getBoardNum());
			fileMapper.insert(attach);
		});
		
		BoardEventFileVO vo = fileMapper.findRepImg(board.getBoardNum());
		String repImg = vo.getFileName();
		String newPath = vo.getUploadPath().substring(0,4) + "/"+ vo.getUploadPath().substring(5,7) + "/" + vo.getUploadPath().substring(8,10);
		String filePath = newPath + "/" + vo.getUuid() + "_"+ vo.getFileName();
		mapper.updateRepImg(repImg, filePath, board.getBoardNum());
	}

	@Transactional
	@Override
	public boolean delete(int boardNum) {
		log.info("delete service");
		
		fileMapper.deleteAll(boardNum);
		
		return mapper.delete(boardNum)==1;
	}

	@Transactional
	@Override
	public boolean update(BoardEventVO board) {
		log.info("update service");
		
		fileMapper.deleteAll(board.getBoardNum());
		
		boolean result = mapper.update(board)==1;
		
		if(result&&board.getAttachList()!=null&&board.getAttachList().size()>0) {
			board.getAttachList().forEach(attach->{
				attach.setBoardNum(board.getBoardNum());
				fileMapper.insert(attach);
			});
		}
		
		BoardEventFileVO vo = fileMapper.findRepImg(board.getBoardNum());
		String repImg = vo.getFileName();
		String newPath = vo.getUploadPath().substring(0,4) + "/"+ vo.getUploadPath().substring(5,7) + "/" + vo.getUploadPath().substring(8,10);
		String filePath = newPath + "/" + vo.getUuid() + "_"+ vo.getFileName();
		mapper.updateRepImg(repImg, filePath, board.getBoardNum());
		
		return result;
	}

	@Override
	public int getTotalCount(Criteria cri) {
		log.info("getTotalCount");
		return mapper.getTotalCount(cri);
	}

	@Override
	public int getTotalCountPastEvent(Criteria cri) {
		log.info("getTotalCountPastEvent");
		return mapper.getTotalCountPastEvent(cri);
	}

	@Override
	public List<BoardEventFileVO> getFileList(int boardNum) {
		log.info("get file list by boardNum" + boardNum);
		return fileMapper.searchFileByBoardNum(boardNum);
	}

	@Override
	public List<BoardEventFileVO> getRepImgList() {
		log.info("getRepImg service impl");
		return fileMapper.findRepImgFiles();
	}

	@Override
	public List<BoardEventVO> getListOverDue(String time) {
		log.info("getListOverDue service impl");
		return mapper.getListOverDue(time);
	}

	@Override
	public int updateOngoing(int boardNum) {
		log.info("updateOngoing service impl");
		return mapper.updateOngoing(boardNum);
	}
	
	
	
}
