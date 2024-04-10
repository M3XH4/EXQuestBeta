import java.io.*;
import java.nio.file.*;
import java.util.*;
public class FileManager {
    public static final String filePathPlayerData = getSourcePath() + "\\files\\playerData.dat";

    private static String getSourcePath() {
        Path currentPath = Paths.get("");
        return currentPath.toAbsolutePath().toString() + "\\src";
    }
    public static void savePlayer(Player player) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePathPlayerData))) {
            outputStream.writeObject(player);
            System.out.println("Player Saved Successfully");
        } catch (IOException e) {
            System.err.println("Error Saving Data: " + e.getMessage());
        }
    }
    public static Player loadPlayer() {
        Player tempPlayer = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePathPlayerData))) {
            tempPlayer = (Player) inputStream.readObject();
            System.out.println("Data Loaded Successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error Loading Data: " + e.getMessage());
        }
        return tempPlayer;
    }
    public static void updatePlayer(String name, int maxHealth, int maxMana, int coins, int level, int exp, int maxExp) {
        Player tempPlayer = loadPlayer();
        if (tempPlayer.getName().equalsIgnoreCase(name)) {
            tempPlayer.setMaxHealth(maxHealth);
            tempPlayer.setHealth(maxHealth);
            tempPlayer.setMaxMana(maxMana);
            tempPlayer.setMana(maxMana);
            tempPlayer.setCoins(coins);
            tempPlayer.setLevel(level);
            tempPlayer.setExp(exp);
            tempPlayer.setMaxExp(maxExp);
            savePlayer(tempPlayer);
        } else {
            System.err.println("User Not Found.");
        }
    }
}
