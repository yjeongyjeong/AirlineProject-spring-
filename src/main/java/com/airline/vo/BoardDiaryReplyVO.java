package com.airline.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*create table boardDiaryReply(
    replyNum int auto_increment primary key,
    boardNum int,
    replyWriter varchar(50),
    replyContent varchar(4000),
    regiDate datetime default current_timestamp,
    modifyDate datetime default current_timestamp,
    constraint fk_boardNum foreign key(boardNum) references boardDiary(boardNum) on delete cascade,
    constraint fk_replyWriter foreign key(replyWriter) references kakaouser(userNick)
);*/

@Getter @Setter @ToString
public class BoardDiaryReplyVO {
	private int replyNum;
	private int boardNum;
	private String replyWriter;
	private String replyContent;
	private String regiDate;
	private String modifyDate;
	private String userId;
}
