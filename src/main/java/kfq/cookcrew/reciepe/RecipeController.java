package kfq.cookcrew.reciepe;

import kfq.cookcrew.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
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
            String toastMarkdown) {
//        System.out.println(file);

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
}
