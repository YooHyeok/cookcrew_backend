package kfq.cookcrew.diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface DietRepository extends JpaRepository<Diet,Integer> {

    @Query(value = "SELECT DISTINCT" +
            " CASE WHEN d.meal_div = 1 THEN '아침'" +
            "WHEN d.meal_div = 2 THEN '점심' " +
            "ELSE '저녁'" +
            "END as title" +
            ", d.diet_date as date " +
            "FROM diet d ORDER BY d.meal_div ASC;",nativeQuery = true) // 쿼리 테이블명은 Entity클래스명과 동일한 첫글자 대문자
    List<Map<String,Object>> findDistinctDietDate();
    /**
     * @Query SELECT dr.r_no FROM diet d WHERE meal_div = '1' AND user_id = 'user' AND diet_date = '지정날짜'
     * @param mealDiv
     * @param id
     * @param date
     * @return 식단 테이블로 부터 닐짜, 아침, user 조건을 갖는 레코드행 반환
     */
    List<Diet> findByMealDivAndUserIdAndDietDate(char mealDiv, String id, Date date);
    List<Diet> findByDietDateAndMealDiv(Date stringToSqlDateFormat, Character mealDiv);



}
