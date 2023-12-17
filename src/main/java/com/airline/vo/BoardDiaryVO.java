package com.airline.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*create table boardDiary(
    boardNum int auto_increment primary key,
    boardTitle varchar(1000) not null,
    boardContent longtext,
    boardWriter varchar(50) not null,  
    regidate datetime default current_timestamp,
    modifydate date default '1900-01-01',
    readcount int default 0,
    replycount int default 0,
    likecount int DEFAULT 0,
    constraint fk_diaryWriter foreign key(boardWriter) references kakaouser(userNick)
);*/

@Getter @Setter @ToString
public class BoardDiaryVO {
	private int boardNum;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String regiDate;
	private String modifyDate;
	private int readCount;
	private int replyCount;
	private int likeCount;
	private String userId;
}
