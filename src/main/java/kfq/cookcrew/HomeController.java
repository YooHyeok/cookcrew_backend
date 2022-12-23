package kfq.cookcrew;

import kfq.cookcrew.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * *****************************************************<p>
 * 패키지:kfq.cookcrew<p>
 * 파일 : HomeController.java<p>
 * 프로그램 설명 : 메인페이지, 각 section을 출력<p>
 * 연관클래스 : RecipeService, DietService, RankService?<p>
 * 연관테이블 : Recipe, diet, diet_recipe, score, like_list<p>
 * 담당 : 이규희<p>
 * *****************************************************<p>
 */
@RestController
public class HomeController extends BaseController {

    /**
     * 메인 페이지
     * 각 Section 출력한다
     * @return 요청 결과 객체 ResponseEntity
     * @param
     */
    @GetMapping("/")
    public ResponseEntity<String> mainPage() {
        // TODO 여기에 기능 구현 - String은 타입에 맞게 수정
        String mainAlert = "메인페이지 입니다.";
        LOGGER.info("로그 테스트 : "+ mainAlert);
        LOGGER.info("로그 테스트 : {}, 패스워드 : {}, 이름 : {}", mainAlert, "바보", "메롱");
        ResponseEntity<String> res = new ResponseEntity<String>(mainAlert, HttpStatus.OK);
        System.out.println(res);
        return res;
    }
}
