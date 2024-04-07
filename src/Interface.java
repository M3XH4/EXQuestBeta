import java.util.*;

public class Interface {
    private Player player;
    private Enemy enemy;
    private Random random;
    private Scanner input;
    boolean commandLoop = true;
    boolean spellLoop = true;
    boolean spellSelectLoop = true;
    boolean playerScreenLoop = true;
    boolean equipScreenLoop = true;
    boolean equipSelectLoop = true;
    private final String tempTitle = "------- Battle Starting -------";
    public Interface(Player player, Enemy enemy) {
        setInput(new Scanner(System.in));
        setRandom(new Random());

        setPlayer(player);
        setEnemy(enemy);
    }
    public void playerScreenDiplay() {
        do {
            playerScreenLoop = true;
            getPlayer().displayFullStats();
            String commandsHelpMsg = "|  ( Equip ) - Equip Your Equipments  |  ( Open ) - Open Inventory  |   ( View ) - View Your Spells   |   ( Roam ) - Roam Around   |";
            System.out.println("Spirit Guide: What Would You Like To Do?");
            Global.placeLine(commandsHelpMsg);
            System.out.println(commandsHelpMsg);
            Global.placeLine(commandsHelpMsg);
            System.out.print("Your Response - ");
            String playerCommand = getInput().nextLine();

            if (playerCommand.equalsIgnoreCase("Equip")) {
                equipEquipmentsDisplay();
            } else if (playerCommand.equalsIgnoreCase("Open")) {
                openInventoryDisplay();
            } else if (playerCommand.equalsIgnoreCase("View")) {

            } else if (playerCommand.equalsIgnoreCase("Roam")) {
                System.out.println("Warrior " + getPlayer().getName() + " Is Roaming Around...");
                playerScreenLoop = false;
                Global.pause();
            } else {
                confuseMessage(2);
            }
        } while (playerScreenLoop);
    }
    public void equipEquipmentsDisplay() {
        do {
            String equipHelpMsg = "|          ( Weapon ) - Equip Weapon         |        ( Helmet )   - Equip Helmet          |         ( Armor ) - Equip Armor       |";
            String equipHelpMsg2 = "|          ( Gloves ) - Equip Gloves         |        ( Leggings ) - Equip Leggings        |         ( Boots)  - Equip Boots       |";
            System.out.println("Spirit Guide: Which Equipment Types Would You Like To Equip Or Would You Like To Go Back? (Back))");
            Global.placeLine(equipHelpMsg2);
            System.out.println(equipHelpMsg);
            System.out.println(equipHelpMsg2);
            Global.placeLine(equipHelpMsg2);
            System.out.print("Your Response - ");
            String equipCommand = getInput().nextLine();
            switch (equipCommand.toLowerCase()) {
                case "weapon" -> {
                    askEquipItem("Weapon", getPlayer().getInventory().getWeapons());
                }
                case "helmet" -> {
                    askEquipItem("Helmet", getPlayer().getInventory().getHelmets());
                }
                case "armor" -> {
                    askEquipItem("Armor", getPlayer().getInventory().getTorsos());
                }
                case "gloves" -> {
                    askEquipItem("Gloves", getPlayer().getInventory().getGloves());
                }
                case "leggings" -> {
                    askEquipItem("Leggings", getPlayer().getInventory().getLeggings());
                }
                case "boots" -> {
                    askEquipItem("Boots", getPlayer().getInventory().getBoots());
                }
                case "back" -> {
                    equipScreenLoop = false;
                }
                default -> {
                    confuseMessage(2);
                }
            }
        } while (equipScreenLoop);
    }
    public void askEquipItem(String itemType, List<? extends Item> items) {
        do {
            getPlayer().getInventory().displayOwnItems(items);
            System.out.println("Spirit Guide: Which " + itemType + " Would You Like To Equip Or Would You Like To Choose Another Equipment? (Back)");
            try {
                Scanner input = new Scanner(System.in);

                String choice = input.nextLine();
                Item chosenItem = getPlayer().getInventory().searchItem(choice);

                if(chosenItem != null) {
                    switch(itemType.toLowerCase()) {
                        case "weapon":
                            if (chosenItem.equals(getPlayer().getInventory().getEquippedWeapon())) {
                                System.out.println("Spirit Guide: Already Equipped Weapon.");
                            } else {
                                getPlayer().equipEquipment((Weapon) chosenItem);
                                System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType);
                            }
                            break;
                        case "helmet":
                            if(chosenItem.equals(getPlayer().getInventory().getEquippedHelmet())) {
                                System.out.println("Spirit Guide: Already Equipped Helmet.");
                            } else {
                                getPlayer().equipEquipment((Helmet) chosenItem);
                                System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType);
                            }
                            break;
                        case "armor":
                            if(chosenItem.equals(getPlayer().getInventory().getEquippedTorso())) {
                                System.out.println("Spirit Guide: Already Equipped Torso.");
                            } else {
                                getPlayer().equipEquipment((Torso) chosenItem);
                                System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType);
                            }
                            break;
                        case "gloves":
                            if(chosenItem.equals(getPlayer().getInventory().getEquippedGloves())) {
                                System.out.println("Spirit Guide: Already Equipped Gloves.");
                            } else {
                                getPlayer().equipEquipment((Gloves) chosenItem);
                                System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType);
                            }
                            break;
                        case "leggings":
                            if(chosenItem.equals(getPlayer().getInventory().getEquippedLeggings())) {
                                System.out.println("Spirit Guide: Already Equipped Leggings.");
                            } else {
                                getPlayer().equipEquipment((Leggings) chosenItem);
                                System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType);
                            }
                            break;
                        case "boots":
                            if(chosenItem.equals(getPlayer().getInventory().getEquippedBoots())) {
                                System.out.println("Spirit Guide: Already Equipped Boots.");
                            } else {
                                getPlayer().equipEquipment((Boots) chosenItem);
                                System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType);
                            }
                            break;
                        default:
                            throw new IncorrectWeaponNameException("I Could Not Find " + choice + " Item In The " + itemType + " Category");
                    }
                    equipSelectLoop = false;
                    break;
                } else {
                    if (choice.equalsIgnoreCase("Back")) {
                        equipSelectLoop = false;
                    } else {
                        throw new IncorrectWeaponNameException("Could Not Find " + choice + " Item In The " + itemType + " Category");
                    }
                }
            } catch (IncorrectWeaponNameException e) {
                System.out.println(e);
            }
        } while(equipSelectLoop);
    }
    public void openInventoryDisplay() {
        do {
            getPlayer().getInventory().displayOwnItems(getPlayer().getInventory().getTempOwnItems());
            System.out.println("Spirit Guide: Would You Like To Go Back? (Back)");
            String inventorySelect = getInput().nextLine();
            if (inventorySelect.equalsIgnoreCase("Back")) {
                break;
            }
        } while (true);
    }
    public void enemyTurnDisplay() {
        System.out.println(getEnemy().getName() + "'s Turn");
        Global.placeLine(tempTitle);
        getEnemy().attacks(getPlayer(), getEnemy(), getRandom().nextInt(getEnemy().getSkills().size()));
    }
    public void playerTurnDisplay() {
        System.out.println("Warrior " + getPlayer().getName() + "'s Turn");
        Global.placeLine(tempTitle);
        do {
            System.out.println("Spirit Guide: What Would You Like To Do? (Attack/Spell/Flee)");
            System.out.print("Your Response - ");
            String command = getInput().nextLine();

            if (command.equalsIgnoreCase("Attack")) {
                System.out.println("Spirit Guide: Do You Want To Attack " + getEnemy().getName() + "? (Yes/No)");
                boolean attackLoop = true;
                do {
                    System.out.print("Your Response - ");
                    String attackAnswer = getInput().nextLine();

                    if (attackAnswer.equalsIgnoreCase("Yes")) {
                        getPlayer().attacks(getPlayer(), getEnemy());
                        commandLoop = false;
                        attackLoop = false;
                    } else if (attackAnswer.equalsIgnoreCase("No")) {
                        attackLoop = false;
                    } else {
                        confuseMessage(1);
                    }
                } while(attackLoop);
            } else if (command.equalsIgnoreCase("Spell")) {
                if (!(getPlayer().getGrimoire().getOwnSpells().isEmpty())) {
                    System.out.println("Spirit Guide: Do You Want To Cast Spell? (Yes/No)");
                    do {
                        System.out.print("Your Response -  ");
                        String spellAnswer = getInput().nextLine();
                        if (spellAnswer.equalsIgnoreCase("Yes")) {
                            System.out.print("Spirit Guide: What Spell Would You Like To Use: ");

                            for(int i = 0; i < getPlayer().getGrimoire().getOwnSpells().size(); i++) {
                                if (i == 0) { System.out.print("("); }
                                System.out.print(getPlayer().getGrimoire().getOwnSpells().get(i).getSpellName());
                                if (i != (getPlayer().getGrimoire().getOwnSpells().size() - 1)) {System.out.print("/"); }
                                else { System.out.println(")"); }
                            }
                            StringBuilder spellsDisplay = new StringBuilder();
                            for(int i = 0; i < getPlayer().getGrimoire().getOwnSpells().size(); i++) {
                                if (i == 0) { spellsDisplay.append("| "); }
                                spellsDisplay.append("( ").append(getPlayer().getGrimoire().getOwnSpells().get(i).getSpellName()).append(" )").append(" - Cast ").append(getPlayer().getGrimoire().getOwnSpells().get(i).getSpellName());
                                if (i != (getPlayer().getGrimoire().getOwnSpells().size() - 1)) { spellsDisplay.append(" | "); }
                                else { spellsDisplay.append(" |"); }
                            }
                            Global.placeLine(spellsDisplay.toString());
                            System.out.println(spellsDisplay);
                            Global.placeLine(spellsDisplay.toString());
                            do {
                                System.out.print("Your Response - ");
                                String spell = getInput().nextLine();
                                Spells selectedSpell = getPlayer().getGrimoire().getSpell(spell);
                                if (selectedSpell != null) {
                                    switch (selectedSpell) {
                                        case Fireball fireball -> spellCastDisplay(fireball);
                                        case Blizzard blizzard -> spellCastDisplay(blizzard);
                                        case Thunder thunder -> spellCastDisplay(thunder);
                                        default -> System.out.println("Spirit Guide: I Cannot Read The Spell You Are Casting. Are You Sure That\nIs One Of Your Choices. Please Cast A Spell Again");
                                    }
                                }
                            } while(spellSelectLoop);
                        } else if (spellAnswer.equalsIgnoreCase("No")) {
                            spellLoop = false;
                        } else {
                            confuseMessage(1);
                        }

                    } while (spellLoop);
                } else {
                    System.out.println("Spirit Guide: You Have Not Learned Any Spells Yet");
                    System.out.println("Spirit Guide: Please Try Another Action.");
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
                String spellSelectionMsg = "| ( Cast ) - Cast Spell |  | ( Read ) - Read Description of Spell |  | ( Back ) - Go Back To Spell Selection |";
                Global.placeLine(spellSelectionMsg);
                System.out.println(spellSelectionMsg);
                Global.placeLine(spellSelectionMsg);
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
        Global.placeLine(tempTitle);
        System.out.println("Returning To Player Screen...");
    }
    public void loseDisplay() {
        System.out.println("Spirit Guide: You Have Been Defeated By " + getEnemy().getName());
        System.out.println("Spirit Guide: Don't Worry Warrior, I Will Revive You");
        Global.placeLine(tempTitle);
        System.out.println("Returning To Player Screen...");
    }
    public void confuseMessage(int msgNumber) {
        System.out.print("Spirit Guide: I Could Not Understand What Your Saying Warrior " + getPlayer().getName() + ", ");
        if (msgNumber == 1) {
            System.out.println(" What Was It Again?");
        } else if (msgNumber == 2) {
            System.out.println(" I Will Repeat My Question Again");
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
