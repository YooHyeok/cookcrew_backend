package kfq.cookcrew.reciepe;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

/**
 * *****************************************************<p>
 * recipe 테이블<p>
 * 프로그램 설명 : 전체레시피, 인기레시피 <p>
 * 담당 : 조현빈, 유재혁, 이규희 <p>
 * *****************************************************
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rno; // 레시피 번호
    @Column
    private String regId; // 작성자 아이디
    @Column
    private String title; //레시피 제목
    @Column
    private String content; //레시피 내용
    @Column(columnDefinition = "int default 0") //default 0
    private Integer cnt; // 조회수
    @Column(columnDefinition = "boolean default TRUE constraint achieve check(achieve in(TRUE,FALSE))") // default 'y'
    private Boolean enabled; // 삭제여부
    @Column
    private Date regDate; // 등록일자
    @Column
    private Date modDate; // 수정일자
    @Column
    private Double kcal; //칼로리
    @Column
    private String thumbPath; //썸네일경로
    @Column
    private String stitle; //상세설명
    @Column
    private String mat;//핵심재료
    @Column
    private String source;//양념
    @Column
    private Double rating;//별점

    public void incrementCnt() {
        this.cnt ++ ;
    }



//    @OneToOne -- Diet테이블과 양방향으로 지정
//    private Diet diet;
}