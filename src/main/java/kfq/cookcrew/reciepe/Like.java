package kfq.cookcrew.reciepe;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name="likeList")
@IdClass(LikeID.class)
public class Like implements Serializable {
    @Id
    @Column(name = "rno")
    private Integer rno;
    @Id
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "isliked")
    private Boolean isliked;
}
