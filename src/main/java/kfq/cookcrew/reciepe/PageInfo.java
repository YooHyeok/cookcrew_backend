package kfq.cookcrew.reciepe;

import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private Integer allPage;
    private Integer curPage;
    private Integer startPage;
    private Integer endPage;
}


