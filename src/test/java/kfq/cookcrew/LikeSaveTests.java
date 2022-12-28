package kfq.cookcrew;

import kfq.cookcrew.common.BaseController;
import kfq.cookcrew.diet.Diet;
import kfq.cookcrew.diet.DietRepository;
import kfq.cookcrew.rank.Like;
import kfq.cookcrew.rank.LikeRepository;
import kfq.cookcrew.rank.LikeService;
import kfq.cookcrew.reciepe.Recipe;
import kfq.cookcrew.reciepe.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LikeSaveTests extends BaseController{
    @Autowired
    LikeRepository likeRepository;
    LikeService likeService;
    Like like;

    @Test
    void addlike1 (String likerId, Integer likedRNo) throws Exception {
        likeRepository.save(new Like(likerId, likedRNo));

    }
}
