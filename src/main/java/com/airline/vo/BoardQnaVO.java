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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardQnaVO {

//	create table boardqna(
//			boardnum int not null primary key auto_increment,
//			boardsubject varchar(1000) not null,
//			boardcontent longtext,
//			boardwriter varchar(100) not null,
//			regidate datetime default current_timestamp,
//			modifydate datetime default current_timestamp,
//			boardreref int,
//			boardrelevel int,
//			boardreseq int,   
//			readcount int default 0  
//			); 
	
	private int boardnum, boardreref, boardrelevel, boardreseq, readcount, repadmin;
	private String boardsubject, boardcontent, boardwriter, regidate, modifydate;
	
}
