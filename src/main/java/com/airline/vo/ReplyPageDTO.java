package com.airline.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyPageDTO {
	
	private int replyCount;
	private List<BoardDiaryReplyVO> list;
	
	
}
