package unsw.loopmania;

public class Subgoal implements Goal{
    private GoalType goalType;
    private int quantity;
    private boolean checkBoss;

    public Subgoal(GoalType goalType, int quantity) {
        this.goalType = goalType;
        this.quantity = quantity;
        checkBoss = false;
    }

    /**
     * check what bosses need to be defeated to reach goal
     * @param world
     * @return boolean
     */
    public boolean checkDoggieBoss(LoopManiaWorld world) {
        if (quantity >= 20) {
            world.setIsDoggie(true);
        }
        if (quantity >= 40) {
            world.setIsFinalBoss(true);
        }
        checkBoss = true;
        return false;
    }

    /**
     * check if goal is met
     * @param targetQuantity of goal
     * @param currentQuantity of goal
     * @return boolean
     */
    public boolean checkGoalStatus(int targetQuantity, int currentQuantity) {
        return currentQuantity >= targetQuantity;
    }

    /**
     * check if character have enough gold/experience/cycles to meet goal of world
     * @param item
     * @return boolean
     */
    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        switch(goalType) {
            case GOLD:
                return checkGoalStatus(quantity, world.getItem().getGold());
            case EXPERIENCE:
                return checkGoalStatus(quantity, world.getItem().getExperience());
            case CYCLES:
                if (!checkBoss) { 
                    checkDoggieBoss(world); 
                }
                return checkGoalStatus(quantity, world.getItem().getCycles());
            default:
                System.err.println("error: invalid subgoal type");
                return false;
        }
    }
    
    /**
     * override the to string method to allow display of goal in frontend
     * @return string
     */
    @Override
    public String toString() {
        return goalType.getGoalType() + ": " + quantity;
    }
}
