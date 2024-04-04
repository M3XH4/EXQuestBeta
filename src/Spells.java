
import java.util.*;
public class Spells {
    public String spellName;
    private String incantation;
    private int spellAttackValue;
    private int manaNeeded;

    public Spells(String spellName, String incantation, int spellAttackValue, int manaNeeded) {
        this.spellName = spellName;
        this.incantation = incantation;
        this.spellAttackValue = spellAttackValue;
        this.manaNeeded = manaNeeded;
    }

    public void getSpellDescription() {
        String spellTitle = getSpellName() + " -------------------";
        System.out.println(spellTitle);
        System.out.println("Spell Attack:   " + getSpellAttackValue());
        System.out.println("Mana Needed:    " + getManaNeeded());
        Global.placeLine(spellTitle);
    }

    public void spellAttack(Stats caster, Stats target) {
        if (caster instanceof Player player) {
            System.out.println("Warrior " + player.getName() + ": " + getIncantation());
            player.setMana(player.getMana() - getManaNeeded());
            System.out.println("Spirit Guide: You Have Lose " + getManaNeeded() + " Mana As You Casted " + getSpellName() + ".");
        } else if (caster instanceof Enemy enemy) {
            System.out.println("Spirit Guide: " + enemy.getName() + " Used " + getSpellName() + ".");
        }
        target.setHealth(target.getHealth() - getSpellAttackValue());
        if (target instanceof Enemy enemy) {
            enemy.setHealth(enemy.getHealth() - getSpellAttackValue());
            if (enemy.getHealth() > 20 && enemy.getHealth() <= enemy.getMaxHealth()) {
                System.out.println("Spirit Guide: Enemy Is Down To " + enemy.getHealth() + "Health.");
            } else if (enemy.getHealth() >= 0 && enemy.getHealth() <= 20) {
                System.out.println("Spirit Guide: Enemy Is Now " + enemy.getHealth() + ", Finish Him!");
            } else {
                System.out.println("Spirit Guide: " + enemy.getName() + " Is Dead");
            }
        } else if (target instanceof Player player) {
            player.setHealth(player.getHealth() - getSpellAttackValue());
            System.out.println("Spirit Guide: You Have Taken " + getSpellAttackValue() + " Damage");
            if (player.getHealth() > 20 && player.getHealth() <= player.getMaxHealth()) {
                System.out.println("Spirit Guide: You Are Down To " + player.getHealth() + " Health.");
            } else {
                System.out.println("Spirit Guide: You Have Been Defeated By " + target.getName());
            }
        }

    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public String getIncantation() {
        return incantation;
    }

    public void setIncantation(String incantation) {
        this.incantation = incantation;
    }

    public int getSpellAttackValue() {
        return spellAttackValue;
    }

    public void setSpellAttackValue(int spellAttackValue) {
        this.spellAttackValue = spellAttackValue;
    }

    public int getManaNeeded() {
        return manaNeeded;
    }

    public void setManaNeeded(int manaNeeded) {
        this.manaNeeded = manaNeeded;
    }
}
class Fireball extends Spells {
    public Fireball() {
        super("Fireball", "By the power sent by the inferno and the heat of the cosmos,\nlet the flames roar and reckon. I cast Fireball", 30, 20);
    }
    public void setFire() {
        System.out.println("Set Fire");
    }
}
class Thunder extends Spells {
    public Thunder() {
        super("Thunder", "In the darkened sky, lightning strikes and the echoes roar,\nlet the electric surge and zone. I cast Thunder", 15, 20);
    }
}
class Blizzard extends Spells {
    public Blizzard() {
        super("Blizzard", "By the frost's embrace in the winter's might,\nlet the snow's unfold and flow. I cast Blizzard", 45, 70);
    }
}
