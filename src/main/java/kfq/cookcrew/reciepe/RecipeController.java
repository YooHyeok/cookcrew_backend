package kfq.cookcrew.reciepe;

import kfq.cookcrew.common.BaseController;
import kfq.cookcrew.rating.RatingService;
import kfq.cookcrew.reciepe.like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    LikeService likeService;
    @Autowired
    RatingService ratingService;
    @GetMapping("/recipelist")
    public ResponseEntity<List<Recipe>> recipeList(){
        System.out.println("dldld");
        ResponseEntity<List<Recipe>> res = null;
        try{
            List<Recipe> recipes = recipeService.recipeList();
            for(Recipe recipe : recipes) {
            }
            res = new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            res = new ResponseEntity<List<Recipe>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping(value={"/recipepage/{page}","/recipes"}) // 전체레시피(최신순) Pagination
    public ResponseEntity<Map<String, Object>> recipePage(@PathVariable(required=false) Integer page,
                                                          @RequestParam(name = "userId", defaultValue = "guest", required = false) String userId){
        if(page == null) page=1;
        ResponseEntity<Map<String, Object>> res = null;
        try {
            System.out.println("userId:"+userId);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurPage(page);
            List<Recipe> recipes = recipeService.recipePage(pageInfo);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageInfo",pageInfo);
            map.put("recipes",recipes);
            List<Boolean> isLikedList = likeService.isLikedList(recipes, userId);

            List<Double> scoreList = ratingService.ratingList(recipes);

            map.put("isLikeds",isLikedList);
            map.put("scoreList",scoreList);

            System.out.println(isLikedList);
            System.out.println("scoreList");
            res = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            res = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping(value={"/poprecipepage/{page}","/poprecipes"})  // 전체레시피 (조회순) pagination
    public ResponseEntity<Map<String, Object>> popRecipePage(@PathVariable(required=false) Integer page,
                                                             @RequestParam(name="userId", defaultValue = "guest", required = false) String userId){
        if(page == null) page=1;
//        System.out.println(page);
        ResponseEntity<Map<String, Object>> res = null;
        try {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurPage(page);
            List<Recipe> recipes = recipeService.popRecipePage(pageInfo);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageInfo",pageInfo);
            map.put("recipes",recipes);
            System.out.println(recipes);
            List<Boolean> isLikedList = likeService.isLikedList(recipes, userId);
            map.put("isLikeds",isLikedList);

            res = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            res = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/poplist")
    public ResponseEntity<List<Recipe>> popList(){
        ResponseEntity<List<Recipe>> res = null;
        try{
            List<Recipe> recipes = recipeService.popRecipes();
            for(Recipe recipe : recipes) {
                System.out.println(recipe);
            }
            res = new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            res = new ResponseEntity<List<Recipe>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }


    @GetMapping("/popmain")  // 메인화면에 조회수 best 5
    public ResponseEntity<List<Recipe>> popMain(){
        ResponseEntity<List<Recipe>> res = null;
        try{
            List<Recipe> recipes = recipeService.popRecipes();
            ArrayList<Recipe> mainPop = new ArrayList<Recipe>();

            for (int i = 0 ; i< 5; i++) {
                mainPop.add(recipes.get(i));
            }

            res = new ResponseEntity<List<Recipe>>(mainPop, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            res = new ResponseEntity<List<Recipe>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }


    @GetMapping("/listmain")  // 메인 페이지에 최신레시피 5개
    public ResponseEntity<List<Recipe>> listMain(){
        System.out.println("dldld");
        ResponseEntity<List<Recipe>> res = null;
        try{
            List<Recipe> recipes = recipeService.recipeList();
            ArrayList<Recipe> mainList = new ArrayList<Recipe>();
            for(int i = 0 ; i< 5; i++) {
                mainList.add(recipes.get(i));
            }
            res = new ResponseEntity<List<Recipe>>(mainList, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            res = new ResponseEntity<List<Recipe>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @PostMapping("/rcpreg") //레시피 등록
    public ResponseEntity<String> rcpReg(
            @RequestParam(name = "file", required = false) MultipartFile file,
            String userId,
            String title,
            String sTitle,
            String mat,
            String source,
            Double kcal,
            String toastHtml,
            String toastMarkdown
    ) {
        ResponseEntity<String> res = null;
        try {
            recipeService.rcpReg( userId,title, sTitle, mat, source,kcal, toastHtml, toastMarkdown,file);
            res = new ResponseEntity<String>("게시글 저장성공", HttpStatus.OK);
//            System.out.println(res);
        }catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("게시글 저장실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @PostMapping("/rcpmodreg") //레시피
    public ResponseEntity<String> rcpmodreg(
            @RequestParam(name = "file", required = false) MultipartFile file, @ModelAttribute(name="recipe") Recipe recipe) {
//        System.out.println(file);

        ResponseEntity<String> res = null;
        try {
            recipeService.rcpModReg(file, recipe);
            res = new ResponseEntity<String>("게시글 저장성공", HttpStatus.OK);
//            System.out.println(res);
        }catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("게시글 저장실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

//    @PutMapping("/rcpmod/{rNo}")
//    public ResponseEntity<Recipe> rcpMod(@PathVariable Integer rNo) {
//        System.out.println("rNo는" + rNo);
//        ResponseEntity<Recipe> res = null;
//        try {
//            Recipe recipe = recipeService.rcpRef(rNo);
//            res = new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            res = new ResponseEntity<Recipe>(HttpStatus.BAD_REQUEST);
//        }
//        return res;
//    }


    @GetMapping("/rcpref/{rNo}") //레시피 상세조회 내용x
    public ResponseEntity<Recipe> rcpRef(@PathVariable Integer rNo) {
//        System.out.println("rNo는" + rNo);
        ResponseEntity<Recipe> res = null;
        try{
            Recipe recipe = recipeService.rcpRef(rNo);
            res = new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Recipe>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
    @GetMapping("/getToast/{rNo}") //상세조회 토스트 api
    public ResponseEntity<Recipe> testHtml(@PathVariable Integer rNo) {
        ResponseEntity<Recipe> res = null;
        try{
            Recipe recipe = recipeService.rcpRef(rNo);
            res = new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Recipe>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
    @GetMapping("/img/{filename}")//상세조회 첨부파일
    public void imageView(@PathVariable String filename, HttpServletResponse response) {
        try {
            String path = "C:/cookcrew_temp/recipe_thumbnail/";
            FileInputStream fis = new FileInputStream(path + filename);
            OutputStream out = response.getOutputStream();
            FileCopyUtils.copy(fis, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/cnt/{rNo}")
    public ResponseEntity<Integer> cnt(@PathVariable Integer rNo) {
        ResponseEntity<Integer> res = null;
//        System.out.println(rNo);
        try {
            Integer rcpCnt = recipeService.updateCnt(rNo);
            res = new ResponseEntity<Integer>(rcpCnt, HttpStatus.OK);
            //System.out.println(res);
        }
        catch (Exception e) {
            e.printStackTrace();
        } return res;
    }
    @PostMapping("/recipedelete")
    public void recipedelete(Integer rNo) {
//        System.out.println(id);
//        System.out.println(rNo);
        try {
            recipeService.deleteRecipe(rNo);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/myrecipe/{userId}")
    public ResponseEntity<List<Recipe>> myRecipe(@PathVariable String userId) {
//        System.out.println(userId);
        ResponseEntity<List<Recipe>> res = null;
        try{
            List<Recipe> myRecipe = recipeService.myRecipe(userId);
            System.out.println(myRecipe);
            res = new ResponseEntity<>(myRecipe,HttpStatus.OK);
//            System.out.println(res);
        }
        catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
    @GetMapping("mylikelist/{userId}")
    public ResponseEntity <List<Recipe>> likelist(@PathVariable String userId
    ) {
//        System.out.println(userId);
        ResponseEntity<List<Recipe>> res = null;
        try {
            List<Recipe> myLikelist = recipeService.likelist(userId);

            res = new ResponseEntity<>(myLikelist,HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
//    @GetMapping("mylikelist/{userId}")
//    public ResponseEntity<Optional<List<Map<Integer,Recipe>>>>myLikelist(@PathVariable String userId){
//        System.out.println(userId);
//        ResponseEntity<Optional<List<Map<Integer,Recipe>>>> res = null;
//        try {
//            Optional<List<Map<String,Recipe>>>olike = recipeService.myLikelist(userId);
//            res = new ResponseEntity<>(olike,HttpStatus.OK);
//            System.out.println(res);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }return res;
//    }
}