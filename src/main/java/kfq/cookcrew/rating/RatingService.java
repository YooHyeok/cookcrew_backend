package kfq.cookcrew.rating;

import kfq.cookcrew.reciepe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
    public Double requestRatingValue(Integer rNo) throws Exception {
        Double orating = ratingRepository.getRatingValue(rNo);
        if(orating != null)
            return  orating;
        else if (orating == null) {
            return 0d;
        }
        throw new Exception("별점 정보없음");
    }

    public List<Double> ratingList(List<Recipe> recipes) throws Exception {
        List<Double> ratings = new ArrayList<>();

            for(int i=0; i<recipes.size(); i++) {
                Double score = requestRatingValue((recipes.get(i).getRno()));
                ratings.add(score);
        }
        return ratings;
    }
}
