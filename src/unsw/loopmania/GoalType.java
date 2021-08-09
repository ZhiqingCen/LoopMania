package unsw.loopmania;

public enum GoalType {
    AND("and"),
    OR("or"),
    GOLD("gold"),
    EXPERIENCE("experience"),
    CYCLES("cycles"),
    BOSSES("bosses");

    private String goalType;

    private GoalType(String goalType) {
        this.goalType = goalType;
    }

    /**
     * getter for GoalType name
     * @return String of goalType
     */
    public String getGoalType() {
        return goalType;
    }
}
