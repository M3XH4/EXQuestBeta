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

        /*getInventory().addItem("Leather Helmet");
        getInventory().addItem("Leather Helmet");
        getInventory().addItem("Leather Helmet");
        getInventory().addItem("Leather Robe");
        getInventory().addItem("Leather Gloves");
        getInventory().addItem("Leather Boots");
        getInventory().addItem("Leather Boots");
        getInventory().addItem("Leather Boots");
        getInventory().addItem("Leather Pants");

        for (int i = 0; i < 5; i++) {
            getInventory().addItem("Health Potion");
            System.out.println("Health Potion Increased To " +  getInventory().getOwnItem("Health Potion").getQuantity());
        }

        for (int i = 0; i < 5; i++) {
            getInventory().addItem("Mana Potion");
            System.out.println("Mana Potion Increased To " + getInventory().getOwnItem("Mana Potion").getQuantity());
        }*/
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
        try {
            if (item instanceof Weapon) {
                getInventory().setEquippedWeapon((Weapon) item);
                getSkills().getFirst().setSkillAttackName(getInventory().getEquippedWeapon().getItemName());
                getSkills().getFirst().setSkillAttackValue(getInventory().getEquippedWeapon().getStatsValue());
            } else if (item instanceof Armor) {
                Armor armor = (Armor) item;
                if (armor instanceof Helmet) {
                    getInventory().setEquippedHelmet((Helmet) armor);
                } else if (armor instanceof Torso) {
                    getInventory().setEquippedTorso((Torso) armor);
                } else if (armor instanceof Gloves) {
                    getInventory().setEquippedGloves((Gloves) armor);
                } else if (armor instanceof Leggings) {
                    getInventory().setEquippedLeggings((Leggings) armor);
                } else if (armor instanceof Boots) {
                    getInventory().setEquippedBoots((Boots) armor);
                }
                setHealth(getHealth() + armor.getStatsValue());
                setMaxHealth(getMaxHealth() + armor.getStatsValue());
            }
            getInventory().getOwnItem(item.getItemName()).setQuantity(getInventory().getOwnItem(item.getItemName()).getQuantity() - 1);
        } catch (Exception e) {
            System.out.println("Spirit Guide: Item Is Not In Inventory.");
        }
    }
    public void removeWeapon() {
        getSkills().getFirst().setSkillAttackName("Fist");
        getSkills().getFirst().setSkillAttackValue(3);
        getInventory().getOwnItem(getInventory().getEquippedWeapon().getItemName()).setQuantity(getInventory().getOwnItem(getInventory().getEquippedWeapon().getItemName()).getQuantity() + 1);
        getInventory().setEquippedWeapon(null);
    }
    public void removeHelmet() {
        setMaxHealth(getMaxHealth() - getInventory().getEquippedHelmet().getStatsValue());
        if(getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().getOwnItem(getInventory().getEquippedHelmet().getItemName()).setQuantity(getInventory().getOwnItem(getInventory().getEquippedHelmet().getItemName()).getQuantity() + 1);
        getInventory().setEquippedBoots(null);
    }
    public void removeTorso() {
        setMaxHealth(getMaxHealth() - getInventory().getEquippedTorso().getStatsValue());
        if(getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().getOwnItem(getInventory().getEquippedTorso().getItemName()).setQuantity(getInventory().getOwnItem(getInventory().getEquippedTorso().getItemName()).getQuantity() + 1);
        getInventory().setEquippedTorso(null);
    }
    public void removeGloves() {
        setMaxHealth(getMaxHealth() - getInventory().getEquippedGloves().getStatsValue());
        if(getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().getOwnItem(getInventory().getEquippedGloves().getItemName()).setQuantity(getInventory().getOwnItem(getInventory().getEquippedGloves().getItemName()).getQuantity() + 1);
        getInventory().setEquippedGloves(null);;
    }
    public void removeLeggings() {
        setMaxHealth(getMaxHealth() - getInventory().getEquippedLeggings().getStatsValue());
        if(getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().getOwnItem(getInventory().getEquippedLeggings().getItemName()).setQuantity(getInventory().getOwnItem(getInventory().getEquippedLeggings().getItemName()).getQuantity() + 1);
        getInventory().setEquippedLeggings(null);
    }
    public void removeBoots() {
        setMaxHealth(getMaxHealth() - getInventory().getEquippedBoots().getStatsValue());
        if (getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        getInventory().getOwnItem(getInventory().getEquippedBoots().getItemName()).setQuantity(getInventory().getOwnItem(getInventory().getEquippedBoots().getItemName()).getQuantity() + 1);
        getInventory().setEquippedBoots(null);
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
