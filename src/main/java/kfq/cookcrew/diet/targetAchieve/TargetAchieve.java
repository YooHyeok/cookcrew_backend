package kfq.cookcrew.diet.targetAchieve;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class TargetAchieve {

    @EmbeddedId //복합키 (아이디, 날짜, 구분)
    private TargetAchieveId targetAchieveId;
    @ColumnDefault("0")
    private Integer targetKcal;
    @Column(columnDefinition = "boolean default false constraint achieve check(achieve in(true,false))")
    private Boolean achieve;

}
