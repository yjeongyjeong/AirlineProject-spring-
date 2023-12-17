package com.airline.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermsVO {
/* termsCode	int
termstitle	varchar(100)
termsContents	longtext
 * */
	public int termsCode;
	public String termsTitle;
	public String termsContents;
}
