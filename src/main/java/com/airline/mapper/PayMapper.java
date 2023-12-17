package com.airline.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface PayMapper {

	public int chargePoint(@Param("userid")String userid,@Param("amount") int amount);

	public int updatePoint(@Param("point")int point,@Param("userid") String userid);

	public int updateKakao(@Param("kakao")int kakao,@Param("userid") String userid);
	//

}
