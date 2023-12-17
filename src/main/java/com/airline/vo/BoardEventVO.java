package com.airline.vo;

import java.util.List; 

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*create table boardEvent(
    boardNum int auto_increment primary key,
    boardTitle varchar(1000) not null,
    boardContent longtext,
    startDate datetime default current_timestamp,
    endDate datetime generated always as (startDate + interval 6 month),
    regiDate datetime default current_timestamp,
    modifydate datetime default current_timestamp,
    readcount int default 0,
    repImg varchar(5000) DEFAULT 'noimage.gif'
);*/

@Getter @Setter @ToString
public class BoardEventVO {
	private int boardNum;
	private String boardTitle;
	private String boardContent;
	private String startDate;
	private String endDate;
	private String regiDate;
	private String modifyDate;
	private int readCount;
	private String repImg;
	private String filePath;
	private int isOngoing;
	
	private List<BoardEventFileVO> attachList;
}
