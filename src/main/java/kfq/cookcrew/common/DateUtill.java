package kfq.cookcrew.common;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 일 월 화 오늘 목 금 토
 * 일 월 화 수 오늘 금 토
 */
public class DateUtill {

    /**
     * 반환타입 : Sql.Date<br/>
     * 오늘날짜 기준 일요일
     */
    public static Date SundayToSqlDate(String today) throws ParseException {
        String pattern = "yyyy-MM-dd";
        java.util.Date parseDate= new SimpleDateFormat(pattern).parse(String.valueOf(today));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);  // 출력용으로 쓸 데이트 포맷
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate);   // calendar 구조체에 오늘 날짜를 저장함
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        Date date = Date.valueOf(simpleDateFormat.format(calendar.getTime()));
        return date;
    }
    /**
     * 반환타입 : Sql.Date <br/>
     * 오늘날짜 기준 일요일
     */
    public static Date SaturdayToSqlDate(String today) throws ParseException {
        String pattern = "yyyy-MM-dd";
        java.util.Date parseDate= new SimpleDateFormat(pattern).parse(String.valueOf(today));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);  // 출력용으로 쓸 데이트 포맷
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate);   // calendar 구조체에 오늘 날짜를 저장함
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        Date date = Date.valueOf(simpleDateFormat.format(calendar.getTime()));
        return date;
    }
}
