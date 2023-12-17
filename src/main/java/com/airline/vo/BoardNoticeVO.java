package com.airline.vo;

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
@Builder
public class BoardNoticeVO {

//	boardnum int not null primary key auto_increment,
//	boardsubject varchar(1000) not null,
//	boardcontent longtext,
//	boardwriter varchar(100) not null,
//	regidate datetime default current_timestamp,
//	modifydate datetime default current_timestamp,
//	readcount int default 0  
	  
	private String boardsubject; 
	private String boardcontent;  
	private String boardwriter; 
	private String regidate;
	private String modifydate;
	private int boardnum;
	private int readcount;
	private int emergency;
	
}
