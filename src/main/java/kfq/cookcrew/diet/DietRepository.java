package kfq.cookcrew.diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface DietRepository extends JpaRepository<Diet,Integer> {


    @Query(value = "SELECT DISTINCT " +
            "CASE WHEN d.meal_div = 1 THEN '아침' " +
            "WHEN d.meal_div = 2 THEN '점심' " +
            "ELSE '저녁' " +
            "END as title" +
            ", CASE WHEN d.meal_div = 1 THEN 'lightblue' " +
            "WHEN d.meal_div = 2 THEN 'lightnavy' " +
            "ELSE 'darkgray' " +
            "END as color" +
            ", d.diet_date as date " +
            ", d.meal_div as mealDiv " +
            "FROM diet d " +
            "WHERE user_id = :userId " +
            "ORDER BY date ASC"
            ,nativeQuery = true) // 쿼리 테이블명은 Entity클래스명과 동일한 첫글자 대문자
    List<Map<String,Object>> findDistinctDietDate(@Param("userId") String userId);
    /**
     * @Query SELECT dr.r_no FROM diet d WHERE meal_div = '1' AND user_id = 'user' AND diet_date = '지정날짜'
     * @param mealDiv
     * @param id
     * @param date
     * @return 식단 테이블로 부터 닐짜, 아침, user 조건을 갖는 레코드행 반환
     */
    List<Diet> findByMealDivAndUserIdAndDietDate(char mealDiv, String id, Date date);

    List<Diet> findByUserIdAndDietDateAndMealDiv(String userId, Date stringToSqlDateFormat, Character mealDiv);

    @Modifying
    @Query(value = "UPDATE diet d " +
            "SET d.achieve = :achieve " +
            ", d.target_kcal = :targetKcal " +
            "WHERE d.diet_date = :dietDate " +
            "AND d.meal_div = :mealDiv"
            , nativeQuery = true)
    void updateDietSave(@Param("dietDate") Date dietDate
            , @Param("mealDiv") char mealDiv
            , @Param("achieve") Boolean achieve
            , @Param("targetKcal") int targetKcal);

    /**
     * 칼로리 랭킹
     * @param mealDiv
     * @param achieve
     * @param startDate
     * @param endDate
     * @return
     */

    String commonState = "FROM diet d LEFT OUTER JOIN recipe r on r.r_no = d.r_no WHERE achieve= 1 AND d.diet_date BETWEEN :startDate AND :endDate ";

    //AND d.meal_div = :mealDiv

//    String mealDiv1Avg = "SELECT (AVG(r.kcal)/d.target_kcal) as meal_div1 ";
//    String mealDiv2Avg = "SELECT (AVG(r.kcal)/d.target_kcal) as meal_div2 ";
//    String mealDiv3Avg = "SELECT (AVG(r.kcal)/d.target_kcal) as meal_div3 ";
//    String avgQuery = "SELECT avg("+ mealDiv1Avg + commonState + "AND d.meal_div = '1' +"
//                                + mealDiv2Avg + commonState + "AND d.meal_div = '2' +"
//                                + mealDiv3Avg + commonState + "AND d.meal_div = '3') FROM Diet";
//    @Query(value = "SELECT d.user_id as id" +
//                ", (AVG(r.kcal)/d.target_kcal)*100 FROM diet d AS rate " +
//                ", ROW_NUMBER() OVER(ORDER BY (AVG(r.kcal)/d.target_kcal)*100 DESC) AS rateRank " +
//                "FROM diet d "+
//                "LEFT OUTER JOIN recipe r "+
//                "ON r.r_no = d.r_no "+
//                "WHERE d.meal_div = :mealDiv " +
//                "  AND achieve = :achieve " +
//                "  AND d.diet_date BETWEEN :startDate AND :endDate "
//                , nativeQuery = true)
//    List<Map<String,Object>> kcalRank(
//            ,@Param("startDate") Date startDate
//            , @Param("endDate") Date endDate );
}
