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
                if (getPlayer().getGrimoire().getOwnSpells().isEmpty()) {
                    System.out.println("Spirit Guide: Do You Want To Cast Spell? (Yes/No)");
                    do {
                        String spellAnswer = getInput().nextLine();
                        if (spellAnswer.equalsIgnoreCase("Yes")) {
                            System.out.println("Spirit Guide: What Spell Would You Like To Use: ");

                            for(int i = 0; i < getPlayer().getGrimoire().getOwnSpells().size(); i++) {
                                if (i == 0) { System.out.print("("); }
                                System.out.print(getPlayer().getGrimoire().getOwnSpells().get(i).getSpellName());
                                if (i != (getPlayer().getGrimoire().getOwnSpells().size() - 1)) {System.out.print("/"); }
                                else { System.out.println(")"); }
                            }
                            do {
                                System.out.println("Your Response - ");
                                String spell = getInput().nextLine();
                                Spells selectedSpell = getPlayer().getGrimoire().getSpell(spell);
                                if (selectedSpell != null) {
                                    switch (selectedSpell) {
                                        case Fireball fireball -> {
                                            spellCastDisplay(fireball);
                                            fireball.setFire();
                                        }
                                        case Blizzard blizzard -> {
                                            spellCastDisplay(blizzard);
                                        }
                                        case Thunder thunder -> {
                                            spellCastDisplay(thunder);
                                        }
                                        default -> {
                                            System.out.println("Spirit Guide: I Cannot Read The Spell You Are Casting. Are You Sure That\nIs One Of Your Choices. Please Cast A Spell Again");
                                        }
                                    }
                                }
                            } while(spellSelectLoop);
                        }

                    } while (spellLoop);
                }
            }
        } while(commandLoop);
    }
    public void spellCastDisplay(Spells spells) {
        boolean spellCommandLoop = true;
        boolean castCommandLoop = true;
        boolean readCommandLoop = true;
        if (getPlayer().getMana() < spells.getManaNeeded()) {
            System.out.println("Spirit Guide: You Have Insufficient Mana, Unable To Cast Spell.");
            spellSelectLoop = false;
            spellLoop = false;
        } else {
            do {
                System.out.println("Spirit Guide: Do You Want To: (Cast/Read/Back)");                                                 
                System.out.println("|Cast - Cast Spell|  |Read - Read Description of Spell|  |Back - Go Back To Spell Selection|");
                System.out.print("Your Response - ");
                String spellCommand = getInput().nextLine();
                if (spellCommand.equalsIgnoreCase("Cast")) {
                    System.out.println("Spirit Guide: Are You Sure You Want To Cast " + spells.getSpellName() + "? (Yes/No)");
                    do {
                        System.out.print("Your Response - ");
                        String castCommand = input.nextLine();
                        if (castCommand.equalsIgnoreCase("Yes"))  {
                            spells.spellAttack(getPlayer(), getEnemy());
                            castCommandLoop = false;
                            spellCommandLoop = false;
                            spellSelectLoop = false;
                            spellLoop = false;
                            commandLoop = false;
                        } else if (castCommand.equalsIgnoreCase("No")) {
                            castCommandLoop = false;
                            spellCommandLoop = false;
                        } else {
                            confuseMessage(1);
                        }
                    } while (castCommandLoop);
                } else if (spellCommand.equalsIgnoreCase("Read")) {
                    spells.getSpellDescription();
                    do {
                        System.out.println("Spirit Guide: Do You Want To Cast This Spell? (Yes/No)");
                        String readCommand = getInput().nextLine();
                        if (readCommand.equalsIgnoreCase("Yes")) {
                            readCommandLoop = false;
                        } else if (readCommand.equalsIgnoreCase("No")) {
                             readCommandLoop = false;
                             spellCommandLoop = false;
                        } else {
                             confuseMessage(2);
                        }
                    } while(readCommandLoop);
                } else if (spellCommand.equalsIgnoreCase("Back")) {
                    spellCommandLoop = false;
                } else {
                    confuseMessage(2);
                }                       

            } while(spellCommandLoop);
        }
    }
    public void winDisplay() {
        System.out.println("Spirit Guide: You Defeated " + getEnemy().getName());
        System.out.println("Spirit Guide: Congratulations!!!");
        getPlayer().levelUp(getEnemy());
    }
    public void loseDisplay() {
        System.out.println("Spirit Guide: You Have Been Defeated By " + getEnemy().getName());
        System.out.println("Spirit Guide: Don't Worry Warrior, I Will Revive You");
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
