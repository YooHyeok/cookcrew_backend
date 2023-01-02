package kfq.cookcrew.rank.diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface DietRankRepository extends JpaRepository<DietRank, Integer> {

//    @Query(value = "SELECT * FROM diet_rank WHERE reg_date=:regDate",nativeQuery = true)
    List<DietRank> findByRegDateAndSortAndAchieveRankLessThanEqual(Date regDate, Integer sort, Integer count);
//    List<DietRank> findByRegDateOrderByAchieveRankAsc(@Param("regDate") Date regDate);
//    List<DietRank> findByRegDateOrderByAchieveRankDesc(@Param("regDate") Date regDate);

    static final String RankDescState = " ROW_NUMBER() OVER(ORDER BY (count(achieve) / 21 * 100) DESC) as achieve_rank";
    static final String SurveState =
            " FROM diet " +
                    "WHERE diet_date BETWEEN :startDate AND :endDate " +
                    " AND achieve = true " +
                    " GROUP BY user_id " +
                    ") as temp";
    //                                ") as temp WHERE temp.achieve_rank <= 3";
    static final String INSERTDietRankDESC = "INSERT INTO diet_rank (user_id, achieve_cnt, achieve_percentage, achieve_rank, start_date, end_date, reg_date, sort) VALUES (" +
            " (SELECT user_id FROM ( SELECT user_id," + RankDescState + SurveState +")" +
            ", (SELECT achieve_cnt FROM ( SELECT achieve_cnt," + RankDescState + ")" +
            ", (SELECT achieve_percentage FROM ( SELECT achieve_percentage," + RankDescState +  SurveState +")" +
            ", (SELECT achieve_rank FROM ( SELECT" + RankDescState +  SurveState +")" +
            ", start_date= :startDate" +
            ", end_date= :endDate" +
            ", reg_date= :regDate" +
            ", sort= :sort";

    /**
     * 챌린지 랭크 저장 DESC
     * @param startDate
     * @param endDate
     * @param regDate
     * @param sort
     */
    @Modifying
    @Query(value = INSERTDietRankDESC, nativeQuery = true)
    void challengeRankDesc(
            @Param("startDate") Date startDate
            , @Param("endDate") Date endDate
            , @Param("regDate") Date regDate
            , @Param("sort") Integer sort
    );

    static final String RankAscState = " ROW_NUMBER() OVER(ORDER BY (count(achieve) / 21 * 100) ASC) as achieve_rank";

    static final String INSERTDietRankASC = "INSERT INTO diet_rank (user_id, achieve_cnt, achieve_percentage, achieve_rank, start_date, end_date, reg_date, sort) VALUES (" +
            " (SELECT user_id FROM ( SELECT user_id," + RankAscState + SurveState +")" +
            ", (SELECT achieve_cnt FROM ( SELECT achieve_cnt," + RankAscState + ")" +
            ", (SELECT achieve_percentage FROM ( SELECT achieve_percentage," + RankAscState + SurveState +")" +
            ", (SELECT achieve_rank FROM ( SELECT" + RankAscState + SurveState +")" +
            ", start_date= :startDate" +
            ", end_date= :endDate" +
            ", reg_date= :regDate" +
            ", sort= :sort";

    /**
     * 챌린지 랭크 역순 저장 ASC
     * @param startDate
     * @param endDate
     * @param regDate
     * @param sort
     */
    @Modifying
    @Query(value = INSERTDietRankASC, nativeQuery = true)
    void challengeRankAsc(
            @Param("startDate") Date startDate
            , @Param("endDate") Date endDate
            , @Param("regDate") Date regDate
            , @Param("sort") Integer sort
    );
}
