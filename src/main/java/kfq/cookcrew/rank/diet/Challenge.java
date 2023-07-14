package kfq.cookcrew.rank.diet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * 참여여부를 체크하기위한 엔티티 테이블
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Challenge {

    @EmbeddedId //복합키
    private ChallengeId challengeId;

    @Column(columnDefinition = "Boolean default false constraint validate check(validate in(true,false))")
    private Boolean validate;

}
