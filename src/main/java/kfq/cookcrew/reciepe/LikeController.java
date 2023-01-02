package kfq.cookcrew.reciepe;

import kfq.cookcrew.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController extends BaseController {
    @Autowired
    LikeService likeService;
    @PostMapping("/like")
    public ResponseEntity<String> liking(@PathVariable Integer rno, String userId) {
        ResponseEntity<String> res = null;
        try{
             likeService.toggleLike(rno,userId);
             res = new ResponseEntity<String>("찜 목록에 추가", HttpStatus.OK);
            return res;
        }catch(Exception e) {
            res = new ResponseEntity<String>("찜하기 실패", HttpStatus.BAD_REQUEST);
            return res;
        }
    }


}
