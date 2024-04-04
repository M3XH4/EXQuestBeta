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
    private ArrayList<Spells> grimoire;

    public Grimoire() {
        setGrimoire(new ArrayList<>());

        addSpell("Fireball");
        addSpell("Blizzard");
        addSpell("Thunder");
    }
    public void addSpell(String spellName) {
        Spells addSpell = searchSpell(spellName);
        getGrimoire().add(addSpell);
    }
    public Spells getSpell(String spellName) {
        Spells getSpell = searchSpell(spellName);
        if (getSpell != null) {
            return getSpell;
        } else {
            return null;
        }
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
    public ArrayList<Spells> getGrimoire() {
        return grimoire;
    }

    public void setGrimoire(ArrayList<Spells> grimoire) {
        this.grimoire = grimoire;
    }
}
