package kfq.cookcrew.reciepe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    /**
     * 작성자 : 유재혁
     * 검색어 포함 SELECT 함수
     * @param searchParam
     * @return
     */
    public List<Recipe> searchByTitleLike(String searchParam) {
        return recipeRepository.searchByTitleLike(searchParam);
    }

    //레시피 작성
    public void rcpReg( String userId,String title, String sTitle, String mat, String source, Double kcal, String toastHtml, String toastMarkdown,
                       MultipartFile file)
            throws Exception {
        Recipe r = new Recipe();
//        System.out.println(r);
        r.setTitle(title);
        r.setRegId(userId);
        r.setContent(toastHtml);
        r.setRegDate(new Date(System.currentTimeMillis()));
        r.setModDate(new Date(System.currentTimeMillis()));
        r.setStitle(sTitle);
        r.setMat(mat);
        r.setSource(source);
        r.setCnt(0);
        r.setKcal(kcal);
        r.setEnabled(true);
        String filename = null;
        if (file != null && !file.isEmpty()) {
            String path = "C:/cookcrew_temp/recipe_thumbnail/";
            filename = file.getOriginalFilename();
            File dFile = new File(path + filename);
            file.transferTo(dFile);
        }
        r.setThumbPath(filename);
        Recipe save = recipeRepository.save(r);
//        System.out.println("##############"+save+"##############");
//        System.out.println(filename);
    }
    public void rcpModReg(MultipartFile file, Recipe recipe)
            throws Exception {
        recipe.setRating(0.0);
        recipe.setEnabled(true);
        recipe.setModDate(new Date(System.currentTimeMillis()));
        String filename = null;
        if (file != null && !file.isEmpty()) {
            String path = "C:/cookcrew_temp/recipe_thumbnail/";
            filename = file.getOriginalFilename();
            File dFile = new File(path + filename);
            file.transferTo(dFile);
            recipe.setThumbPath(filename);
        }

        Recipe save = recipeRepository.save(recipe);
    }

    //레시피 상세보기
    public Recipe rcpRef(Integer rNo) throws Exception {
        Optional<Recipe> orecipe = recipeRepository.findById(rNo);
//        System.out.println(orecipe.get());

        if(!orecipe.isPresent() && !orecipe.get().getEnabled()) {
            throw new Exception("삭제된 글입니다.");
        }
        return orecipe.get();
    }

    //레시피 리스트
    //페이징처리 추가 필요
    public List<Recipe> recipeList() throws Exception {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes;
    }

    public Integer updateCnt(Integer rNo) throws Exception {

        //Integer orecipe = recipeRepository.getCntByRno(rNo);

//        if(!orecipe.isPresent()) throw new Exception("조회수 오류");
        //Recipe recipe = new Recipe();
        Optional<Recipe> orecipe = recipeRepository.findById(rNo);
        if(orecipe.isEmpty()) {
            throw new Exception("레시프 조회 오류");
        }
        Recipe recipe = orecipe.get();
//        System.out.println(recipe.getCnt());
        recipe.incrementCnt();
        recipeRepository.save(recipe);
        return recipe.getCnt();
    }
    public void deleteRecipe(Integer rNo) throws Exception{
        recipeRepository.deleteByRno(rNo);
        }

}
