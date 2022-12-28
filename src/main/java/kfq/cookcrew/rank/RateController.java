package kfq.cookcrew.rank;


import kfq.cookcrew.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateController {
    @Autowired
    RateService rateService;
    @PutMapping("/addrate")
    public ResponseEntity<String> addRate(Rate rate){
        ResponseEntity<String>res = null;

        try{
            Integer ratedRNo = rate.getRNo();
            String raterId = rate.getId();
            Integer rating = rate.getRating();
            rateService.addRate(
                    new Rate(ratedRNo,raterId, rating));
            res = new ResponseEntity<String>("평가가 저장되었습니다.", HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            res = new ResponseEntity<String>("평가저장에 실패했습니다.", HttpStatus.BAD_REQUEST);

        }
        return res;
    }
}



