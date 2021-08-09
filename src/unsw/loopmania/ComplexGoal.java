package unsw.loopmania;

import java.util.ArrayList;

public class ComplexGoal implements Goal{
    private ArrayList<Goal> goals;
    private GoalType goalType;

    public ComplexGoal(GoalType goalType, ArrayList<Goal> goals) {
        this.goals = goals;
        this.goalType = goalType;
    }

    /**
     * check if character have enough gold &/ experience &/cycles to meet goal of world
     * @param item
     * @return boolean
     */
    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        for (Goal subGoal : goals) {
            if (goalType.equals(GoalType.AND) && !subGoal.checkGoal(world)) {
                return false;
            } else if (goalType.equals(GoalType.OR) && subGoal.checkGoal(world)) {
                return true;
            }
        }
        if (goalType.equals(GoalType.AND)) {
            return true;
        } else if (goalType.equals(GoalType.OR)) {
            return false;
        }
        return false;
    }

    /**
     * override the to string method to allow display of goal in frontend
     * @return string
     */
    @Override
    public String toString() {
        String goalString = "";
        for (int i = 0; i < goals.size(); i++) {
            if (i == goals.size() - 1) {
                goalString += goals.get(i).toString();
            } else {
                goalString += goals.get(i).toString() + " " + goalType.getGoalType() + "\n";
            }
        }
        return goalString;
    }
}
