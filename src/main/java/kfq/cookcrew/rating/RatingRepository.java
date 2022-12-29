package kfq.cookcrew.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Integer> {
    public Optional<Rating> findByUserId(String userId);
    public Optional<Rating> findByUserIdAndRno(String userId, Integer rNo);

    @Query(value = "select r.ratingValue from rating r where rno=:rno")
    public List<Integer> getRatingValue(@Param("rno") Integer rno);
}
