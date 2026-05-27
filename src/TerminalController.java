import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class TerminalController {
    private final TerminalSessionRegistry registry;

    public TerminalController(TerminalSessionRegistry registry) {
        this.registry = registry;
    }

    @PostMapping
    public TerminalResponse createSession() {
        return registry.createSession();
    }

    @PostMapping("/{sessionId}/command")
    public ResponseEntity<TerminalResponse> sendCommand(
            @PathVariable String sessionId,
            @RequestBody TerminalCommandRequest request
    ) {
        String command = request == null ? "" : request.command();
        return ResponseEntity.ok(registry.sendCommand(sessionId, command));
    }

    @PostMapping("/{sessionId}/reset")
    public TerminalResponse resetSession(@PathVariable String sessionId) {
        return registry.resetSession(sessionId);
    }
}
