package br.com.joaopedrofazzolo.itenspregao.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            String firebaseCredentials = System.getenv("FIREBASE_CREDENTIALS");
            if (firebaseCredentials == null || firebaseCredentials.trim().isEmpty()) {
                throw new IOException("FIREBASE_CREDENTIALS environment variable is not set or empty");
            }

            String projectId = System.getenv("FIREBASE_PROJECT_ID");
            if (projectId == null || projectId.trim().isEmpty()) {
                throw new IOException("FIREBASE_PROJECT_ID environment variable is not set or empty");
            }

            try {
                JsonParser.parseString(firebaseCredentials);
            } catch (JsonParseException e) {
                throw new IOException("FIREBASE_CREDENTIALS contains malformed JSON: " + e.getMessage(), e);
            }

            ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseCredentials.getBytes());
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://" + projectId + ".firebaseio.com")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Firebase: " + e.getMessage(), e);
        }
    }
}