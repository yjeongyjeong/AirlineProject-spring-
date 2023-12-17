package com.airline.vo;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

		private int pageNum; //page num
		private int amount; //page 당 데이터 개수
		
		private String type; // T, C, W title content writer
		private String keyword; //검색 내용
		
		private String order;
		
		public Criteria() {
			this(1,10);
		}
		
		public Criteria(int pageNum, int amount) {
			this.pageNum = pageNum;
			if(pageNum<=1) {this.pageNum = 0;}
			this.amount = amount;
		}
		
		public String[] getTypeArr() {
			return type == null? new String[] {} : type.split("");
		}
		
		public int getNewStart() {
			return pageNum<1 ? 0 : (pageNum-1) * amount;
		}
		
		public String getListLink() {
			UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
													.queryParam("pageNum", this.pageNum)
													.queryParam("amount", this.getAmount())
													.queryParam("type", this.getType())
													.queryParam("keyword", this.getKeyword());
			return builder.toUriString();
		}
		
		public String getListLink2() {
			UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
													.queryParam("pageNum", this.pageNum)
													.queryParam("amount", this.getAmount())
													.queryParam("type", this.getType())
													.queryParam("keyword", this.getKeyword())
													.queryParam("order", this.getOrder());
			return builder.toUriString();
		}
		
}
