import java.util.*;
public class Stats {
    private String name;
    private String battleCry;
    private int health = 0;
    private int maxHealth = 0;
    private int mana = 0;
    private int maxMana = 0;
    private int exp = 0;
    private int maxExp = 0;
    private int level = 0;
    private ArrayList<Skills> skills;
    public void displayStats() {
        String nameTitle = "-------- " + getName() + " --------";
        System.out.println(nameTitle);
        System.out.println("Health: " + getHealth() + "/" + getMaxHealth());
        if (this instanceof Player player) {
            System.out.println("Mana: " + player.getMana() + "/" + getMana());
        }
        Global.placeLine(nameTitle);
    }
    public void attacking() {
        if (this instanceof Player player) {
            System.out.println("Warrior " + player.getName() + ": Prepare To Be Defeated.");
        } else if (this instanceof Enemy enemy) {
            System.out.println(enemy.getName() + ": " + enemy.getBattleCry());
        }
    }
    public void levelUp(Enemy enemy) {
        if (this instanceof Player player) {
            player.setExp(player.getExp() + enemy.getExp());

            System.out.println("Spirit Guide: Warrior " + player.getName() + " You Have Acquired " + enemy.getExp() + " EXP From " + enemy.getName() + ",");

            if (player.getExp() >= player.getMaxExp()) {
                int levelGained = player.getExp() / player.getMaxExp();
                int newLevel = player.getLevel() + levelGained;
                int remainingExp = player.getExp() % player.getMaxExp();

                player.setLevel(newLevel);
                player.setMaxExp((int) (player.getMaxExp() * 1.2));
                player.setExp(remainingExp);

                System.out.println("Spirit Guide: Warrior " + player.getName() + " You Have Leveled Up To " + player.getLevel() + ". Congratulations!");
                System.out.println("Spirit Guide: Your Required Experience To Level Up Has Increased To " + player.getMaxExp() + ".");
            } else {
                System.out.println("Spirit Guide: Warrior " + player.getName() + " You Do Not Have The Enough Experience To Level Up.");
                System.out.println("Spirit Guide: You Need " + (player.getMaxExp() - player.getExp()) + " More EXP Points.");
            }
        }
    }
    public void attacks(Player player, Enemy enemy) {
        if (this instanceof Player) {
            System.out.println("Spirit Guide: You Have Used " + player.getSkillName() + ".");
            enemy.setHealth(enemy.getHealth() - player.getSkillAttackValue());
            if (enemy.getHealth() > 20 && enemy.getHealth() <= enemy.getMaxHealth()) {
                System.out.println("Spirit Guide: Enemy Is Down To " + enemy.getHealth() + "Health.");
            } else if (enemy.getHealth() >= 0 && enemy.getHealth() <= 20){
                System.out.println("Spirit Guide: Enemy Is Now " + enemy.getHealth() + ", Finish Him!");
            } else {
                System.out.println("Spirit Guide: " + enemy.getName() + " Is Dead");
            }
        } else if (this instanceof Enemy) {
            System.out.println("Spirit Guide: " + enemy.getName() + " Used " + enemy.getSkillName() + ".");
            player.setHealth(player.getHealth() - enemy.getSkillAttackValue());
            System.out.println("Spirit Guide: You Have Taken " + enemy.getSkillAttackValue() + " Damage.");

            if (player.getHealth() > 20 && player.getHealth() <= player.getMaxHealth()) {
                System.out.println("Spirit Guide: You Are Down To " + player.getHealth() + " Health.");
            } else {
                System.out.println("Spirit Guide: You Have Been Defeated By " + enemy.getName());
            }
        }
    }
    public void attacks(Player player, Enemy enemy, int attackNumber) {
        if (this instanceof Player) {
            System.out.println("Spirit Guide: You Have Used " + player.getSkillName(attackNumber) + ".");
            enemy.setHealth(enemy.getHealth() - player.getSkillAttackValue(attackNumber));
            if (enemy.getHealth() > 20 && enemy.getHealth() <= enemy.getMaxHealth()) {
                System.out.println("Spirit Guide: Enemy Is Down To " + enemy.getHealth() + "Health.");
            } else if (enemy.getHealth() >= 0 && enemy.getHealth() <= 20){
                System.out.println("Spirit Guide: Enemy Is Now " + enemy.getHealth() + ", Finish Him!");
            } else {
                System.out.println("Spirit Guide: " + enemy.getName() + " Is Dead");
            }
        } else if (this instanceof Enemy) {
            System.out.println("Spirit Guide: " + enemy.getName() + " Used " + enemy.getSkillName(attackNumber) + ".");
            player.setHealth(player.getHealth() - enemy.getSkillAttackValue(attackNumber));
            System.out.println("Spirit Guide: You Have Taken " + enemy.getSkillAttackValue(attackNumber) + " Damage.");

            if (player.getHealth() > 20 && player.getHealth() <= player.getMaxHealth()) {
                System.out.println("Spirit Guide: You Are Down To " + player.getHealth() + " Health.");
            } else {
                System.out.println("Spirit Guide: You Have Been Defeated By " + enemy.getName());
            }
        }
    }
    public String getSkillName() {
        if (this instanceof Player player) {
            return player.getSkills().getFirst().getSkillAttackName();
        } else if (this instanceof Enemy enemy) {
            return enemy.getSkills().getFirst().getSkillAttackName();
        }
        return null;
    }
    public String getSkillName(int num) {
        if (this instanceof Player player) {
            return player.getSkills().get(num).getSkillAttackName();
        } else if (this instanceof Enemy enemy) {
            return enemy.getSkills().get(num).getSkillAttackName();
        }
        return null;
    }
    public int getSkillAttackValue() {
        if (this instanceof Player player) {
            return player.getSkills().getFirst().getSkillAttackValue();
        } else if (this instanceof Enemy enemy) {
            return enemy.getSkills().getFirst().getSkillAttackValue();
        }
        return 0;
    }
    public int getSkillAttackValue(int num) {
        if (this instanceof Player player) {
            return player.getSkills().get(num).getSkillAttackValue();
        } else if (this instanceof Enemy enemy) {
            return enemy.getSkills().get(num).getSkillAttackValue();
        }
        return 0;
    }
    public ArrayList<Skills> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skills> skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBattleCry() {
        return battleCry;
    }

    public void setBattleCry(String battleCry) {
        this.battleCry = battleCry;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}