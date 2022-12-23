package kfq.cookcrew.diet;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DietDTO {
    private Integer rNo;
    private String regId;
    private String title;
    private String content;
    private Integer cnt;
    private Character enabled;
    private Date regDate;
    private Date modDate;
    private Integer kcal;
    private String thumb_path;
}
