package kfq.cookcrew;

import kfq.cookcrew.common.BaseController;
import kfq.cookcrew.diet.Diet;
import kfq.cookcrew.diet.DietRepository;
import kfq.cookcrew.reciepe.Recipe;
import kfq.cookcrew.reciepe.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DietApplicationTests extends BaseController {

	@Autowired
	DietRepository dietRepository;
	@Autowired
	RecipeRepository recipeRepository;

	@Test
	void dietFindAll() {
		List<Diet> all = dietRepository.findAll();
		LOGGER.info("결과 | 전체 : {}",all);
	}

	@Test
	void dietFindById() {
		Optional<Diet> byId = dietRepository.findById(2);
		Diet diet1 = byId.get();
		Diet diet2 = dietRepository.findById(2).get();
		LOGGER.info("결과 | diet1 : {}, diet2 : {}",diet1,diet2);
	}

	@Test
	void recipeFindAll() {
		List<Recipe> all = recipeRepository.findAll();
		LOGGER.info("전체 : {}",all);
	}

	/**
	 * 메서드명명규칙
	 * 식단 리스트 (Recipe 엔티티 Join)
     * @Description 식단 조회 (#> 레피시엔티티 JOIN 레시피 글번호 조회)
	 * @Query  SELECT dr.r_no FROM diet d WHERE meal_div = {'2'} AND user_id = {'user'} AND diet_date = {'2022-12-15'}
	 */
	@Test
	void recipeConditionJoin() {
		List<Diet> diets = dietRepository.findByMealDivAndUserIdAndDietDate('2', "user", Date.valueOf("2022-12-15"));
		for (Diet diet : diets) {
			System.out.println("음 : "+diet);
		}
	}

	/**
	 * @Description 레시피 Find
	 * @Query SELECT * FROM recipe WHERE r_no = {게시글번호}
	 */
	@Test
	void recipeFind() {
		Optional<Recipe> recipeList = recipeRepository.findById(2);
		System.out.println("음음 : "+recipeList.get());
	}

	/**
	 * @Description 다이어트 레시피 insert/update -> 글번호를 List 혹은 배열로 받은 후 반복문으로 처리?
	 * @Query INSERT INTO diet VALUES(식단번호, 아이디, 식단날짜, 식사구분, 달성여부, 목표칼로리, 레시피번호)
	 */
	@Test
	void dietSave() {

		Recipe recipe = recipeRepository.findById(2).get();
		LOGGER.info(recipe.toString());
		Diet diet = new Diet(0
				,"user"
				, Date.valueOf("2022-12-15")
				,'2'
				,null
				, 400
				,recipe);
		dietRepository.save(diet);
	}

	/**
	 * @Description : 배열로 넘겨받은 글번호 파라미터를 식단에 등록 혹은 갱신
	 * @Param : Array => rNoArray {1,2,3,4}
	 * @Query-SELECT : SELECT * FROM recipe WHERE r_no = {rNoArray 루프}
	 * @Return-SELECT : r_no를 담고있는 Recipe객체
	 * @Query-INSERT : [루프] <p>INSERT INTO diet VALUES({"아이디"},{"날짜"},{"식사구분"},{달성여부},{목표칼로리})
	 * @Query-UPDATE : [루프] <p>UPDATE diet SET user_id={"user"},diet_date={"2022-12-15"}, meal_div={'2'}, achieve={'y'}, target_kcal={400} WHERE r_no={rNo 루프}
	 *
	 * 추가 수정 삭제시 처리방안...
	 * UI에서 (-) 버튼 클릭시 배열에서 값 제거... 원본데이터와 비교?
	 * (-)버튼 클릭시 바로 DELETE를 한다면 겁색후 append도 바로 Insert해야한다.
	 * 만약 데이터가 있다면?
	 * 값을 append하는 순간 Recipe의 rno값만 넘기고 값을 제거하는 순간에도 Recipe의 rno값만 제거한다.
	 */
	@Test
	void dietUpdate() {
		//게시글 번호
		Integer[] rNoArray = {1,2,3,4};
		List<Integer> rNoList = new ArrayList<>(Arrays.asList(rNoArray));
		System.out.println(rNoList);
		// 여러번 Save 혹은 Update
		for(Integer rNo : rNoList) {
			Recipe recipe = recipeRepository.findById(rNo).get();
			Diet diet = new Diet(0
								,"user"
								, Date.valueOf("2022-12-15")
								,'2'
								,null
								, 400
								,recipe);
			dietRepository.save(diet);
		}
	}

	@Test
	void recipeSearch() {
		String resultParam = "";
		String searchTitle = "소고기";
		String wildCard = "%";
		resultParam = wildCard + searchTitle + wildCard;
//		LOGGER.info(resultParam);
//		List<Recipe> byTitleLikeList = recipeRepository.findByTitleLike(resultParam);
//		for(Recipe searchRecipe:byTitleLikeList) {
//			LOGGER.info("검색 결과 : {}",searchRecipe);
//		}
		List<Recipe> byTitleLikeList = recipeRepository.searchByTitleLike(resultParam);
		System.out.println(byTitleLikeList);
		for(Recipe searchRecipe: byTitleLikeList) {
//			LOGGER.info("검색 결과 : {}, {}",searchRecipe.getRno(),searchRecipe.getTitle());
			System.out.println(searchRecipe);
		}



	}
}

