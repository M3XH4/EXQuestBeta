import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SoundManager {

    public static void playMP3(String fileName) {
        try {
            FileInputStream file = new FileInputStream(getSourcePath() + fileName);
            AdvancedPlayer player = new AdvancedPlayer(file);
            player.play();
        } catch (JavaLayerException | IOException e) {
            System.err.println("Error Playing Data: " + e.getMessage());
        }
    }
    private static String getSourcePath() {
        Path currentPath = Paths.get("");
        return currentPath.toAbsolutePath() + "\\src\\files\\sounds\\";
    }
}
