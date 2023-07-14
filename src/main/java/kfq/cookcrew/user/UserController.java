package kfq.cookcrew.user;

import kfq.cookcrew.common.BaseController;
import kfq.cookcrew.common.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static kfq.cookcrew.common.Path.USERPROFILE;


/**
 * *****************************************************<p>
 * 패키지:kfq.cookcrew.user<p>
 * 파일 : UserController.java<p>
 * 프로그램 설명 : 회원가입, 로그인(토큰), 마이페이지, 로그아웃<p>
 * 연관테이블 : user<p>
 * 담당 : 박지혜<p>
 * *****************************************************<p>
 */
@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;
    private final UserRepository userrepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param user
     * @return ResponseEntity 결과 객체
     */
    @PostMapping("/join")
    public ResponseEntity<Boolean> userJoin(@RequestBody User user) {
        ResponseEntity<Boolean> res = null;
        try{
            Boolean result = userService.userJoin(user);
            res = new ResponseEntity<Boolean>(result, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }


    /**
     * 회원가입시 [중복확인] 기능
     * @param id
     * @return ResponseEntity 결과 객체
     */
    @PostMapping("/exsitById")
    public ResponseEntity<Boolean> isExistById(String id) {
        ResponseEntity<Boolean> res = null;
        try{
            Boolean result = userService.existsById(id);
            res = new ResponseEntity<Boolean>(result, HttpStatus.OK);
        } catch (Exception e){
            res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }


    /**
     * 회원가입시 [중복확인] 기능
     * @param nickname
     * @return ResponseEntity 결과 객체
     */
    @PostMapping("/existByNn")
    public ResponseEntity<Boolean> isExistByNn(String nickname) {
        ResponseEntity<Boolean> res = null;
        try{
            Boolean result = userService.existByNn(nickname);
            res = new ResponseEntity<Boolean>(result, HttpStatus.OK);
        } catch (Exception e){
            res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    /**
     * 로그인(Security 토큰처리)
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("id") String id,
                                                     @RequestParam("password") String password,
                                                     HttpServletRequest request){

        System.out.println(id);
        System.out.println(password);

        Map<String, String> res = new HashMap<>();
        User user = (User)userService.loadUserByUsername(id);
        Boolean pwResult = passwordEncoder.matches(password, user.getPassword());

        if(user!=null && pwResult) {

            String accessToken = jwtTokenProvider.createToken(user.getUsername());
            String refreshToken =jwtTokenProvider.refreshToken(user.getUsername());
            System.out.println("userId :::: "+user.getUsername());
            System.out.println("accessToken :::: "+accessToken);
            System.out.println("refreshToken :::: "+refreshToken);

            res.put("userId",user.getUsername() );
            res.put("accessToken", accessToken);
            res.put("refreshToken", refreshToken);
            return new ResponseEntity<Map<String, String>>(res, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, String>>(HttpStatus.BAD_REQUEST);

    }

    /**
     * 마이페이지
     */
    @GetMapping("/mypage")
    public ResponseEntity<User> myInfo(@RequestParam("id") String id) {
        ResponseEntity<User> result = null;
        User user = userService.myInfo(id);
        try {
            result = new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @PostMapping("/mypagemod")
    public ResponseEntity<User> myInfoMod(@ModelAttribute User user,
                                          @RequestParam(name = "file", required = false) MultipartFile file)
    {
        ResponseEntity<User> res = null;
        try{
            User usermod = userService.myInfoMod(user, file);
            res = new ResponseEntity<User>(usermod, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            res = new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/profile/{id}")
    public void profileThumbnail(@PathVariable String id, HttpServletResponse response) {
        try {
            byte[] thumbnail = userService.profileThumbnail(id);
            OutputStream out = response.getOutputStream();
            out.write(thumbnail);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}