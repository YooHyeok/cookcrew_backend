package kfq.cookcrew.reciepe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
//    List<Recipe> findByTitleContaining(String title);
//    List<Recipe> findByTitleContains(String title);
//    List<Recipe> findByTitleIsContaining(String title);
//    List<Recipe> findByTitleLike(String title);
    @Query("SELECT r FROM Recipe r WHERE r.title LIKE %:title% ORDER BY r.rno DESC") // 쿼리 테이블명은 Entity클래스명과 동일한 첫글자 대문자
    List<Recipe> searchByTitleLike(@Param("title") String title);

//    @Query(value = "update recipe r set r.cnt = r.cnt+1 where rno=:rNo")
//    public Integer incrementCnt(@Param("rno") Integer rNo);

    @Query("SELECT CASE WHEN r.cnt = null THEN 0 END as cnt FROM Recipe r WHERE r.rno=:rno")
    public Integer getCntByRno(@Param("rno") Integer rno);

    @Modifying
    @Transactional
    @Query("UPDATE Recipe r SET r.enabled=false WHERE r.rno=:rno")
    public void deleteByRno(@Param("rno") Integer rno);

    @Query("SELECT r from Recipe r WHERE r.regId=:userId")
    public List<Recipe> findMyRecipe(@Param("userId") String userId);

    @Query(value = "SELECT rno" +
                    "FROM like_list" +
                    "WHERE userId=:userId"
                ,nativeQuery = true)
    public List<Integer> myLike(@Param("userId") String userId);

    @Query("SELECT r FROM Recipe r WHERE r.rno=:rno")
    public List<Map<String,Recipe>> findMyLike(@Param("rno") Integer rno);


}
