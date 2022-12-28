package kfq.cookcrew.rank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    LikeRepository likeRepository;

    // 찜하기
    public void addLike(String likerId, Integer likedRno) throws Exception {
        likeRepository.save( new Like(likerId, likedRno));

    }

    public void ccLike(String likerId, Integer likedRno) throws  Exception {
       if ( likeRepository.findById(likerId).equals(likeRepository.findByLikerIdAndLikedRno(likerId, likedRno))){
           likeRepository.delete(likeRepository.findByLikerIdAndLikedRno(likerId, likedRno).get());
       }
    }
    // 각 글의 좋아요 개수 구하기
    public Long likeCount(Integer rNo) throws Exception {
        return likeRepository.count();
    }

    // 사용자가 찜한 글목록 가져오기
    public Optional<Like> likeList(String id) throws Exception{
        Optional<Like> lLike = likeRepository.findById(id);
        if(!(lLike.isEmpty())){
            return lLike;
        }
        throw new Exception("찜한 레시피가 없습니다.");
    }

    // 글의 찜 개수

}
