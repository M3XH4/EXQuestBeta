import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class SpellManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<Spells> spells;

    public SpellManager() {
        setSpells(new ArrayList<>());

        getSpells().add(new Fireball());
        getSpells().add(new Thunder());
        getSpells().add(new Blizzard());
    }

    public ArrayList<Spells> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spells> spells) {
        this.spells = spells;
    }
}
class Grimoire extends SpellManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Spells> ownSpells;

    public Grimoire() {
        setOwnSpells(new ArrayList<>());

        addSpell("Fireball");
        addSpell("Blizzard");
        addSpell("Thunder");
    }
    public void addSpell(String spellName) {
        Spells addSpell = searchSpell(spellName);
        getOwnSpells().add(addSpell);
    }
    public Spells getSpell(String spellName) {
        return searchSpell(spellName);
    }
    public Spells searchSpell(String spellName) {
        for (Spells spell: getSpells()) {
            if (spell.getSpellName().equalsIgnoreCase(spellName)) {
                return spell;
            }
        }
        return null;
    }
    public ArrayList<Spells> getOwnSpells() {
        return ownSpells;
    }

    public void setOwnSpells(ArrayList<Spells> ownSpells) {
        this.ownSpells = ownSpells;
    }

    public void displayOwnSpells() {
        String headerItems = "|                     Name                     |               Spell Damage                |              Mana Needed              |";
        Global.placeLine(headerItems);
        System.out.println(headerItems);
        Global.placeLine(headerItems);
        for (Spells spells : getOwnSpells()) {
            System.out.print("| " + Global.spacerString(44, spells.getSpellName()) + " | " + Global.spacerString(41, spells.getSpellAttackValue() + " ATK") + " | " + Global.spacerString(37, ("-" + spells.getManaNeeded() + " MP")) + " |\n");
        }
        if(getOwnSpells().isEmpty()) {
            System.out.println("|\t\t\t\t\t\t\t\t\t\t\t   |  \t\t\t\t\t\t\t\t\t\t   | \t\t\t\t\t\t\t\t\t   |");
        }
        Global.placeLine(headerItems);
    }
}
