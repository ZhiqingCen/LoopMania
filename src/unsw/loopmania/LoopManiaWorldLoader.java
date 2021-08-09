package unsw.loopmania;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

/**
 * Loads a world from a .json file.
 * 
 * By extending this class, a subclass can hook into entity creation.
 * This is useful for creating UI elements with corresponding entities.
 * 
 * this class is used to load the world.
 * it loads non-spawning entities from the configuration files.
 * spawning of enemies/cards must be handled by the controller.
 */
public abstract class LoopManiaWorldLoader {
    private JSONObject json;
    private ArrayList<String> rareItems = new ArrayList<String>();
    public LoopManiaWorldLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("worlds/" + filename)));
    }

    /**
     * Parses the JSON to create a world.
     */
    public LoopManiaWorld load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        // path variable is collection of coordinates with directions of path taken...
        List<Pair<Integer, Integer>> orderedPath = loadPathTiles(json.getJSONObject("path"), width, height);

        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath);

        JSONArray jsonEntities = json.getJSONArray("entities");

        JSONObject goals = json.getJSONObject("goal-condition");
        world.setGoalCondition(loadGoal(goals));

        // load non-path entities later so that they're shown on-top
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(world, jsonEntities.getJSONObject(i), orderedPath);
        }
        // load rareItem to the world
        if(json.getJSONArray("rare_items").isEmpty()) world.setRareItem(null);
        else{
            for(int i = 0; i<json.getJSONArray("rare_items").length(); i++){
                world.setRareItem(loadRareItem(json.getJSONArray("rare_items").get(i)));
            }
        }


        return world;
    }

    /**
     * load an entity into the world
     * @param world backend world object
     * @param json a JSON object to parse (different from the )
     * @param orderedPath list of pairs of x, y cell coordinates representing game path
     */
    private void loadEntity(LoopManiaWorld world, JSONObject currentJson, List<Pair<Integer, Integer>> orderedPath) {
        String type = currentJson.getString("type");
        int x = currentJson.getInt("x");
        int y = currentJson.getInt("y");
        int indexInPath = orderedPath.indexOf(new Pair<Integer, Integer>(x, y));
        assert indexInPath != -1;

        Entity entity = null;
        switch (type) {
        case "hero_castle":
            Character character = new Character(new PathPosition(indexInPath, orderedPath));
            world.setCharacter(character);
            onLoad(character);
            entity = character;
            break;
        case "path_tile":
            throw new RuntimeException("path_tile's aren't valid entities, define the path externally.");
        }
        world.addEntity(entity);
    }

    /**
     * load path tiles
     * @param path json data loaded from file containing path information
     * @param width width in number of cells
     * @param height height in number of cells
     * @return list of x, y cell coordinate pairs representing game path
     */
    private List<Pair<Integer, Integer>> loadPathTiles(JSONObject path, int width, int height) {
        if (!path.getString("type").equals("path_tile")) {
            // ... possible extension
            throw new RuntimeException(
                    "Path object requires path_tile type.  No other path types supported at this moment.");
        }
        PathTile starting = new PathTile(new SimpleIntegerProperty(path.getInt("x")), new SimpleIntegerProperty(path.getInt("y")));
        if (starting.getY() >= height || starting.getY() < 0 || starting.getX() >= width || starting.getX() < 0) {
            throw new IllegalArgumentException("Starting point of path is out of bounds");
        }
        // load connected path tiles
        List<PathTile.Direction> connections = new ArrayList<>();
        for (Object dir: path.getJSONArray("path").toList()){
            connections.add(Enum.valueOf(PathTile.Direction.class, dir.toString()));
        }

        if (connections.size() == 0) {
            throw new IllegalArgumentException(
                "This path just consists of a single tile, it needs to consist of multiple to form a loop.");
        }

        // load the first position into the orderedPath
        PathTile.Direction first = connections.get(0);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(Pair.with(starting.getX(), starting.getY()));

        int x = starting.getX() + first.getXOffset();
        int y = starting.getY() + first.getYOffset();

        // add all coordinates of the path into the orderedPath
        for (int i = 1; i < connections.size(); i++) {
            orderedPath.add(Pair.with(x, y));
            
            if (y >= height || y < 0 || x >= width || x < 0) {
                throw new IllegalArgumentException("Path goes out of bounds at direction index " + (i - 1) + " (" + connections.get(i - 1) + ")");
            }
            
            PathTile.Direction dir = connections.get(i);
            PathTile tile = new PathTile(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            x += dir.getXOffset();
            y += dir.getYOffset();
            if (orderedPath.contains(Pair.with(x, y)) && !(x == starting.getX() && y == starting.getY())) {
                throw new IllegalArgumentException("Path crosses itself at direction index " + i + " (" + dir + ")");
            }
            onLoad(tile);
        }
        // we should connect back to the starting point
        if (x != starting.getX() || y != starting.getY()) {
            throw new IllegalArgumentException(String.format(
                    "Path must loop back around on itself, this path doesn't finish where it began, it finishes at %d, %d.",
                    x, y));
        }
        onLoad(starting);
        return orderedPath;
    }

    public abstract void onLoad(Character character);
    public abstract void onLoad(PathTile pathTile);
    
    /**
     * load goal from JSON file and add to Goal
     * @param JSONObject goals
     * @return Goal
     */
    public Goal loadGoal(JSONObject goals) {
        String type = goals.getString("goal");
        GoalType goalType;
        Goal ret;
        switch(type) {
            case "AND":
                goalType = GoalType.AND;
                break;
            case "OR":
                goalType = GoalType.OR;
                break;
            case "gold":
                goalType = GoalType.GOLD;
                break;
            case "experience":
                goalType = GoalType.EXPERIENCE;
                break;
            case "cycles":
                goalType = GoalType.CYCLES;
                break;
            case "bosses":
                goalType = GoalType.BOSSES;
                break;
            default:
                throw new Error("invalid goal type");
        }

        if (goalType.equals(GoalType.AND) || goalType.equals(GoalType.OR)) {
            JSONArray subgoals = goals.getJSONArray("subgoals");
            ArrayList<Goal> leafgoals = new ArrayList<Goal>();
            for (int i = 0; i < subgoals.length(); i++) {
                JSONObject subgoal = subgoals.getJSONObject(i);
                leafgoals.add(loadGoal(subgoal));
            }
            ret = new ComplexGoal(goalType, leafgoals);
        } else if (goalType.equals(GoalType.BOSSES)) {
            ret = new BossGoal(goalType);
        } else {
            int goalQuantity = goals.getInt("quantity");
            ret = new Subgoal(goalType, goalQuantity);
        }
        return ret;
    }

    /**
     * load the rareItem from json file and load it to world
     * @param rareItemType
     * @return rareItem
     */
    public ArrayList<String> loadRareItem(Object rareItemType){
        int x = 1;
        int y = 2;
        
        Equipment rareItem = null;

        if (rareItemType.equals("the_one_ring")){
            rareItem = new TheOneRing(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            rareItems.add(rareItem.getName());
        }
        else if(rareItemType.equals("tree_stump")){
            rareItem = new TreeStump(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            rareItems.add(rareItem.getName());
        }
        else if (rareItemType.equals("anduril_flame_of_the_west")) {
            rareItem = new Anduril(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            rareItems.add(rareItem.getName());
        } else {
            System.err.println("error: invalid rare item");
        }
        return rareItems;
    }
}
