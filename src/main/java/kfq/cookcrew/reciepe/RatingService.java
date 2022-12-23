package kfq.cookcrew.reciepe;

import kfq.cookcrew.reciepe.Rating;
import kfq.cookcrew.reciepe.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    //별점 등록
    public void ratingReg(String id, Integer ratingValue, Integer rNo) throws Exception{
        Rating r = new Rating();
        r.setRatingValue(ratingValue);
        r.setUserId(id);
        r.setRno(rNo);
        Optional<Rating> search_rating = ratingRepository.findByUserIdAndRno(id, rNo);
        if(search_rating.isPresent()) {
            search_rating.get().setRatingValue(ratingValue);
            ratingRepository.save(search_rating.get());
        } else {
            ratingRepository.save(r);
        }
    }
    //별점 뿌리기
    public List<Integer> requestRatingValue(Integer rNo) throws Exception {
        List<Integer> orating = ratingRepository.getRatingValue(rNo);
        if(orating != null)
            return  orating;
        throw new Exception("별점 정보없음");
    }
}
