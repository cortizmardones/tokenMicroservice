package com.example.demo.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfig {

    @Bean
    Firestore firestore() throws IOException {
    	
        ClassPathResource resource = new ClassPathResource("usuarios-dba5f-firebase-adminsdk-q5u02-e9adf0a04c.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        @SuppressWarnings("deprecation")
		FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(credentials)
            .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore(app);
    }
}