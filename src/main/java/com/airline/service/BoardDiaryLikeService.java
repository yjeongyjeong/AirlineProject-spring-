package com.airline.service;

import org.apache.ibatis.annotations.Param;

public interface BoardDiaryLikeService {

	public boolean insertLike(@Param("boardNum") int boardNum, @Param("userId") String userId);
	
	public boolean deleteLike(@Param("boardNum") int boardNum, @Param("userId") String userId);
	
	public int checkLike(@Param("boardNum") int boardNum, @Param("userId") String userId);
	
	public int likeCount(int boardNum);	
	
}
