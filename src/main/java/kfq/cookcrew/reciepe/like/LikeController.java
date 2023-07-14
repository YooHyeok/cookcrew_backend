package kfq.cookcrew.reciepe.like;

import kfq.cookcrew.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController extends BaseController {
    @Autowired
    LikeService likeService;
    @PostMapping("/like")
    public ResponseEntity<String> liking(@RequestParam Integer rno, String userId) {
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

//    @GetMapping("/likestatus")
//    public ResponseEntity<List<Integer>> likestatus(@RequestParam String userId) {
//        ResponseEntity<List<Integer>> res = null;
//        try{
//            List<Integer> userLikeList = likeService.likeStatus(userId);
//            res = new ResponseEntity<List<Integer>>(userLikeList, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return res;
//    }
}
