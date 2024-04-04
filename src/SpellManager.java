import java.util.*;

public class SpellManager {
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
class Grimoire extends SpellManager {
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
    public Spells searchSpell(int spellIndex) {
        try {
            return getSpells().get(spellIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    public ArrayList<Spells> getOwnSpells() {
        return ownSpells;
    }

    public void setOwnSpells(ArrayList<Spells> ownSpells) {
        this.ownSpells = ownSpells;
    }
}
