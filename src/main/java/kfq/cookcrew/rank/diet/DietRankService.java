package kfq.cookcrew.rank.diet;

import kfq.cookcrew.common.DateUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DietRankService {
    private final DietRankRepository dietRankRepository;
    public List<DietRank> findByRegDateAndSort(Integer sort) throws Exception {

        // 등록일자 : 매주 일요일 로직 구현.
        Date today = new Date(System.currentTimeMillis()); // 등록일자(오늘)
        Date regDate = DateUtill.SundayToSqlDate(String.valueOf(today));
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
}
