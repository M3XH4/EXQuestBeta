public class Skills {
    private String skillAttackName;
    private int skillAttackValue;

    public Skills(String skillAttackName, int skillAttackValue) {
        setSkillAttackName(skillAttackName);
        setSkillAttackValue(skillAttackValue);
    }

    public String getSkillAttackName() {
        return skillAttackName;
    }

    public void setSkillAttackName(String skillAttackName) {
        this.skillAttackName = skillAttackName;
    }

    public int getSkillAttackValue() {
        return skillAttackValue;
    }

    public void setSkillAttackValue(int skillAttackValue) {
        this.skillAttackValue = skillAttackValue;
    }
}
