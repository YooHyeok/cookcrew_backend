package kfq.cookcrew.reciepe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeID implements Serializable {
    private Integer rno;
    private String userId;
}
