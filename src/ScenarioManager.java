import java.util.*;

public class ScenarioManager {
    private ArrayList<Scenario> scenarios;

    private Battle battle;
    private Market market;
    private Screen screen;
    private Camping camping;
    public ScenarioManager(Player player, Enemy enemy) {
        setScenarios(new ArrayList<>());
        setBattle(new Battle(player, enemy));
        setMarket(new Market(player, enemy));
        setScreen(new Screen(player, enemy));
        setCamping(new Camping(player, enemy));

        addScenario(getCamping());
        addScenario(getBattle());
        addScenario(getMarket());
    }

    public Camping getCamping() {
        return camping;
    }

    public void setCamping(Camping camping) {
        this.camping = camping;
    }

    public void addScenario(Scenario scenario) {
        getScenarios().add(scenario);
    }
    public ArrayList<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(ArrayList<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
