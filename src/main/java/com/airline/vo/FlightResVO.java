package com.airline.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FlightResVO {
	private String resno;
	private String userid;
	private String username;
	private String flightname;
	private String departure;
	private String arrival;
	private String arrtime;
	private String deptime;
	private String purchasetime;
	private String seatid;
	private int ispaid;  //0결제 1 미결제
	private int isCancel; //0취소안함 1취소
	private int isCheckin; //0체크인 안함 1체크인
	
	/*
	 * @Builder public FlightResVO(String resno, String userid, String username,
	 * String flightname,String departure, String arrival, String arrtime,String
	 * deptime,String seatid) { this.resno = resno; this.userid = userid;
	 * this.username = username; this.flightname = flightname; this.departure =
	 * departure; this.arrival = arrival; this.arrtime = arrtime; this.deptime =
	 * deptime; this.seatid = seatid; }
	 */
	//
	
}
