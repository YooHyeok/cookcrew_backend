package kfq.cookcrew.reciepe;

import kfq.cookcrew.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *****************************************************<p>
 * 패키지:kfq.cookcrew.reciepe<p>
 * 파일 : RecipeController.java<p>
 * Recipe 컨트롤러<p>
 * 프로그램 설명 : 레시피 리스트, 레시피등록, 레시피 상세, 레시피수정, 레시피삭제 <p>
 * 연관테이블 : recipe
 * 담당 : 이규희, 유재혁, 조현빈<p>
 * *****************************************************<p>
 */
@RestController
public class RecipeController extends BaseController {
    @Autowired
        RecipeService recipeService;
    @GetMapping("/recipelist")
    public ResponseEntity<List<Recipe>> recipeList(){
        ResponseEntity<List<Recipe>> res = null;
        try{
            List<Recipe> recipes = recipeService.recipeList();
            res = new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            res = new ResponseEntity<List<Recipe>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }



}
