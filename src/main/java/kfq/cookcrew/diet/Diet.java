package kfq.cookcrew.diet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

/**
 * *****************************************************<p>
 * diet 테이블 엔터티 클래스<p>
 * 프로그램 설명 : 식단 리스트 입,출력 테이블<p>
 * 담당 : 유재혁<p>
 * *****************************************************<p>
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dNo; //식단 번호
    @Column
    private String id; //회원 아이디
    @Column
    private Date dietDate; //날짜
    @Column
    private Integer targetKcal; //목표 칼로리
    @Column
    private String mealDiv; //식사분류 (도메인 : 아침,점심,저녁 = 1,2,3)
    @Column
    private String achieve;

}


