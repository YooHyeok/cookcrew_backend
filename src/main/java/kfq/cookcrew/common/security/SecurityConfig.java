package kfq.cookcrew.common.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        //	csrf라는 공격을 방어하겠다는 의미. 이쪽으로 들어오는것은 막겠다.
        http.csrf().disable();
        http.httpBasic().disable().authorizeHttpRequests()
                .antMatchers("/**/*").permitAll()
//		.antMatchers("/login**", "/web-respirces/**", "/actuator/**").permitAll()
//		.antMatchers("/admit/**").hasRole("ADMIN")
//		.antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}

