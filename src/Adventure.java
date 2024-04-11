import java.util.*;
public class Adventure {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean gameLoop = true;
        Player player = FileManager.loadPlayer();
        String gameTitle = "-------------------------------------------------------       EXQuest       -------------------------------------------------------";
        System.out.println(ColorTextManager.BACKGROUND_BLACK + ColorTextManager.BLUE + gameTitle + ColorTextManager.RESET);
        SoundManager.playMP3("start_game.mp3");
        if (player == null) {
            System.out.println("Unknown Person: Good Day Traveller, What Is Your Name? ");
            System.out.print("Your Name - ");
            String playerName = input.nextLine();
            player = new Player(playerName);
            System.out.println("Unknown Person: Welcome To The World Of Gaia, Warrior " + playerName);
            System.out.println("Unknown Person: I Am A Spirit Guide, I Will Help You Throughout Your Journey In This World.");
            System.out.println("Spirit Guide: Do You Want To Start Your Adventure? (Yes/No)");
            System.out.println("Spirit Guide: If You Answer No, I Can Use A Spell To Help You Go Back To Your World.");
        } else {
            System.out.println("Spirit Guide: Welcome Back, Warrior " + player.getName() + ".");
            System.out.println("Spirit Guide: Do You Want To Continue Your Adventure? (Yes/No)");
        }

        do {
            System.out.print("Your Response - ");
            String gameStart = input.nextLine();
            if (gameStart.equalsIgnoreCase("Yes")) {
                FileManager.savePlayer(player);
                if (player.getInventory().getOwnItems().isEmpty()) {
                    System.out.println("Spirit Guide: Here, Warrior " + player.getName() + ", I Will Provide You Some Basic Essentials To Survive This World.");
                    player.getInventory().addItem("Leather Helmet", 1);
                    player.getInventory().addItem("Leather Robe", 1);
                    player.getInventory().addItem("Leather Gloves", 1);
                    player.getInventory().addItem("Iron Armor", 1);
                    player.getInventory().addItem("Leather Pants", 2);
                    player.getInventory().addItem("Leather Boots", 1);
                    player.getInventory().addItem("Lesser Health Potion", 10);
                    player.getInventory().addItem("Lesser Mana Potion", 10);
                }
                Global.placeColorLine(gameTitle);
                do {
                    Enemy enemy = new Enemy();
                    ScenarioManager scenarioManager = new ScenarioManager(player, enemy);
                    scenarioManager.getScreen().startScreen();
                    Random random = new Random();
                    int randomizedEventNumber = random.nextInt(scenarioManager.getScenarios().size());
                    if (randomizedEventNumber == 1) {
                        scenarioManager.getBattle().startBattle();
                    } else if (randomizedEventNumber == 2) {
                        scenarioManager.getMarket().startMarket();
                    } else if (randomizedEventNumber == 3) {
                        scenarioManager.getCamping().startCamp();
                    }
                } while(true);
            } else if (gameStart.equalsIgnoreCase("No")) {
                Global.placeColorLine(gameTitle);
                do {
                    System.out.println("Spirit Guide: Do You Want To Go Back To Your World? (Yes/No)");
                    System.out.print("Your Response - ");
                    String answer = input.nextLine();
                    if (answer.equalsIgnoreCase("Yes")) {
                        System.out.println("Spirit Guide: Farewell, Warrior " + player.getName() + ".");
                        FileManager.deletePlayer();
                        gameLoop = false;
                        break;
                    } else if (answer.equalsIgnoreCase("No")) {
                        break;
                    } else {
                        System.out.println("Spirit Guide: I'm Sorry, But I Could Not Understand What You're Saying Warrior " + player.getName() + ".");
                    }
                } while (true);
                Global.placeColorLine(gameTitle);
                System.out.println("Spirit Guide: Do You Want To Continue Your Adventure? (Yes/No)");
            } else {
                System.out.println("Spirit Guide: I'm Sorry, But I Could Not Understand Your Response, What Was It Again?");
                System.out.println("Spirit Guide: Do You Want To Continue Your Adventure? (Yes/No)");
            }
        } while (gameLoop);

    }
}
