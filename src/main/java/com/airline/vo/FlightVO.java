package com.airline.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightVO {
	private int fno;
	private String flightName;
	private String depDay;
	private String arrDay;
	private String fullDeptime;
	private String depTime;
	private String fullArrtime;
	private String arrTime;
	private String flightTime;
	private String fullDeparture;
	private String depCode;
	private String depName;
	private String fullArrival;
	private String arrCode;
	private String arrName;
	private int depRegionCode;
	private int arrRegionCode;
	private String ageGroup; //나이구간
	private float ageDiscountRate; //나이구간 추가
	private String reason; //adminpage용
	public int isDelete; //0 default 등록 1 수정 2 삭제
	private int seatCount;
}
