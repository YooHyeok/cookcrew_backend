package kfq.cookcrew.diet;

import kfq.cookcrew.common.BaseController;
import kfq.cookcrew.common.util.DateUtill;
import kfq.cookcrew.diet.targetAchieve.TargetAchieveRepository;
import kfq.cookcrew.reciepe.Recipe;
import kfq.cookcrew.reciepe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *****************************************************<p>
 * 패키지:kfq.cookcrew.diet<p>
 * 파일 : DietController.java<p>
 * 프로그램 설명 : 식단 리스트 출력, 식단 추가 수정<p>
 * 연관테이블 : diet, diet_recipe, recipe
 * 담당 : 유재혁<p>
 * *****************************************************<p>
 */
@RestController
@RequiredArgsConstructor //생성자 주입
public class DietController extends BaseController {

    private final RecipeService recipeService;
    private final DietService dietService;
    private final TargetAchieveRepository targetAchieveRepository;



    /**
     * 식단 "표" 전체 조회 - 이벤트 값 출력
     * 식단구분, 날짜를 중복 제거해서 리스트로 반환한다
     * @return List<Map<String,Object>>
     */
    @GetMapping("/dietSearchMonthAll")
    public ResponseEntity<Map<String,Object>> dietList(String userId) {

        ResponseEntity <Map<String,Object>> result = null;
        try {
            List<Map<String,Object>> dietList = dietService.findDistinctDietDate(userId);
            Date today = new Date(System.currentTimeMillis());
            Date startDate = DateUtill.SundayToSqlDate(String.valueOf(today));
            Date endDate = DateUtill.SaturdayToSqlDate(String.valueOf(today));
            Map<String, Object> map = new HashMap<>();
            map.put("dietList",dietList);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
//            for(Map<String,Object> data : dietList) {
//                for(String key : data.keySet()) {
//                    String value = data.get(key).toString();
//                    System.out.println("{" + key + " : " + value+"}");
//                }
//            }
            result = new ResponseEntity<>(map, HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return result;
    }
    @GetMapping("/dietSearch")
//    public ResponseEntity<List<Diet>> dietSearchByEvent(String userId, String dietDate, Character mealDiv) throws ParseException {
    public ResponseEntity<Map<String, Object>> dietSearchByEvent(String userId, String dietDate, Character mealDiv) throws ParseException {
        Date dietDateSql = Date.valueOf(dietDate);
        ResponseEntity<Map<String, Object>> result = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String,Object> dietData = dietService.findDietAndTargetAchieve(userId, dietDateSql, mealDiv);
            System.out.println(dietData);
            result = new ResponseEntity<>(dietData, HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        return result;
    }
    @PostMapping("/dietAdd")
    public ResponseEntity<String> dietAdd(@RequestBody Map<String,String> param) {
        LOGGER.info("AddParameter : {}", param);
        ResponseEntity<String> result = null;

        String userId = param.get("userId");
        Date dietDate = Date.valueOf(param.get("dietDate"));
        Character mealDiv = param.get("mealDiv").charAt(0);
        Integer rno = Integer.parseInt(param.get("rno"));
//        Recipe recipe = new Recipe();
//        recipe.setRno(rno);
        try {
            Diet diet = new Diet(0, userId
                    , dietDate
                    , mealDiv
//                    , recipe
                    , new Recipe(rno) // 생성자 주입...
            );
            dietService.save(diet);
            result = new ResponseEntity<>("식단추가성공", HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>("식단추가실패", HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    /**
     * 목표 칼로리 저장 API
     * @param param
     * @return
     */
    @PutMapping("/targetKcalSave")
    public ResponseEntity<String> targetKcalSave(@RequestBody Map<String,String> param) {
        LOGGER.info("saveParameter : {}", param);
        ResponseEntity<String> result = null;
        try {
            dietService.insertTargetSave(
                    param.get("userId")
                    , Date.valueOf(param.get("dietDate")) // 식단일자
                    , param.get("mealDiv").charAt(0)
                    , Integer.parseInt(param.get("targetKcal"))
                );
            result = new ResponseEntity<>("저장 성공", HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>("저장 실패", HttpStatus.BAD_REQUEST);
        }
        return result;
    }
    @PutMapping("/achieveSave")
    public ResponseEntity<String> achieveSave(@RequestBody Map<String,String> param) {
        LOGGER.info("saveParameter : {}", param);
        ResponseEntity<String> result = null;
        String dietDate = param.get("dietDate");
        try {
            dietService.updateAchieve(
                    param.get("userId")
                    , Date.valueOf(param.get("dietDate")) // 식단일자
                    , Boolean.parseBoolean(param.get("achieve"))
                    , param.get("mealDiv").charAt(0)
                );
            result = new ResponseEntity<>("저장 성공", HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>("저장 실패", HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    /**
     * 식단 레시피 검색 API
     * @param param
     * @return
     */
    @GetMapping("/recipeSearch")
    public ResponseEntity<List<Map<String, Object>>> recipeSearchList(String param) {
        System.out.println(param);
        ResponseEntity <List<Map<String, Object>>> result = null;
        try {
            List<Map<String, Object>> recipeSearchList = recipeService.searchByTitleLike(param);
//            for(Recipe recipe : recipeSearchList) {
//                LOGGER.info(recipe.toString());
//            }
            result = new ResponseEntity<>(recipeSearchList, HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(result);
        return result;
    }
    /**
     * 식단 삭제 API
     * @param param
     * @return
     */


    @DeleteMapping("/dietDelete")
    public ResponseEntity<String> dietDelete(@RequestBody Map<String, String> param) {
        ResponseEntity<String> res = null;
        LOGGER.info("파라미터 값 : {}", param);
        Integer dNo = Integer.parseInt(param.get("dNo"));
        try {
            dietService.deleteById(dNo);
        }catch (Exception e) {
            res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

}
