package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {

    public static final int unequippedInventoryWidth = 5;
    public static final int unequippedInventoryHeight = 4;
    public boolean GameState = true;
    private boolean countActive = false;
    private List<BasicEnemy> spawningEnemies;

    /**
     * width of the world in GridPane cells
     */
    private int width;
    private List<Integer> counts = new ArrayList<>();

    /**
     * height of the world in GridPane cells
     */
    private int height;
    private List<BasicEnemy> fightingEnemies = new ArrayList<>();
    private List<BasicEnemy> cycleDefeatedEnemies = new ArrayList<>();
    /**
    * Goal of world
    */
    private Equipment HealthPotionGold;
    private boolean setHealthPotionGold;
    private Goal goal;
    private boolean isDoggie;
    private boolean isFinalBoss;
    private boolean doggieDefeated;
    private boolean finalBossDefeated;
    private ConfusingMode confusing;
    private int shopCycle = 1;
    private int i = 2;
    private boolean droppedPotion = false;

    /**
     * Item of world
     */
    private Item item;

    /**
     * mape of world
     */
    private Map map;
    private static final int spawningGold = 10;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    /**
     * The game mode
     */
    private Mode mode;

    /**
     * The character
     */
    private Character character;

    /**
     * The three different enemies are stored in this list
     */
    private List<BasicEnemy> enemies;

    /**
     * The cards are stored in this list
     */
    private List<Card> cardEntities;

    /**
     * Some of items may droped from defated enemies, stored in this list
     */
    private List<Entity> unequippedInventoryItems;

    /**
     * The euqiped items are stored in this list
     */
    private List<Entity> equippedInventoryItems;

    /**
     * The generated buildings from cards are stored here
     */
    private List<Entity> buildingEntities;

    /**
     * The random object
     */
    private Random random;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    private HerosCastle herosCastle;
    private boolean healthPotionPicked;

    private boolean campfirehelper;

    private ArrayList<String> rareItems;
    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        item = new Item(0,0,0);
        nonSpecifiedEntities = new ArrayList<>();
        rareItems = new ArrayList<String>();
        character = new Character(new PathPosition(0, orderedPath));
        map = new Map();
        goal = new Subgoal(GoalType.GOLD, 100);
        enemies = new ArrayList<>();
        buildingEntities = new ArrayList<>();
        this.orderedPath = orderedPath;
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        equippedInventoryItems = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            equippedInventoryItems.add(null);
        }
        random = new Random();
        mode = Mode.STANDARD;
        isDoggie = false;
        isFinalBoss = false;
        setHealthPotionGold = true;
        healthPotionPicked = false;
        herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1), mode);
        campfirehelper = false;
    }

    public HerosCastle getHerosCastle() {
        return herosCastle;
    }

    public List<BasicEnemy> getSpawningEnemies () {
        return spawningEnemies;
    }

    public boolean getIsDoggie() {
        return isDoggie;
    }

    public void setIsDoggie(boolean isDoggie) {
        this.isDoggie = isDoggie;
    }

    public boolean getIsFinalBoss() {
        return isFinalBoss;
    }

    public void setIsFinalBoss(boolean isFinalBoss) {
        this.isFinalBoss = isFinalBoss;
    }

    public Mode getMode(){
        return mode;
    }

    public Character getCharacter(){
        return character;
    }

    public void setMode(Mode mode){
        this.mode = mode;
        herosCastle.setMode(mode);
    }

    public boolean getGameState(){
        return GameState;
    }

    public void setGameState(boolean GameState){
        this.GameState = GameState;
    }

    public void setGoalCondition(Goal goal){
        this.goal = goal;
    }
    
    public List<Entity> getEquippedInventoryItems() {
        return equippedInventoryItems;
    }

    public List<Entity> getUnequippedInventoryItems() {
        return unequippedInventoryItems;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map getMap(){
        return map;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public Item getItem(){
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Card> getCardEntities() {
        return cardEntities;
    }

    public Goal getGoal() {
        return goal;
    }

    public List<Entity> getNonSpecifiedEntities() {
        return nonSpecifiedEntities;
    }

    public List<BasicEnemy> getBasicEnemies(){
        return enemies;
    }

    public void setOrderPath(List<Pair<Integer, Integer>> orderedPath){
        this.orderedPath = orderedPath;
    }

    public List<Pair<Integer, Integer>> getOrderPath(){
        return orderedPath;
    }

    public List<Entity> getBuildingEntity(){
        return buildingEntities;
    }

    /**
     * if the shop will show in HerosCastle at specific cycle, return true
     * @return boolean
     */
    public boolean displayShop(){
        if (item.getCycles() == shopCycle && character.getX() == 1 && character.getY() == 1) {
            shopCycle += i;
            i++;
            return true;
        }
        return false;
    }

    /**
     * The x,y has a card or equipment return true
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean checkTheCoordinateHaveItems(int x, int y){
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return true;
            }
        }
        for (Card c: cardEntities){
            if ((c.getX() == x) && (c.getY() == y)){
                return true;
            }
        }
        return false;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        nonSpecifiedEntities.add(entity);
    }

    /**
     * add character health when passing through a village
     */
    public void villageAddHealth() {
        for (Entity building:buildingEntities) {
            if (building.getClass().getSimpleName().equals("Village")) {
                Village village = (Village) building;
                if (village.CharacterPositionCheck(character)) {
                    System.out.println("---Village take effect---");
                    village.CharacterHealthRecover(character);
                    System.out.println("damage: "+character.getDamage());
                }
            }
        }
    }

    /**
     * Campfire add damage
     */
    public void CampfireUse() {
        for (Entity building:buildingEntities) {
            if (building.getClass().getSimpleName().equals("Campfire")) {
                Campfire cf = (Campfire) building;
                if (cf.CharacterPositionCheck(character) && campfirehelper == false) {
                    System.out.println("---Campfire take effect---");
                    cf.doubleAttack(character);
                    campfirehelper = true;
                } else if(!cf.CharacterPositionCheck(character) && campfirehelper == true) {
                    System.out.println("---Campfire lose effect---");
                    cf.restoreAttack(character);
                    campfirehelper = false;
                }
            }
        }
    }

    /**
     * Tower help battle
     */
    public void TowerUse() {
        for (Entity building:buildingEntities) {
            if (building.getClass().getSimpleName().equals("Tower")) {
                Tower to = (Tower)building;
                if (to.CharacterPositionCheck(character)) {
                    for(BasicEnemy ally:enemies) {
                        if(to.EnemeyPositionCheck(ally)) {
                            System.out.println("---Tower take effect---");
                            to.enemiesHPReduce(ally);
                        }
                        
                    }
                }
            }
        }
    }

    /**
     * SuperTower help battle
     */
    public void SuperTowerUse() {
        for (Entity building:buildingEntities) {
            if (building.getClass().getSimpleName().equals("SuperTower")) {
                SuperTower to = (SuperTower)building;
                if (to.CharacterPositionCheck(character)) {
                    for (BasicEnemy ally:enemies) {
                        if (to.EnemeyPositionCheck(ally)) {
                            System.out.println("---SuperTower take effect---");
                            to.enemiesHPReduce(ally);
                        }
                        
                    }
                }
            }
        }
    }

    /**
     * Trap help battle 
     */
    public void TrapUse() {
        boolean use = false;
        for (Entity building:buildingEntities) {
            if (building.getClass().getSimpleName().equals("Trap")) {
                Trap tr = (Trap)building;
                for (BasicEnemy ally:enemies) {
                    if (tr.EnemeyPositionCheck(ally)) {
                        System.out.println("---Trap take effect---");
                        tr.enemiesHPReduce(ally);
                        removeBuilding(tr);
                        use = true;
                        break;
                    }
                }
            }
            if(use == true)break;
        }
    }

    /**
     * Barracks realease solider
     */
    public boolean BarracksUse() {
        for (Entity building:buildingEntities) {
            if (building.getClass().getSimpleName().equals("Barracks")) {
                Barracks ba = (Barracks)building;
                if (ba.CharacterPositionCheck(character)) {
                    if (ba.SoliderRealease(character)) {
                        System.out.println("---Barracks take effect---");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * check whether the spawn position is ahead of character and is not at the start of the cycle
     * @param pos
     * @return ahead
     */
    public boolean aheadOfCharacter(Pair<Integer,Integer> pos) {
        boolean ahead = false;
        int spawnPos = orderedPath.indexOf(pos);
        if (spawnPos>character.getPathPosition().getCurrentPositionInPath()&&spawnPos != 0) {
            ahead = true;
        }
        return ahead;
    }

    public int getSpawningGold() {
        return spawningGold;
    }

    /**
     * pick gold and health potion if character reaches the point
     */
    public void pickGoldHealthPotion() {
        Equipment newSpawningEquipments = possiblySpawnEquipments();
        if (newSpawningEquipments!=null) {
                character.setInBattle(true);
                if (newSpawningEquipments.getX()==getCharacter().getX()&&newSpawningEquipments.getY()==getCharacter().getY()){
                    switch (newSpawningEquipments.getName()) {
                        case "Gold":
                            Gold gold = (Gold) newSpawningEquipments;
                            gold.addGold(getItem(),getSpawningGold());
                            System.out.println(spawningGold + " gold picked");
                            break;
                        case "HealthPotion":
                            healthPotionPicked = true;
                            System.out.println("Health potion picked");
                            droppedPotion = true;
                            break;
                    }
                    newSpawningEquipments.destroy();
                    HealthPotionGold = null;
            }
        }
        character.setInBattle(false);
    }

    public boolean getHealthPotionPicked() {
        return healthPotionPicked;
    }

    public void setHealthPotionPicked(boolean picked) {
        healthPotionPicked = picked;
    }

    /**
     * change the direction if the enemy meets the end of the cycle
     * @param e enemy
     */
    public void checkPosition(BasicEnemy e) {
        int characterPos = character.getPathPosition().getCurrentPositionInPath();
        int enemyPos = e.getPathPosition().getCurrentPositionInPath();
        if (enemyPos == orderedPath.size()-1) {
            e.setDirection(0);
        }
        if (enemyPos<=characterPos) {
            e.setDirection(1);
        }
    }

    /**
     * spawns equipment if the conditions warrant it, adds to world
     * @return list of the equipment to be displayed on screen
     */
    public Equipment possiblySpawnEquipments() {
        Pair<Integer,Integer> pos = possiblyGetEquipmentSpawnPosition();
        int cycle = item.getCycles();
        if (pos!=null && orderedPath.contains(pos) && pos.getValue0()!=orderedPath.get(0).getValue0()&&pos.getValue1()!=orderedPath.get(0).getValue1()) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(pos.getValue0());
            SimpleIntegerProperty y = new SimpleIntegerProperty(pos.getValue1());
            if (character.getX()==orderedPath.get(0).getValue0()&&character.getY()==orderedPath.get(0).getValue1()&&fightingEnemies.isEmpty()) {
                HealthPotionGold = null;
                setHealthPotionGold = true;
                healthPotionPicked = false;
            }
            if (cycle % 2 == 0 && setHealthPotionGold) {
                HealthPotion healthPotion = new HealthPotion(x, y);
                HealthPotionGold = healthPotion;
                setHealthPotionGold = false;
            }
            if (cycle % 2 != 0 && setHealthPotionGold) {
                Gold gold = new Gold(x, y);
                HealthPotionGold = gold;
                setHealthPotionGold = false;
            }
        }
        return HealthPotionGold;
    }
    
    public void createSlugs(int totalNumber) {
        while (counts.get(0) < totalNumber) {
            Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
            if (pos != null && orderedPath.contains(pos)){
                int indexInPath = orderedPath.indexOf(pos);
                PathPosition position = new PathPosition(indexInPath, orderedPath);
                Slug slug = new Slug(position);
                enemies.add(slug);
                spawningEnemies.add(slug);
                counts.set(0, counts.get(0)+1);
            }
        }
    }

    public void createZombies(int totalNumber) {
        while (counts.get(1) < totalNumber) {
            Pair<Integer, Integer> pos = possiblyGetZombieOrVampirePosition("ZombiePit");
            if(pos.getValue0()== 10 && pos.getValue1()==10)break;
            if (pos != null && orderedPath.contains(pos)){
                int indexInPath = orderedPath.indexOf(pos);
                PathPosition position = new PathPosition(indexInPath, orderedPath);
                Zombie zombie = new Zombie(position);
                enemies.add(zombie);
                spawningEnemies.add(zombie);
                counts.set(1, counts.get(1)+1);
            }
        }
    }

    /**
     * create vampires till the total number
     * @param totalNumber
     */
    public void createVampires(int totalNumber) {
        while (counts.get(2) < totalNumber) {
            Pair<Integer, Integer> pos = possiblyGetZombieOrVampirePosition("VampireCastle");
            if (pos.getValue0() == 10 && pos.getValue1() == 10) break;
            if (pos != null && orderedPath.contains(pos)) {
                int indexInPath = orderedPath.indexOf(pos);
                PathPosition position = new PathPosition(indexInPath, orderedPath);
                Vampire vampire = new Vampire(position);
                enemies.add(vampire);
                spawningEnemies.add(vampire);
                counts.set(2, counts.get(2)+1);
            }
        }
    }

    /**
     * create vampires till the total number
     * @param totalNumber
     */
    public void createDoggie() {
        while (counts.get(3) < 1) {
            Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
            if (pos != null && orderedPath.contains(pos)){
                int indexInPath = orderedPath.indexOf(pos);
                PathPosition position = new PathPosition(indexInPath, orderedPath);
                Doggie doggie = new Doggie(position);
                enemies.add(doggie);
                spawningEnemies.add(doggie);
                counts.set(3, counts.get(3)+1);
            }
        }
    }

    /**
     * create vampires till the total number
     * @param totalNumber
     */
    public void createFinalBoss() {
        while (counts.get(4) < 1) {
            Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
            if (pos != null && orderedPath.contains(pos)){
                int indexInPath = orderedPath.indexOf(pos);
                PathPosition position = new PathPosition(indexInPath, orderedPath);
                FinalBoss finalBoss = new FinalBoss(position);
                enemies.add(finalBoss);
                spawningEnemies.add(finalBoss);
                counts.set(4, counts.get(4)+1);
                System.out.println("create Elan Muske total "+counts.get(4));
            }
        }
    }
    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<BasicEnemy> possiblySpawnEnemies(){
        // initialize for the first round
        if (counts.isEmpty()) {
            counts.add(0);
            counts.add(0);
            counts.add(0);
            counts.add(0);
            counts.add(0);
        }
        // reset the counts when the character passes the start of the cycle
        if (character.getX()==orderedPath.get(0).getValue0()&&character.getY()==orderedPath.get(0).getValue1()&&fightingEnemies.isEmpty())
        {
            cycleDefeatedEnemies.clear();
            for(int i=0; i<3; ++i) {
                counts.set(i, 0);
            }
        }
        spawningEnemies = new ArrayList<>();
        int cycle = item.getCycles();
        if (cycle == 20) {
            createDoggie();
        }
        if (cycle >= 40 && item.getExperience()>=10000) {
            createFinalBoss();
        }
        // create enemies based on which cycle it is
        if (cycle <= 1) {
            createSlugs(6);
        }
        else if (cycle <= 4) {
            createSlugs(10);
            createZombies(1);
        }
        else if (cycle <= 10) {
            createSlugs(12);
            createZombies(2);
        }
        else {
            createSlugs(12);
            createZombies(4);
        }
        if(cycle % 5==0 && cycle > 1)createVampires(1);

        System.out.println("Total amount of enemies in cycle "+cycle);
        System.out.println("Slug total amount: "+counts.get(0));
        System.out.println("Zombie total amount: "+counts.get(1));
        System.out.println("Vampire total amount: "+counts.get(2));
        System.out.println("Doggie total amount: "+counts.get(3));
        System.out.println("Elan Muske total amount: "+counts.get(4));
        return spawningEnemies;
    }

    /**
     * update if a boss is defeated
     */
    public void updateBossDefeated() {
        for (BasicEnemy e:cycleDefeatedEnemies) {
            if (e.getName()=="Doggie") {
                item.setDoggieCoin(e.getDoggieCoin());
                doggieDefeated = true;
            }
            else if (e.getName() == "FinalBoss") {
                finalBossDefeated = true;
            }
        }
    }

    /**
     * check if bosses required is deafeated
     * @return boolean
     */
    public boolean checkBossDefeated() {
        if (isDoggie && doggieDefeated && isFinalBoss && finalBossDefeated) {
            return true;
        }
        if (isDoggie && doggieDefeated && !isFinalBoss) {
            return true;
        }
        return false;
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    public void killEnemy(BasicEnemy enemy){
        enemy.destroy();
        // changed
        enemies.remove(enemy);
    }

    /**
     * check if the critical damage is applied
     * @param criticalChance
     * @return
     */
    public boolean ifCritical(double criticalChance) {
        Random random = new Random();
        if (random.nextInt(101)<=100*criticalChance) {
            System.out.println("critical attack!");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * check if the final boss is alive
     */
    public boolean checkFinalBossAlive() {
        if (this.item.getCycles()==40) {
            for (BasicEnemy e:spawningEnemies) {
                if (e.getName()=="FinalBoss") {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<BasicEnemy> runBattles() {
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        if (item.getCycles() >= 40 && !finalBossDefeated && !this.item.getDoubled()) {
            System.out.print("Price of doggie coin raises from " + this.item.getPriceOfDoggieCoin());
            this.item.setDoubled(true);
            this.item.setPriceOfDoggieCoin(2*this.item.getPriceOfDoggieCoin());
            System.out.println(" to "+this.item.getPriceOfDoggieCoin());
        }
        for (int j = 0; j < enemies.size(); ++j) {
            BasicEnemy e = enemies.get(j);
            if ((!fightingEnemies.contains(e)) && (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) <= e.getBattleRange())){
                fightingEnemies.add(e);
                for (BasicEnemy ally:enemies) {
                    if ((!fightingEnemies.contains(e)) && ( Math.pow((ally.getX()-e.getX()), 2) +  Math.pow((ally.getY()-e.getY()), 2) <= ally.getSupportRange() )) {
                        fightingEnemies.add(ally);
                    }
                }
                while (!fightingEnemies.isEmpty()) {
                    character.setInBattle(true);
                    for (int i=0; i<fightingEnemies.size(); ++i) {
                        BasicEnemy fighting = fightingEnemies.get(i);
                        if (countActive == false) {
                            activateWeaponAttribute(fighting);
                        }
                        int damageToEnemy = character.getDamage();
                        int damageFromEnemy = fighting.getDamage();
                        int damageFromSoldier = 0;
                        fighting.setInBattle(true);
                        if (fighting.getName()=="Vampire"&&ifCritical(fighting.getCriticalChance())) {
                            damageFromEnemy = fighting.getCriticalDamage();
                        }
                        if (fighting.getName()=="Doggie"&&ifCritical(fighting.getCriticalChance())) {
                            damageToEnemy = 0;
                        }
                        if (!character.getAlly().isEmpty()) {
                            Soldier soldier = character.getAlly().get(0);
                            damageFromSoldier = soldier.getDamage();
                        }
                        damageToEnemy += damageFromSoldier;
                        fighting.addHealth(-(damageToEnemy));
                        if (fighting.getHealth()<=0) {
                            System.out.println(e.getClass().getSimpleName()+" defeated");
                            damageFromEnemy = 0;
                            fightingEnemies.remove(fighting);
                            defeatedEnemies.add(fighting);
                            cycleDefeatedEnemies.add(fighting);
                            if (fighting.getName()=="Doggie"||fighting.getName()=="Vampire"||fighting.getName()=="FinalBoss") {
                                unactivatedWeaponAttribute(fighting);
                                if (fightingEnemies.size() != 0) {
                                    countActive = true;
                               }
                            }
                            killEnemy(fighting);
                        }
                        else {
                            if (checkFinalBossAlive()&&fighting.getName()!="FinalBoss") {
                                fighting.addHealth(5);
                            }
                        }
                        if (!checkFinalBossAlive()) {
                            this.item.setPriceOfDoggieCoin(this.item.getPriceOfDoggieCoin());
                        }
                        updateBossDefeated();
                        if (!character.getAlly().isEmpty()) {
                            Soldier soldier = character.getAlly().get(0);
                            if (fighting.getName()=="Zombie"&&ifCritical(fighting.getCriticalChance())) {
                                character.getAlly().remove(0);
                                System.out.println("Soldier turned to zombie!");
                                Zombie turnedZombie = new Zombie(character.getPathPosition());
                                fightingEnemies.add(turnedZombie);
                            } else {
                                soldier.fight(damageFromEnemy);
                                if (soldier.getDead()) {
                                    character.getAlly().remove(0);
                                }
                            }
                        }
                        character.fight(damageFromEnemy);
                    }
                }
                countActive = false;
            }
        }
        for (BasicEnemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            this.item.setGold(this.item.getGold()+e.getGold());
            System.out.println("Gold obtained: "+e.getGold());
            this.item.setExperience(this.item.getExperience()+e.getExperience());
            System.out.println("Experience obtained: "+e.getExperience());

            killEnemy(e);
        }
        character.setInBattle(false);
        return defeatedEnemies;
    }

    /**
     * active all the equipments in equippedInventoryItem which work 
     * at enemy
     * @param enemy
     */
    public void activateWeaponAttribute(BasicEnemy enemy) {
        for (Entity item:getEquippedInventoryItems()) {
            if (item != null) {
                String type = item.getClass().getSimpleName();   
                switch(type) {
                    case "Stake":
                        Stake stake = (Stake) item;
                        if (stake.checkVampire(enemy)) {
                            stake.addFeatureVampire(character);
                            countActive = true;
                        } 
                        break;
                    case "Staff":
                        Staff staff = (Staff) item;
                        staff.changeEnemy(enemy);
                        countActive = true;
                        break;
                    case "Armour":
                        Armour armour = (Armour) item;
                        armour.halfAttack(enemy);
                        countActive = true;
                        break;
                    case "Shield":
                        Shield shield = (Shield) item;
                        if (shield.checkVampire(enemy)) {
                            shield.reduceAttackChance(enemy);
                            countActive = true;
                        } 
                        break;
                    case "Helmet":
                        Helmet helmet = (Helmet) item;
                        helmet.reducedDamage(enemy);
                        countActive = true;
                        break;
                    case "Anduril":
                        Anduril anduril = (Anduril) item;
                        if (anduril.checkBoss(enemy)) {
                            anduril.addFeatureBoss(character);
                            countActive = true;

                            if (mode.equals(Mode.CONFUSING)) {
                                confusing.addBossFeature(character);
                            }    
                        }  
                        break;
                    case "TreeStump":
                        TreeStump treeStump = (TreeStump) item;
                        if (treeStump.checkBoss(enemy)) {
                            treeStump.addFeatureBoss(character);
                            countActive = true;
                            if (mode.equals(Mode.CONFUSING)) {
                                confusing.addBossFeature(character);
                            }    
                        }
                        break;
                    case "TheOneRing":
                        TreeStump rareItem = (TreeStump) item;
                        if (rareItem.checkBoss(enemy) && mode.equals(Mode.CONFUSING)) {
                            confusing.addBossFeature(character);
                        }
                        break;
                }
            }
        }
    }


    /**
     * unactivated all the equipments in equippedInventoryItem which work for 
     * doggie and ElanMuske and vampire after battle
     */
    public void unactivatedWeaponAttribute(BasicEnemy enemy) {
        String name = enemy.getClass().getSimpleName();

        for (Entity item:getEquippedInventoryItems()) {
            if (item != null) {
                String type = item.getClass().getSimpleName();
                switch(type) {
                    case "Stake":
                        Stake stake = (Stake) item;
                        if (name.equals("Vampire")) {
                            stake.unequippedVampire(character);
                            countActive = false;
                        }        
                        break;
                    case "Anduril":
                        Anduril anduril = (Anduril) item;
                        if (name.equals("Doggie") || name.equals("FinalBoss")) {
                            anduril.unequippedBoss(character);
                            countActive = false;
                            if (mode.equals(Mode.CONFUSING)) {
                                confusing.removeBossFeature(character);
                            }         
                        }
                        break;
                    case "TreeStump":
                        TreeStump treeStump = (TreeStump) item;
                        if (name.equals("Doggie") || name.equals("FinalBoss")) {
                            treeStump.unequippedBoss(character);
                            countActive = false;
                            if (mode.equals(Mode.CONFUSING)) {
                                confusing.removeBossFeature(character);
                            }    
                        }
                        break;
                    case "TheOneRing":
                        if (name.equals("Doggie") || name.equals("FinalBoss")) {
                            if (mode.equals(Mode.CONFUSING)) {
                                confusing.removeBossFeature(character);
                            }
                        }
                        break;
    
                }
            }
        }
    }

    
    /**
     * spawn a specific type of card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadCard(int cardNumber){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            item.setGold(item.getGold() + 5);
            item.setExperience(item.getExperience() + 10);
            removeCard(0);
        }

        switch(cardNumber){
            case 0:
                VampireCastleCard vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "VampireCastleCard");
                cardEntities.add(vampireCastleCard);
                return vampireCastleCard;
            case 1:
                ZombiePitCard zombiePitCard = new ZombiePitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "ZombiePitCard");
                cardEntities.add(zombiePitCard);
                return zombiePitCard;
            case 2:
                TowerCard towerCard = new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "TowerCard");
                cardEntities.add(towerCard);
                return towerCard;
            case 3:
                VillageCard villageCard = new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "VillageCard");
                cardEntities.add(villageCard);
                return villageCard;
            case 4:
                BarracksCard barracksCard = new BarracksCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "BarracksCard");
                cardEntities.add(barracksCard);
                return barracksCard;
            case 5:
                TrapCard trapCard = new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "TrapCard");
                cardEntities.add(trapCard);
                return trapCard;
            case 6:
                CampfireCard campfireCard = new CampfireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "CampfireCard");
                cardEntities.add(campfireCard);
                return campfireCard;
            case 7: 
                SuperTowerCard SuperTowerCard = new SuperTowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "SuperTowerCard");
                cardEntities.add(SuperTowerCard);
                return SuperTowerCard;
        }
        return null;

    }

    /**
     * get an rareItem from LoopManiaWorldLoader and set it to world
     * @param rareItem
     * @return boolean
     */
    public void setRareItem(ArrayList<String> rareItems){
        this.rareItems = rareItems;
    }

    public boolean checkRareItems(String name){
        boolean returnItem = false;
        int cycle = random.nextInt(40);
        if(rareItems != null){
            if (rareItems.contains(name) && name.equals("TheOneRing")){
                if(getItem().getCycles() == cycle){
                    returnItem = true;
                }
            }
            else if(rareItems.contains(name) && name.equals("Anduril")){
                if(getItem().getCycles() == cycle){
                    returnItem = true;
                }
            }
            else if(rareItems.contains(name) && name.equals("TreeStump")){
                if(getItem().getCycles() == cycle){
                    returnItem = true;
                }
            } else {
                System.err.println("error: invalid rare item");
            }
        }
        return returnItem;
    }

    /**
     * random load card function
     * @return Card
     */
    public Card loadCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            item.setGold(item.getGold() + 5);
            item.setExperience(item.getExperience() + 10);
            removeCard(0);
        }
        int random_loadcard = random.nextInt(8);

        switch(random_loadcard){
            case 0:
                VampireCastleCard vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "VampireCastleCard");
                cardEntities.add(vampireCastleCard);
                return vampireCastleCard;
            case 1:
                ZombiePitCard zombiePitCard = new ZombiePitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "ZombiePitCard");
                cardEntities.add(zombiePitCard);
                return zombiePitCard;
            case 2:
                TowerCard towerCard = new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "TowerCard");
                cardEntities.add(towerCard);
                return towerCard;
            case 3:
                VillageCard villageCard = new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "VillageCard");
                cardEntities.add(villageCard);
                return villageCard;
            case 4:
                BarracksCard barracksCard = new BarracksCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "BarracksCard");
                cardEntities.add(barracksCard);
                return barracksCard;
            case 5:
                TrapCard trapCard = new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "TrapCard");
                cardEntities.add(trapCard);
                return trapCard;
            case 6:
                CampfireCard campfireCard = new CampfireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "CampfireCard");
                cardEntities.add(campfireCard);
                return campfireCard;
            case 7: 
                SuperTowerCard superTowerCard = new SuperTowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "SuperTowerCard");
                cardEntities.add(superTowerCard);
                return superTowerCard;
        }
        return null;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * spawn a specific type of equipment in the world and return the sword entity
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    public Entity spawnUnequippedSwordOrHealthPotion(int index){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            item.setGold(item.getGold() + 5);
            item.setExperience(item.getExperience() + 10);
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        int x = 0;
        int y = 0;
        while(true){
            x = random.nextInt(8);
            y = random.nextInt(8);
            if( map.CheckPath(x, y) == true){
                break;
            }
        }
        switch(index){
            case 0:
                Gold gold = new Gold(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                gold.setGold(20);
                return gold;
            case 1:
                HealthPotion healthpotion = new HealthPotion(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                unequippedInventoryItems.add(healthpotion);
                return healthpotion;
            default:
                System.err.println("Invalid to generate item in world");
        }
        return null;
    }
    
    /**
     * spawn a equipment in the world and return the sword entity
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    public Entity spawnUnequippedSwordOrHealthPotion(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            item.setGold(item.getGold() + 5);
            item.setExperience(item.getExperience() + 10);
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        int x = 0;
        int y = 0;
        int z = 0;
        while(true){
            x = random.nextInt(8);
            y = random.nextInt(8);
            z = random.nextInt(2);
            if( map.CheckPath(x, y) == true){
                break;
            }
        }
        switch(z){
            case 0:
                Sword sword = new Sword(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                unequippedInventoryItems.add(sword);
                return sword;
            case 1:
                HealthPotion healthpotion = new HealthPotion(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                unequippedInventoryItems.add(healthpotion);
                return healthpotion;
        }
        return null;
    }


    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        System.out.println("remove "+item.getClass().getSimpleName()+" from unequipped inventory");
        removeUnequippedInventoryItem(item);
    }

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        character.moveDownPath();
        moveBasicEnemies();
        herosCastle.setDisplayShop(displayShop());
        updateDoggieCoinPrice();
        if (character.getX() == 1 && character.getY() == 2){
            getItem().setCycles(getItem().getCycles() + 1);
        }
    }

    /**
     * update doggie coin price every tick move
     */
    public void updateDoggieCoinPrice() {
        item.setPriceOfDoggieCoin(random.nextInt(100));
    }

    /**
     * add an item from the unequipped inventory
     * @param item item to be removed
     */
    public Entity addUnequippedInventoryItem(Entity item){
        if (!herosCastle.getCharacterInCastle() && !droppedPotion) {
            System.out.println(item.getClass().getSimpleName() + " dropped");
        }
        droppedPotion = false;
        unequippedInventoryItems.add(item);
        return item;
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * add an item from the unequipped inventory
     * @param item item to be removed
     */
    public void addEquippedInventoryItem(Entity item){
        checkEquippedInventoryItem(item);
        System.out.println(item.getClass().getSimpleName()+" Equipped");
        removeUnequippedInventoryItem(item);  
    }

    /**
     * identity the item's type and place to the related index in equipped Inventory
     * if there is item already, then remove it
     * @param item
     */
    private void checkEquippedInventoryItem(Entity item){

        String countRareItem = "";
        for (Entity equipment :getEquippedInventoryItems()) {
            if (equipment != null) {
                String equipmenttype = equipment.getClass().getSimpleName();
                if (equipmenttype.equals("Anduril") || equipmenttype.equals("TreeStump") || equipmenttype.equals("TheOneRing")) {
                    countRareItem = equipmenttype;
                }
            }
        }

        String type = item.getClass().getSimpleName();

        if (type.equals("Anduril") || type.equals("TreeStump") || type.equals("TheOneRing")){
            if (countRareItem.equals("Anduril")) {
                Entity oldItem = equippedInventoryItems.get(0);
                removeEquippedInventoryItem(oldItem);
            } else if (countRareItem.equals("TreeStump")) {
                Entity oldItem = equippedInventoryItems.get(2);
                removeEquippedInventoryItem(oldItem);
            } else if (countRareItem.equals("TheOneRing")) {
                Entity oldItem = equippedInventoryItems.get(4);
                removeEquippedInventoryItem(oldItem);
            }
        }
        
        if (type.equals("Sword") || type.equals("Staff") || type.equals("Stake") || type.equals("Anduril")) {
            if (equippedInventoryItems.get(0) != null) {
                Entity oldItem = equippedInventoryItems.get(0);
                removeEquippedInventoryItem(oldItem);
            }   
            equippedInventoryItems.set(0, item);
            if (type.equals("Sword")){
                Sword sword = (Sword) item;
                sword.addFeature(character);
            } else if (type.equals("Staff")) {
                Staff staff = (Staff) item;
                staff.addFeature(character);
            } else if (type.equals("Stake")) {
                Stake stake = (Stake) item;
                stake.addFeature(character);
            } else if (type.equals("Anduril")) {
                Anduril anduril = (Anduril) item;
                anduril.addFeature(character);
                if (mode.equals(Mode.CONFUSING)) {
                    confusing = new ConfusingMode(type);
                    confusing.addFeature(character);
                }
            }
        } else if (type.equals("Armour")) {
            if (equippedInventoryItems.get(1) != null) {
                Entity oldItem = equippedInventoryItems.get(1);
                removeEquippedInventoryItem(oldItem);
            } 
            equippedInventoryItems.set(1, item);
            Armour armour = (Armour) item;
            armour.addFeature(character);  
        } else if (type.equals("Shield") || type.equals("TreeStump")) {
            if (equippedInventoryItems.get(2) != null) {
                Entity oldItem = equippedInventoryItems.get(2);
                removeEquippedInventoryItem(oldItem);
            } 
            equippedInventoryItems.set(2, item);
            if (type.equals("Shield")){
                Shield shield = (Shield) item;
                shield.addFeature(character);
            } else if (type.equals("TreeStump")) {
                TreeStump treeStump = (TreeStump) item;
                treeStump.addFeature(character);
                if (mode.equals(Mode.CONFUSING)) {
                    confusing = new ConfusingMode(type);
                    confusing.addFeature(character);
                }
            }
        } else if (type.equals("Helmet")) {
            if (equippedInventoryItems.get(3) != null) {
                Entity oldItem = equippedInventoryItems.get(3);
                removeEquippedInventoryItem(oldItem);
            }           
            equippedInventoryItems.set(3, item);
            Helmet helmet = (Helmet) item;
            helmet.addFeature(character);           
        } else if (type.equals("TheOneRing")) {
            if (equippedInventoryItems.get(4) != null) {
                Entity oldItem = equippedInventoryItems.get(4);
                removeEquippedInventoryItem(oldItem);
            }           
            equippedInventoryItems.set(4, item);
            TheOneRing theOneRing = (TheOneRing) item;
            theOneRing.makeLive(character);
            if (mode.equals(Mode.CONFUSING)) {
                confusing = new ConfusingMode(type);
                confusing.addFeature(character);
            }
        }
    }

    /**
     * remove an item from the equipped inventory
     * and subtracts the attribute value that this item gives the character
     * @param item item to be removed
     */
    public void removeEquippedInventoryItem(Entity item){
        System.out.println(item.getClass().getSimpleName()+" unequipped");
        item.destroy();
        String type = item.getClass().getSimpleName();
        int index = 0;
            
        switch(type) {
            case "Sword":
                Sword sword = (Sword) item;
                sword.unequipped(character);
                break;
            case "Staff":
                Staff staff = (Staff) item;
                staff.unequipped(character);
                break;
            case "Stake":
                Stake stake = (Stake) item;
                stake.unequipped(character);
                break;
            case "Armour":
                Armour armour = (Armour) item;
                armour.unequipped(character);
                index = 1;
                break;
            case "Shield":
                Shield shield = (Shield) item;
                shield.unequipped(character);
                index = 2;
                break;
            case "Helmet":
                Helmet helmet = (Helmet) item;
                helmet.unequipped(character);
                index = 3;
                break;
            case "TheOneRing":
                TheOneRing ring = (TheOneRing) item;
                ring.unequipped(character);
                index = 4;
                if (mode.equals(Mode.CONFUSING)) {
                    confusing.removeFeature(character);
                }
                break;
            case "Anduril":
                Anduril anduril = (Anduril) item;
                anduril.unequipped(character);
                if (mode.equals(Mode.CONFUSING)) {
                    confusing.removeFeature(character);
                }
                break;
            case "TreeStump":
                TreeStump treeStump = (TreeStump) item;
                treeStump.unequipped(character);
                index = 2;
                if (mode.equals(Mode.CONFUSING)) {
                    confusing.removeFeature(character);
                }
                break;
            case "HealthPotion":
                // HealthPotion healthPotion = (HealthPotion) item;
                index = 5;
                break;
        }
        equippedInventoryItems.set(index, null);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    public void removeItemByPositionInUnequippedInventoryItems(int index){
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

     /**
     * remove item at a particular index in the equipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    public void removeItemByPositionInequippedInventoryItems(int index){
        Entity item = equippedInventoryItems.get(index);
        item.destroy();
        equippedInventoryItems.remove(index);
    }


    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    public Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        for (BasicEnemy e: enemies){
            checkPosition(e);
            if (fightingEnemies.isEmpty()) {
                e.move();
            }
            
            if (e.getName().equals("Vampire")) {
                Pair<Integer,Integer> pos = e.getPosition();
                List<Pair<Integer,Integer>> range = map.getRange(pos, 2);
                List<Pair<Integer,Integer>> campFireLocations = new ArrayList<>();
                for (Entity building:buildingEntities) {
                    if (((Building) building).getType().equals("Campfire")) {
                        campFireLocations.add(((Building) building).getPosition());
                    }
                }
                for (Pair<Integer,Integer> cell:range) {
                    if (campFireLocations.contains(cell)) {
                        if (e.getDirection()==1) {
                            e.moveUpPath();
                            e.moveUpPath();
                        }
                        else {
                            e.moveDownPath();
                            e.moveDownPath();
                        }
                    }
                }
            }
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an equipment
     * @return a random coordinate pair
     */
    public Pair<Integer,Integer> possiblyGetEquipmentSpawnPosition() {
        while(true){
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (x >= 8) x = x-2;
            if (y >= 8) y = y-2;
            if (map.CheckPath(x, y) == true) {
                Pair<Integer,Integer> pos = new Pair<Integer,Integer>(x,y);
                return pos;
            }
        }
    }
    
    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return  a random coordinate pair if should go ahead
     */
    public Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition(){

        int coordinate_x = 0;
        int coordinate_y = 0;
        
        while (true) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (x >= 8) {x = x-2;}
            if (y >= 8) {y = y-2;}
            if(map.CheckPath(x, y) == true){
                if(buildingEntities.size() == 0) {
                    Pair<Integer, Integer> findPosition = new Pair<Integer,Integer>(x, y);
                    return findPosition;
                }
                for(Entity building:buildingEntities){
                    if(((Building) building).getType().equals("Campfire")){
                        int i = building.getX();
                        int j = building.getY();
                        Pair<Integer,Integer> Campfire_location = new Pair<Integer,Integer>(i, j);
                        for(Entity building2:buildingEntities){
                            if(((Building) building2).getType().equals("VampireCastle")){
                                Pair<Integer,Integer> newCell = new Pair<Integer,Integer>(x, y);
                                List<Pair<Integer,Integer>> range = map.getRange(Campfire_location, 2);
                                
                                if(range.contains(newCell)){
                                    continue;
                                }else{
                                    coordinate_x = x;
                                    coordinate_y = y;
                                    break;
                                }
                            } else{
                                coordinate_x = x;
                                coordinate_y = y;
                            }
                        }
                    } else{
                        coordinate_x = x;
                        coordinate_y = y;
                    }
                }
            }
            Pair<Integer, Integer> findPosition = new Pair<Integer,Integer>(coordinate_x, coordinate_y);
            return findPosition;
        }
        
    }

    /**
     * get a position which could be used to spawn a Vampire from VampireCastle or Zombie from ZombiePit
     * @param type
     * @return  a random coordinate pair if should go ahead
     */
    public Pair<Integer, Integer> possiblyGetZombieOrVampirePosition(String type){
        int coordinate_x = 10;
        int coordinate_y = 10;
        for(Entity building:buildingEntities){
            if(((Building) building).getType().equals(type)){
                int i = building.getX();
                int j = building.getY();
                if (j == 0 && (i < 7 || i > 0)) {
                    coordinate_x = i;
                    coordinate_y = j+1;
                } else if (j == 2 && (i < 6 || i > 1)) {
                    coordinate_x = i;
                    coordinate_y = j-1;
                } else if (j == 5 && (i < 6 || i > 1)) {
                    coordinate_x = i;
                    coordinate_y = j+1;
                } else if (j == 7 && (i < 7 || i > 0)) {
                    coordinate_x = i;
                    coordinate_y = j-1;
                } else if (i == 0 && (j < 7 || j > 0)) {
                    coordinate_x = i+1;
                    coordinate_y = j;
                } else if (i == 2 && (j < 6 || j > 1)) {
                    coordinate_x = i-1;
                    coordinate_y = j;
                } else if (i == 5 && (j < 6 || j > 1)) {
                    coordinate_x = i+1;
                    coordinate_y = j;
                } else if (i == 7 && (j < 7 || j > 0)) {
                    coordinate_x = i-1;
                    coordinate_y = j;
                }
                break;
            }
        }
        Pair<Integer, Integer> findPosition = new Pair<Integer,Integer>(coordinate_x, coordinate_y);
        return findPosition;
    }

    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Entity convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c: cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }
        Building newBuilding = null;
        BuildingPathCheck buildingPathCheck = new BuildingPathCheck();
        switch (card.type) {
            case "VampireCastleCard":
                if (buildingPathCheck.VampireCastle(buildingNodeX, buildingNodeY) == true) {
                    newBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), "VampireCastle");
                    buildingEntities.add(newBuilding);
                    break;
                } else {
                    System.out.println("Invalid position to put VampireCastleCard");
                    return null;
                }
                
            case "ZombiePitCard":
                if (buildingPathCheck.ZombiePit(buildingNodeX, buildingNodeY) == true) {
                    newBuilding = new ZombiePit(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), "ZombiePit");
                    buildingEntities.add(newBuilding);
                    break;
                } else {
                    System.out.println("Invalid position to put ZombiePitCard");
                    return null;
                }
            
            case "TowerCard":
                if(buildingPathCheck.Tower(buildingNodeX, buildingNodeY) == true){
                    newBuilding = new Tower(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY),"Tower");
                    buildingEntities.add(newBuilding);
                    break;
                } else{
                    System.out.println("Invalid position to put TowerCard");
                    return null;
                }
            
            case "VillageCard":
                if (buildingPathCheck.Village(buildingNodeX, buildingNodeY) == true) {
                    newBuilding = new Village(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), "Village");
                    buildingEntities.add(newBuilding);
                    break;
                } else {
                    System.out.println("Invalid position to put VillageCard");
                    return null;
                }
            
            case "BarracksCard":
                if (buildingPathCheck.Barracks(buildingNodeX, buildingNodeY) == true) {
                    newBuilding = new Barracks(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), "Barracks");
                    buildingEntities.add(newBuilding);
                    break;
                } else {
                    System.out.println("Invalid position to put BarracksCard");
                    return null;
                }
            
            case "TrapCard":
                if (buildingPathCheck.Trap(buildingNodeX, buildingNodeY) == true) {
                    newBuilding = new Trap(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), "Trap");
                    buildingEntities.add(newBuilding);
                    break;
                } else {
                    System.out.println("Invalid position to put TrapCard");
                    return null;
                }
            
            case "CampfireCard":
                if (buildingPathCheck.Campfire(buildingNodeX, buildingNodeY) == true) {
                    newBuilding = new Campfire(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), "Campfire");
                    buildingEntities.add(newBuilding);
                    break;
                } else {
                    System.out.println("Invalid position to put CampfireCard");
                    return null;
                }
            case "SuperTowerCard":
                if (buildingPathCheck.AF(buildingNodeX, buildingNodeY) == true) {
                    newBuilding = new SuperTower(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), "SuperTower");
                    buildingEntities.add(newBuilding);
                    break;
                } else{
                    return null;
                }
            default:
                System.err.println("Invalid position");
        }
        
        // destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);
        return newBuilding;
    }

    /**
     * The set random function
     * @param seed
     */
    public void setRandom (int seed) {
        random = new Random(seed);
    }

    /**
     * remove the building from given x,y, if this place have an building entity
     * @param seed
     */
    public void removeBuilding (Entity b) {
        getBuildingEntity().remove(b);
        b.destroy();
    }
}