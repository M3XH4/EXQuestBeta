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
        getCLI().playerScreenDisplay();
    }
}
class Camping extends Scenario {
    public Camping(Player player, Enemy enemy) {
        super(player, enemy);
    }
    public void startCamp() {
        String tempTitle = "------- Battle Starting -------";
        Global.placeLine(tempTitle);
        System.out.println("Spirit Guide: Maybe We Should Rest Here, It's Safe Around Here");
        System.out.println("Warrior " + getPlayer().getName() + ": Okay.");
        Global.pause();
        System.out.println("Spirit Guide: Rise And Shine Warrior, It's Time To Move.");
        System.out.println("Warrior " + getPlayer().getName() + ": All Right, Thanks.");
        System.out.println("Spirit Guide: You Have Successfully Recovered Your Health And Mana To The Full.");
        getPlayer().setHealth(getPlayer().getMaxHealth());
        getPlayer().setMana(getPlayer().getMaxMana());
        Global.placeLine(tempTitle);
    }
}
class Market extends Scenario {
    private Set<Item> sellItems;
    private Set<Item> sellTempItems;
    public Market(Player player, Enemy enemy) {
        super(player, enemy);
        setSellItems(new HashSet<>());
        setSellTempItems(new HashSet<>());
        for (int i = 0; i < 10; i++) {
            Item tempItem = getPlayer().getInventory().getItem(i);
            sellItems.add(tempItem);
        }
    }
    public void startMarket() {
        boolean marketSelectLoop = true;
        String tempTitle = "------- Battle Starting -------";
        Global.placeLine(tempTitle);
        System.out.println("Spirit Guide: There Is A Travelling Merchant Over There.");
        System.out.println("Spirit Guide: He Sells 10 Items And Buys Your Stuff As Well.");
        System.out.println("Spirit Guide: He Always Have Sell New Items Every Time You Visit Him.");
        System.out.println("Spirit Guide: Do You Want To Go To Him? (Yes/No)");
        do {
            System.out.print("Your Response - ");
            String marketSelect = getInput().nextLine();
            if (marketSelect.equalsIgnoreCase("Yes")) {
                getCLI().marketDisplay(getSellTempItems(getSellItems()));
                marketSelectLoop = false;
            } else if (marketSelect.equalsIgnoreCase("No")) {
                marketSelectLoop = false;
            } else {
                System.out.println("Spirit Guide: I Could Not Understand What Your Saying Warrior " + getPlayer().getName() + ", What Was It Again?");
            }
        } while (marketSelectLoop);
    }

    public Set<Item> getSellItems() {
        return sellItems;
    }

    public void setSellItems(Set<Item> sellItems) {
        this.sellItems = sellItems;
    }

    public static Set<? extends Item> getSellTempItems(Set<? extends Item> sellItems) {
        Set<Item> tempSellItems = new HashSet<>();
        for (Item item: sellItems) {
            if (item instanceof Weapon) {
                if (item instanceof Knife knife) {
                    tempSellItems.add(new Knife(knife));
                } else if (item instanceof Sword sword) {
                    tempSellItems.add(new Sword(sword));
                } else if (item instanceof Spear spear) {
                    tempSellItems.add(new Spear(spear));
                }
            } else if (item instanceof Armor) {
                if (item instanceof Helmet helmet) {
                    tempSellItems.add(new Helmet(helmet));
                } else if (item instanceof Torso torso) {
                    tempSellItems.add(new Torso(torso));
                } else if (item instanceof Gloves gloves) {
                    tempSellItems.add(new Gloves(gloves));
                } else if (item instanceof Leggings leggings) {
                    tempSellItems.add(new Leggings(leggings));
                } else if (item instanceof Boots boots) {
                    tempSellItems.add(new Boots(boots));
                }
            } else if (item instanceof Potion potion) {
                tempSellItems.add(new Potion(potion));
            }
        }
        return tempSellItems;
    }

    public void setSellTempItems(Set<Item> sellTempItems) {
        this.sellTempItems = sellTempItems;
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
            } else {
                getCLI().enemyTurnDisplay();
            }
            Global.pause();
            Global.placeLine(temp_title);
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