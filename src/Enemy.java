import java.util.*;
public class Enemy extends Stats {
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public Enemy() {
        Boar boar = new Boar();
        LesserVampire lesserVampire = new LesserVampire();

        setEnemies(new ArrayList<>(Arrays.asList(
                boar,
                lesserVampire
        )));
    }

    public Enemy(String name, String battleCry, int health, int exp, ArrayList<Skills> skills) {
        setName(name);
        setBattleCry(battleCry);
        setHealth(health);
        setMaxHealth(health);
        setExp(exp);
        setMaxExp(exp);
        setSkills(skills);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
}
class Boar extends Enemy {
    public Boar() {
        super("Boar", "Oin-Oink Oink!!!!", 40, 20, new ArrayList<>(Arrays.asList(
                new Skills("Boar Rush", 5),
                new Skills("Head Butt", 3)
        )));
    }
}
class LesserVampire extends Enemy {
    public LesserVampire() {
        super("Lesser Vampire", "Blah Bla-Blahhh, I Drain Your Blood To The Brim", 60, 75, new ArrayList<>(Arrays.asList(
                new Skills("Blood Control: Blood Bullet", 8),
                new Skills("Bite", 5),
                new Skills("Blod Control: Blood Zone Break", 13)
        )));
    }
}