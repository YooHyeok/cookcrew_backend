package kfq.cookcrew.user;

import kfq.cookcrew.reciepe.LikeRepository;
import kfq.cookcrew.reciepe.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import static kfq.cookcrew.common.Path.USERPROFILE;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    LikeRepository likeRepository;

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
//        System.out.println(checkId);
        return checkId;
    }

    public Boolean existByNn(String nickname) throws Exception {

        boolean checkNn = userRepository.existsByNickname(nickname);

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
    public User myInfo(String id) {
        return userRepository.findById(id).get();
    }


    public User myInfoMod(User user, MultipartFile file) throws Exception {
        String filename = null;
        Optional<User> ouser = userRepository.findById(user.getId());
        if(ouser.isEmpty()) {
            throw new Exception("회원정보 오류");
        }
        User orgUser = ouser.get();
        orgUser.setNickname(user.getNickname());
        orgUser.setAddress((user.getAddress()));
        orgUser.setAddrDetail(user.getAddrDetail());
        orgUser.setPostcode((user.getPostcode()));
        orgUser.setEmail((user.getEmail()));
        if(!file.isEmpty()) {
            orgUser.setThumbnail(file.getBytes());
        }
        userRepository.save(orgUser);
        return orgUser;
    }
}

