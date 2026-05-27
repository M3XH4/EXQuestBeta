import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class WebAdventureSession {
    private static final String TITLE = "-------------------------------------------------------       EXQuest       -------------------------------------------------------";

    private enum Mode {
        AWAITING_NAME,
        START_CONFIRM,
        DELETE_CONFIRM,
        MAIN,
        INVENTORY,
        EQUIP_TYPE,
        EQUIP_ITEM,
        CONSUME_ITEM,
        BATTLE_ACTION,
        MARKET_CONFIRM,
        MARKET
    }

    private final Random random = new Random();
    private final List<Item> marketItems = new ArrayList<>();
    private Player player;
    private Enemy enemy;
    private Mode mode;
    private String pendingEquipType;

    public List<String> boot() {
        List<String> out = new ArrayList<>();
        out.add(TITLE);
        out.add("Booting EXQuest web terminal...");

        player = FileManager.loadPlayer();
        if (player == null) {
            mode = Mode.AWAITING_NAME;
            out.add("Unknown Person: Good Day Traveller, What Is Your Name?");
        } else {
            mode = Mode.START_CONFIRM;
            out.add("Spirit Guide: Welcome Back, Warrior " + player.getName() + ".");
            out.add("Spirit Guide: Do You Want To Continue Your Adventure? (Yes/No)");
        }
        return out;
    }

    public List<String> handle(String rawCommand) {
        String command = rawCommand == null ? "" : rawCommand.trim();
        List<String> out = new ArrayList<>();

        if (command.isBlank()) {
            out.add("Spirit Guide: I am listening.");
            return out;
        }

        switch (mode) {
            case AWAITING_NAME -> handleName(command, out);
            case START_CONFIRM -> handleStartConfirm(command, out);
            case DELETE_CONFIRM -> handleDeleteConfirm(command, out);
            case MAIN -> handleMain(command, out);
            case INVENTORY -> handleInventory(command, out);
            case EQUIP_TYPE -> handleEquipType(command, out);
            case EQUIP_ITEM -> handleEquipItem(command, out);
            case CONSUME_ITEM -> handleConsumeItem(command, out);
            case BATTLE_ACTION -> handleBattle(command, out);
            case MARKET_CONFIRM -> handleMarketConfirm(command, out);
            case MARKET -> handleMarket(command, out);
            default -> {
                mode = Mode.MAIN;
                renderMain(out);
            }
        }

        return out;
    }

    public String getPrompt() {
        return switch (mode) {
            case AWAITING_NAME -> "Your Name";
            case START_CONFIRM, DELETE_CONFIRM, MARKET_CONFIRM -> "Yes/No";
            case INVENTORY -> "Inventory";
            case EQUIP_TYPE -> "Equipment Type";
            case EQUIP_ITEM -> "Item Name";
            case CONSUME_ITEM -> "Consumable";
            case BATTLE_ACTION -> "Battle";
            case MARKET -> "Market";
            default -> "Your Response";
        };
    }

    private void handleName(String command, List<String> out) {
        player = new Player(command);
        out.add("Unknown Person: Welcome To The World Of Gaia, Warrior " + player.getName() + ".");
        out.add("Unknown Person: I Am A Spirit Guide, I Will Help You Throughout Your Journey In This World.");
        out.add("Spirit Guide: Do You Want To Start Your Adventure? (Yes/No)");
        mode = Mode.START_CONFIRM;
    }

    private void handleStartConfirm(String command, List<String> out) {
        if (isYes(command)) {
            grantStarterItems(out);
            save();
            mode = Mode.MAIN;
            renderMain(out);
        } else if (isNo(command)) {
            mode = Mode.DELETE_CONFIRM;
            out.add("Spirit Guide: Do You Want To Go Back To Your World? (Yes/No)");
            out.add("WARNING: Save Will Be Deleted If Continued.");
        } else {
            out.add("Spirit Guide: I'm Sorry, But I Could Not Understand Your Response, What Was It Again?");
            out.add("Spirit Guide: Do You Want To Continue Your Adventure? (Yes/No)");
        }
    }

    private void handleDeleteConfirm(String command, List<String> out) {
        if (isYes(command)) {
            if (player != null) {
                out.add("Spirit Guide: Farewell, Warrior " + player.getName() + ".");
            }
            FileManager.deletePlayer();
            player = null;
            mode = Mode.AWAITING_NAME;
            out.add("Unknown Person: Good Day Traveller, What Is Your Name?");
        } else if (isNo(command)) {
            mode = Mode.START_CONFIRM;
            out.add("Spirit Guide: Do You Want To Continue Your Adventure? (Yes/No)");
        } else {
            out.add("Spirit Guide: I could not understand that. Please answer Yes or No.");
        }
    }

    private void handleMain(String command, List<String> out) {
        String lower = command.toLowerCase(Locale.ROOT);
        if (lower.equals("help") || lower.equals("main")) {
            renderMain(out);
        } else if (lower.equals("roam")) {
            startRandomEvent(out);
        } else if (lower.equals("open") || lower.equals("inventory")) {
            mode = Mode.INVENTORY;
            renderInventory(out);
        } else if (lower.equals("view") || lower.equals("spells")) {
            renderSpells(out);
            out.add("Spirit Guide: What Would You Like To Do Next?");
        } else if (lower.equals("equip")) {
            mode = Mode.EQUIP_TYPE;
            renderEquipTypes(out);
        } else if (lower.startsWith("equip ")) {
            pendingEquipType = lower.substring("equip ".length()).trim();
            mode = Mode.EQUIP_ITEM;
            renderEquipItems(out, pendingEquipType);
        } else if (lower.startsWith("consume ")) {
            consumeItem(command.substring("consume ".length()).trim(), out);
        } else if (lower.equals("save")) {
            save();
            out.add("Spirit Guide: Your adventure has been saved.");
        } else if (lower.equals("reset")) {
            mode = Mode.DELETE_CONFIRM;
            out.add("Spirit Guide: Do You Want To Go Back To Your World? (Yes/No)");
            out.add("WARNING: Save Will Be Deleted If Continued.");
        } else {
            out.add("Spirit Guide: Commands: Equip, Open, View, Roam, Save, Reset, Help.");
        }
    }

    private void handleInventory(String command, List<String> out) {
        String lower = command.toLowerCase(Locale.ROOT);
        if (lower.equals("back") || lower.equals("main")) {
            mode = Mode.MAIN;
            renderMain(out);
        } else if (lower.equals("consume")) {
            mode = Mode.CONSUME_ITEM;
            renderConsumables(out);
        } else if (lower.startsWith("consume ")) {
            consumeItem(command.substring("consume ".length()).trim(), out);
            mode = Mode.INVENTORY;
            renderInventory(out);
        } else if (lower.equals("equip")) {
            mode = Mode.EQUIP_TYPE;
            renderEquipTypes(out);
        } else if (lower.startsWith("equip ")) {
            pendingEquipType = lower.substring("equip ".length()).trim();
            mode = Mode.EQUIP_ITEM;
            renderEquipItems(out, pendingEquipType);
        } else {
            out.add("Spirit Guide: Inventory commands: Equip, Consume, Back.");
        }
    }

    private void handleEquipType(String command, List<String> out) {
        String lower = command.toLowerCase(Locale.ROOT);
        if (lower.equals("back") || lower.equals("main")) {
            mode = Mode.MAIN;
            renderMain(out);
            return;
        }

        pendingEquipType = lower;
        mode = Mode.EQUIP_ITEM;
        renderEquipItems(out, pendingEquipType);
    }

    private void handleEquipItem(String command, List<String> out) {
        if (command.equalsIgnoreCase("back")) {
            mode = Mode.MAIN;
            renderMain(out);
            return;
        }

        equipItem(pendingEquipType, command, out);
        save();
        mode = Mode.MAIN;
        renderMain(out);
    }

    private void handleConsumeItem(String command, List<String> out) {
        if (command.equalsIgnoreCase("back")) {
            mode = Mode.INVENTORY;
            renderInventory(out);
            return;
        }

        consumeItem(command, out);
        save();
        mode = Mode.INVENTORY;
        renderInventory(out);
    }

    private void handleBattle(String command, List<String> out) {
        String lower = command.toLowerCase(Locale.ROOT);
        if (lower.equals("flee")) {
            out.add("Warrior " + player.getName() + " retreated from battle.");
            mode = Mode.MAIN;
            renderMain(out);
        } else if (lower.equals("attack")) {
            playerAttack(out);
            finishPlayerTurn(out);
        } else if (lower.equals("spell")) {
            renderSpells(out);
            out.add("Spirit Guide: Cast with: spell <name>");
        } else if (lower.startsWith("spell ")) {
            castSpell(command.substring("spell ".length()).trim(), out);
            finishPlayerTurn(out);
        } else if (findSpell(command) != null) {
            castSpell(command, out);
            finishPlayerTurn(out);
        } else {
            out.add("Spirit Guide: Battle commands: Attack, Spell <name>, Flee.");
        }
    }

    private void handleMarketConfirm(String command, List<String> out) {
        if (isYes(command)) {
            mode = Mode.MARKET;
            renderMarket(out);
        } else if (isNo(command)) {
            mode = Mode.MAIN;
            out.add("Spirit Guide: We move on from the travelling merchant.");
            renderMain(out);
        } else {
            out.add("Spirit Guide: Do You Want To Go To Him? (Yes/No)");
        }
    }

    private void handleMarket(String command, List<String> out) {
        String lower = command.toLowerCase(Locale.ROOT);
        if (lower.equals("leave") || lower.equals("back") || lower.equals("main")) {
            mode = Mode.MAIN;
            out.add("Travelling Merchant: Safe travels, customer.");
            renderMain(out);
        } else if (lower.startsWith("buy ")) {
            buyItem(command.substring("buy ".length()).trim(), out);
            renderMarket(out);
        } else if (lower.startsWith("sell ")) {
            sellItem(command.substring("sell ".length()).trim(), out);
            renderMarket(out);
        } else {
            out.add("Travelling Merchant: Commands: Buy <item>, Sell <item>, Leave.");
        }
    }

    private void renderMain(List<String> out) {
        out.add("");
        out.add(formatStats());
        out.add("Spirit Guide: What Would You Like To Do?");
        out.add("( Equip )  ( Open )  ( View )  ( Roam )  ( Save )");
    }

    private void renderInventory(List<String> out) {
        out.add("Inventory");
        List<Item> items = player.getInventory().getOwnItems().stream()
                .filter(item -> item.getQuantity() > 0)
                .toList();
        if (items.isEmpty()) {
            out.add("  Empty");
        } else {
            for (Item item : items) {
                out.add("  " + item.getItemName() + " x" + item.getQuantity() + " | " + itemType(item) + " | " + itemStats(item));
            }
        }
        out.add("Commands: Equip, Consume, Back.");
    }

    private void renderEquipTypes(List<String> out) {
        out.add("Spirit Guide: Which Equipment Type Would You Like To Equip?");
        out.add("( Weapon )  ( Helmet )  ( Armor )  ( Gloves )  ( Leggings )  ( Boots )  ( Back )");
    }

    private void renderEquipItems(List<String> out, String type) {
        Class<? extends Item> itemClass = equipClass(type);
        if (itemClass == null) {
            out.add("Spirit Guide: I do not know that equipment type.");
            mode = Mode.EQUIP_TYPE;
            renderEquipTypes(out);
            return;
        }

        List<? extends Item> items = player.getInventory().getItemOfType(itemClass);
        out.add("Spirit Guide: Choose a " + readableEquipType(type) + " to equip. (Back)");
        if (items.isEmpty()) {
            out.add("  No available " + readableEquipType(type) + " items.");
        } else {
            for (Item item : items) {
                out.add("  " + item.getItemName() + " x" + item.getQuantity() + " | " + itemStats(item));
            }
        }
    }

    private void renderConsumables(List<String> out) {
        List<Potion> potions = player.getInventory().getItemOfType(Potion.class);
        out.add("Spirit Guide: Which Consumable Would You Like To Consume? (Back)");
        if (potions.isEmpty()) {
            out.add("  No consumables available.");
        } else {
            for (Potion potion : potions) {
                out.add("  " + potion.getItemName() + " x" + potion.getQuantity() + " | " + potion.getItemDesc());
            }
        }
    }

    private void renderSpells(List<String> out) {
        out.add("Grimoire");
        for (Spells spell : player.getGrimoire().getOwnSpells()) {
            out.add("  " + spell.getSpellName() + " | " + spell.getSpellAttackValue() + " ATK | -" + spell.getManaNeeded() + " MP");
        }
    }

    private void renderBattle(List<String> out) {
        out.add("");
        out.add("Battle");
        out.add("  Warrior " + player.getName() + ": " + player.getHealth() + "/" + player.getMaxHealth() + " HP, " + player.getMana() + "/" + player.getMaxMana() + " MP");
        out.add("  " + enemy.getName() + ": " + enemy.getHealth() + "/" + enemy.getMaxHealth() + " HP");
        out.add("Spirit Guide: What Would You Like To Do? (Attack/Spell/Flee)");
    }

    private void renderMarket(List<String> out) {
        out.add("Travelling Merchant: These are the goods today.");
        for (Item item : marketItems) {
            out.add("  " + item.getItemName() + " | " + itemType(item) + " | " + itemStats(item) + " | " + item.getMarketValue() + " Coins");
        }
        out.add("Coins: " + player.getCoins());
        out.add("Commands: Buy <item>, Sell <item>, Leave.");
    }

    private void startRandomEvent(List<String> out) {
        out.add("Warrior " + player.getName() + " Is Roaming Around...");
        int event = random.nextInt(3);
        if (event == 0) {
            startCamp(out);
        } else if (event == 1) {
            startBattle(out);
        } else {
            startMarket(out);
        }
    }

    private void startCamp(List<String> out) {
        out.add("Spirit Guide: Maybe We Should Rest Here, It's Safe Around Here.");
        player.setHealth(player.getMaxHealth());
        player.setMana(player.getMaxMana());
        save();
        out.add("Spirit Guide: You Have Successfully Recovered Your Health And Mana To The Full.");
        mode = Mode.MAIN;
        renderMain(out);
    }

    private void startBattle(List<String> out) {
        List<Enemy> enemies = new Enemy().getEnemies();
        enemy = enemies.get(random.nextInt(enemies.size()));
        enemy.setHealth(enemy.getMaxHealth());
        out.add("Spirit Guide: Be Careful, " + enemy.getName() + " Is Heading Your Way.");
        out.add(enemy.getName() + ": " + enemy.getBattleCry());
        mode = Mode.BATTLE_ACTION;
        renderBattle(out);
    }

    private void startMarket(List<String> out) {
        marketItems.clear();
        marketItems.addAll(new MarketInventory().getTempSellItems());
        Collections.shuffle(marketItems);
        if (marketItems.size() > 10) {
            marketItems.subList(10, marketItems.size()).clear();
        }
        mode = Mode.MARKET_CONFIRM;
        out.add("Spirit Guide: There Is A Travelling Merchant Over There.");
        out.add("Spirit Guide: He Sells 10 Items And Buys Your Stuff As Well.");
        out.add("Spirit Guide: Do You Want To Go To Him? (Yes/No)");
    }

    private void playerAttack(List<String> out) {
        out.add("Spirit Guide: You Have Used " + player.getSkillName() + ".");
        enemy.setHealth(enemy.getHealth() - player.getSkillAttackValue());
        describeEnemyHealth(out);
    }

    private void castSpell(String spellName, List<String> out) {
        Spells spell = findSpell(spellName);
        if (spell == null) {
            out.add("Spirit Guide: I cannot read that spell. Try: spell Fireball.");
            return;
        }
        if (player.getMana() < spell.getManaNeeded()) {
            out.add("Spirit Guide: You Have Insufficient Mana, Unable To Cast Spell.");
            return;
        }

        out.add("Warrior " + player.getName() + ": " + spell.getIncantation());
        player.setMana(player.getMana() - spell.getManaNeeded());
        out.add("Spirit Guide: You Have Lost " + spell.getManaNeeded() + " Mana As You Casted " + spell.getSpellName() + ".");
        enemy.setHealth(enemy.getHealth() - spell.getSpellAttackValue());
        describeEnemyHealth(out);
    }

    private void finishPlayerTurn(List<String> out) {
        if (enemy.getHealth() <= 0) {
            winBattle(out);
            return;
        }

        enemyTurn(out);
        if (player.getHealth() <= 0) {
            loseBattle(out);
            return;
        }

        restoreMana(10, out);
        save();
        renderBattle(out);
    }

    private void enemyTurn(List<String> out) {
        int attackNumber = random.nextInt(enemy.getSkills().size());
        Skills skill = enemy.getSkills().get(attackNumber);
        out.add("Spirit Guide: " + enemy.getName() + " Used " + skill.getSkillAttackName() + ".");
        if (skill.getSkillType().equals(Global.AttributeType.Heal)) {
            enemy.setHealth(Math.min(enemy.getMaxHealth(), enemy.getHealth() + skill.getSkillAttackValue()));
            out.add("Spirit Guide: " + enemy.getName() + " Restored Its Health To " + enemy.getHealth() + ".");
            return;
        }

        player.setHealth(player.getHealth() - skill.getSkillAttackValue());
        out.add("Spirit Guide: You Have Taken " + skill.getSkillAttackValue() + " Damage.");
        if (player.getHealth() > 0) {
            out.add("Spirit Guide: You Are Down To " + player.getHealth() + " Health.");
        }
    }

    private void winBattle(List<String> out) {
        out.add("Spirit Guide: You Defeated " + enemy.getName() + ".");
        out.add("Spirit Guide: Congratulations!!!");
        gainExperience(enemy, out);
        player.setCoins(player.getCoins() + enemy.getCoins());
        out.add("Spirit Guide: You Acquired " + enemy.getCoins() + " Coins From " + enemy.getName() + ".");
        save();
        mode = Mode.MAIN;
        renderMain(out);
    }

    private void loseBattle(List<String> out) {
        out.add("Spirit Guide: You Have Been Defeated By " + enemy.getName() + ".");
        out.add("Spirit Guide: Don't Worry Warrior, I Will Revive You.");
        player.setHealth(player.getMaxHealth());
        player.setMana(player.getMaxMana());
        save();
        mode = Mode.MAIN;
        renderMain(out);
    }

    private void describeEnemyHealth(List<String> out) {
        if (enemy.getHealth() > 20 && enemy.getHealth() <= enemy.getMaxHealth()) {
            out.add("Spirit Guide: Enemy Is Down To " + enemy.getHealth() + " Health.");
        } else if (enemy.getHealth() > 0) {
            out.add("Spirit Guide: Enemy Is Now " + enemy.getHealth() + ", Finish Him!");
        } else {
            out.add("Spirit Guide: " + enemy.getName() + " Is Dead.");
        }
    }

    private void gainExperience(Enemy defeatedEnemy, List<String> out) {
        player.setExp(player.getExp() + defeatedEnemy.getExp());
        out.add("Spirit Guide: Warrior " + player.getName() + " You Have Acquired " + defeatedEnemy.getExp() + " EXP From " + defeatedEnemy.getName() + ".");

        if (player.getExp() >= player.getMaxExp()) {
            int levelGained = player.getExp() / player.getMaxExp();
            int remainingExp = player.getExp() % player.getMaxExp();
            player.setLevel(player.getLevel() + levelGained);
            player.setExp(remainingExp);
            player.setMaxExp((int) (player.getMaxExp() * 1.2));
            player.setMaxHealth(player.getMaxHealth() + 10);
            player.setMaxMana(player.getMaxMana() + 10);
            player.setHealth(player.getMaxHealth());
            player.setMana(player.getMaxMana());
            player.getSkills().getFirst().setSkillAttackValue(player.getSkillAttackValue() + 1);
            out.add("Spirit Guide: Warrior " + player.getName() + " You Have Leveled Up To " + player.getLevel() + ".");
            out.add("Spirit Guide: Health, Mana, And Attack Have Increased.");
        } else {
            out.add("Spirit Guide: You Need " + (player.getMaxExp() - player.getExp()) + " More EXP Points.");
        }
    }

    private void restoreMana(int amount, List<String> out) {
        if (player.getMana() < player.getMaxMana()) {
            player.setMana(Math.min(player.getMaxMana(), player.getMana() + amount));
            out.add("Spirit Guide: Mana is Restored To " + player.getMana() + ".");
        }
    }

    private void consumeItem(String itemName, List<String> out) {
        Item item = player.getInventory().getOwnItem(itemName);
        if (!(item instanceof Potion potion) || item.getQuantity() <= 0) {
            out.add("Spirit Guide: I Could Not Find That Consumable In Your Inventory.");
            return;
        }

        Global.AttributeType attribute = potion.getAttribute();
        if (attribute == Global.AttributeType.Health) {
            if (player.getHealth() >= player.getMaxHealth()) {
                out.add("Spirit Guide: Health Is Full, I Advise You To Not Drink A Health Potion.");
                return;
            }
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + potion.getStatsValue()));
            out.add("Spirit Guide: Health is Restored To " + player.getHealth() + ".");
        } else if (attribute == Global.AttributeType.Mana) {
            if (player.getMana() >= player.getMaxMana()) {
                out.add("Spirit Guide: Mana Is Full, I Advise You To Not Drink A Mana Potion.");
                return;
            }
            player.setMana(Math.min(player.getMaxMana(), player.getMana() + potion.getStatsValue()));
            out.add("Spirit Guide: Mana is Restored To " + player.getMana() + ".");
        }

        item.setQuantity(item.getQuantity() - 1);
        out.add("Spirit Guide: Successfully Drank " + item.getItemName() + ".");
        save();
    }

    private void equipItem(String type, String itemName, List<String> out) {
        Class<? extends Item> itemClass = equipClass(type);
        Item item = player.getInventory().getOwnItem(itemName);
        if (itemClass == null || item == null || !itemClass.isInstance(item) || item.getQuantity() <= 0) {
            out.add("Spirit Guide: You Don't Have That Item In Your Inventory.");
            return;
        }

        removeExistingEquipment(type, out);
        if (item instanceof Weapon weapon) {
            player.getInventory().setEquippedWeapon(weapon);
            player.getSkills().getFirst().setSkillAttackName(weapon.getItemName());
            player.getSkills().getFirst().setSkillAttackValue(weapon.getStatsValue());
        } else if (item instanceof Helmet helmet) {
            player.getInventory().setEquippedHelmet(helmet);
            applyArmor(helmet);
        } else if (item instanceof Torso torso) {
            player.getInventory().setEquippedTorso(torso);
            applyArmor(torso);
        } else if (item instanceof Gloves gloves) {
            player.getInventory().setEquippedGloves(gloves);
            applyArmor(gloves);
        } else if (item instanceof Leggings leggings) {
            player.getInventory().setEquippedLeggings(leggings);
            applyArmor(leggings);
        } else if (item instanceof Boots boots) {
            player.getInventory().setEquippedBoots(boots);
            applyArmor(boots);
        }

        item.setQuantity(item.getQuantity() - 1);
        out.add("Spirit Guide: " + item.getItemName() + " Equipped Successfully.");
    }

    private void removeExistingEquipment(String type, List<String> out) {
        Item equipped = switch (normalizeEquipType(type)) {
            case "weapon" -> player.getInventory().getEquippedWeapon();
            case "helmet" -> player.getInventory().getEquippedHelmet();
            case "armor" -> player.getInventory().getEquippedTorso();
            case "gloves" -> player.getInventory().getEquippedGloves();
            case "leggings" -> player.getInventory().getEquippedLeggings();
            case "boots" -> player.getInventory().getEquippedBoots();
            default -> null;
        };
        if (equipped == null) {
            return;
        }

        Item inventoryItem = player.getInventory().getOwnItem(equipped.getItemName());
        if (inventoryItem != null) {
            inventoryItem.setQuantity(inventoryItem.getQuantity() + 1);
        }
        if (equipped instanceof Armor armor) {
            player.setMaxHealth(player.getMaxHealth() - armor.getStatsValue());
            player.setHealth(Math.min(player.getHealth(), player.getMaxHealth()));
        }
        switch (normalizeEquipType(type)) {
            case "weapon" -> {
                player.getInventory().setEquippedWeapon(null);
                player.getSkills().getFirst().setSkillAttackName("Fist");
                player.getSkills().getFirst().setSkillAttackValue(3);
            }
            case "helmet" -> player.getInventory().setEquippedHelmet(null);
            case "armor" -> player.getInventory().setEquippedTorso(null);
            case "gloves" -> player.getInventory().setEquippedGloves(null);
            case "leggings" -> player.getInventory().setEquippedLeggings(null);
            case "boots" -> player.getInventory().setEquippedBoots(null);
            default -> {
            }
        }
        out.add("Spirit Guide: Removed " + equipped.getItemName() + ".");
    }

    private void applyArmor(Armor armor) {
        player.setMaxHealth(player.getMaxHealth() + armor.getStatsValue());
        player.setHealth(player.getHealth() + armor.getStatsValue());
    }

    private void buyItem(String itemName, List<String> out) {
        Item item = marketItems.stream()
                .filter(marketItem -> marketItem.getItemName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
        if (item == null) {
            out.add("Travelling Merchant: I'm Sorry, But I Don't Have That Item Today.");
            return;
        }
        if (player.getCoins() < item.getMarketValue()) {
            out.add("Spirit Guide: You Don't Have The Sufficient Coins To Purchase That Item.");
            return;
        }

        player.setCoins(player.getCoins() - item.getMarketValue());
        addItem(item.getItemName(), 1);
        save();
        out.add("Travelling Merchant: Thank You For Purchasing.");
        out.add("Spirit Guide: You Have Lost " + item.getMarketValue() + " Coins From Purchasing " + item.getItemName() + ".");
    }

    private void sellItem(String itemName, List<String> out) {
        Item item = player.getInventory().getOwnItem(itemName);
        if (item == null || item.getQuantity() <= 0 || isEquipped(item)) {
            out.add("Travelling Merchant: I cannot buy that item right now.");
            return;
        }

        int payout = (int) Math.floor(item.getMarketValue() * 0.65);
        item.setQuantity(item.getQuantity() - 1);
        player.setCoins(player.getCoins() + payout);
        save();
        out.add("Travelling Merchant: I purchased " + item.getItemName() + " for " + payout + " Coins.");
    }

    private void grantStarterItems(List<String> out) {
        if (!player.getInventory().getOwnItems().isEmpty()) {
            return;
        }
        out.add("Spirit Guide: Here, Warrior " + player.getName() + ", I Will Provide You Some Basic Essentials To Survive This World.");
        addStarter("Leather Helmet", 1, out);
        addStarter("Leather Robe", 1, out);
        addStarter("Leather Gloves", 1, out);
        addStarter("Iron Armor", 1, out);
        addStarter("Leather Pants", 2, out);
        addStarter("Leather Boots", 1, out);
        addStarter("Lesser Health Potion", 10, out);
        addStarter("Lesser Mana Potion", 10, out);
    }

    private void addStarter(String itemName, int quantity, List<String> out) {
        addItem(itemName, quantity);
        out.add("Spirit Guide: Obtained " + itemName + " x" + quantity + ".");
    }

    private void addItem(String itemName, int quantity) {
        Item existing = player.getInventory().getOwnItem(itemName);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return;
        }

        Item item = player.getInventory().getItem(itemName);
        if (item != null) {
            item.setQuantity(quantity);
            player.getInventory().getOwnItems().add(item);
        }
    }

    private Spells findSpell(String spellName) {
        return player.getGrimoire().getOwnSpells().stream()
                .filter(spell -> spell.getSpellName().equalsIgnoreCase(spellName))
                .findFirst()
                .orElse(null);
    }

    private String formatStats() {
        Inventory inventory = player.getInventory();
        List<String> equipment = List.of(
                "Weapon=" + itemName(inventory.getEquippedWeapon()),
                "Helmet=" + itemName(inventory.getEquippedHelmet()),
                "Armor=" + itemName(inventory.getEquippedTorso()),
                "Gloves=" + itemName(inventory.getEquippedGloves()),
                "Leggings=" + itemName(inventory.getEquippedLeggings()),
                "Boots=" + itemName(inventory.getEquippedBoots())
        );
        return "Warrior " + player.getName()
                + " | HP " + player.getHealth() + "/" + player.getMaxHealth()
                + " | MP " + player.getMana() + "/" + player.getMaxMana()
                + " | ATK " + player.getSkillAttackValue()
                + " | Coins " + player.getCoins()
                + " | Lv " + player.getLevel()
                + " | EXP " + player.getExp() + "/" + player.getMaxExp()
                + "\nEquipment: " + String.join(", ", equipment);
    }

    private Class<? extends Item> equipClass(String type) {
        return switch (normalizeEquipType(type)) {
            case "weapon" -> Weapon.class;
            case "helmet" -> Helmet.class;
            case "armor" -> Torso.class;
            case "gloves" -> Gloves.class;
            case "leggings" -> Leggings.class;
            case "boots" -> Boots.class;
            default -> null;
        };
    }

    private String normalizeEquipType(String type) {
        if (type == null) {
            return "";
        }
        String normalized = type.toLowerCase(Locale.ROOT).trim();
        if (normalized.equals("torso") || normalized.equals("robe") || normalized.equals("chest")) {
            return "armor";
        }
        return normalized;
    }

    private String readableEquipType(String type) {
        String normalized = normalizeEquipType(type);
        if (normalized.isBlank()) {
            return "equipment";
        }
        return normalized;
    }

    private String itemName(Item item) {
        return item == null ? "None" : item.getItemName();
    }

    private String itemType(Item item) {
        if (item instanceof Potion) {
            return "Potion";
        }
        if (item instanceof Weapon) {
            return "Weapon";
        }
        if (item instanceof Armor) {
            return "Armor";
        }
        return item.getClass().getSimpleName();
    }

    private String itemStats(Item item) {
        if (item instanceof Potion potion) {
            return "+" + potion.getStatsValue() + " " + potion.getAttribute();
        }
        if (item instanceof Weapon) {
            return "+" + item.getStatsValue() + " ATK";
        }
        if (item instanceof Armor) {
            return "+" + item.getStatsValue() + " HP";
        }
        return "-";
    }

    private boolean isEquipped(Item item) {
        Inventory inventory = player.getInventory();
        List<Item> equipped = new ArrayList<>();
        equipped.add(inventory.getEquippedWeapon());
        equipped.add(inventory.getEquippedHelmet());
        equipped.add(inventory.getEquippedTorso());
        equipped.add(inventory.getEquippedGloves());
        equipped.add(inventory.getEquippedLeggings());
        equipped.add(inventory.getEquippedBoots());
        return equipped.stream()
                .filter(equippedItem -> equippedItem != null)
                .anyMatch(equippedItem -> equippedItem.getItemName().equalsIgnoreCase(item.getItemName()));
    }

    private boolean isYes(String command) {
        return command.equalsIgnoreCase("yes") || command.equalsIgnoreCase("y");
    }

    private boolean isNo(String command) {
        return command.equalsIgnoreCase("no") || command.equalsIgnoreCase("n");
    }

    private void save() {
        if (player != null) {
            FileManager.savePlayer(player);
        }
    }
}
