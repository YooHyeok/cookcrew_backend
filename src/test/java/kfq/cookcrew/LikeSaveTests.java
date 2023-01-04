package kfq.cookcrew;

import kfq.cookcrew.common.BaseController;

import kfq.cookcrew.reciepe.like.Like;
import kfq.cookcrew.reciepe.like.LikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
class LikeSaveTests extends BaseController{
    @Autowired
    LikeRepository likeRepository;
    @Test
    public void likeTest() {
        log.info(likeRepository.findAll().toString());
    }

    @Test
    public void saveLikeTest() {
        Like like = new Like(12, "user12", true);
        likeRepository.save(like);
    }



}
