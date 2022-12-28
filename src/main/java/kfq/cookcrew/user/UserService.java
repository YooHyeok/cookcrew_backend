package kfq.cookcrew.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Boolean userJoin(User user){
        String encoderPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("인코딩 된 패스워드 !!!! :" + encoderPassword);
        user.setPassword(encoderPassword);
//        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
//        user.setCreated(now);
        User saveresult = userRepository.save(user);
        if(saveresult.getId() != user.getId()) { //저장 실패
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

    public User myInfo(String id) {
        return userRepository.findById(id).get();
    }

}