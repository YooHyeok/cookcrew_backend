package kfq.cookcrew.diet;

import kfq.cookcrew.common.DateUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
    public List<Diet> findAll() {
        return dietRepository.findAll();
    }
    public List<Map<String,Object>> findDistinctDietDate(String userId) {
        return dietRepository.findDistinctDietDate(userId);
    }
    public List<Diet> findByUserIdAndDietDateAndMealDiv(String userId, Date stringToSqlDateFormat, Character mealDiv) {
        return dietRepository.findByUserIdAndDietDateAndMealDiv(userId, stringToSqlDateFormat, mealDiv);
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
        Date startDate = DateUtill.SundayToSqlDate(String.valueOf(dietDate));
        Date endDate = DateUtill.SaturdayToSqlDate(String.valueOf(dietDate));
        dietRepository.updateDietSave(startDate, endDate, achieve, mealDiv, targetKcal);
    }
}
