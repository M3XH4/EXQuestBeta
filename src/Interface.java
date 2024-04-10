import java.util.*;

public class Interface {
    private Player player;
    private Enemy enemy;
    private Random random;
    private Scanner input;
    private Inventory inventory;
    private MarketInventory marketInventory;
    boolean commandLoop = true;
    boolean spellLoop = true;
    boolean spellSelectLoop = true;
    boolean playerScreenLoop = true;
    boolean equipScreenLoop = true;
    boolean equipSelectLoop = true;
    boolean inventorySelectLoop = true;
    boolean consumableSelectLoop = true;
    boolean spellsSelectLoop = true;
    boolean marketSelectLoop = true;
    private final String tempTitle = "------- Battle Starting -------";
    public Interface(Player player, Enemy enemy) {
        setInput(new Scanner(System.in));
        setRandom(new Random());
        setInventory(new Inventory());
        setMarketInventory(new MarketInventory());

        setPlayer(player);
        setEnemy(enemy);
    }
    public void playerScreenDisplay() {
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
                viewSpellDisplay();
            } else if (playerCommand.equalsIgnoreCase("Roam")) {
                System.out.println("Warrior " + getPlayer().getName() + " Is Roaming Around...");
                playerScreenLoop = false;
                Global.pause();
            } else if (playerCommand.equalsIgnoreCase("Save")) {
                FileManager.updatePlayer(getPlayer().getName(), getPlayer().getMaxHealth(), getPlayer().getMaxMana(), getPlayer().getCoins(),
                        getPlayer().getLevel(), getPlayer().getExp(), getPlayer().getMaxExp(), getPlayer().getInventory().getOwnItems(),
                        getPlayer().getInventory().getEquippedWeapon(), getPlayer().getInventory().getEquippedHelmet(), getPlayer().getInventory().getEquippedTorso(),
                        getPlayer().getInventory().getEquippedGloves(), getPlayer().getInventory().getEquippedLeggings(), getPlayer().getInventory().getEquippedBoots());
            } else {
                confuseMessage(2);
            }
        } while (playerScreenLoop);
    }
    public void viewSpellDisplay() {
        do {
            spellsSelectLoop = true;
            getPlayer().getGrimoire().displayOwnSpells();
            System.out.println("Spirit Guide: Would You Like To Go Back? (Back)");
            System.out.print("Your Response - ");
            String spellsSelect = getInput().nextLine();
            if (spellsSelect.equalsIgnoreCase("Back")) {
                spellsSelectLoop = false;
            } else {
                confuseMessage(2);
            }
        } while (spellsSelectLoop);
    }
    public void equipEquipmentsDisplay() {
        do {
            String equipHelpMsg = "|          ( Weapon ) - Equip Weapon         |        ( Helmet )   - Equip Helmet          |         ( Armor ) - Equip Armor       |";
            String equipHelpMsg2 = "|          ( Gloves ) - Equip Gloves         |        ( Leggings ) - Equip Leggings        |         ( Boots)  - Equip Boots       |";
            System.out.println("Spirit Guide: Which Equipment Types Would You Like To Equip Or Would You Like To Go Back? (Back)");
            Global.placeLine(equipHelpMsg2);
            System.out.println(equipHelpMsg);
            System.out.println(equipHelpMsg2);
            Global.placeLine(equipHelpMsg2);
            System.out.print("Your Response - ");
            String equipCommand = getInput().nextLine();
            switch (equipCommand.toLowerCase()) {
                case "weapon" -> askEquipItem("Weapon", getPlayer().getInventory().getItemOfType(Weapon.class));
                case "helmet" -> askEquipItem("Helmet", getPlayer().getInventory().getItemOfType(Helmet.class));
                case "armor" -> askEquipItem("Armor", getPlayer().getInventory().getItemOfType(Torso.class));
                case "gloves" -> askEquipItem("Gloves", getPlayer().getInventory().getItemOfType(Gloves.class));
                case "leggings" -> askEquipItem("Leggings", getPlayer().getInventory().getItemOfType(Leggings.class));
                case "boots" -> askEquipItem("Boots", getPlayer().getInventory().getItemOfType(Boots.class));
                case "back" -> equipScreenLoop = false;
                default -> confuseMessage(2);
            }
        } while (equipScreenLoop);
    }
    public void askEquipItem(String itemType, List<? extends Item> items) {
        do {
            getPlayer().getInventory().displayOwnItems(items);
            System.out.println("Spirit Guide: Which " + itemType + " Would You Like To Equip Or Would You Like To Choose Another Equipment? (Back)");
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Your Response - ");
                String choice = input.nextLine();
                Item chosenItem = getPlayer().getInventory().searchItem(choice);

                if(chosenItem != null) {
                    switch(itemType.toLowerCase()) {
                        case "weapon":
                            try {
                                if (chosenItem.getQuantity() != 0) {
                                    if (chosenItem.equals(getPlayer().getInventory().getEquippedWeapon())) {
                                        System.out.println("Spirit Guide: Already Equipped Weapon.");
                                    } else {
                                        getPlayer().equipEquipment(chosenItem);
                                        System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType);
                                    }
                                } else {
                                    System.out.println("Spirit Guide: You Don't Have That Item In Inventory.");
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("Spirit Guide: You Cannot Put This Weapon On In Your Hands");
                            }
                            break;
                        case "helmet":
                            try {
                                if (chosenItem.getQuantity() != 0) {
                                    if(chosenItem.equals(getPlayer().getInventory().getEquippedHelmet())) {
                                        System.out.println("Spirit Guide: Already Equipped Helmet.");
                                    } else {
                                        getPlayer().equipEquipment(chosenItem);
                                        System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType + ".");
                                    }
                                } else {
                                    System.out.println("Spirit Guide: You Don't Have That Item In Your Inventory.");
                                }
                            } catch (Exception e) {
                                System.out.println("Spirit Guide: You Cannot Put This Equipment On In Your Head.");
                            }
                            break;
                        case "armor":
                            try {
                                if (chosenItem.getQuantity() != 0) {
                                    if(chosenItem.equals(getPlayer().getInventory().getEquippedTorso())) {
                                        System.out.println("Spirit Guide: Already Equipped Torso.");
                                    } else {
                                        getPlayer().equipEquipment(chosenItem);
                                        System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType);
                                    }
                                } else {
                                    System.out.println("Spirit Guide: You Don't Have That Item In Inventory.");
                                }
                            } catch (Exception e) {
                                System.out.println("Spirit Guide: You Cannot Put This Equipment On In Your Body.");
                            }
                            break;
                        case "gloves":
                            try {
                                if (chosenItem.getQuantity() != 0) {
                                    if(chosenItem.equals(getPlayer().getInventory().getEquippedGloves())) {
                                        System.out.println("Spirit Guide: Already Equipped Gloves.");
                                    } else {
                                        getPlayer().equipEquipment(chosenItem);
                                        System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType + ".");
                                    }
                                } else {
                                    System.out.println("Spirit Guide: You Don't Have That Item In Your Inventory.");
                                }
                            } catch (Exception e) {
                                System.out.println("Spirit Guide: You Cannot Put This Equipment On In Your Hands.");
                            }
                            break;
                        case "leggings":
                            try {
                                if (chosenItem.getQuantity() != 0) {
                                    if(chosenItem.equals(getPlayer().getInventory().getEquippedLeggings())) {
                                        System.out.println("Spirit Guide: Already Equipped Leggings.");
                                    } else {
                                        getPlayer().equipEquipment(chosenItem);
                                        System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType + ".");
                                    }
                                } else {
                                    System.out.println("Spirit Guide: You Don't Have That Item In Your Inventory.");
                                }
                            } catch (Exception e) {
                                System.out.println("Spirit Guide: You Cannot Put This Equipment On In Your Legs.");
                            }
                            break;
                        case "boots":
                            try {
                                if (chosenItem.getQuantity() != 0) {
                                    if(chosenItem.equals(getPlayer().getInventory().getEquippedBoots())) {
                                        System.out.println("Spirit Guide: Already Equipped Boots.");
                                    } else {
                                        getPlayer().equipEquipment(chosenItem);
                                        System.out.println(chosenItem.getItemName() + " Equipped Successfully On " + itemType + ".");
                                    }
                                } else {
                                    System.out.println("Spirit Guide: You Don't Have That Item In Your Inventory.");
                                }
                            } catch (Exception e) {
                                System.out.println("Spirit Guide: You Cannot Put This Equipment On In Your Feet.");
                            }
                            break;
                        default:
                            throw new IncorrectWeaponNameException("Spirit Guide: You Don't Have That Item In Your Inventory.");
                    }
                    equipSelectLoop = false;
                    break;
                } else {
                    if (choice.equalsIgnoreCase("Back")) {
                        equipSelectLoop = false;
                    } else {
                        throw new IncorrectWeaponNameException("Spirit Guide: You Don't Have That Item In Your Inventory.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Spirit Guide: Consumable Is Not In Inventory");
            }
        } while(equipSelectLoop);
    }
    public void openInventoryDisplay() {
        do {
            inventorySelectLoop = true;
            String inventoryCommandMsg = "|      ( Equip ) - Equip Your Equipments      |     ( Consume ) - Consume Consumables     |       ( Back ) - Back To Previous      |";
            getPlayer().getInventory().displayOwnItems(getPlayer().getInventory().getTempOwnItems());
            System.out.println("Spirit Guide: What Would You Like To Do? ");
            Global.placeLine(inventoryCommandMsg);
            System.out.println(inventoryCommandMsg);
            Global.placeLine(inventoryCommandMsg);
            System.out.print("Your Response - ");
            String inventorySelect = getInput().nextLine();
            if (inventorySelect.equalsIgnoreCase("Equip")) {
                inventorySelectLoop = false;
                equipEquipmentsDisplay();
            } else if (inventorySelect.equalsIgnoreCase("Consume")) {
                inventorySelectLoop = false;
                consumeConsumablesDisplay();
            }
            else if (inventorySelect.equalsIgnoreCase("Back")) {
                inventorySelectLoop = false;
            } else {
                confuseMessage(2);
            }
        } while (inventorySelectLoop);
    }
    public void consumeConsumablesDisplay() {
        do {
            consumableSelectLoop = true;
            getPlayer().getInventory().displayOwnItems(getPlayer().getInventory().getItemOfType(Consumables.class));
            System.out.println("Spirit Guide: Which Consumables Would You Like To Consume Or Would You Like To Go Back? (Back)");
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Your Response - ");
                String choice = input.nextLine();
                Item chosenItem = getPlayer().getInventory().searchItem(choice);

                if (chosenItem != null) {
                    if (chosenItem instanceof Potion potion) {
                        try {
                            if (chosenItem.getQuantity() != 0) {
                                potion.drinkPotion(getPlayer());
                                consumableSelectLoop = false;
                            } else {
                                System.out.println("Spirit Guide: You Don't Have That Item In Inventory.");
                            }
                        } catch (Exception e) {
                            System.out.println("Spirit Guide: You Cannot Consume This Consumable.");
                        }
                    }
                } else {
                    if (choice.equalsIgnoreCase("Back")) {
                        consumableSelectLoop = false;
                    } else {
                        System.out.println("Spirit Guide: You Cannot Consume This Consumable.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Spirit I Could Not Find That Consumable In Your Inventory.");
            }
        } while(consumableSelectLoop);
    }
    public void marketDisplay(Set<? extends Item> sellItems) {
        System.out.println("Travelling Merchant: Welcome Customer, These Are Things That I'm Selling Today.");
        Global.pause();
        do {
            Set<? extends Item> sellTempItems = Market.getSellTempItems(sellItems);
            getMarketInventory().displaySellItems(sellTempItems);
            System.out.println("Travelling Merchant: Would You Like To Buy Or Sell Items: (Buy/Sell/Leave)");
            System.out.print("Your Response - ");
            String marketSelect = getInput().nextLine();
            if (marketSelect.equalsIgnoreCase("Buy")) {
                boolean buySelectLoop = true;
                do {
                    System.out.println("Travelling Merchant: Which Item Would You Like To Buy? (Back)");
                    System.out.print("Your Response - ");
                    String buySelect = getInput().nextLine();
                    if (!(buySelect.equalsIgnoreCase("Back"))){
                        Item item = getInventory().getItem(buySelect);
                        boolean itemExist = false;
                        for (Item sellItem: sellItems) {
                            if (sellItem.getItemName().equals(item.getItemName())) {
                                itemExist = true;
                                break;
                            }
                        }
                        if (item != null && itemExist) {
                            boolean buyingSelectLoop = true;
                            System.out.println("Travelling Merchant: The Item You Are Trying To Buy Is " + item.getItemName() + " At " + item.getMarketValue() + " Coins.");
                            if (getPlayer().getCoins() >= item.getMarketValue()) {
                                int buyTurn = 1;
                                do {
                                    if (buyTurn == 1) {
                                        System.out.println("Travelling Merchant: Would You Like To Purchase Item? (Yes/No)");
                                    } else {
                                        System.out.println("Travelling Merchant: Would You Like To Purchase " + item.getItemName() + " Again? (Yes/No)");
                                    }
                                    System.out.print("Your Response - ");
                                    String buyingSelect = getInput().nextLine();
                                    if (buyingSelect.equalsIgnoreCase("Yes")) {
                                        getPlayer().setCoins(getPlayer().getCoins() - item.getMarketValue());
                                        System.out.println("Travelling Merchant: Thank You For Purchasing.");
                                        System.out.println("Spirit Guide: You Have Lose " + item.getMarketValue() + " Coins From Purchasing " + item.getItemName() + ".");
                                        getPlayer().getInventory().addItem(item.getItemName());
                                        buyTurn++;
                                    } else if (buyingSelect.equalsIgnoreCase("No")) {
                                        buyingSelectLoop = false;
                                        getMarketInventory().displaySellItems(sellTempItems);
                                    } else {
                                        confuseMessage(2);
                                    }
                                } while (buyingSelectLoop);
                            } else {
                                System.out.println("Spirit Guide: You Don't Have The Sufficient Coins To Purchase That Item.");
                                buySelectLoop = false;
                            }
                        } else {
                            try {
                                if (item != null) {
                                    System.out.println("Travelling Merchant: Your Item Is The " + item.getItemName() + ".");
                                    System.out.println("Travelling Merchant: I'm Sorry, But I Don't Have That Item Today. Let Me Ask Again.");
                                }
                            } catch (Exception e) {
                                System.out.println("Travelling Merchant: I Don't Understand What That Item Is. Let Me Asks Again");
                            }
                        }
                    } else {
                        if (buySelect.equalsIgnoreCase("Back")) {
                            buySelectLoop = false;
                        } else {
                            confuseMessage(2);
                        }
                    }
                } while (buySelectLoop);
            } else if (marketSelect.equalsIgnoreCase("Sell")) {
                boolean sellSelectLoop = true;
                do {
                    System.out.println("Travelling Merchant: I Purchase Items At 65% Of Their Original Value.");
                    Global.pause();
                    getPlayer().getInventory().displayOwnItems(getPlayer().getInventory().getTempOwnItems());
                    System.out.println("Travelling Merchant: What Item Would You Like To Sell? (Back)");
                    System.out.print("Your Response - ");
                    String sellSelect = getInput().nextLine();
                    if(!(sellSelect.equalsIgnoreCase("Back"))) {

                    } else {
                        if (sellSelect.equalsIgnoreCase("Back")) {
                            sellSelectLoop = false;
                        } else {
                            confuseMessage(2);
                        }
                    }
                } while (sellSelectLoop);
            } else if (marketSelect.equalsIgnoreCase("Leave")) {
                marketSelectLoop = false;
            } else {
                confuseMessage(2);
            }
        } while (marketSelectLoop);
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
                    do {
                        System.out.println("Spirit Guide: Do You Want To Cast Spell? (Yes/No)");
                        System.out.print("Your Response - ");
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
                            confuseMessage(2);
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
        getPlayer().setCoins(getPlayer().getCoins() + getEnemy().getCoins());
        System.out.println("Spirit Guide: You Acquired " + getEnemy().getCoins() + " Coins From " + getEnemy().getName() + ".");
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public MarketInventory getMarketInventory() {
        return marketInventory;
    }

    public void setMarketInventory(MarketInventory marketInventory) {
        this.marketInventory = marketInventory;
    }
}
