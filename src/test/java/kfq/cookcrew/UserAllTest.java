package kfq.cookcrew;

import kfq.cookcrew.user.User;
import kfq.cookcrew.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserAllTest {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("패스워드 암호화 테스트")
	void passwordEncoder() {
		//given
		String rawPassword = "12345678";

		//when
		String encodePassword = passwordEncoder.encode(rawPassword);

		//then
		assertAll(
				() -> assertNotEquals(rawPassword, encodePassword),
				() -> assertTrue(passwordEncoder.matches(rawPassword, encodePassword))
		);
	}


<<<<<<< HEAD
//	@Test
//	@DisplayName("회원가입 패스워드 암호화 테스트!")
//	void userJoinTest() {
//
//		userService.userJoin(
//				new User(
//						"user"
//						, "박지혜짱짱걸"
//						, "user1234"
//						, "주소"
//						, "상세주소"
//						, "이메일!"
//						, "인증번호"
//						, new Date(System.currentTimeMillis())
//						, null
//				)
//		);
//	}
=======
	@Test
	@DisplayName("회원가입 패스워드 암호화 테스트!")
	void userJoinTest() {

		userService.userJoin(
				new User(
						"user"
						, "박지혜짱짱걸"
						, "user1234"
						, "주소"
						, null
						, "상세주소"
						, "이메일!"
						, true
						, new Date(System.currentTimeMillis())
						, null
				)
		);
	}
>>>>>>> webdevyoo
}
