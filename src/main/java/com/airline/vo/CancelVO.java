package com.airline.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CancelVO {
	private int cno;
	private String resno;
	private String userid ;
	private int isCancel;
	private int cancelOk;
	private String cancelTime;
	private String requestTime;
}
