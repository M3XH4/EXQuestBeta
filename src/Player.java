import java.io.*;
import java.util.*;

public class Player extends Stats implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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
        setCoins(100);
    }
    public void displayFullStats() {
        String nameTitle = "|--------------- " + getName() + " ---------------|";
        displayStats();
        System.out.println("| Attack:\t" + Global.spacerString(nameTitle.length() - 14, Integer.toString(getSkills().getFirst().getSkillAttackValue())) + " |");
        System.out.println("| Coins:\t" + Global.spacerString(nameTitle.length() - 14, Integer.toString(getCoins())) + " |");
        System.out.print("Experience ");
        Global.placeLine( 11, nameTitle);
        System.out.println("| Level:\t" + Global.spacerString(nameTitle.length() - 14, Integer.toString(getLevel())) + " |");
        System.out.println("| Exp:\t\t" + Global.spacerString(nameTitle.length() - 14, getExp() + "/" + getMaxExp()) + " |");
        System.out.print("Equipment ");
        Global.placeLine( 10, nameTitle);
        System.out.println("| Weapon:\t" + Global.spacerString(nameTitle.length() - 14, (getInventory().getEquippedWeapon() != null) ? getInventory().getEquippedWeapon().getItemName() : "None") + " |");
        System.out.println("| Helmet:\t" + Global.spacerString(nameTitle.length() - 14, (getInventory().getEquippedHelmet() != null) ? getInventory().getEquippedHelmet().getItemName() : "None") + " |");
        System.out.println("| Armor:\t" + Global.spacerString(nameTitle.length() - 14, (getInventory().getEquippedTorso() != null) ? getInventory().getEquippedTorso().getItemName() : "None") + " |");
        System.out.println("| Gloves:\t" + Global.spacerString(nameTitle.length() - 14, (getInventory().getEquippedGloves() != null) ? getInventory().getEquippedGloves().getItemName() : "None") + " |");
        System.out.println("| Leggings:\t" + Global.spacerString(nameTitle.length() - 14, (getInventory().getEquippedLeggings() != null) ? getInventory().getEquippedLeggings().getItemName() : "None") + " |");
        System.out.println("| Boots:\t" + Global.spacerString(nameTitle.length() - 14, (getInventory().getEquippedBoots() != null) ? getInventory().getEquippedBoots().getItemName() : "None") + " |");
        Global.placeLine(nameTitle);
    }
    public void equipEquipment(Item item){
        if(item.getQuantity() != 0) {
            if (getInventory().getEquippedWeapon().equals(item) ||
                getInventory().getEquippedHelmet().equals(item) ||
                getInventory().getEquippedTorso().equals(item) ||
                getInventory().getEquippedGloves().equals(item) ||
                getInventory().getEquippedLeggings().equals(item) ||
                getInventory().getEquippedBoots().equals(item)) {
                System.out.println("Spirit Guide: Warrior, You Have Already Equipped This Item.");
            } else {
                Scanner input = new Scanner(System.in);
                String choice;
                if (getInventory().getEquippedWeapon() != null && item.getClass().getSimpleName().equalsIgnoreCase("Weapon")) {
                    System.out.println("Would You Like To Remove " + getInventory().getEquippedWeapon().getItemName() + "? (Yes/No)");
                    choice = input.nextLine();
                    if (choice.equalsIgnoreCase("Yes")) {
                        removeEquippedItem(getInventory().getEquippedWeapon());
                    } else {
                        return;
                    }
                } else if (getInventory().getEquippedHelmet() != null && item.getClass().getSimpleName().equalsIgnoreCase("Helmet")) {
                    System.out.println("Would You Like To Remove " + getInventory().getEquippedHelmet().getItemName() + "? (Yes/No)");
                    choice = input.nextLine();
                    if (choice.equalsIgnoreCase("Yes")) {
                        removeEquippedItem(getInventory().getEquippedHelmet());
                    } else {
                        return;
                    }
                } else if (getInventory().getEquippedTorso() != null && item.getClass().getSimpleName().equalsIgnoreCase("Torso")) {
                    System.out.println("Would You Like To Remove " + getInventory().getEquippedTorso().getItemName() + "? (Yes/No)");
                    choice = input.nextLine();
                    if (choice.equalsIgnoreCase("Yes")) {
                        removeEquippedItem(getInventory().getEquippedTorso());
                    } else {
                        return;
                    }
                } else if (getInventory().getEquippedGloves() != null && item.getClass().getSimpleName().equalsIgnoreCase("Gloves")) {
                    System.out.println("Would You Like To Remove " + getInventory().getEquippedGloves().getItemName() + "? (Yes/No)");
                    choice = input.nextLine();
                    if (choice.equalsIgnoreCase("Yes")) {
                        removeEquippedItem(getInventory().getEquippedGloves());
                    } else {
                        return;
                    }
                } else if (getInventory().getEquippedLeggings() != null && item.getClass().getSimpleName().equalsIgnoreCase("Leggings")) {
                    System.out.println("Would You Like To Remove " + getInventory().getEquippedLeggings().getItemName() + "? (Yes/No)");
                    choice = input.nextLine();
                    if (choice.equalsIgnoreCase("Yes")) {
                        removeEquippedItem(getInventory().getEquippedLeggings());
                    } else {
                        return;
                    }
                } else if (getInventory().getEquippedBoots() != null && item.getClass().getSimpleName().equalsIgnoreCase("Boots")) {
                    System.out.println("Would You Like To Remove " + getInventory().getEquippedBoots().getItemName() + "? (Yes/No)");
                    choice = input.nextLine();
                    if (choice.equalsIgnoreCase("Yes")) {
                        removeEquippedItem(getInventory().getEquippedBoots());
                    } else {
                        return;
                    }
                }

                try {
                    if (item instanceof Weapon) {
                        getInventory().setEquippedWeapon((Weapon) item);
                        getSkills().getFirst().setSkillAttackName(getInventory().getEquippedWeapon().getItemName());
                        getSkills().getFirst().setSkillAttackValue(getInventory().getEquippedWeapon().getStatsValue());
                    } else if (item instanceof Armor armor) {
                        switch (armor) {
                            case Helmet helmet -> getInventory().setEquippedHelmet(helmet);
                            case Torso torso -> getInventory().setEquippedTorso(torso);
                            case Gloves gloves -> getInventory().setEquippedGloves(gloves);
                            case Leggings leggings -> getInventory().setEquippedLeggings(leggings);
                            case Boots boots -> getInventory().setEquippedBoots(boots);
                            default -> {
                            }
                        }
                        setHealth(getHealth() + armor.getStatsValue());
                        setMaxHealth(getMaxHealth() + armor.getStatsValue());
                    }
                    getInventory().getOwnItem(item.getItemName()).setQuantity(getInventory().getOwnItem(item.getItemName()).getQuantity() - 1);
                } catch (Exception e) {
                    System.out.println("Spirit Guide: Item Is Not In Inventory.");
                }
            }
        }
    }
    public void removeEquippedItem(Item equippedItem) {
        String itemName = equippedItem.getItemName();
        getInventory().getOwnItem(itemName).setQuantity(getInventory().getOwnItem(itemName).getQuantity() + 1);

        if (equippedItem instanceof Weapon) {
            getSkills().getFirst().setSkillAttackName("Fist");
            getSkills().getFirst().setSkillAttackValue(getSkillAttackValue() - equippedItem.getStatsValue() + 3);
            getInventory().setEquippedWeapon(null);
        } else {
            setMaxHealth(getMaxHealth() - equippedItem.getStatsValue());
            if (getHealth() > getMaxHealth()) {
                setHealth(getMaxHealth());
            }
            switch (equippedItem.getClass().getSimpleName()) {
                case "Helmet" -> getInventory().setEquippedHelmet(null) ;
                case "Torso" -> getInventory().setEquippedTorso(null);
                case "Gloves" -> getInventory().setEquippedGloves(null);
                case "Leggings" -> getInventory().setEquippedLeggings(null);
                case "Boots" -> getInventory().setEquippedBoots(null);
                default -> {}
            }
        }
        System.out.println("Spirit Guide: You Have Removed The " + equippedItem.getItemName() + ".");
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
}
