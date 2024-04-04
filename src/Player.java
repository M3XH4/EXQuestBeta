import java.util.*;

public class Player extends Stats {
    Inventory inventory;
    Grimoire grimoire;
    public Player(String name) {
        setInventory(new Inventory());
        setGrimoire(new Grimoire());

        setName(name);
        setHealth(100);
        setMaxHealth(getHealth());
        setMana(100);
        setMaxMana(getMana());
        setExp(0);
        setMaxExp(100);
        setLevel(1);
        setSkills(new ArrayList<>(List.of(new Skills("Fist", 3))));
    }
    public void displayFullStats() {
        String temp_title = "-------- " + getName() + " --------";
        displayStats();
        System.out.println("Attack: " + getSkills().getFirst().getSkillAttackValue());
        Global.placeLine(temp_title);
        System.out.println("Level:  " + getLevel());
        System.out.println("Exp:    " + getExp() + "/" + getMaxExp());
        System.out.print("Equipment ");
        Global.placeLine(temp_title, 10);
        System.out.println("Weapon: " + getInventory().getItemWeapon().getItemName());
        System.out.println("Head:   " + getInventory().getItemHelmet().getItemName());
        System.out.println("Body;   " + getInventory().getItemTorso().getItemName());
        System.out.println("Hands:  " + getInventory().getItemGloves().getItemName());
        System.out.println("Legs:   " + getInventory().getItemLeggings().getItemName());
        System.out.println("Foot:   " + getInventory().getItemBoots().getItemName());
        Global.placeLine(temp_title);
    }
    public void equipWeapon(Weapon weapon) {
        getInventory().setItemWeapon(weapon);
        getSkills().getFirst().setSkillAttackName(getInventory().getItemWeapon().getItemName());
        getSkills().getFirst().setSkillAttackValue(getInventory().getItemWeapon().getStatsValue());
    }
    public void equipHelmet(Helmet helmet) {
        getInventory().setItemHelmet(helmet);
        setHealth(getHealth() + getInventory().getItemHelmet().getStatsValue());
        setMaxHealth(getMaxHealth() + getInventory().getItemHelmet().getStatsValue());
    }
    public void equipTorso(Torso torso) {
        getInventory().setItemTorso(torso);
        setHealth(getHealth() + getInventory().getItemTorso().getStatsValue());
        setMaxHealth(getMaxHealth() + getInventory().getItemTorso().getStatsValue());
    }
    public void equipGloves(Gloves gloves) {
        getInventory().setItemGloves(gloves);
        setHealth(getHealth() + getInventory().getItemGloves().getStatsValue());
        setMaxHealth(getMaxHealth() + getInventory().getItemGloves().getStatsValue());
    }
    public void equipLeggings(Leggings leggings) {
        getInventory().setItemLeggings(leggings);
        setHealth(getHealth() + getInventory().getItemLeggings().getStatsValue());
        setMaxHealth(getMaxHealth() + getInventory().getItemLeggings().getStatsValue());
    }
    public void equipBoots(Boots boots) {
        getInventory().setItemBoots(boots);
        setHealth(getHealth() + getInventory().getItemBoots().getStatsValue());
        setMaxHealth(getMaxHealth() + getInventory().getItemBoots().getStatsValue());
    }

    public void removeWeapon() {
        getSkills().getFirst().setSkillAttackName("Fist");
        getSkills().getFirst().setSkillAttackValue(3);
        getInventory().setItemWeapon(null);
    }
    public void removeHelmet() {
        setMaxHealth(getMaxHealth() - getInventory().getItemHelmet().getStatsValue());
        if(getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().setItemHelmet(null);
    }
    public void removeTorso() {
        setMaxHealth(getMaxHealth() - getInventory().getItemTorso().getStatsValue());
        if(getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().setItemTorso(null);
    }
    public void removeGloves() {
        setMaxHealth(getMaxHealth() - getInventory().getItemGloves().getStatsValue());
        if(getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().setItemGloves(null);;
    }
    public void removeLeggings() {
        setMaxHealth(getMaxHealth() - getInventory().getItemLeggings().getStatsValue());
        if(getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().setItemLeggings(null);
    }
    public void removeBoots() {
        setMaxHealth(getMaxHealth() - getInventory().getItemBoots().getStatsValue());
        if (getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().setItemBoots(null);
    }

    public Grimoire getGrimoire() {
        return grimoire;
    }

    public void setGrimoire(Grimoire grimoire) {
        this.grimoire = grimoire;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public static void main(String[] args) {

        Helmet helmet = new Helmet("Iron Helmet", 10, 200);
        Sword sword = new Sword("Excalibur", 15, 1500);
        Torso torso = new Torso("Aegis", 30, 2400);
        Boots boots = new Boots("Hermes' Boots", 12, 1300);

        Player player = new Player("Sean");
        player.displayStats();
        System.out.println("AAAAAAAAAAAAAAA");
        player.displayFullStats();
        System.out.println("AAAAAAAAAAAAAAA");
        player.getInventory().addItem(helmet);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(torso);
        player.getInventory().addItem(boots);

        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Your Inventory:");
            for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                if (i == 0) {
                    System.out.print("| ");
                }
                System.out.print(player.getInventory().getItem(i).getItemName());
                if (i < (player.getInventory().getItems().size() - 1)) {
                    System.out.print("  |   ");
                }
                if (i == (player.getInventory().getItems().size() - 1)) {
                    System.out.println("    |");
                    System.out.print("\n");
                }
            }
            System.out.println("Do You Want To Equip Yourself? (Yes/No)");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("Yes")) {
                System.out.println("What Part Would You Like To Equip");
                System.out.println("|   Weapon  |   Head    |   Body    |   Hands  |   Legs |   Foot    |");
                String partChoice = input.nextLine();

                player.getInventory().getAllWeapons();
                player.getInventory().getAllArmors();

                if (partChoice.equalsIgnoreCase("Weapon")) {
                    player.getInventory().askEquipItem("Weapon", player.getInventory().getWeapons());
                } else if (partChoice.equalsIgnoreCase("Head")) {
                    player.getInventory().askEquipItem("Helmet", player.getInventory().getHelmets());
                } else if (partChoice.equalsIgnoreCase("Body")) {
                    player.getInventory().askEquipItem("Torso", player.getInventory().getTorsos());
                } else if (partChoice.equalsIgnoreCase("Hands")) {
                    player.getInventory().askEquipItem("Gloves", player.getInventory().getGloves());
                } else if (partChoice.equalsIgnoreCase("Legs")) {
                    player.getInventory().askEquipItem("Leggings", player.getInventory().getLeggings());
                } else if (partChoice.equalsIgnoreCase("Foot")) {
                    player.getInventory().askEquipItem("Boots", player.getInventory().getBoots());
                }
                player.displayFullStats();
                break;
            } else if (choice.equalsIgnoreCase("No")) {

            } else {
                System.out.println("I don't understand Your response");
            }
        } while (true);
        do {
            System.out.println("Your Inventory:");
            for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                if (i == 0) {
                    System.out.print("| ");
                }
                System.out.print(player.getInventory().getItem(i).getItemName());
                if (i < (player.getInventory().getItems().size() - 1)) {
                    System.out.print("  |   ");
                }
                if (i == (player.getInventory().getItems().size() - 1)) {
                    System.out.println("    |");
                    System.out.print("\n");
                }
            }
            System.out.println("Do You Want To Equip Yourself? (Yes/No)");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("Yes")) {
                System.out.println("What Part Would You Like To Equip");
                System.out.println("|   Weapon  |   Head    |   Body    |   Hands  |   Legs |   Foot    |");
                String partChoice = input.nextLine();

                player.getInventory().getAllWeapons();
                player.getInventory().getAllArmors();

                if (partChoice.equalsIgnoreCase("Weapon")) {
                    player.getInventory().askEquipItem("Weapon", player.getInventory().getWeapons());
                } else if (partChoice.equalsIgnoreCase("Head")) {
                    player.getInventory().askEquipItem("Helmet", player.getInventory().getHelmets());
                } else if (partChoice.equalsIgnoreCase("Body")) {
                    player.getInventory().askEquipItem("Torso", player.getInventory().getTorsos());
                } else if (partChoice.equalsIgnoreCase("Hands")) {
                    player.getInventory().askEquipItem("Gloves", player.getInventory().getGloves());
                } else if (partChoice.equalsIgnoreCase("Legs")) {
                    player.getInventory().askEquipItem("Leggings", player.getInventory().getLeggings());
                } else if (partChoice.equalsIgnoreCase("Foot")) {
                    player.getInventory().askEquipItem("Boots", player.getInventory().getBoots());
                }
                player.displayFullStats();
                break;
            } else if (choice.equalsIgnoreCase("No")) {

            } else {
                System.out.println("I don't understand Your response");
            }
        } while (true);
    }
}
