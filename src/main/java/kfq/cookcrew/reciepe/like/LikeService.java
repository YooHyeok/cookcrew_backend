package kfq.cookcrew.reciepe.like;

import kfq.cookcrew.reciepe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    LikeRepository likeRepository;

    public void saveLike(Like like) throws Exception{
        likeRepository.save(like);
    }

    public void saveLike(LikeID likeID) throws Exception {
        likeRepository.save(new Like((likeID.getRno()),(likeID.getUserId())));
    }

    public void saveLike(Integer rno, String userId) {
        likeRepository.save(new Like((rno), (userId) ));
    }

    public void toggleLike(Integer rno, String userId) throws Exception{
        LikeID likeID = new LikeID(rno,userId);
        Optional<Like> like = likeRepository.findLike(userId, rno);
        if(like.isEmpty()){
            likeRepository.save(new Like(rno, userId));
        } else {
            likeRepository.delete(like.get());
        }
    }

    public Boolean isLikePresent(Integer rno, String userId) throws Exception {
        Optional<Like> like = likeRepository.findLike(userId, rno);
        if (like.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<Boolean> isLikedList(List<Recipe> recipes, String userId) throws Exception {
        List<Boolean> isLikeds = new ArrayList<>();
        if(userId.equals("guest")) {
            for(int i=0; i<recipes.size(); i++) {
                isLikeds.add(false);
            }
        } else {
            for (Recipe recipe : recipes) {
                Optional<Like> olike = likeRepository.findLike(userId, recipe.getRno());
                if (olike.isPresent()) {
                    isLikeds.add(true);
                } else {
                    isLikeds.add(false);
                }
            }
        }
        return isLikeds;
    }

/*    public List<Integer> likeStatus(String userId) throws Exception {
        List<Integer> userLikeStatus = likeRepository.findByUserId(userId);
        return userLikeStatus;
    }*/
    
}
