import java.util.*;
public class ItemManager {
    private ArrayList<Item> items;
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
    public ItemManager() {
        setItems(new ArrayList<>());
        setWeapons(new ArrayList<>());
        setKnives(new ArrayList<>());
        setSpears(new ArrayList<>());
        setSwords(new ArrayList<>());
        setArmors(new ArrayList<>());
        setHelmets(new ArrayList<>());
        setTorsos(new ArrayList<>());
        setGloves(new ArrayList<>());
        setLeggings(new ArrayList<>());
        setBoots(new ArrayList<>());
    }
    public ArrayList<Weapon> getAllWeapons() {
        ArrayList<Weapon> temp_weapons = new ArrayList<>();

        for(Item item: items) {
            if (item instanceof Weapon) {
                if(item instanceof Knife) {
                    getKnives().add((Knife) item);
                } else if (item instanceof Sword) {
                    getSwords().add((Sword) item);
                } else if (item instanceof Spear) {
                    getSpears().add((Spear) item);
                }
                temp_weapons.add((Weapon) item);
            }
        }
        setWeapons(temp_weapons);
        return getWeapons();
    }
    public ArrayList<Armor> getAllArmors() {
        ArrayList<Armor> temp_armors = new ArrayList<>();

        for(Item item: items) {
            if (item instanceof Armor) {
                if (item instanceof Helmet) {
                    getHelmets().add((Helmet) item);
                } else if (item instanceof Torso) {
                    getTorsos().add((Torso) item);
                } else if (item instanceof Gloves) {
                    getGloves().add((Gloves) item);
                } else if (item instanceof Leggings) {
                    getLeggings().add((Leggings) item);
                } else if (item instanceof Boots) {
                    getBoots().add((Boots) item);
                }
                temp_armors.add((Armor) item);
            }
        }
        setArmors(temp_armors);
        return getArmors();
    }

    public ArrayList<Knife> getKnives() {
        return knives;
    }

    public void setKnives(ArrayList<Knife> knives) {
        this.knives = knives;
    }

    public ArrayList<Sword> getSwords() {
        return swords;
    }

    public void setSwords(ArrayList<Sword> swords) {
        this.swords = swords;
    }

    public ArrayList<Spear> getSpears() {
        return spears;
    }

    public void setSpears(ArrayList<Spear> spears) {
        this.spears = spears;
    }

    public ArrayList<Helmet> getHelmets() {
        return helmets;
    }

    public void setHelmets(ArrayList<Helmet> helmets) {
        this.helmets = helmets;
    }

    public ArrayList<Torso> getTorsos() {
        return torsos;
    }

    public void setTorsos(ArrayList<Torso> torsos) {
        this.torsos = torsos;
    }

    public ArrayList<Gloves> getGloves() {
        return gloves;
    }

    public void setGloves(ArrayList<Gloves> gloves) {
        this.gloves = gloves;
    }

    public ArrayList<Leggings> getLeggings() {
        return leggings;
    }

    public void setLeggings(ArrayList<Leggings> leggings) {
        this.leggings = leggings;
    }

    public ArrayList<Boots> getBoots() {
        return boots;
    }

    public void setBoots(ArrayList<Boots> boots) {
        this.boots = boots;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    public ArrayList<Weapon> getWeapons() {
        return this.weapons;
    }
    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }
    public ArrayList<Armor> getArmors() {
        return armors;
    }

    public void setArmors(ArrayList<Armor> armors) {
        this.armors = armors;
    }
}

class Inventory extends ItemManager {
    private Weapon noWeapon;
    private Armor noArmor;
    private Weapon weapon = null;
    private Helmet helmet = null;
    private Torso torso = null;
    private Gloves gloves = null;
    private Leggings leggings = null;
    private Boots boots = null;

    public Inventory() {
        setNoWeapon(new NoItemWeapon());
        setNoArmor(new NoItemArmor());
    }
    public void addItem(Item item) {
        if (!(getItems().contains(item))) {
            getItems().add(item);
        } else {
            System.out.println("You Have That Item.");
        }
    }
    public Item getItem(String itemName) {
        Item temp_item = searchItem(itemName);
        return temp_item;
    }
    public Item getItem(int itemIndex) {
        Item temp_item = searchItem(itemIndex);
        return temp_item;
    }
    public Item searchItem(String itemName) {
        for (Item item: getItems()) {
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

    public Weapon getItemWeapon() {
        if (this.weapon != null) {
            return this.weapon;
        } else {
            return getNoWeapon();
        }
    }

    public void setItemWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getItemHelmet() {
        if (this.helmet != null) {
            return this.helmet;
        } else {
            return getNoArmor();
        }
    }

    public void setItemHelmet(Helmet helmet) {
        this.helmet = helmet;
    }

    public Armor getItemTorso() {
        if (this.torso != null) {
            return this.torso;
        } else {
            return getNoArmor();
        }
    }

    public void setItemTorso(Torso torso) {
        this.torso = torso;
    }

    public Armor getItemGloves() {
        if (this.gloves != null) {
            return this.gloves;
        } else {
            return getNoArmor();
        }
    }

    public void setItemGloves(Gloves gloves) {
        this.gloves = gloves;
    }

    public Armor getItemLeggings() {
        if (this.leggings != null) {
            return this.leggings;
        } else {
            return getNoArmor();
        }
    }

    public void setItemLeggings(Leggings leggings) {
        this.leggings = leggings;
    }

    public Armor getItemBoots() {
        if (this.boots != null) {
            return this.boots;
        } else {
            return getNoArmor();
        }
    }

    public void setItemBoots(Boots boots) {
        this.boots = boots;
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

    public void setNoArmor(Armor noArmor) {
        this.noArmor = noArmor;
    }
    public void displayItems(String itemType, List<? extends Item> items) {
        System.out.println("Available " + itemType + " To Equip Are: ");
        for (int i = 0; i < items.size(); i++) {
            if (i == 0) {
                System.out.print("| ");
            }
            System.out.print(items.get(i).getItemName());
            if (i < (items.size() - 1)) {
                System.out.print("  |   ");
            }
            if (i == (items.size() - 1)) {
                System.out.println("    |");
                System.out.println();
            }
        }
    }
    public void askEquipItem(String itemType, List<? extends Item> items) {
        this.displayItems(itemType, items);
        System.out.println("What " + itemType + " Would You Like To Equip: ");

        try {
            Scanner input = new Scanner(System.in);

            String choice = input.nextLine();
            Item chosenItem = searchItem(choice);

            if(chosenItem != null) {
                switch(itemType.toLowerCase()) {
                    case "weapon":
                        setItemWeapon((Weapon) chosenItem);
                        break;
                    case "helmet":
                        setItemHelmet((Helmet) chosenItem);
                        break;
                    case "torso":
                        setItemTorso((Torso) chosenItem);
                        break;
                    case "gloves":
                        setItemGloves((Gloves) chosenItem);
                        break;
                    case "leggings":
                        setItemLeggings((Leggings) chosenItem);
                        break;
                    case "boots":
                        setItemBoots((Boots) chosenItem);
                        break;
                    default:
                        throw new IncorrectWeaponNameException("Could Not Find " + itemType + ": " + choice);
                }
            } else {
                throw new IncorrectWeaponNameException("Could Not Find " + itemType + ": " + choice);
            }
        } catch (IncorrectWeaponNameException e) {
            System.out.println(e);
        }
    }
}
