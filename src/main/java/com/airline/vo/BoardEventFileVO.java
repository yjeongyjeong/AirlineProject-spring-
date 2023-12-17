package com.airline.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*create table boardEventFile(
    fileNum int auto_increment primary key,
    boardNum int,
    uuid varchar(2000),
    uploadPath varchar(2000),
    fileName varchar(2000),
    fileType varchar(1) DEFAULT 'I',
    fileOrder varchar(50) DEFAULT 'uploadFile01',
	repImgYn varchar(1) default 'N',
    fileSize int,
    constraint fk_Evnet_file_boardNum foreign key(boardNum) references boardEvent(boardNum) on delete cascade
);*/

@Getter @Setter @ToString
public class BoardEventFileVO {
	private int fileNum;
	private int boardNum;
	private String uuid;
	private String uploadPath;
	private String fileName;
	private String fileType;
	private String fileOrder;
	private String repImgYn;
	private int fileSize;
}
