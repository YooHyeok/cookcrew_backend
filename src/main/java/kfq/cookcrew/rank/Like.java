package kfq.cookcrew.rank;

import lombok.*;

import javax.persistence.*;
/**
 * *****************************************************<p>
 * like_list 테이블 엔터티 클래스<p>
 * 프로그램 설명 : 찜목록 관련 추가, 조회, 삭제(업데이트) 테이블<p>
 * 담당 : 이규희<p>
 * *****************************************************<p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "like_list")
public class Like {
    @Id
    private String likerId;; // 회원 아이디
    @Column
    private Integer likedRno; // 레시피 번호


}
