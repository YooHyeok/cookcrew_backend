package kfq.cookcrew.rating;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RatingController {
    @Autowired
    RatingService ratingService;

    //별점 등록
    @PostMapping("/ratingreg")
    public ResponseEntity<String> ratingReg(String id, Integer ratingValue, Integer rNo) {
        ResponseEntity<String> res = null;
        try {
            ratingService.ratingReg(id, ratingValue, rNo);
            res = new ResponseEntity<String>("별점 저장성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("별점저장 실패",HttpStatus.BAD_REQUEST );
        }
        return res;
    }
    //별점뿌리기
    @GetMapping("/ratingvalue/{rNo}")
    public ResponseEntity<List<Integer>> ratingValue(@PathVariable Integer rNo) {
        ResponseEntity<List<Integer>> res = null;
        try{
            List<Integer> rating = ratingService.requestRatingValue(rNo);
            System.out.println(rating);
            res = new ResponseEntity<List<Integer>>(rating,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Integer>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
}
