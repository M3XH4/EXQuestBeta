import java.io.*;
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
        getItems().add(new Potion("Lesser Health Potion", "Adds 10 HP To Your Health", 10, 15, Global.AttributeType.Health));
        getItems().add(new Potion("Lesser Mana Potion", "Adds 10 MP To Your Health", 10, 15, Global.AttributeType.Mana));
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
class MarketInventory extends ItemManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Set<Item> marketItems;

    public MarketInventory() {
        setMarketItems(new HashSet<>());

        for(Item item: getItems()) {
            getMarketItems().add(item);
        }
    }

    public Set<Item> getMarketItems() {
        return marketItems;
    }

    public void setMarketItems(Set<Item> marketItems) {
        this.marketItems = marketItems;
    }
    public void displaySellItems() {
        String headerItems = "|              Name             |        Type         |       Stats      |              Description              |      Value     |";
        Global.placeLine(headerItems);
        System.out.println(headerItems);
        Global.placeLine(headerItems);
        for (Item item : getTempSellItems()) {
            if (item != null) {
                if (item instanceof Consumables) {
                    System.out.print("| " + Global.spacerString(29, item.getItemName()) + " | " + Global.spacerString(19, item.getClass().getSimpleName()) + " | " + Global.spacerString(16, "") + " | " + Global.spacerString(37, item.getItemDesc()) + " | " +  Global.spacerString(15, (item.getMarketValue() + " Coins")) + "|\n");
                } else {
                    String statValue = (item instanceof Weapon) ? ("+" + item.getStatsValue() + " ATK") : ("+" +item.getStatsValue() + " HP");
                    System.out.print("| " + Global.spacerString(29, item.getItemName()) + " | " + Global.spacerString(19, item.getClass().getSimpleName()) + " | " + Global.spacerString(16, statValue) + " | " + Global.spacerString(37, item.getItemDesc()) + " | " +  Global.spacerString(15, (item.getMarketValue() + " Coins"))  + "|\n");
                }
            }
        }
        if(getTempSellItems().isEmpty()) {
            System.out.println("|\t\t\t\t\t\t\t\t| \t\t   | \t\t\t\t\t  |  \t\t\t\t | \t\t\t\t\t\t\t\t\t\t\t   |");
        }
        Global.placeLine(headerItems);
    }
    public Set<Item> getTempSellItems() {
        Set<Item> tempItems = new HashSet<>();
        for (Item item: getMarketItems()) {
            switch (item) {
                case Weapon ignored -> {
                    switch (item) {
                        case Knife knife -> tempItems.add(new Knife(knife));
                        case Sword sword -> tempItems.add(new Sword(sword));
                        case Spear spear -> tempItems.add(new Spear(spear));
                        default -> {
                        }
                    }
                }
                case Armor ignored -> {
                    switch (item) {
                        case Helmet helmet -> tempItems.add(new Helmet(helmet));
                        case Torso torso -> tempItems.add(new Torso(torso));
                        case Gloves glove -> tempItems.add(new Gloves(glove));
                        case Leggings legging -> tempItems.add(new Leggings(legging));
                        case Boots boot -> tempItems.add(new Boots(boot));
                        default -> {
                        }
                    }
                }
                case Potion potion -> tempItems.add(new Potion(potion));
                default -> {
                }
            }
        }
        return tempItems;
    }
}
class Inventory extends ItemManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Item> ownItems;
    private Weapon equippedWeapon = null;
    private Helmet equippedHelmet = null;
    private Torso equippedTorso = null;
    private Gloves equippedGloves = null;
    private Leggings equippedLeggings = null;
    private Boots equippedBoots = null;

    public Inventory() {
        setOwnItems(new ArrayList<>());

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
    public ArrayList<Item> getTempOwnItems() {
        ArrayList<Item> tempItems = new ArrayList<>();
        for (Item item: getOwnItems()) {
            if(item.getQuantity() != 0) {
                switch (item) {
                    case Weapon ignored -> {
                        switch (item) {
                            case Knife knife -> tempItems.add(new Knife(knife));
                            case Sword sword -> tempItems.add(new Sword(sword));
                            case Spear spear -> tempItems.add(new Spear(spear));
                            default -> {
                            }
                        }
                    }
                    case Armor ignored -> {
                        switch (item) {
                            case Helmet helmet -> tempItems.add(new Helmet(helmet));
                            case Torso torso -> tempItems.add(new Torso(torso));
                            case Gloves glove -> tempItems.add(new Gloves(glove));
                            case Leggings legging -> tempItems.add(new Leggings(legging));
                            case Boots boot -> tempItems.add(new Boots(boot));
                            default -> {
                            }
                        }
                    }
                    case Potion potion -> tempItems.add(new Potion(potion));
                    default -> {
                    }
                }
            }
        }
        return tempItems;
    }
    public void addItem(String itemName, int quantity) {
        ArrayList<String> itemNames = new ArrayList<>();
        for (int i = 0; i < getOwnItems().size(); i++) {
            itemNames.add(getOwnItems().get(i).getItemName());
        }
        if(!(itemNames.contains(itemName))) {
            Item tempItem = searchItem(itemName);
            tempItem.setQuantity(quantity);
            getOwnItems().add(tempItem);
        } else {
            Item tempItem = getOwnItem(itemName);
            tempItem.setQuantity(tempItem.getQuantity() + quantity);
        }
        System.out.println("Spirit Guide: Obtained " + itemName + " x" + quantity);
    }
    public Item getItem(String itemName) {
        return searchItem(itemName);
    }
    public Item getItem(int itemIndex) {
        return searchItem(itemIndex);
    }
    public Item getOwnItem(String itemName) {
        return searchOwnItem(itemName);
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
        }
        return null;
    }
    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }
    public Armor getEquippedHelmet() {
        if (this.equippedHelmet != null) {
            return this.equippedHelmet;
        }
        return null;
    }
    public void setEquippedHelmet(Helmet equippedHelmet) {
        this.equippedHelmet = equippedHelmet;
    }
    public Armor getEquippedTorso() {
        if (this.equippedTorso != null) {
            return this.equippedTorso;
        }
        return null;
    }
    public void setEquippedTorso(Torso equippedTorso) {
        this.equippedTorso = equippedTorso;
    }
    public Armor getEquippedGloves() {
        if (this.equippedGloves != null) {
            return this.equippedGloves;
        }
        return null;
    }
    public void setEquippedGloves(Gloves equippedGloves) {
        this.equippedGloves = equippedGloves;
    }
    public Armor getEquippedLeggings() {
        if (this.equippedLeggings != null) {
            return this.equippedLeggings;
        }
        return null;
    }
    public void setEquippedLeggings(Leggings equippedLeggings) {
        this.equippedLeggings = equippedLeggings;
    }
    public Armor getEquippedBoots() {
        if (this.equippedBoots != null) {
            return this.equippedBoots;
        }
        return null;
    }
    public void setEquippedBoots(Boots equippedBoots) {
        this.equippedBoots = equippedBoots;
    }

    public ArrayList<Item> getOwnItems() {
        return ownItems;
    }

    public void setOwnItems(ArrayList<Item> ownItems) {
        this.ownItems = ownItems;
    }

    public <T extends Item> ArrayList<T> getItemOfType(Class<T> itemType) {
        ArrayList<T> itemList = new ArrayList<>();
        for(Item item : getOwnItems()) {
            if (itemType.isInstance(item) && item.getQuantity() != 0) {
                itemList.add(itemType.cast(item));
            }
        }
        return itemList;
    }
}
