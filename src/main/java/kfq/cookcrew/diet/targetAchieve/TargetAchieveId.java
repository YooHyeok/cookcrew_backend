package kfq.cookcrew.diet.targetAchieve;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TargetAchieveId implements Serializable {
    @Column private String userId;
    @Column private Date dietDate;
    @Column(columnDefinition = "char default '1' constraint meal_div_achieve check(meal_div in('1','2','3'))")
    private Character mealDiv;
}
