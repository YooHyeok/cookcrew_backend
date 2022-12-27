package kfq.cookcrew.common.quartz;

import kfq.cookcrew.diet.DietService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.sql.Date;

public class DietJob extends QuartzJobBean {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DietService dietService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("매 시간 실행 될 작업 작성 공간");
        Long currentMillis = System.currentTimeMillis(); //현재시간 밀리세컨드
        Long sevenDaysMillis = 604800000L; //7일에 대한 밀리세컨드 단위
        Long oneDayMillis = 86400000L; //1일에 대한 밀리세컨드 단위

        Date beforeSevenDay = new Date(currentMillis-sevenDaysMillis); //7일전 월요일
        Date beforeoneDay = new Date(currentMillis-oneDayMillis);// 1일전 일요일

        LOGGER.info("7일전 : {}",beforeSevenDay);
        LOGGER.info("하루전 : {}",beforeoneDay);
        //현재 날짜는 월요일 이므로 어제 날짜를 기준으로 지난주 데이터 크롤
//        List<Map<String,Object>> rankResult =  dietService.kcalRank(mealDiv, achieve, startDate, endDate);
    }
}
