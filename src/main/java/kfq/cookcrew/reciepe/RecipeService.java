package kfq.cookcrew.reciepe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    //레시피 작성
    public void writeRecipe(Recipe recipe, MultipartFile thumbFile) throws Exception {
        String filename = null;
        String thumbPath = null;
        if(thumbFile!= null && !thumbFile.isEmpty()) {
            String path = "C:/";    //폴더 만들어서 경로 통일 바람
            filename = thumbFile.getOriginalFilename();
            thumbPath = path + filename;
            File dFile = new File(path + filename);
            thumbFile.transferTo(dFile);
        }
        recipe.setThumbPath(thumbPath);
        recipeRepository.save(recipe);
    }
    //레시피 상세보기
    public Recipe detailRecipe(Integer rId) throws Exception {
        Optional<Recipe> oRecipe = recipeRepository.findById(rId);
        if(oRecipe.isPresent()) return oRecipe.get();
        throw new Exception("레시피번호 오류");
    }

    //레시피 리스트
    //페이징처리 추가 필요
    public List<Recipe> recipeList() throws Exception {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes;
    }


}
