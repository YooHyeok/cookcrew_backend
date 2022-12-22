package kfq.cookcrew.reciepe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    public List<Recipe> searchByTitleLike(String searchParam) {
        return recipeRepository.searchByTitleLike(searchParam);
    }
}
