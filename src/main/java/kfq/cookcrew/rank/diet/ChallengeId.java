package kfq.cookcrew.rank.diet;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

/**
 * Challenge 엔티티 테이블의 복합키 설정
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ChallengeId implements Serializable {
    @Column private String userId;
    @Column private Date startDate;
    @Column private Date endDate;

}
