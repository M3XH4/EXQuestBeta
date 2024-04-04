public class Item {
    private String itemName;
    private String itemDesc;
    private int statsValue;
    private int marketValue;

    public Item(String itemName, int statsValue, int marketValue) {
        this.itemName = itemName;
        this.statsValue = statsValue;
        this.marketValue = marketValue;
    }

    public Item(String itemName, String itemDesc, int statsValue, int marketValue) {
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.statsValue = statsValue;
        this.marketValue = marketValue;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getStatsValue() {
        return statsValue;
    }

    public void setStatsValue(int statsValue) {
        this.statsValue = statsValue;
    }

    public int getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(int marketValue) {
        this.marketValue = marketValue;
    }

    public void displayItem() {
        String itemTitle = this.getItemName() + " ----------------";
        System.out.println(itemTitle);
        System.out.println("Desc:   " + this.getItemDesc());
        System.out.println("Type:   " + this.getClass().getSimpleName());
        Global.placeLine(itemTitle);
        this.displayItemStats();;
        Global.placeLine(itemTitle);
        System.out.println("Value:  " + this.getMarketValue());
    }
    public void displayItemStats() {
        if (this instanceof Weapon) {
            System.out.println("ATK:    " + this.getStatsValue());
        } else if (this instanceof Armor) {
            System.out.println("HP:    " + this.getStatsValue());
        }
    }
}
// WEAPONS
class Weapon extends Item {
    public Weapon(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Weapon(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}
class NoItemWeapon extends Weapon {
    public NoItemWeapon() {
        super("None", null, 0, 0);
    }
}
class Sword extends Weapon {
    public Sword(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Sword(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}
class Knife extends Weapon {
    public Knife(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Knife(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}
class Spear extends Weapon {
    public Spear(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Spear(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}

// ARMORS
class Armor extends Item {
    public Armor(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Armor(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}
class NoItemArmor extends Armor {
    public NoItemArmor() {
        super("None", null, 0, 0);
    }
}
class Helmet extends Armor {
    public Helmet(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Helmet(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}
class Torso extends Armor {
    public Torso(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Torso(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}
class Gloves extends Armor {
    public Gloves(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Gloves(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}
class Leggings extends Armor {
    public Leggings(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Leggings(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}
class Boots extends Armor {
    public Boots(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Boots(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }
}