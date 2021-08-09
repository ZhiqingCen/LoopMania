package unsw.loopmania;

public class BossGoal implements Goal{
    private GoalType goalType;

    public BossGoal(GoalType goalType) {
        this.goalType = goalType;
    }

    /**
     * check if boss goal met
     * @return boolean
     */
    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        return world.checkBossDefeated();
    }

    /**
     * override the to string method to allow display of goal in frontend
     * @return string
     */
    @Override
    public String toString() {
        return goalType.getGoalType();
    }
}
