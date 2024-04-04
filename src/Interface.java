import java.util.*;

public class Interface {
    private Player player;
    private Enemy enemy;
    private Random random;
    private Scanner input;

    boolean commandLoop = true;
    boolean spellLoop = true;
    boolean spellSelectLoop = true;

    public Interface(Player player, Enemy enemy) {
        setInput(new Scanner(System.in));
        setRandom(new Random());

        setPlayer(player);
        setEnemy(enemy);
    }

    public void enemyTurnDisplay() {
        System.out.println(getEnemy().getName() + "'s Turn");
        getEnemy().attacks(getPlayer(), getEnemy(), getRandom().nextInt(getEnemy().getSkills().size()));
    }
    public void playerTurnDisplay() {
        System.out.println("Warrior " + getPlayer().getName() + "'s Turn");
        do {
            System.out.println("Spirit Guide: What Would You Like To Do? (Attack/Spell/Flee)");
            System.out.print("Your Response - ");
            String command = getInput().nextLine();

            if (command.equalsIgnoreCase("Attack")) {
                System.out.println("Spirit Guide: Do `You Want TO Attack + " + getEnemy().getName() + "? (Yes/No)");
                do {
                    System.out.print("Your Response - ");
                    String attackAnswer = getInput().nextLine();

                    if (attackAnswer.equalsIgnoreCase("Yes")) {
                        getPlayer().attacks(getPlayer(), getEnemy());
                        commandLoop = false;
                        break;
                    } else if (attackAnswer.equalsIgnoreCase("No")) {
                        break;
                    } else {
                        confuseMessage(1);
                    }
                } while(true);
            } else if (command.equalsIgnoreCase("Spell")) {
                if (getPlayer().getGrimoire().getGrimoire().isEmpty()) {
                    System.out.println("Spirit Guide: Do You Want To Cast Spell? (Yes/No)");
                }
            }
        } while(commandLoop);
    }
    public void confuseMessage(int msgNumber) {
        System.out.print("Spirit Guide: I Could NOt Understand What Your Saying Warrior " + getPlayer().getName());
        if (msgNumber == 1) {
            System.out.println(", What Was It Again?");
        } else if (msgNumber == 2) {
            System.out.println(", I Will Repeat My Question Again");
        }
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }
}
