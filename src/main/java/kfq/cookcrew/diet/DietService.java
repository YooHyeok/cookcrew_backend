package kfq.cookcrew.diet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
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
    public List<Map<String,Object>> findDistinctDietDate() {
        return dietRepository.findDistinctDietDate();
    }
    public List<Diet> findByDietDateAndMealDiv(Date stringToSqlDateFormat, Character mealDiv) {
        return dietRepository.findByDietDateAndMealDiv(stringToSqlDateFormat, mealDiv);
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
    public void updateDietSave(Date dietDate, char mealDiv, Boolean achieve, int targetKcal) {
        dietRepository.updateDietSave(dietDate,mealDiv,achieve,targetKcal);
    }
//    public List<Map<String,Object>> kcalRank(Character mealDiv, Boolean achieve, Date startDate, Date endDate) {
//        return dietRepository.kcalRank(mealDiv, achieve, startDate, endDate);
//    }
}
