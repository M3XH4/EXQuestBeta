import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TerminalSessionRegistry {
    private final Map<String, WebAdventureSession> sessions = new ConcurrentHashMap<>();

    public TerminalResponse createSession() {
        String id = UUID.randomUUID().toString();
        WebAdventureSession session = new WebAdventureSession();
        sessions.put(id, session);
        return new TerminalResponse(id, session.boot(), session.getPrompt());
    }

    public TerminalResponse sendCommand(String sessionId, String command) {
        WebAdventureSession session = sessions.computeIfAbsent(sessionId, ignored -> new WebAdventureSession());
        return new TerminalResponse(sessionId, session.handle(command), session.getPrompt());
    }

    public TerminalResponse resetSession(String sessionId) {
        WebAdventureSession session = new WebAdventureSession();
        sessions.put(sessionId, session);
        return new TerminalResponse(sessionId, session.boot(), session.getPrompt());
    }
}
