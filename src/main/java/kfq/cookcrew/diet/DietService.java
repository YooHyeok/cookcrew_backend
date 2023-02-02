package kfq.cookcrew.diet;

import kfq.cookcrew.common.util.DateUtil;
import kfq.cookcrew.diet.targetAchieve.TargetAchieve;
import kfq.cookcrew.diet.targetAchieve.TargetAchieveId;
import kfq.cookcrew.diet.targetAchieve.TargetAchieveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * *****************************************************<p>
 * 패키지:kfq.cookcrew.diet<p>
 * 파일 : DietService.java<p>
 * 프로그램 설명 : 식단 관리 서비스 설정<p>
 * 연관테이블 : diet, diet_recipe, recipe
 * 담당 : 유재혁<p>
 * *****************************************************<p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietService {

    private final DietRepository dietRepository;
    private final TargetAchieveRepository targetAchieveRepository;

    public List<Diet> findAll() {
        return dietRepository.findAll();
    }
    public List<Map<String,Object>> findDistinctDietDate(String userId) {
        return dietRepository.findDistinctDietDate(userId);
    }
    public Map<String,Object> findDietAndTargetAchieve(String userId, Date dietDateSql, Character mealDiv) {
        Map<String,Object> dietData = null;
        try{
            List<Diet> dietList =
                    dietRepository.findByUserIdAndDietDateAndMealDiv(userId, dietDateSql, mealDiv);
            Optional<TargetAchieve> achieve =
                    targetAchieveRepository.findById(new TargetAchieveId(userId, dietDateSql, mealDiv));
            dietData = new HashMap<>();
            dietData.put("dietList",dietList);
            dietData.put("achieve",!achieve.isPresent() ? "null" : achieve.get());
        } catch (Exception e) {
            throw e;
        }
        return dietData;
    }

    @Transactional(readOnly = false)
    public void save(Diet diet) {
        dietRepository.save(diet);
    }

    @Transactional(readOnly = false)
    public void deleteById(Integer dNo) {
        dietRepository.deleteById(dNo);
    }

    @Transactional(readOnly = false)
    public void updateDietSave(String dietDate, Boolean achieve, Character mealDiv, Integer targetKcal) throws ParseException {
        // dietDate 날짜를 기준으로 이번주의 일요일, 토요일 값을 계산하는 로직 구현.
        Date startDate = DateUtil.SundayToSqlDate(String.valueOf(dietDate));
        Date endDate = DateUtil.SaturdayToSqlDate(String.valueOf(dietDate));
        dietRepository.updateDietSave(startDate, endDate, achieve, mealDiv, targetKcal);
    }

    @Transactional(readOnly = false)
    public void insertTargetSave(String userId, Date dietDate, Character mealDiv, Integer targetKcal) {
//        dietRepository.insertTargetSave(userId, dietDate, mealDiv, targetKcal);
        targetAchieveRepository.save(
                new TargetAchieve(
                        new TargetAchieveId(userId, dietDate, mealDiv)
                        ,targetKcal
                        ,null)
        );
    }

    @Transactional(readOnly = false)
    public void updateAchieve(String userId, Date dietDate, Boolean achieve, Character mealDiv) throws ParseException {
        dietRepository.updateAchieve(userId, dietDate,mealDiv ,achieve );
//        targetAchieveRepository.save(userId, dietDate, achieve, mealDiv);
    }
}
