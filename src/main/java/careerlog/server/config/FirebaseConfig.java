package careerlog.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        String firebaseSdkRelativePath = "./src/main/resources/firebase.json";

        File file = new File(firebaseSdkRelativePath);
        // Linux 상대 경로 처리 이슈로 인해, 절대 경로로 변환하여 입력
        String firebaseSdkAbsolutePath = String.valueOf(file.getAbsoluteFile());

        FileInputStream serviceAccount = new FileInputStream(firebaseSdkAbsolutePath);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        log.info("success to initialize FirebaseApp");
    }
}
