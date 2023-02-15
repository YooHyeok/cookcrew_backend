package kfq.cookcrew.common.queryDSL;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDSLConfiguration {
    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public JPAQuery jpaQuery() {
        return new JPAQuery(entityManager);
    }
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
