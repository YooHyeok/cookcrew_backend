package kfq.cookcrew.reciepe.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    LikeRepository likeRepository;

    public void saveLike(Integer rno, String userId, Boolean isliked) throws Exception{
        likeRepository.save(new Like(rno, userId, isliked));
    }

    public void saveLike(Like like) throws Exception{
        likeRepository.save(like);
    }

    public void saveLike(LikeID likeID) throws Exception {
        likeRepository.save(new Like((likeID.getRno()),(likeID.getUserId()),true));
    }

    public void saveLike(Integer rno, String userId) {
        likeRepository.save(new Like((rno), (userId), true));
    }

    public void toggleLike(Integer rno, String userId) throws Exception{
        LikeService likeService = new LikeService();
        LikeID likeID = new LikeID(rno,userId);
        Optional<Like> like = likeRepository.findById(likeID);
        if(like.isEmpty()){
            likeService.saveLike(likeID);
        } else {
            Boolean curLike = like.get().getIsliked();
            like.get().setIsliked(!curLike);
        }

    }
}
