import java.util.*;

public class Global {
    public enum AttributeType {
        Mana,
        Health,
        Attack,
        Heal
    }
    public static void placeLine(String msg) {
        int lineLength = msg.length();
        for (int i = 0; i < lineLength; i++) {
            System.out.print("-");
            if (i == (lineLength - 1)) {
                System.out.print("\n");
            }
        }
    }
    public static void placeLine(int min, String msg) {
        int lineLength = msg.length() - min;
        for (int i = 0; i < lineLength; i++) {
            System.out.print("-");
            if (i == (lineLength - 1)) {
                System.out.print("\n");
            }
        }
    }
    public static void placeLine(String msg, int plus) {
        int lineLength = msg.length() + plus;
        for (int i = 0; i < lineLength; i++) {
            System.out.print("-");
            if (i == (lineLength - 1)) {
                System.out.print("\n");
            }
        }
    }
    public static void pause() {
        Scanner input = new Scanner(System.in);
        System.out.print("Press Enter To Continue...");
        input.nextLine();
    }
    public static String spacerString(int spaces, String itemString) {
        int itemNameLength = spaces - itemString.length();
        return itemString + " ".repeat(Math.max(0, itemNameLength));
    }
}
