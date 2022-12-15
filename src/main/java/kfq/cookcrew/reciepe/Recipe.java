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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rNo;
    @Column
    private String regId;
    @Column
    private String title;
    @Column
    private String content;
    @Column(columnDefinition = "int default 0") //default 0
    private String cnt;
    @Column
    private Character enabled;
    @Column
    private Date regDate;
    @Column
    private Date modDate;
    @Column
    private Integer kcal;
    @Column
    private String thumb_path;

//    @OneToOne -- Diet테이블과 양방향으로 지정
//    private Diet diet;
}
