package kfq.cookcrew.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Page 정보에 대한 VO 클래스파일
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
	private Integer allPage; //전체페이지
	private Integer curPage; //현재페이지
	private Integer startPage; //시작페이지
	private Integer endPage; //끝페이지
}
