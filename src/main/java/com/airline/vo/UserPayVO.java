package com.airline.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPayVO {
	private int pno;
	private String userid;
	private String resno;
	private int price;
	private String getDate;
	private int mileage;
	private int sum;
	

}
