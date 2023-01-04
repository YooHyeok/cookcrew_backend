package kfq.cookcrew.reciepe.like;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, LikeID> {
    public List<Like> findByUserId(@Param("userId") String userId );

    @Query(value="select l from likeList l where userId=:userId and rno=:rno")
    public Optional<Like> findLike(@Param("userId") String userId, @Param("rno") Integer rno);

}
