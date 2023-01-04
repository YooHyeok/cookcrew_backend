package kfq.cookcrew;

import kfq.cookcrew.common.BaseController;

import kfq.cookcrew.reciepe.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
class LikeSaveTests extends BaseController{
    @Autowired
    LikeRepository likeRepository;
    LikeService likeService;
    @Test
    public void likeTest() {
        log.info(likeRepository.findAll().toString());
    }

    @Test
    public void saveLikeTest() {
        Like like = new Like(12, "user12");
        likeRepository.save(like);
    }

    @Test
    public void toggleLikeTest() {
        LikeService likeService = new LikeService();
        Integer rno = 44;
        String userId = "user44";
        LikeID likeID = new LikeID(rno,userId);
        Optional<Like> like = likeRepository.findById(likeID);
        if(like.isEmpty()){
            likeRepository.save(new Like(rno, userId));
        } else {
              likeRepository.save(new Like(rno, userId));
//            Boolean curLike = like.get().getIsliked();
//            System.out.println(curLike);
//            like.get().setIsliked(!curLike);
//            curLike = like.get().getIsliked();
//            System.out.println(curLike);
//            System.out.println(like.get().getIsliked()
        }
    }

    @Test
    public void isLikedTest() {
        Optional<Like> like = likeRepository.findLike("user1", 2);
        if(like.isEmpty()) {
            System.out.println("none");
        } else {
            System.out.println(like.get());
        }
    }


}
