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

    public List<Recipe> searchByTitleLike(String searchParam) {
        return recipeRepository.searchByTitleLike(searchParam);
    }

    //레시피 작성
    public void rcpReg(String regId, String title , String sTitle, String mat, String source,Double kcal, String toastHtml, String toastMarkdown,
                       MultipartFile file )
            throws Exception{
        Recipe r = new Recipe();
        r.setTitle(title);
        r.setRegId(regId);
        r.setContent(toastHtml);
//        r.setKcal(0);
        r.setRegDate(new Date(System.currentTimeMillis()));
        r.setSTitle(sTitle);
        r.setMat(mat);
        r.setSource(source);
        r.setCnt(0);
        r.setKcal(kcal);
        String filename = null;
        if(file!=null && !file.isEmpty()) {
            String path = "C:/cookcrew_temp/recipe_thumbnail/";
            filename = file.getOriginalFilename();
            File dFile = new File(path + filename);
            file.transferTo(dFile);
        }
        r.setThumbPath(filename);
        Recipe save = recipeRepository.save(r);
        System.out.println("##############"+save+"##############");
        System.out.println(filename);
    }
    //레시피 상세보기
    public Recipe rcpRef(Integer rNo) throws Exception {
        Optional<Recipe> orecipe = recipeRepository.findById(rNo);
        if(orecipe.isPresent())
            return orecipe.get();
        throw new Exception("글 번호 오류");

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
        System.out.println(recipe.getCnt());
        recipe.incrementCnt();
        recipeRepository.save(recipe);
        return recipe.getCnt();
    }


}
