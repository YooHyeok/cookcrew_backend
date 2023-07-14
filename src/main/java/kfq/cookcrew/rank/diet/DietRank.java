package kfq.cookcrew.rank.diet;

import lombok.*;
import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class DietRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer drPk;
    @Column
    private Date regDate;
    @Column
    private String userId;
    @Column
    private Integer achieveCnt;
    @Column
    private Integer achieveRank;
    @Column
    private Double achievePercentage;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private Integer sort;

}
