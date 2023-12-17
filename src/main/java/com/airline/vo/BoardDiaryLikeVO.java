package com.airline.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*create table boardDiaryLike(
    likeNum int auto_increment primary key,      
    boardNum int,
    userId varchar(50),
    likeDate datetime default current_timestamp,
    constraint fk_like_userId foreign key(userId) references kakaouser(userId) on delete cascade,
    constraint fk_like_boardNum foreign key(boardNum) references boardDiary(boardNum) on delete cascade
);*/

@Getter @Setter @ToString
public class BoardDiaryLikeVO {
	private int likeNum;
	private int boardNum;
	private String userId;
	private String likeDate;
}
