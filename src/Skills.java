public class Skills {
    private String skillAttackName;
    private int skillAttackValue;
    private Global.AttributeType skillType;

    public Skills(String skillAttackName, int skillAttackValue) {
        setSkillAttackName(skillAttackName);
        setSkillAttackValue(skillAttackValue);
        setSkillType(Global.AttributeType.Attack);
    }
    public Skills(String skillAttackName, Global.AttributeType skillType, int skillAttackValue) {
        setSkillAttackName(skillAttackName);
        setSkillAttackValue(skillAttackValue);
        setSkillType(skillType);
    }

    public Global.AttributeType getSkillType() {
        return skillType;
    }

    public void setSkillType(Global.AttributeType skillType) {
        this.skillType = skillType;
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
