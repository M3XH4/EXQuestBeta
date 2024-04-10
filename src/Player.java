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
        System.out.println("| Weapon:\t" + Global.spacerString(nameTitle.length() - 14, getInventory().getEquippedWeapon().getItemName()) + " |");
        System.out.println("| Helmet:\t" + Global.spacerString(nameTitle.length() - 14, getInventory().getEquippedHelmet().getItemName()) + " |");
        System.out.println("| Armor:\t" + Global.spacerString(nameTitle.length() - 14, getInventory().getEquippedTorso().getItemName()) + " |");
        System.out.println("| Gloves:\t" + Global.spacerString(nameTitle.length() - 14, getInventory().getEquippedGloves().getItemName()) + " |");
        System.out.println("| Leggings:\t" + Global.spacerString(nameTitle.length() - 14, getInventory().getEquippedLeggings().getItemName()) + " |");
        System.out.println("| Boots:\t" + Global.spacerString(nameTitle.length() - 14, getInventory().getEquippedBoots().getItemName()) + " |");
        Global.placeLine(nameTitle);
    }
    public void equipEquipment(Item item){
        if(item.getQuantity() != 0) {
            if (getInventory().getEquippedWeapon() != item &&
                getInventory().getEquippedHelmet() != item &&
                getInventory().getEquippedTorso() != item &&
                getInventory().getEquippedGloves() != item &&
                getInventory().getEquippedLeggings() != item &&
                getInventory().getEquippedBoots() != item) {

            } else if (getInventory().getEquippedWeapon().equals(item) ||
                getInventory().getEquippedHelmet().equals(item) ||
                getInventory().getEquippedTorso().equals(item) ||
                getInventory().getEquippedGloves().equals(item) ||
                getInventory().getEquippedLeggings().equals(item) ||
                getInventory().getEquippedBoots().equals(item)) {
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
            } else {
                System.out.println("Spirit Guide: Warrior, You Have Already Equipped This Item.");
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
        System.out.println("");
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
