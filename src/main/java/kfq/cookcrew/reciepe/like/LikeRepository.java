package kfq.cookcrew.reciepe.like;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, LikeID> {

    @Query(value="select " +
            "r.*" +
            ", (select count(rtg.user_id) from rating rtg where rno = r.rno) as score " +
            ", (SELECT count(ll.user_id) " +
            "FROM like_list ll " +
            "WHERE ll.rno = r.rno AND ll.user_id=:userId) as likeValue " +
            " from like_list l " +
            "left outer join recipe r " +
            "ON l.rno = r.rno " +
            "WHERE l.user_id =:userId"
            ,nativeQuery = true)
    public Page<Map<String,Object>> findByUserId(@Param("userId") String userId, PageRequest pageRequest);

    @Query(value="select l from likeList l where userId=:userId and rno=:rno")
    public Optional<Like> findLike(@Param("userId") String userId, @Param("rno") Integer rno);

}
