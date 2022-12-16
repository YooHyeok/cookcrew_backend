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
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DietApplicationTests extends BaseController {

	@Autowired
	DietRepository dietRepository;
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
	 * 쿼리 : SELECT dr.r_no FROM diet d WHERE meal_div = '1' AND user_id = 'user' AND diet_date = '2022-12-15'
	 */
	@Test
	void recipeConditionJoin() {
		List<Diet> diets = dietRepository.findByMealDivAndUserIdAndDietDate('1', "user", Date.valueOf("2022-12-15"));
		for (Diet diet : diets) {
			System.out.println("음 : "+diet);
		}
	}

}
