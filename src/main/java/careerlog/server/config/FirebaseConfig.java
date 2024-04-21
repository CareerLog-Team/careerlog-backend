package careerlog.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        String firebaseSdkPath = "/home/ubuntu/app/src/main/resources/firebase.json";
        String localFirebaseSdkPath = "/src/main/resources/firebase.json";

        log.info("sdk path : {}", firebaseSdkPath);

        FileInputStream serviceAccount = new FileInputStream(firebaseSdkPath);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        log.info("success to initialize FirebaseApp");
    }

}
