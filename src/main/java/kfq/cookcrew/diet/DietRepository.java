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

    /**
     * 캘린더 첫 진입 페이지
     * 각 날짜에 해당하는 이벤트 정보 조회 쿼리 메소드
     * @param userId
     * @return
     */
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
            ,nativeQuery = true)
    List<Map<String,Object>> findDistinctDietDate(@Param("userId") String userId);

    /**
     * @Query SELECT dr.r_no FROM diet d WHERE meal_div = '1' AND user_id = 'user' AND diet_date = '지정날짜'
     * @param mealDiv
     * @param id
     * @param date
     * @return 식단 테이블로 부터 닐짜, 아침, user 조건을 갖는 레코드행 반환
     */

    List<Diet> findByUserIdAndDietDateAndMealDiv(String userId, Date stringToSqlDateFormat, Character mealDiv);

    /**
     * 달성 여부, 목표칼로리 update
     * @param startDate
     * @param endDate
     * @param achieve
     * @param mealDiv
     * @param targetKcal
     */
    @Modifying
    @Query(value = "UPDATE diet d " +
            "SET d.target_kcal = :targetKcal " +
            "WHERE d.meal_div = :mealDiv " +
            "And d.achieve = :achieve" +
            "AND d.diet_date BETWEEN :startDate AND :endDate"
            , nativeQuery = true)
    void updateDietSave(@Param("startDate") Date startDate
            , @Param("endDate") Date endDate
            , @Param("achieve") Boolean achieve
            , @Param("mealDiv") Character mealDiv
            , @Param("targetKcal") int targetKcal);

   /* @Modifying
    @Query(value = "INSERT INTO target " +
            "VALUES(:userId, :dietDate, :mealDiv, :targetKcal)"
            , nativeQuery = true)
    void insertTargetSave(
            @Param("userId") String userId
            , @Param("dietDate") Date dietDate
            , @Param("mealDiv") Character mealDiv
            , @Param("targetKcal") int targetKcal);*/

    @Modifying
    @Query(value = "UPDATE target_achieve ta " +
            "SET ta.achieve = :achieve " +
            "WHERE ta.user_id = :userId " +
            "AND ta.diet_date = :dietDate " +
            "AND ta.meal_div = :mealDiv"
            , nativeQuery = true)
    void updateAchieve(
            @Param("userId") String userId
            , @Param("dietDate") Date dietDate
            , @Param("mealDiv") Character mealDiv
            , @Param("achieve") Boolean achieve
    );
}


