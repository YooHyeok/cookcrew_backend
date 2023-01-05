package kfq.cookcrew.diet;

import kfq.cookcrew.reciepe.Recipe;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Date;

/**
 * *****************************************************<p>
 * diet 테이블 엔터티 클래스<p>
 * 프로그램 설명 : 식단 리스트 입,출력 테이블<p>
 * 담당 : 유재혁<p>
 * *****************************************************<p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicInsert
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dNo; //식단 번호
    @Column
    private String userId; //회원 아이디
    @Column
    private Date dietDate; //날짜
    @Column(columnDefinition = "char default '1' constraint meal_div check(meal_div in('1','2','3'))")
    private Character mealDiv; //식사분류 (default 1 도메인 : 아침,점심,저녁 = 1,2,3)
//    @Column(columnDefinition = "Boolean default false constraint achieve_diet check(achieve in(true,false))")
//    private Boolean achieve;
//    @Column(columnDefinition = "Boolean default false constraint challenge check(challenge in(true,false))")
//    private Boolean challenge;
//    @Column  private Integer targetKcal; //목표 칼로리

//    OneToOne을 할때 (create든 update든) 해당 매핑을 기준으로 컬럼을 Diet 엔티티에 인식(생성)시킨다.
//    @Column
//    private Integer rNo; //레시피 번호
//    @OneToOne(mappedBy = "diet", targetEntity = Recipe.class, fetch = FetchType.LAZY) //양방향일경우에 사용


    /**
     * 쿼리 : LEFT OUTER JOIN diet_recipe dr ON d.d_no = dr.d_no ;
     */
    @OneToOne
    @JoinColumn(name = "rno")
    private Recipe recipe; //getter메소드 롬복처리
}


