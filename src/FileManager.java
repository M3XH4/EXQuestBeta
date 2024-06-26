import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class FileManager {
    public static final String filePathPlayerData = getSourcePath() + "\\files\\playerData.dat";

    private static String getSourcePath() {
        Path currentPath = Paths.get("");
        return currentPath.toAbsolutePath() + "\\src";
    }
    public static void savePlayer(Player player) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePathPlayerData))) {
            outputStream.writeObject(player);
        } catch (IOException e) {
            System.err.println("Error Saving Data: " + e.getMessage());
        }
    }
    public static Player loadPlayer() {
        Player tempPlayer = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePathPlayerData))) {
            tempPlayer = (Player) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error Loading Data: " + e.getMessage());
        }
        return tempPlayer;
    }
    public static void deletePlayer() {
        try {
            File file = new File(filePathPlayerData);
            if (file.delete()) {
                System.out.println("Spirit Guide: I Wish For Your Safety And I Hope We Will See Each Other Again");
            }
        } catch (Exception e) {
            System.err.println("Spirit Guide: Sorry Hero, But I Cannot Open The Portal To Your World.");
        }
    }
    public static void updatePlayer(String name, int maxHealth, int maxMana, int coins,
                                    int level, int exp, int maxExp, ArrayList<Item> inventory, Weapon weapon, Armor helmet, Armor torso,
                                    Armor gloves, Armor leggings, Armor boots) {
        Player tempPlayer = loadPlayer();
        if (tempPlayer.getName().equalsIgnoreCase(name)) {
            try {
                tempPlayer.setMaxHealth(maxHealth);
                tempPlayer.setHealth(maxHealth);
                tempPlayer.setMaxMana(maxMana);
                tempPlayer.setMana(maxMana);
                tempPlayer.setCoins(coins);
                tempPlayer.setLevel(level);
                tempPlayer.setExp(exp);
                tempPlayer.setMaxExp(maxExp);
                tempPlayer.getInventory().setOwnItems(inventory);
                if (weapon != null) {
                    tempPlayer.getInventory().setEquippedWeapon(weapon);
                }
                if (helmet != null) {
                    tempPlayer.getInventory().setEquippedHelmet((Helmet) helmet);
                }
                if (torso != null) {
                    tempPlayer.getInventory().setEquippedTorso((Torso) torso);
                }
                if (gloves != null) {
                    tempPlayer.getInventory().setEquippedGloves((Gloves) gloves);
                }
                if (leggings != null) {
                    tempPlayer.getInventory().setEquippedLeggings((Leggings) leggings);
                }
                if (boots != null) {
                    tempPlayer.getInventory().setEquippedBoots((Boots) boots);
                }
                savePlayer(tempPlayer);
            } catch (Exception e) {
                System.out.println("Spirit Guide: It Seems That This Save Option Is Not Working.");
            }
        } else {
            System.err.println("Spirit Guide: Where Are You Warrior?");
        }
    }
}
