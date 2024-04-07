import java.lang.reflect.Array;
import java.util.*;
public class Scenario {
    private Scanner input;
    private Player player;
    private Enemy enemy;
    private Random random;
    private Interface cli;

    public Scenario(Player player, Enemy enemy) {
        setInput(new Scanner(System.in));
        setRandom(new Random());
        setPlayer(player);
        setEnemy(enemy);
        setEnemy(getEnemy().getEnemies().get(getRandom().nextInt(getEnemy().getEnemies().size())));

        setCLI(new Interface(getPlayer(), getEnemy()));
    }

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
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

    public Interface getCLI() {
        return cli;
    }
    public void setCLI(Interface cli) {
        this.cli = cli;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
class Screen extends Scenario {
    public Screen(Player player, Enemy enemy) {
        super(player, enemy);
    }

    public void startScreen() {
        getCLI().playerScreenDiplay();
    }
}
class Market extends Scenario {
    public Market(Player player, Enemy enemy) {
        super(player, enemy);
    }
}
class Battle extends Scenario {
    public Battle(Player player, Enemy enemy) {
        super(player, enemy);
    }

    public void startBattle() {
        String[] battleMessage = {
                "Spirit Guide: Be Careful, " + getEnemy().getName() + " Is Heading Your Way.",
                "Spirit Guide: " + getEnemy().getName() + " Ambushes You, Warrior " + getPlayer().getName() + " Heed Carefully"
        };
        getEnemy().setHealth(getEnemy().getMaxHealth());
        String temp_title = "------- Battle Starting -------";
        System.out.println(battleMessage[getRandom().nextInt(battleMessage.length)]);
        getEnemy().attacking();
        System.out.println(temp_title);

        int turn = 1;

        while (getPlayer().getHealth() > 0 && getEnemy().getHealth() > 0) {
            System.out.print("\nTurn " + turn + " ");
            Global.placeLine(7, temp_title);
            if (!(turn % 2 == 0)) {
                getCLI().playerTurnDisplay();
                Global.pause();
                Global.placeLine(temp_title);
            } else {
                getCLI().enemyTurnDisplay();
                Global.pause();
                Global.placeLine(temp_title);
            }
            getPlayer().addAttribute(Global.AttributeType.Mana, 10);
            turn ++;
            Global.placeLine(temp_title);
        }
        if (getPlayer().getHealth() <= 0) {
            getCLI().loseDisplay();
            Global.pause();
        } else if (getEnemy().getHealth() <= 0) {
            getCLI().winDisplay();
            Global.pause();
        }
    }
}