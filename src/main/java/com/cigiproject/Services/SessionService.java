package main.java.com.cigiproject.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SessionService {
    private Session activeSession; // Only one active session
    private static final String STORAGE_PATH = "session/session.json"; // File to store the session
    private ObjectMapper objectMapper = new ObjectMapper();

    // Make the Session class static
    public static class Session {
        private int id;
        private String email;
        private String role;
        private String token;

        public Session() {
            // Default constructor required by Jackson
        }

        public Session(int id, String email, String role, String token) {
            this.id = id;
            this.email = email;
            this.role = role;
            this.token = token;
        }

        public int getId() {
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

    /**
     * Creates a new session and invalidates the previous one.
     *
     * @param userId The ID of the user.
     * @param email  The email of the user.
     * @param role   The role of the user.
     * @return The session token.
     */
    public String createSession(int userId, String email, String role) {
        // Invalidate the previous session if it exists
        if (activeSession != null) {
            invalidateSession();
        }

        // Create a new session
        String token = UUID.randomUUID().toString();
        activeSession = new Session(userId, email, role, token);

        // Save the session to file
        saveSessionToFile();
        return token;
    }

    /**
     * Gets the current active session.
     *
     * @return The active session, or null if no session exists.
     */
    public Session getSession() {
        loadSessionFromFile();
        return activeSession;
    }

    /**
     * Validates the current session.
     *
     * @return True if a valid session exists, false otherwise.
     */
    public boolean validateSession() {
        loadSessionFromFile();
        return activeSession != null;
    }

    /**
     * Invalidates the current session.
     */
    public void invalidateSession() {
        activeSession = null;
        deleteSessionFile();
    }

    /**
     * Saves the current session to a file.
     */
    private void saveSessionToFile() {
        try {
            // Ensure the storage directory exists
            File storageDir = new File("session");
            if (!storageDir.exists()) {
                storageDir.mkdirs(); // Create the directory if it doesn't exist
            }

            // Save the session to the file
            objectMapper.writeValue(new File(STORAGE_PATH), activeSession);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the session from the file.
     */
    private void loadSessionFromFile() {
        try {
            File file = new File(STORAGE_PATH);
            if (file.exists()) {
                activeSession = objectMapper.readValue(file, Session.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the session file.
     */
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