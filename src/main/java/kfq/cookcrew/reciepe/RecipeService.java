package kfq.cookcrew.reciepe;

import kfq.cookcrew.common.page.PageInfo;
import kfq.cookcrew.reciepe.like.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    LikeRepository likeRepository;

    /**
     * 작성자 : 유재혁
     * 검색어 포함 SELECT 함수
     * @param searchParam
     * @return
     */
    public List<Map<String, Object>> searchByTitleLike(String searchParam) {
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

        final String PATH = "C:/cookcrew_temp/recipe_thumbnail/";
        if (file != null && !file.isEmpty()) {
            filename = file.getOriginalFilename();
            File dFile = new File(PATH + filename);
            file.transferTo(dFile);
        }
        r.setThumbPath("/img/"+filename);
        r.setKcal(kcal);
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
        List<Recipe> recipes = recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "rno"));
        List<Recipe> enabled = recipeRepository.findEnabledRno(recipes);
        return enabled;
    }

    public List<Recipe> popRecipes() throws  Exception {
        List<Recipe> popRecipes = recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "cnt"));
        List<Recipe> enabled = recipeRepository.findEnabledCnt(popRecipes);
        return enabled;
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

    // 최신 순 정렬 페이지네이션
    public List<Recipe> recipePage(PageInfo pageInfo) throws Exception {
        PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage()-1, 12,
                Sort.by(Sort.Direction.DESC,"rno"));
        Page<Recipe> pages = recipeRepository.findEnabled(pageRequest);
        int maxPage = pages.getTotalPages();
        int curPage = pageInfo.getCurPage();
        System.out.println(curPage);
        int startPage = 0;
        int endPage = 0;
        if (curPage % 10 == 0){ //현재 페이지가 10으로 나눴을때 나머지가 0인 경우는 10의 배수이기 때문.
            startPage = pageInfo.getCurPage()/10*10-9;
            endPage = curPage;   //10, 20, 30, 40
        }else {
            startPage = pageInfo.getCurPage()/10*10+1;
            endPage = startPage+10 -1;   //10, 20, 30, 40
        }
        //1, 11, 21, 31...
        if(endPage>maxPage) endPage = maxPage;
//        System.out.println("page"+(pageInfo.getCurPage()));

        pageInfo.setAllPage(maxPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);

        return pages.getContent();
    }

    // 조회 순 정렬 페이지네이션
    public List<Recipe> popRecipePage(PageInfo pageInfo) throws Exception {
        PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage()-1, 12,
                Sort.by(Sort.Direction.DESC,"cnt"));
        Page<Recipe> pages = recipeRepository.findEnabled(pageRequest);
        int maxPage = pages.getTotalPages();
        int curPage = pageInfo.getCurPage();
        int startPage = 0;
        int endPage = 0;
        if (curPage %10 ==0){
            startPage = pageInfo.getCurPage()/10*10-9;
            endPage = curPage;	//10, 20, 30, 40
        }else {
            startPage = pageInfo.getCurPage()/10*10+1;
            endPage = startPage+10 -1;	//10, 20, 30, 40
        }
        //1, 11, 21, 31...
        if(endPage>maxPage) endPage = maxPage;

        pageInfo.setAllPage(maxPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);

        return pages.getContent();
    }

    public void deleteRecipe(Integer rNo) throws Exception{
        recipeRepository.deleteByRno(rNo);
    }
    public List<Recipe> myRecipe(String userId) throws Exception {
        List<Recipe> recipe = recipeRepository.findMyRecipe(userId);

        if (recipe == null) {
            throw new Exception("레시피 정보 없음");
        }
//        System.out.println("왓다팍  :"+recipe);
        return recipe;
    }
    public List<Map<String, Object>> likelist(String userId, PageInfo pageInfo) throws Exception{
        PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage()-1, 12,
                Sort.by(Sort.Direction.DESC,"rno"));
        Page<Map<String,Object>> pages = likeRepository.findByUserId(userId, pageRequest);
        int maxPage = pages.getTotalPages();
        int curPage = pageInfo.getCurPage();
        System.out.println(curPage);
        int startPage = 0;
        int endPage = 0;
        if (curPage %10 ==0){
            startPage = pageInfo.getCurPage()/10*10-9;
            endPage = curPage;   //10, 20, 30, 40
        }else {
            startPage = pageInfo.getCurPage()/10*10+1;
            endPage = startPage+10 -1;   //10, 20, 30, 40
        }
        //1, 11, 21, 31...
        if(endPage>maxPage) endPage = maxPage;
//        System.out.println("page"+(pageInfo.getCurPage()));

        pageInfo.setAllPage(maxPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        System.out.println(pages.getContent());
        return pages.getContent();
//        return null;
    }

    public List<Map<String,Object>> myrecipeList(String id) {
        return recipeRepository.findByUserId(id);
    }
    public List<Recipe> searchResultPage(PageInfo pageInfo, String keyword) throws Exception {
        PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage()-1, 12,
                Sort.by(Sort.Direction.DESC,"rno"));
        Page<Recipe> pages = recipeRepository.searchByKeyword(keyword,pageRequest);
        int maxPage = pages.getTotalPages();
        int curPage = pageInfo.getCurPage();
        System.out.println(curPage);
        int startPage = 0;
        int endPage = 0;
        if (curPage %10 ==0){
            startPage = pageInfo.getCurPage()/10*10-9;
            endPage = curPage;	//10, 20, 30, 40
        }else {
            startPage = pageInfo.getCurPage()/10*10+1;
            endPage = startPage+10 -1;	//10, 20, 30, 40
        }
        //1, 11, 21, 31...
        if(endPage>maxPage) endPage = maxPage;
//        System.out.println("page"+(pageInfo.getCurPage()));

        pageInfo.setAllPage(maxPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);

        return pages.getContent();
    }
    public List<Recipe> searchByKeyword(String searchParam) {
        return recipeRepository.searchByKeyword(searchParam);
    }

}
