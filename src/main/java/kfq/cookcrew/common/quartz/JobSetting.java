package kfq.cookcrew.common.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import static org.quartz.JobBuilder.newJob;

@Configuration
public class JobSetting {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start(){
        JobDetail jobDetail = buildJobDetail(DietJob.class, new HashMap());

        try{
            // 0 0 * * 1 매주 일요일 자정 12시 1분에 작업 실행
            scheduler.scheduleJob(jobDetail, buildJobTrigger("0 0 * * 7 ?"));
            
        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

    public Trigger buildJobTrigger(String scheduleExp){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail buildJobDetail(Class job, Map params){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).usingJobData(jobDataMap).build();
    }
}
