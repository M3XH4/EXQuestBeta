import java.util.*;
public class Adventure {
    public static void main(String[] args) {
        boolean gameLoop = true;
        Scanner input = new Scanner(System.in);

        System.out.println("---------- EXQuest ----------");
        System.out.println("Unknown Person: Good Day Traveller, What Is Your Name? ");
        System.out.print("Your Name- ");
        String playerName = input.nextLine();
        System.out.println("Unknown Person: Welcome To The World Of Gaia, Warrior " + playerName);
        System.out.println("Unknown Person: I Am A Spirit Guide, I Will Help You Throughout Your Journey In This World.");

        Player player = new Player(playerName);

        do {
            System.out.println("Spirit Guide: Do You Want To Start Your Adventure? (Yes/No)");
            System.out.println("Spirit Guide: If You Answer No, I Can Use A Spell To Help You Go Back To YOur World.");
            String gameStart = input.nextLine();

            if (gameStart.equalsIgnoreCase("Yes")) {
                gameLoop = false;
                do {
                    Enemy enemy = new Enemy();
                    ScenarioManager scenarioManager = new ScenarioManager(player, enemy);
                    scenarioManager.getBattle().startBattle();
                } while(true);
            } else if (gameStart.equalsIgnoreCase("No")) {
                do {
                    System.out.println("Spirit Guide: Do You Want To Go Back To Your World? (Yes/No)");
                    System.out.println("Your Response - ");
                    String answer = input.nextLine();
                    if (answer.equalsIgnoreCase("Yes")) {
                        System.out.println("Spirit Guide: Farewell, Warrior " + player.getName() + ".");
                        System.out.println("Spirit Guide: I Wish For Your Safety And I Hope We Will See Each Other Again");
                    } else if (answer.equalsIgnoreCase("No")) {
                        break;
                    } else {
                        System.out.println("Spirit Guide: I'm Sorry, But I Could Not Understand What You're Saying Warrior " + player.getName() + ".");
                    }
                } while (true);
            }

        } while (gameLoop);

    }
}
