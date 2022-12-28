package kfq.cookcrew.rank;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "rate_list")
public class Rate {
    @Id
    private Integer rNo; // 레시피 번호
    @Column
    private String id; // 회원 아이디
    @Column
    private Integer rating; //별점
}
