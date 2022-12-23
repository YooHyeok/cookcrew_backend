package kfq.cookcrew.reciepe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
//    List<Recipe> findByTitleContaining(String title);
//    List<Recipe> findByTitleContains(String title);
//    List<Recipe> findByTitleIsContaining(String title);
//    List<Recipe> findByTitleLike(String title);
    @Query("SELECT r FROM Recipe r WHERE r.title LIKE %:title%") // 쿼리 테이블명은 Entity클래스명과 동일한 첫글자 대문자
    List<Recipe> searchByTitleLike(@Param("title") String title);
}
