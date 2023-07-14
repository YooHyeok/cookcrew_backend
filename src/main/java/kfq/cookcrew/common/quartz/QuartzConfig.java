package kfq.cookcrew.common.quartz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class QuartzConfig {
    private final DataSource dataSource;
    private final ApplicationContext applicationContext;
    private final PlatformTransactionManager platformTransactionManager;
}
