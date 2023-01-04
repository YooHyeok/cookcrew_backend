package kfq.cookcrew.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Boolean userJoin(User user) {
        String encoderPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("인코딩 된 패스워드 !!!! :" + encoderPassword);
        user.setPassword(encoderPassword);
//        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
//        user.setCreated(now);
        User saveresult = userRepository.save(user);
        if (saveresult.getId() != user.getId()) { //저장 실패
            return false;
        }
        return true;
    }

    public Boolean existsById(String id) throws Exception {

        boolean checkId = userRepository.existsById(id);
        System.out.println(checkId);
        return checkId;
    }

    public Boolean existByNn(String nickname) throws Exception {

        boolean checkNn = userRepository.existsByNickname(nickname);
        System.out.println(checkNn);

        return checkNn;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).get();
    }

    //    public User myInfo2(User user) throws Exception{
//        Optional<User> userByOptionalList = userRepository.findById(user.getId()); //id에 해당하는 정보의 jpa 반환타입은 Optional이라는 리스트형태의 인터페이스이다.
//        User userObject = userByOptionalList.get(); //리스트 형태에서 .get() 하면 Generic User타입의 객체로 반환을 받는다.
//        return userObject;
//    }
//        public Optional<User> myInfo2(User user) throws Exception{
//        Optional<User> userByOptionalList = userRepository.findById(user.getId()); //id에 해당하는 정보의 jpa 반환타입은 Optional이라는 리스트형태의 인터페이스이다.
//        return userByOptionalList;
//    }
    public User myInfo(String id) {return userRepository.findById(id).get();}


    public User myInfoMod(User user, MultipartFile file) throws Exception {
        String filename = null;
        User result = userRepository.save(user);
        if (result.getId() != user.getId()) { //저장 실패
            throw new Exception();}
        // 파일을 첨부하지 않아도 String 변수를 받아서 뿌리고 오류 발생을 예방하기 위해 && IsEmpty 해준거임
        else if (result.getId() == user.getId()) {
            if (file != null && !file.isEmpty()) {
                String path = "D:\\FinishProject\\ImageFile";
                filename = file.getOriginalFilename();
                File dFile = new File(path + filename);
                file.transferTo(dFile);
//            user = userRepository.findById(user.getId()).get();
                user.setFilename(filename);
            }
        }
        return user;
        }
//        return userRepository.findById(id).get();
    }

