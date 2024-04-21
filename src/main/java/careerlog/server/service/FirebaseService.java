package careerlog.server.service;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FirebaseService {

    public static String createUser(String email, String password) {
        try {
            // 사용자 생성 요청 생성
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

            log.info("사용자가 생성되었습니다 : {}", userRecord.getUid());
            return userRecord.getUid();
        } catch (FirebaseAuthException e) {
            throw new IllegalStateException(e);
        }
    }
}
