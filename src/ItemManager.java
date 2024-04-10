import java.io.Serial;
import java.io.Serializable;
import java.util.*;
public class ItemManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Item> items;
    public ItemManager() {
        setItems(new ArrayList<>());


        getItems().add(new Gloves("Leather Gloves", "", 3, 7));
        getItems().add(new Helmet("Leather Helmet", "", 4, 10));
        getItems().add(new Leggings("Leather Pants", "",5, 16));
        getItems().add(new Sword("Iron Sword", "", 6, 55));
        getItems().add(new Boots("Leather Boots", "", 3, 7));
        getItems().add(new Torso("Leather Robe", "", 8, 25));
        getItems().add(new Potion("Health Potion", "Adds 10 HP To Your Health", 10, 20, Global.AttributeType.Health));
        getItems().add(new Potion("Mana Potion", "Adds 10 MP To Your Health", 10, 20, Global.AttributeType.Mana));
        getItems().add(new Knife("Simple Knife", "", 4, 26));
        getItems().add(new Spear("Iron Spear", "", 14, 70));
        getItems().add(new Torso("Iron Armor", "", 17, 80));
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}

class Inventory extends ItemManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Item> ownItems;
    private ArrayList<Weapon> weapons;
    private ArrayList<Knife> knives;
    private ArrayList<Sword> swords;
    private ArrayList<Spear> spears;
    private ArrayList<Armor> armors;
    private ArrayList<Helmet> helmets;
    private ArrayList<Torso> torsos;
    private ArrayList<Gloves> gloves;
    private ArrayList<Leggings> leggings;
    private ArrayList<Boots> boots;
    private ArrayList<Consumables> consumables;
    private Weapon noWeapon;
    private Armor noArmor;
    private Weapon equippedWeapon = null;
    private Helmet equippedHelmet = null;
    private Torso equippedTorso = null;
    private Gloves equippedGloves = null;
    private Leggings equippedLeggings = null;
    private Boots equippedBoots = null;

    public Inventory() {
        setOwnItems(new ArrayList<>());
        setConsumables(new ArrayList<>());
        setKnives(new ArrayList<>());
        setSpears(new ArrayList<>());
        setSwords(new ArrayList<>());
        setArmors(new ArrayList<>());
        setHelmets(new ArrayList<>());
        setTorsos(new ArrayList<>());
        setGloves(new ArrayList<>());
        setLeggings(new ArrayList<>());
        setBoots(new ArrayList<>());

        setNoWeapon(new NoItemWeapon());
        setNoArmor(new NoItemArmor());
    }
    public void displayOwnItems(List<? extends Item> items) {
        String headerItems = "|              Name             | Quantity |         Type         |       Stats      |                  Description                |";
        Global.placeLine(headerItems);
        System.out.println(headerItems);
        Global.placeLine(headerItems);
        for (Item item : items) {
            if (item.getQuantity() != 0) {
                if (item instanceof Consumables) {
                    System.out.print("| " + Global.spacerString(29, item.getItemName()) + " | " + Global.spacerString(8, Integer.toString(item.getQuantity())) + " | " + Global.spacerString(20, item.getClass().getSimpleName()) + " | " + Global.spacerString(16, "") + " | " + Global.spacerString(43, item.getItemDesc()) + " |\n");
                } else {
                    String statValue = (item instanceof Weapon) ? ("+" + item.getStatsValue() + " ATK") : ("+" +item.getStatsValue() + " HP");
                    System.out.print("| " + Global.spacerString(29, item.getItemName()) + " | " + Global.spacerString(8, Integer.toString(item.getQuantity())) + " | " + Global.spacerString(20, item.getClass().getSimpleName()) + " | " + Global.spacerString(16, statValue) + " | " + Global.spacerString(43, item.getItemDesc()) + " |\n");
                }
            }
            else if (item.getQuantity() == 0) {
                System.out.println("|\t\t\t\t\t\t\t\t| \t\t   | \t\t\t\t\t  |  \t\t\t\t | \t\t\t\t\t\t\t\t\t\t\t   |");
            }
        }
        if(items.isEmpty()) {
            System.out.println("|\t\t\t\t\t\t\t\t| \t\t   | \t\t\t\t\t  |  \t\t\t\t | \t\t\t\t\t\t\t\t\t\t\t   |");
        }
        Global.placeLine(headerItems);
        items.clear();
    }
    public void displaySellItems(Set<? extends Item> items) {
        String headerItems = "|              Name             |        Type         |       Stats      |              Description              |      Value     |";
        Global.placeLine(headerItems);
        System.out.println(headerItems);
        Global.placeLine(headerItems);
        for (Item item : items) {
            if (item != null) {
                if (item instanceof Consumables) {
                    System.out.print("| " + Global.spacerString(29, item.getItemName()) + " | " + Global.spacerString(19, item.getClass().getSimpleName()) + " | " + Global.spacerString(16, "") + " | " + Global.spacerString(37, item.getItemDesc()) + " | " +  Global.spacerString(15, (item.getMarketValue() + " Coins")) + "|\n");
                } else {
                    String statValue = (item instanceof Weapon) ? ("+" + item.getStatsValue() + " ATK") : ("+" +item.getStatsValue() + " HP");
                    System.out.print("| " + Global.spacerString(29, item.getItemName()) + " | " + Global.spacerString(19, item.getClass().getSimpleName()) + " | " + Global.spacerString(16, statValue) + " | " + Global.spacerString(37, item.getItemDesc()) + " | " +  Global.spacerString(15, (item.getMarketValue() + " Coins"))  + "|\n");
                }
            }
        }
        if(items.isEmpty()) {
            System.out.println("|\t\t\t\t\t\t\t\t| \t\t   | \t\t\t\t\t  |  \t\t\t\t | \t\t\t\t\t\t\t\t\t\t\t   |");
        }
        Global.placeLine(headerItems);
        items.clear();
    }
    public ArrayList<Item> getTempOwnItems() {
        ArrayList<Item> tempItems = new ArrayList<>();
        for (Item item: getOwnItems()) {
            if(item.getQuantity() != 0) {
                if (item instanceof Weapon) {
                    if (item instanceof Knife knife) {
                        tempItems.add(new Knife(knife));
                    } else if (item instanceof Sword sword) {
                        tempItems.add(new Sword(sword));
                    } else if (item instanceof Spear spear) {
                        tempItems.add(new Spear(spear));
                    }
                } else if (item instanceof Armor) {
                    if (item instanceof Helmet helmet) {
                        tempItems.add(new Helmet(helmet));
                    } else if (item instanceof Torso torso) {
                        tempItems.add(new Torso(torso));
                    } else if (item instanceof Gloves gloves) {
                        tempItems.add(new Gloves(gloves));
                    } else if (item instanceof Leggings leggings) {
                        tempItems.add(new Leggings(leggings));
                    } else if (item instanceof Boots boots) {
                        tempItems.add(new Boots(boots));
                    }
                } else if (item instanceof Potion potion) {
                    tempItems.add(new Potion(potion));
                }
            }
        }
        return tempItems;
    }
    public void addItem(String itemName) {
        ArrayList<String> itemNames = new ArrayList<>();
        for (int i = 0; i < getOwnItems().size(); i++) {
            itemNames.add(getOwnItems().get(i).getItemName());
        }
        if (!(itemNames.contains(itemName))) {
            getOwnItems().add(searchItem(itemName));
        } else {
            getOwnItem(itemName).setQuantity(getOwnItem(itemName).getQuantity() + 1);
        }
    }
    public Item getItem(String itemName) {
        return searchItem(itemName);
    }
    public Item getItem(int itemIndex) {
        return searchItem(itemIndex);
    }
    public Item getOwnItem(String itemName) {
        if (searchOwnItem(itemName).getQuantity() != 0) {
            return searchOwnItem(itemName);
        }
        return null;
    }
    public Item getOwnItem(int itemIndex) {
        return searchOwnItem(itemIndex);
    }
    public Item searchItem(String itemName) {
        for (Item item: getItems()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    public Item searchOwnItem(String itemName) {
        for (Item item: getOwnItems()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    public Item searchItem(int itemIndex) {
        try {
            return getItems().get(itemIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    public Item searchOwnItem(int itemIndex) {
        try {
            return getOwnItems().get(itemIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    public Weapon getEquippedWeapon() {
        if (this.equippedWeapon != null) {
            return this.equippedWeapon;
        } else {
            return getNoWeapon();
        }
    }
    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }
    public Armor getEquippedHelmet() {
        if (this.equippedHelmet != null) {
            return this.equippedHelmet;
        } else {
            return getNoArmor();
        }
    }
    public void setEquippedHelmet(Helmet equippedHelmet) {
        this.equippedHelmet = equippedHelmet;
    }
    public Armor getEquippedTorso() {
        if (this.equippedTorso != null) {
            return this.equippedTorso;
        } else {
            return getNoArmor();
        }
    }
    public void setEquippedTorso(Torso equippedTorso) {
        this.equippedTorso = equippedTorso;
    }
    public Armor getEquippedGloves() {
        if (this.equippedGloves != null) {
            return this.equippedGloves;
        } else {
            return getNoArmor();
        }
    }
    public void setEquippedGloves(Gloves equippedGloves) {
        this.equippedGloves = equippedGloves;
    }
    public Armor getEquippedLeggings() {
        if (this.equippedLeggings != null) {
            return this.equippedLeggings;
        } else {
            return getNoArmor();
        }
    }
    public void setEquippedLeggings(Leggings equippedLeggings) {
        this.equippedLeggings = equippedLeggings;
    }
    public Armor getEquippedBoots() {
        if (this.equippedBoots != null) {
            return this.equippedBoots;
        } else {
            return getNoArmor();
        }
    }
    public void setEquippedBoots(Boots equippedBoots) {
        this.equippedBoots = equippedBoots;
    }

    public Weapon getNoWeapon() {
        return noWeapon;
    }

    public void setNoWeapon(Weapon noWeapon) {
        this.noWeapon = noWeapon;
    }

    public Armor getNoArmor() {
        return noArmor;
    }

    public ArrayList<Item> getOwnItems() {
        return ownItems;
    }

    public void setOwnItems(ArrayList<Item> ownItems) {
        this.ownItems = ownItems;
    }

    public ArrayList<Consumables> getConsumables() {
        for(Item item: getOwnItems()) {
            if (item instanceof Consumables) {
                if (item instanceof Potion) {
                    if (item.getQuantity() != 0) {
                        consumables.add((Potion) item);
                    }
                }
            }
        }
        return consumables;
    }

    public void setConsumables(ArrayList<Consumables> consumables) {
        this.consumables = consumables;
    }

    public void setNoArmor(Armor noArmor) {
        this.noArmor = noArmor;
    }
    public ArrayList<Knife> getKnives() {
        for(Item item: getOwnItems()) {
            if (item instanceof Weapon) {
                if (item instanceof Knife) {
                    if (item.getQuantity() != 0) {
                        knives.add((Knife) item);
                    }
                }
            }
        }
        return knives;
    }

    public void setKnives(ArrayList<Knife> knives) {
        this.knives = knives;
    }

    public ArrayList<Sword> getSwords() {
        for(Item item: getOwnItems()) {
            if (item instanceof Weapon) {
                if (item instanceof Sword) {
                    if (item.getQuantity() != 0) {
                        swords.add((Sword) item);
                    }
                }
            }
        }
        return swords;
    }

    public void setSwords(ArrayList<Sword> swords) {
        this.swords = swords;
    }

    public ArrayList<Spear> getSpears() {
        for(Item item: getOwnItems()) {
            if (item instanceof Weapon) {
                if (item instanceof Spear) {
                    if (item.getQuantity() != 0) {
                        spears.add((Spear) item);
                    }
                }
            }
        }
        return spears;
    }

    public void setSpears(ArrayList<Spear> spears) {
        this.spears = spears;
    }

    public ArrayList<Helmet> getHelmets() {
        for(Item item: getOwnItems()) {
            if (item instanceof Armor) {
                if (item instanceof Helmet) {
                    if (item.getQuantity() != 0) {
                        helmets.add((Helmet) item);
                    }
                }
            }
        }
        return helmets;
    }

    public void setHelmets(ArrayList<Helmet> helmets) {
        this.helmets = helmets;
    }

    public ArrayList<Torso> getTorsos() {
        for(Item item: getOwnItems()) {
            if (item instanceof Armor) {
                if (item instanceof Torso) {
                    if (item.getQuantity() != 0) {
                        torsos.add((Torso) item);
                    }
                }
            }
        }
        return torsos;
    }

    public void setTorsos(ArrayList<Torso> torsos) {
        this.torsos = torsos;
    }

    public ArrayList<Gloves> getGloves() {
        for(Item item: getOwnItems()) {
            if (item instanceof Armor) {
                if (item instanceof Gloves) {
                    if (item.getQuantity() != 0) {
                        gloves.add((Gloves) item);
                    }
                }
            }
        }
        return gloves;
    }

    public void setGloves(ArrayList<Gloves> gloves) {
        this.gloves = gloves;
    }

    public ArrayList<Leggings> getLeggings() {
        for(Item item: getOwnItems()) {
            if (item instanceof Armor) {
                if (item instanceof Leggings) {
                    if (item.getQuantity() != 0) {
                        leggings.add((Leggings) item);
                    }
                }
            }
        }
        return leggings;
    }

    public void setLeggings(ArrayList<Leggings> leggings) {
        this.leggings = leggings;
    }

    public ArrayList<Boots> getBoots() {
        for(Item item: getOwnItems()) {
            if (item instanceof Armor) {
                if (item instanceof Boots) {
                    if (item.getQuantity() != 0) {
                        boots.add((Boots) item);
                    }
                }
            }
        }
        return boots;
    }
    public void setBoots(ArrayList<Boots> boots) {
        this.boots = boots;
    }
    public void searchWeapons() {
        ArrayList<Weapon> temp_weapons = new ArrayList<>();

        for(Item item: getOwnItems()) {
            if (item instanceof Weapon) {
                if (item.getQuantity() != 0) {
                    temp_weapons.add((Weapon) item);
                }
            }
        }
        setWeapons(temp_weapons);
    }
    public void searchArmors() {
        ArrayList<Armor> temp_armors = new ArrayList<>();

        for(Item item: getOwnItems()) {
            if (item instanceof Armor) {
                if (item.getQuantity() != 0) {
                    temp_armors.add((Armor) item);
                }
            }
        }
        setArmors(temp_armors);
    }

    public ArrayList<Weapon> getWeapons() {
        searchWeapons();
        return this.weapons;
    }
    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }
    public ArrayList<Armor> getArmors() {
        searchArmors();
        return this.armors;
    }

    public void setArmors(ArrayList<Armor> armors) {
        this.armors = armors;
    }
}
