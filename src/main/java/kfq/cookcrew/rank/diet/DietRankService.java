package kfq.cookcrew.rank.diet;

import kfq.cookcrew.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DietRankService {
    private final DietRankRepository dietRankRepository;
    public List<DietRank> findByRegDateAndSort(Integer sort) throws Exception {

        // 등록일자 : 매주 일요일 로직 구현.
        Date today = new Date(System.currentTimeMillis()); // 등록일자(오늘)
        Date regDate = DateUtil.SundayToSqlDate(String.valueOf(today));
        List<DietRank> dietRankList = null;
            dietRankList = dietRankRepository.findByRegDateAndSortAndAchieveRankLessThanEqual(regDate, sort, 3);
//        if(CollectionUtils.isEmpty(dietRankList)) {
//            throw new Exception("null");
//        }
        return dietRankList;
    }

    @Transactional(readOnly = false)
    public void challengeRank(Date startDate, Date endDate, Date regDate) {
        dietRankRepository.challengeRankDesc(startDate, endDate, regDate, 1);
        dietRankRepository.challengeRankAsc(startDate, endDate, regDate, 2);
    }

    public void saveValidate(Boolean challenge, String userId) throws ParseException {
        Date today = new Date(System.currentTimeMillis());
        Date startDate = DateUtil.SundayToSqlDate(String.valueOf(today));
        Date endDate = DateUtil.SaturdayToSqlDate(String.valueOf(today));
        dietRankRepository.save(
                new Challenge(
                        new ChallengeId(userId, startDate, endDate)
                        , challenge
                )
        );
    }
    public Challenge searchValidate(String userId) throws Exception {
        Date today = new Date(System.currentTimeMillis());
        Date startDate = DateUtil.SundayToSqlDate(String.valueOf(today));
        Date endDate = DateUtil.SaturdayToSqlDate(String.valueOf(today));
        Optional<Challenge> byId = dietRankRepository.findById(
                new ChallengeId(userId, startDate, endDate));
            if(!byId.isPresent()) {
                throw new Exception("null");
            }
        return byId.get();
    }
}
