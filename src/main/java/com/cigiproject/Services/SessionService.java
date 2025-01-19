package main.java.com.cigiproject.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionService {
    private Map<String, Session> activeSessions = new HashMap<>();
    private static final String STORAGE_PATH = "session/sessions.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    // Make the Session class static
    public static class Session {
        private String id;
        private String email;
        private String role;
        private String token;

        public Session() {
            // Default constructor required by Jackson
        }

        public Session(String id, String email, String role, String token) {
            this.id = id;
            this.email = email;
            this.role = role;
            this.token = token;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }

        public String getToken() {
            return token;
        }
    }

    public String createSession(String userId, String email, String role) {
        String token = UUID.randomUUID().toString();
        Session session = new Session(userId, email, role, token);
        activeSessions.put(token, session);
        saveSessionsToFile();
        return token;
    }

    public Session getSession(String token) {
        loadSessionsFromFile();
        return activeSessions.get(token);
    }

    public boolean validateSession(String token) {
        loadSessionsFromFile();
        return activeSessions.containsKey(token);
    }

    public void invalidateSession(String token) {
        activeSessions.remove(token);
        saveSessionsToFile();

        // Delete the session file if no sessions are left
        if (activeSessions.isEmpty()) {
            deleteSessionFile();
        }
    }

    private void saveSessionsToFile() {
        try {
            // Ensure the storage directory exists
            File storageDir = new File("session");
            if (!storageDir.exists()) {
                storageDir.mkdirs(); // Create the directory if it doesn't exist
            }

            // Save sessions to the file
            objectMapper.writeValue(new File(STORAGE_PATH), activeSessions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSessionsFromFile() {
        try {
            File file = new File(STORAGE_PATH);
            if (file.exists()) {
                activeSessions = objectMapper.readValue(file, objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Session.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSessionFile() {
        File file = new File(STORAGE_PATH);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Session file deleted successfully.");
            } else {
                System.out.println("Failed to delete the session file.");
            }
        }
    }
}