package kfq.cookcrew.rank;

import kfq.cookcrew.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * *****************************************************<p>
 * 패키지:kfq.cookcrew.rank<p>
 * 파일 : RankController.java<p>
 * 프로그램 설명 : 베스트쉐프, 챌린지랭킹 조회<p>
 * 연관테이블 : likeList, score<p>
 * 담당 : 이규희<p>
 * *****************************************************<p>
 */
@RestController
public class LikeController extends BaseController {
    @Autowired
    LikeService likeService;
    LikeRepository likeRepository;

    @PostMapping("/addlike")
    public ResponseEntity<String> addLike(
            @RequestParam("likerId") String likerId,
            @RequestParam("likedRno") Integer likedRno) {

        ResponseEntity<String> res = null;
        try {
            likeService.addLike(likerId, likedRno);
            res = new ResponseEntity<String>("찜목록에 추가했습니다.", HttpStatus.OK);
        }catch (Exception e){
            res = new ResponseEntity<String>("찜 저장에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }



//        ResponseEntity<String> res = null;
//        if (((likeRepository.findById(likerId).isPresent())&&(likeRepository.findById(likerId).get().getLikedRno()) == likedRno)) {
//            try {
//                likeService.ccLike(likerId, likedRno);
//                res = new ResponseEntity<String>("찜목록에서 제거되었습니다.", HttpStatus.OK);
//            } catch (Exception e) {
//                res = new ResponseEntity<String>("찜 취소 실패", HttpStatus.BAD_REQUEST);
//            }
//
////        } else {
////            try {
////                likeService.addLike(likerId, likedRno);
////                System.out.println("찜저장");
////                res = new ResponseEntity<String>("찜목록에 추가되었습니다.", HttpStatus.OK);
////            } catch (Exception e) {
////                e.printStackTrace();
////                if (likeRepository.findById(likerId).get().getLikedRno().equals(likedRno)) {
////                    res = new ResponseEntity<String>("이미 찜했습니다.", HttpStatus.BAD_REQUEST);
////                } else {
////                    res = new ResponseEntity<String>("찜하기 실패", HttpStatus.BAD_REQUEST);
////                }
////            }
//        }

        return res;
    }
}











    /**
     * 챌린지 랭킹 메서드
     */




