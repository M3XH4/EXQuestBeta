import java.util.Scanner;

public class Global {
    public static void placeLine(String msg) {
        int lineLength = msg.length();
        for (int i = 0; i < lineLength; i++) {
            System.out.print("-");
            if (i == (lineLength - 1)) {
                System.out.print("\n");
            }
        }
    }
    public static void placeLine(String msg, int min) {
        int lineLength = msg.length() - min;
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
        String next = input.nextLine();
    }
}
