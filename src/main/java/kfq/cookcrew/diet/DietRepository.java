package kfq.cookcrew.diet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface DietRepository extends JpaRepository<Diet,Integer> {

    /**
     * @Query SELECT dr.r_no FROM diet d WHERE meal_div = '1' AND user_id = 'user' AND diet_date = '지정날짜'
     * @param mealDiv
     * @param id
     * @param date
     * @return 식단 테이블로 부터 닐짜, 아침, user 조건을 갖는 레코드행 반환
     */
    List<Diet> findByMealDivAndUserIdAndDietDate(char mealDiv, String id, Date date);

}
