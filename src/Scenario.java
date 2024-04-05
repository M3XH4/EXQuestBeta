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
        getPlayer().displayFullStats();
        String commandsHelpMsg = "| ( Equip ) - Equip Your Equipments | ( Open ) - Open Inventory | ( View ) - View Your Spells | ( Roam ) - Roam Around |";
        System.out.println("Spirit Guide: What Would You Like To Do?");
        Global.placeLine(commandsHelpMsg);
        System.out.println(commandsHelpMsg);
        Global.placeLine(commandsHelpMsg);
        System.out.print("Your Response - ");
        String playerCommand = getInput().nextLine();

        if (playerCommand.equalsIgnoreCase("Equip")) {

            String equipHelpMsg = "| ( Weapon ) - Equip Weapon\t| ( Helmet ) - Equip Helmet \t| ( Armor ) - Equip Armor\t|";
            String equipHelpMsg2 = "| ( Gloves ) - Equip Gloves\t| ( Leggings ) - Equip Leggings\t| ( Boots)  - Equip Boots\t|";
            System.out.println("Spirit Guide: Which Equipment Types Would You Like To Equip");
            Global.placeLine(equipHelpMsg2, 2);
            System.out.println(equipHelpMsg);
            System.out.println(equipHelpMsg2);
            Global.placeLine(equipHelpMsg2, 2);
            System.out.print("Your Response - ");
            String equipCommand = getInput().nextLine();

        } else if (playerCommand.equalsIgnoreCase("Open")) {

        } else if (playerCommand.equalsIgnoreCase("View")) {

        } else if (playerCommand.equalsIgnoreCase("Roam")) {
            System.out.println("Warrior " + getPlayer().getName() + " Is Roaming Around...");
            Global.pause();
        } else {
        }
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
        getEnemy().setHealth(getEnemy().getMaxHealth());
        getEnemy().attacking();

        String[] battleMessage = {
                "Spirit Guide: Be Careful, " + getEnemy().getName() + " Is Heading Your Way.",
                "Spirit Guide: " + getEnemy().getName() + " Ambushes You, Warrior " + getPlayer().getName() + " Heed Carefully"
        };

        String temp_title = "------- Battle Starting -------";
        System.out.println(battleMessage[getRandom().nextInt(battleMessage.length)]);
        System.out.println(temp_title);

        int turn = 1;

        while (getPlayer().getHealth() > 0 && getEnemy().getHealth() > 0) {
            System.out.print("\nTurn " + turn + " ");
            Global.placeLine(7, temp_title);
            if (!(turn % 2 == 0)) {
                getCLI().playerTurnDisplay();
                Global.pause();
            } else {
                getCLI().enemyTurnDisplay();
                Global.placeLine(temp_title);
                Global.pause();
            }
            addManaPerTurn();
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
    public void addManaPerTurn() {
        if (getPlayer().getMana() <= getPlayer().getMaxMana()) {
            getPlayer().setMana(getPlayer().getMana() + 10);
            if (getPlayer().getMana() > getPlayer().getMaxMana()) {
                getPlayer().setMana(getPlayer().getMaxMana());
            }
        }
    }
}