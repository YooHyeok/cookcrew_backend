package kfq.cookcrew.diet;

import kfq.cookcrew.common.BaseController;
import kfq.cookcrew.reciepe.Recipe;
import kfq.cookcrew.reciepe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
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

    /**
     * 식단 "표" 전체 조회 - 이벤트 값 출력
     * 식단구분, 날짜를 중복 제거해서 리스트로 반환한다
     * @return List<Map<String,Object>>
     */
    @GetMapping("/dietSearchMonthAll")
    public ResponseEntity<List<Map<String,Object>>> dietList() {

        ResponseEntity <List<Map<String,Object>>> result = null;
        List<Map<String,Object>> dietList = dietService.findDistinctDietDate();
        List<String> resList = new ArrayList();
        int i = 0;
        for(Map<String,Object> data : dietList) {
            for(String key : data.keySet()) {
                String value = data.get(key).toString();
//                System.out.println("{" + key + " : " + value+"}");
                resList.add(i++, "{" + key + " : " + value+"}");
            }
        }
        try {
            result = new ResponseEntity<>(dietList, HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>(dietList, HttpStatus.BAD_REQUEST);
        }
//        System.out.println(result);
        return result;
    }
    @GetMapping("/dietSearch")
    public ResponseEntity<List<Diet>> dietSearchByEvent(String dietDate, Character mealDiv) {

        LOGGER.info("dietDate : {}, mealDiv : {}", dietDate, mealDiv);

        Date StringToSqlDateFormat = Date.valueOf(dietDate);
        ResponseEntity <List<Diet>> result = null;
        List<Diet> dietListBy = dietService.findByDietDateAndMealDiv(StringToSqlDateFormat, mealDiv);

        try {
            result = new ResponseEntity<>(dietListBy, HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>(dietListBy, HttpStatus.BAD_REQUEST);
        }
//        System.out.println(result);
        return result;
    }
    @PostMapping("/dietAdd")
    public ResponseEntity<String> dietSave(@RequestBody Map<String,String> param) {
        LOGGER.info("saveParameter : {}", param);
        ResponseEntity<String> result = null;
//        System.out.println(param.get("rNo"));
        try {
//            Diet diet = new Diet(0, "userexample"
//                    , Date.valueOf(param.get("dietDate"))
//                    , param.get("mealDiv").charAt(0)
//                    , 'n'
//                    , 0
//                    , new Recipe(Integer.parseInt(param.get("rNo"))
//                    , ""
//                    , ""
//                    , ""
//                    , 0
//                    , '0'
//                    , null
//                    , null
//                    , 0.00f
//                    , "")
//            );
//            System.out.println("즐 : "+diet);
//            dietService.save(diet);


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @GetMapping("/recipeSearch")
    public ResponseEntity<List<Recipe>> recipeSearchList(String param) {
        System.out.println(param);
        ResponseEntity <List<Recipe>> result = null;
        try {
            List<Recipe> recipeSearchList = recipeService.searchByTitleLike(param);
            for(Recipe recipe : recipeSearchList) {
                LOGGER.info(recipe.toString());
            }
            result = new ResponseEntity<>(recipeSearchList, HttpStatus.OK);
        }catch (Exception e) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(result);
        return result;
    }

    @DeleteMapping("/dietDelete")
    public ResponseEntity<String> dietDelete(@RequestBody Map<String, String> param) {

        LOGGER.info("파라미터 값 : {}", param);
        Integer dNo = Integer.parseInt(param.get("dNo"));
        dietService.deleteById(dNo);
        return null;
    }
}
