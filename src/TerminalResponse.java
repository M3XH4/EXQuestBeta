import java.util.List;

public record TerminalResponse(String sessionId, List<String> lines, String prompt) {
}
