package kfq.cookcrew;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kfq.cookcrew.reciepe.QRecipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class QueryDSLTest {

    @Autowired
    JPAQueryFactory jpaQueryFactory;
    @Autowired
    JPAQuery jpaQuery;

    /*@Test
    void queryDslTest(){
        QRecipe qrecipe = QRecipe.recipe;
        List<Recipe>  recipeList = jpaQuery
                .from(qrecipe)
                .where(qrecipe.title.like("%김치%"))
                .orderBy(qrecipe.cnt.asc())
                .fetch();

        for (Recipe recipe : recipeList) {
            System.out.println("recipe Title : " + recipe);
        }
    }*/
    @Test
    void queryDslFactoryTest(){
        QRecipe qrecipe = QRecipe.recipe;
        List<String> recipeList = jpaQueryFactory
                .select(qrecipe.title)
                .from(qrecipe)
                .where(qrecipe.title.like("%김치%"))
                .orderBy(qrecipe.cnt.asc())
                .fetch();

        for (String recipe : recipeList) {
            System.out.println("recipe Title : " + recipe);
        }
    }

}
