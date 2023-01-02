package kfq.cookcrew.rank;

import kfq.cookcrew.reciepe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RateService {
    @Autowired
    RateRepository rateRepository;

    // 평점 저장
    public void addRate(Rate rate) throws Exception {
        rateRepository.save(rate);
    }
    // 등록글의 평균 별점
    public float getAvg(Rate rate) throws Exception {
        return 3.3F;

    }
}



