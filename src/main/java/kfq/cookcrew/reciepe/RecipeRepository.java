package kfq.cookcrew.reciepe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Query(value = "SELECT " +
            "rep.rno" +
            ", rep.reg_id" +
            ", rep.title" +
            ", rep.cnt" +
            ", rep.kcal" +
            ", (select count(rtg.user_id) from rating rtg where rno = rep.rno) as score " +
            ", (select count(ll.user_id) from like_list ll where ll.rno = rep.rno) as likeValue " +
            "FROM recipe rep " +
            "WHERE rep.title LIKE %:title% AND rep.enabled=true ORDER BY rep.rno DESC", nativeQuery = true) // 쿼리 테이블명은 Entity클래스명과 동일한 첫글자 대문자
    List<Map<String, Object>> searchByTitleLike(@Param("title") String title);

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

    /**
     * 나의 레시피 조회
     * @param id
     * @return
     */
    @Query(value = "SELECT " +
            "rep.rno" +
            ", rep.title" +
            ", rep.reg_date" +
            ", rep.kcal" +
            ", rep.cnt" +
            ", (select count(rtg.user_id) from rating rtg where rno = rep.rno) as score " +
            ", (select count(ll.user_id) from like_list ll where ll.rno = rep.rno) as likeValue " +
            "FROM recipe rep " +
            "WHERE rep.reg_id =:userId AND rep.enabled = true ORDER BY rep.rno DESC", nativeQuery = true)
    List<Map<String,Object>> findByUserId(@Param("userId") String id);
    @Query("SELECT r FROM Recipe r WHERE r.title LIKE %:title% and enabled = true") // 쿼리 테이블명은 Entity클래스명과 동일한 첫글자 대문자
    Page<Recipe> searchByKeyword(@Param("title") String title, PageRequest pageRequest);
    @Query("SELECT r FROM Recipe r WHERE r.title LIKE %:title% and enabled = true") // 쿼리 테이블명은 Entity클래스명과 동일한 첫글자 대문자
    List<Recipe> searchByKeyword(@Param("title") String title);

    @Query("select r from Recipe r where enabled = true")
    Page<Recipe> findEnabled(PageRequest pageRequest);
    @Query("select r from Recipe r where enabled = true order by cnt desc")
    List<Recipe> findEnabledCnt(List<Recipe> lrecipe);
    @Query("select r from Recipe r where enabled = true order by rno desc" )
    List<Recipe> findEnabledRno(List<Recipe> lrecipe);

}
