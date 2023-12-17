package com.airline.mapper;

import org.apache.ibatis.annotations.Param;

public interface BoardDiaryLikeMapper {

	public int insertLike(@Param("boardNum") int boardNum, @Param("userId") String userId);
	
	public int deleteLike(@Param("boardNum") int boardNum, @Param("userId") String userId);
	
	public int checkLike(@Param("boardNum") int boardNum, @Param("userId") String userId);
	
	public int likeCount(int boardNum);	
	
}
