public class Item {
    private String itemName;
    private String itemDesc;
    private int statsValue;
    private int marketValue;
    private int quantity;

    public Item(String itemName, int statsValue, int marketValue) {
        setItemName(itemName);
        setStatsValue(statsValue);
        setMarketValue(marketValue);
        setQuantity(1);
    }

    public Item(String itemName, String itemDesc, int statsValue, int marketValue) {
        setItemName(itemName);
        setItemDesc(itemDesc);
        setStatsValue(statsValue);
        setMarketValue(marketValue);
        setQuantity(1);
    }

    public Item(Item item) {
        setItemName(item.getItemName());
        setItemDesc(item.getItemDesc());
        setStatsValue(item.getStatsValue());
        setMarketValue(item.getMarketValue());
        setQuantity(item.getQuantity());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemName() {
        return this.itemName;
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
}
class Consumables extends Item {
    public Consumables(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Consumables(Item item) {
        super(item);
    }
}
class Potion extends Consumables {
    Global.AttributeType attribute;
    public Potion(String itemName, String itemDesc, int statsValue, int marketValue, Global.AttributeType attribute) {
        super(itemName, itemDesc, statsValue, marketValue);
        setAttribute(attribute);
    }

    public Potion(Item item) {
        super(item);
        setAttribute(getAttribute());
    }

    public void drinkPotion(Stats player) {
        if (getQuantity() != 0) {
            boolean drankSuccessfully = player.addAttribute(getAttribute(), getStatsValue());
            if(drankSuccessfully) {
                System.out.println("Spirit Guide: Successfully Drank " + getItemName() + " Potion");
                System.out.println("Spirit Guide: Successfully Restored " + getAttribute() + " To " + player.getMaxHealth() + ".");
                setQuantity(getQuantity() - 1);
            } else {
                System.out.println("Spirit Guide: You Did Not Drink The Potion, Warrior " + player.getName() + ".");
            }
        } else {
            System.out.println("Spirit Guide: You Don't Have " + getItemName() + " In Your Inventory.");
        }
    }

    public Global.AttributeType getAttribute() {
        return attribute;
    }

    public void setAttribute(Global.AttributeType attribute) {
        this.attribute = attribute;
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

    public Weapon(Weapon item) {
        super(item);
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

    public Sword(Sword item) {
        super(item);
    }
}
class Knife extends Weapon {
    public Knife(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Knife(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }

    public Knife(Knife item) {
        super(item);
    }
}
class Spear extends Weapon {
    public Spear(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Spear(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }

    public Spear(Spear item) {
        super(item);
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

    public Armor(Armor item) {
        super(item);
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

    public Helmet(Helmet helmet) {
        super(helmet);
    }
}
class Torso extends Armor {
    public Torso(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Torso(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }

    public Torso(Torso item) {
        super(item);
    }
}
class Gloves extends Armor {
    public Gloves(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Gloves(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }

    public Gloves(Gloves item) {
        super(item);
    }
}
class Leggings extends Armor {
    public Leggings(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Leggings(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }

    public Leggings(Leggings item) {
        super(item);
    }
}
class Boots extends Armor {
    public Boots(String itemName, String itemDesc, int statsValue, int marketValue) {
        super(itemName, itemDesc, statsValue, marketValue);
    }

    public Boots(String itemName, int statsValue, int marketValue) {
        super(itemName, statsValue, marketValue);
    }

    public Boots(Boots item) {
        super(item);
    }
}