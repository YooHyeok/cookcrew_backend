package kfq.cookcrew.rating;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="rating")
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;
    @Column
    private Integer rno;
    @Column
    private String userId;
    @Column
    private Integer ratingValue;
}
