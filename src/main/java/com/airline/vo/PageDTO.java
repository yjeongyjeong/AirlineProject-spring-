package com.airline.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;
	private Criteria cri;
	
	private int realEnd;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		if(cri.getPageNum() == 0) {
			cri.setPageNum(1);
		}
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
		this.startPage = this.endPage -9;
		
		this.realEnd = (int)(Math.ceil((total)*1.0/cri.getAmount()));
		if(realEnd < endPage) endPage = realEnd;
		//버튼 활성화 여부(기본 변수가 boolean)
		this.prev = this.startPage >1;
		this.next = this.endPage < realEnd;
	}
}
