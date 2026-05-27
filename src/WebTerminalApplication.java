import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({TerminalController.class, TerminalSessionRegistry.class})
public class WebTerminalApplication {
    public static void main(String[] args) {
        System.setProperty("exquest.sound.enabled", "true");
        SpringApplication.run(WebTerminalApplication.class, args);
    }
}
