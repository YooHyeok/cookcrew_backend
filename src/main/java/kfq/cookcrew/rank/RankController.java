package kfq.cookcrew.rank;

import kfq.cookcrew.common.BaseController;
import kfq.cookcrew.rank.diet.Challenge;
import kfq.cookcrew.rank.diet.DietRank;
import kfq.cookcrew.rank.diet.DietRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *****************************************************<p>
 * 패키지:kfq.cookcrew.rank<p>
 * 파일 : RankController.java<p>
 * 프로그램 설명 : 베스트쉐프, 챌린지랭킹 조회<p>
 * 연관테이블 : likeList, score<p>
 * 담당 : 이규희, 유재혁<p>
 * *****************************************************<p>
 */
@RestController
public class RankController extends BaseController {

    @Autowired
    private DietRankService dietRankService;

    /**
     * 베스트 쉐프 메서드
     */
    // TODO 이 주석을 지우고 해당 위치에 (현재 34번 라인) 메소드를 선언 하세요

    /**
     * 챌린지 랭킹 메서드 (정순/역순)
     */
    @GetMapping("/dietRankThree")
    public ResponseEntity<Object> dietRank() {
        ResponseEntity<Object> res = null;
        try {
            List<DietRank> dietRankASCList = dietRankService.findByRegDateAndSort(2);
            List<DietRank> dietRankDESCList = dietRankService.findByRegDateAndSort(1);
            Map<String,List<DietRank>> map = new HashMap<> ();
            map.put("ascList",dietRankASCList); // 순검색
            map.put("descList",dietRankDESCList); // 역검색
            res = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage() == "null") {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
            }
            res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    /**
     * 챌린지 랭킹 참여 여부
     * @param userId
     * @return
     */
    @GetMapping("/searchValidate")
    public ResponseEntity<Object> searchValidate(String userId) {
        ResponseEntity<Object> res = null;
        try {
            Challenge challenge = dietRankService.searchValidate(userId);
            System.out.println(challenge);
            res = new ResponseEntity<>(challenge, HttpStatus.OK);
        }catch (Exception e) {
            if (e.getMessage() == "null") {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
            }
            res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
    /**
     * 챌린지 랭킹 참여 여부
     * @param param
     * @return
     */
    @PutMapping("/saveValidate")
    public ResponseEntity<String> saveValidate(@RequestBody Map<String,String> param) {
        ResponseEntity<String> res = null;
        System.out.println(param);
        System.out.println(param.get("challenge"));
        try {
            dietRankService.saveValidate(
                    Boolean.parseBoolean(param.get("challenge"))
                    ,param.get("userId")
            );
        }catch (Exception e) {

        }
        return res;
    }
}
