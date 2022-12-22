package kfq.cookcrew.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
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
    public void save(Diet diet) {
        dietRepository.save(diet);
    }
    public void deleteById(Integer dNo) {
        dietRepository.deleteById(dNo);
    }
}
