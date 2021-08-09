package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
//import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
//import unsw.loopmania.Slug;
import unsw.loopmania.*;
import unsw.loopmania.Character;



/**
 * The test of worldstate
 */
public class LoopManiaWorldTest {
    
    public List<Pair<Integer,Integer>> createOrderedPath() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        for (int cell=1; cell<=6; ++cell) {
            Pair<Integer,Integer> road= new Pair<>(1, cell);
            orderedPath.add(road);   
        }
        for (int cell=2; cell<=6; ++cell) {
            Pair<Integer,Integer> road= new Pair<>(cell, 6);
            orderedPath.add(road);   
        }
        for (int cell=5; cell>=1; --cell) {
            Pair<Integer,Integer> road= new Pair<>(6, cell);
            orderedPath.add(road);   
        }
        for (int cell=5; cell>=2; --cell) {
            Pair<Integer,Integer> road= new Pair<>(cell, 1);
            orderedPath.add(road);   
        }
        return orderedPath;
    }

    public LoopManiaWorld new_world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(1, 1), new Pair<>(1, 2)));
    public LoopManiaWorld world = new LoopManiaWorld(8,8,createOrderedPath());
    public PathPosition character_position = new PathPosition(0, Arrays.asList(new Pair<>(1, 1), new Pair<>(1, 2)));
    public PathPosition character_position2 = new PathPosition(0, Arrays.asList(new Pair<>(5, 1)));
    public PathPosition character_position3 = new PathPosition(0, Arrays.asList(new Pair<>(6, 2)));


    private List<Entity> test_unequippedInventoryItem;
    private List<Entity> test_equippedInventoryItem;
    
    @Test
   /**
    *  Test for set a character
    */
    public void TestSetCharacter(){
        Character Link = new Character(character_position);
        new_world.setCharacter(Link);

        assertEquals(1,Link.getX());
    }

    @Test
    /**
     * Test for set gamestate
     */
    public void TestSetGamestate(){
        new_world.setGameState(false);
        assertEquals(false, new_world.getGameState());
    }
    
    /**
     * Test for convert card to building
     */
    
     @Test
     public void TestconvertCardToBuildingByCoordinates(){
        new_world.loadCard(0);
        new_world.loadCard(1);
        new_world.loadCard(2);
        new_world.loadCard(3);
        new_world.loadCard(4);
        new_world.loadCard(5);
        new_world.loadCard(6);
        new_world.loadCard(0);

        assertEquals("VampireCastleCard",new_world.getCardEntities().get(0).getType());

        assertEquals(null, new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),1,5));
        Entity newBuilding = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),0,5);
        assertEquals(0, newBuilding.getX());
        assertEquals(5, newBuilding.getY());
        assertEquals("unsw.loopmania.VampireCastleBuilding", newBuilding.getClass().getName());

        assertEquals(null, new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),1,5));
        Entity newBuilding2 = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),2,5);
        assertEquals(2, newBuilding2.getX());
        assertEquals(5, newBuilding2.getY());
        assertEquals("TowerCard", new_world.getCardEntities().get(0).getType());
        assertEquals("unsw.loopmania.ZombiePit", newBuilding2.getClass().getName());

        assertEquals(null, new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),1,5));
        Entity newBuilding3 = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),0,4);
        assertEquals(0, newBuilding3.getX());
        assertEquals(4, newBuilding3.getY());
        assertEquals("VillageCard", new_world.getCardEntities().get(0).getType());
        assertEquals("unsw.loopmania.Tower", newBuilding3.getClass().getName());

        assertEquals(null, new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),0,5));
        Entity newBuilding4 = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),1,4);
        assertEquals(1, newBuilding4.getX());
        assertEquals(4, newBuilding4.getY());
        assertEquals("BarracksCard", new_world.getCardEntities().get(0).getType());
        assertEquals("unsw.loopmania.Village", newBuilding4.getClass().getName());

        assertEquals(null, new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),0,5));
        Entity newBuilding5 = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),1,4);
        assertEquals(1, newBuilding5.getX());
        assertEquals(4, newBuilding5.getY());
        assertEquals("TrapCard", new_world.getCardEntities().get(0).getType());
        assertEquals("unsw.loopmania.Barracks", newBuilding5.getClass().getName());

        assertEquals(null, new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),0,5));
        Entity newBuilding6 = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),1,2);
        assertEquals(1, newBuilding6.getX());
        assertEquals(2, newBuilding6.getY());
        assertEquals("CampfireCard", new_world.getCardEntities().get(0).getType());
        assertEquals("unsw.loopmania.Trap", newBuilding6.getClass().getName());

        Entity newBuilding7 = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),0,2);
        assertEquals(0, newBuilding7.getX());
        assertEquals(2, newBuilding7.getY());
        assertEquals("unsw.loopmania.Campfire", newBuilding7.getClass().getName());

        Entity newBuilding8 = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),0,2);
        assertEquals("unsw.loopmania.VampireCastleBuilding", newBuilding8.getClass().getName());

        new_world.loadCard(7);
        assertEquals(null, new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),1,5));
        Entity newBuilding9 = new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),0,2);
        assertEquals(0, newBuilding9.getX());
        assertEquals(2, newBuilding9.getY());
        assertEquals("unsw.loopmania.SuperTower", newBuilding9.getClass().getName());
     }
    
    /**
     * Test for add UnequippedSword
     */
    @Test
    public void TestSpawnUnequippedSword(){
        Entity item = new_world.spawnUnequippedSwordOrHealthPotion(1);
        List<Entity> unequippedInventoryItems = new_world.getUnequippedInventoryItems();
        
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == item.getX()) && (e.getY() == item.getY())){
                assertTrue(true);
            }
            
        }
    }

    /**
     * Test for Display HerosCastle
     */
    @Test
    public void TestHeorsCastles(){

        new_world.getItem().setCycles(1);
        System.out.println(new_world.getCharacter().getX());
        System.out.println(new_world.getCharacter().getY());
        assertEquals(true, new_world.displayShop());

        new_world.getItem().setCycles(3);
        assertEquals(true, new_world.displayShop());

        new_world.getItem().setCycles(6);
        assertEquals(true, new_world.displayShop());

        new_world.getItem().setCycles(10);
        assertEquals(true, new_world.displayShop());

        new_world.getItem().setCycles(15);
        assertEquals(true, new_world.displayShop());

        new_world.getItem().setCycles(9);
        assertEquals(false, new_world.displayShop());

    }

    /**
     * Test for CheckTheCoordinateHaveItems, if it has an item, return true
     */
    @Test
    public void TestCheckTheCoordinateHaveItems(){
        assertEquals(false, new_world.checkTheCoordinateHaveItems(0, 0));

        new_world.loadCard();
        assertEquals(true, new_world.checkTheCoordinateHaveItems(0, 0));

        new_world.spawnUnequippedSwordOrHealthPotion(1);
        assertEquals(true, new_world.checkTheCoordinateHaveItems(new_world.getUnequippedInventoryItems().get(0).getX(), new_world.getUnequippedInventoryItems().get(0).getY()));

        assertEquals(false, new_world.checkTheCoordinateHaveItems(6,3));

    }

    /**
     * Test for addEntity
     */
    @Test
    public void TestAddEntity(){
        Sword sword = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));

        new_world.addEntity(sword);
        assertEquals(sword, new_world.getNonSpecifiedEntities().get(0));
    }

    /**
     * The test for add unequippedInventoryItem
     */
    @Test
    public void TestAddUnequippedInventoryItem(){
        
        Sword sword = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        new_world.addUnequippedInventoryItem(sword);
        test_unequippedInventoryItem = new ArrayList<Entity>();
        test_unequippedInventoryItem = new_world.getUnequippedInventoryItems();        

        assertEquals(sword, test_unequippedInventoryItem.get(0));
    }

    /**
     * The test for remove unequippedInventoryItem
     */
    @Test
    public void TestRemoveUnequippedInventoryItem(){
        Sword sword = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        HealthPotion hp = new HealthPotion(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3));
        new_world.addUnequippedInventoryItem(sword);
        new_world.addUnequippedInventoryItem(hp);
        test_unequippedInventoryItem = new ArrayList<Entity>();
        test_unequippedInventoryItem = new_world.getUnequippedInventoryItems();

        assertEquals(sword, test_unequippedInventoryItem.get(0));
        assertEquals(hp, test_unequippedInventoryItem.get(1));

        new_world.removeUnequippedInventoryItem(sword);
        assertEquals(hp, test_unequippedInventoryItem.get(0));
        
    }

    /**
     * The test for generate a random card
     */
    @Test
    public void testRandomLoadCard() {
        new_world.setRandom(7);
        List<Card> cardEntities = new_world.getCardEntities();
        VampireCastleCard vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "VampireCastleCard");
        //ZombiePitCard zombiePitCard = new ZombiePitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "ZombiePitCard");
        TowerCard towerCard = new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "TowerCard");
        VillageCard villageCard = new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "VillageCard");
        BarracksCard barracksCard = new BarracksCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "BarracksCard");
        TrapCard trapCard = new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "TrapCard");
        //CampfireCard campfireCard = new CampfireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "CampfireCard");
        SuperTowerCard superTowerCard = new SuperTowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "SuperTowerCard");

        assertEquals(trapCard.getType(), new_world.loadCard().getType());
        assertEquals(trapCard.getType(), new_world.loadCard().getType());
        assertEquals(trapCard.getType(), new_world.loadCard().getType());
        assertEquals(vampireCastleCard.getType(), new_world.loadCard().getType());
        assertEquals(towerCard.getType(), new_world.loadCard().getType());
        assertEquals(villageCard.getType(), new_world.loadCard().getType());
        assertEquals(superTowerCard.getType(), new_world.loadCard().getType());
        assertEquals(trapCard.getType(), new_world.loadCard().getType());
        assertEquals(trapCard.getType(), new_world.loadCard().getType());
        assertEquals(barracksCard.getType(), new_world.loadCard().getType());
        assertEquals(towerCard.getType(), new_world.loadCard().getType());
        
        assertEquals(15, new_world.getItem().getGold());
        assertEquals(30, new_world.getItem().getExperience());
        assertEquals(vampireCastleCard.getType(), new_world.getCardEntities().get(0).getType());
    }

    /**
     * test for load specific card
     */
    @Test
    public void testLoadCard(){
        List<Card> cardEntities = new_world.getCardEntities();

        VampireCastleCard vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "VampireCastleCard");
        ZombiePitCard zombiePitCard = new ZombiePitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "ZombiePitCard");
        TowerCard towerCard = new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "TowerCard");
        VillageCard villageCard = new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "VillageCard");
        BarracksCard barracksCard = new BarracksCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "BarracksCard");
        TrapCard trapCard = new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "TrapCard");
        CampfireCard campfireCard = new CampfireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "CampfireCard");
        SuperTowerCard superTowerCard = new SuperTowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), "SuperTowerCard");
        
        assertEquals(zombiePitCard.getType(), new_world.loadCard(1).getType());
        assertEquals(villageCard.getType(), new_world.loadCard(3).getType());
        assertEquals(trapCard.getType(), new_world.loadCard(5).getType());
        assertEquals(barracksCard.getType(), new_world.loadCard(4).getType());
        assertEquals(towerCard.getType(), new_world.loadCard(2).getType());
        assertEquals(vampireCastleCard.getType(), new_world.loadCard(0).getType());
        assertEquals(campfireCard.getType(), new_world.loadCard(6).getType());

        assertEquals(zombiePitCard.getType(), new_world.loadCard(1).getType());
        assertEquals(villageCard.getType(), new_world.loadCard(3).getType());
        assertEquals(trapCard.getType(), new_world.loadCard(5).getType());
        assertEquals(barracksCard.getType(), new_world.loadCard(4).getType());
        assertEquals(towerCard.getType(), new_world.loadCard(2).getType());
        assertEquals(vampireCastleCard.getType(), new_world.loadCard(0).getType());
        assertEquals(campfireCard.getType(), new_world.loadCard(6).getType());

        assertEquals(30, new_world.getItem().getGold());
        assertEquals(60, new_world.getItem().getExperience());
        assertEquals(campfireCard.getType(), new_world.getCardEntities().get(0).getType());

        assertEquals(superTowerCard.getType(), new_world.loadCard(7).getType());
        assertEquals(null, new_world.loadCard(8));
    }

    /**
     * Test for add an item form unequiped list to equipped list
     */
    @Test
    public void testAddEquippedInventoryItem(){
        Sword sword = new Sword(new SimpleIntegerProperty(-1), new SimpleIntegerProperty(-1));
        new_world.addUnequippedInventoryItem(sword);
        assertEquals(sword, new_world.getUnequippedInventoryItems().get(0));
        new_world.addEquippedInventoryItem(sword);

        assertEquals(sword, new_world.getEquippedInventoryItems().get(0));
    }

    /**
     * Test for spawn an unequipped wword or healthPotion in LoopManiaWorld
     */
    @Test
    public void testSpawnunequippedSwordOrHealthPotion(){
        new_world.setRandom(1);
        Entity item = new_world.spawnUnequippedSwordOrHealthPotion();
        Entity item2 = new_world.spawnUnequippedSwordOrHealthPotion();
        Entity item3 = new_world.spawnUnequippedSwordOrHealthPotion();
        Entity item4 = new_world.spawnUnequippedSwordOrHealthPotion();
        Entity item5 = new_world.spawnUnequippedSwordOrHealthPotion();
        Entity item6 = new_world.spawnUnequippedSwordOrHealthPotion();

        assertEquals("unsw.loopmania.HealthPotion",item.getClass().getName());
        assertEquals("unsw.loopmania.Sword",item2.getClass().getName());
        assertEquals("unsw.loopmania.Sword",item3.getClass().getName());
        assertEquals("unsw.loopmania.HealthPotion",item4.getClass().getName());
        assertEquals("unsw.loopmania.Sword",item5.getClass().getName());
        assertEquals("unsw.loopmania.HealthPotion",item6.getClass().getName());
        assertEquals(0, new_world.getItem().getGold());
        assertEquals(0, new_world.getItem().getExperience());
    }

    /**
     * Test for spawn a specific unequipped wword or healthPotion in LoopManiaWorld
     */
    @Test
    public void testSpawnSpecificunequippedSwordOrHealthPotion(){
        new_world.setRandom(1);
        Entity item = new_world.spawnUnequippedSwordOrHealthPotion(1);
        Entity item2 = new_world.spawnUnequippedSwordOrHealthPotion(0);
        Entity item3 = new_world.spawnUnequippedSwordOrHealthPotion(2);
        assertEquals(null, item3);
        assertEquals("unsw.loopmania.HealthPotion",item.getClass().getName());
        assertEquals("unsw.loopmania.Gold",item2.getClass().getName());

    }

    /**
     * test for spawn an position to place enemy
     */
    @Test
    public void testPossiblyGetBasicEnemySpawnPosition(){
        new_world.setRandom(5);
        
        Pair<Integer,Integer> Cell = new Pair<Integer, Integer>(6, 1);
        assertEquals(Cell, new_world.possiblyGetBasicEnemySpawnPosition());

        Pair<Integer,Integer> Cell2 = new Pair<Integer, Integer>(6, 5);
        assertEquals(Cell2, new_world.possiblyGetBasicEnemySpawnPosition());

        Pair<Integer,Integer> Cell3 = new Pair<Integer, Integer>(3, 6);
        assertEquals(Cell3, new_world.possiblyGetBasicEnemySpawnPosition());

        Pair<Integer,Integer> Cell4 = new Pair<Integer, Integer>(4, 6);
        assertEquals(Cell4, new_world.possiblyGetBasicEnemySpawnPosition());

        //Pair<Integer,Integer> Cell5 = new Pair<Integer, Integer>(6, 3);
        //assertEquals(Cell5, new_world.possiblyGetBasicEnemySpawnPosition());

        new_world.loadCard(0);
        new_world.loadCard(6);
        new_world.convertCardToBuildingByCoordinates(0, 0, 0, 2);
        new_world.convertCardToBuildingByCoordinates(0, 0, 7, 3);
        Pair<Integer,Integer> Cell5 = new Pair<Integer, Integer>(6, 3);
        assertEquals(Cell5, new_world.possiblyGetBasicEnemySpawnPosition());

    }
    
    /**
     * test for spawn a basic enemy
     */
       @Test
        public void testSpawnBasicEnemy(){
            new_world.setRandom(5);
            new_world.possiblySpawnEnemies();
            PathPosition slugPosition = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
            Slug slug = new Slug(slugPosition);
            assertEquals(slug.getName(), new_world.getBasicEnemies().get(0).getName());
            assertEquals(6, new_world.getBasicEnemies().size());

            //To do 
            LoopManiaWorld new_world2 = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(1, 1), new Pair<>(1, 2)));
            new_world2.setRandom(5);
            new_world2.getItem().setCycles(13);
            new_world2.possiblySpawnEnemies();
            assertEquals(12, new_world2.getBasicEnemies().size());

       }

       /**
        * Test for kill enemy
        */
        @Test
        public void testKillEnemy(){
            PathPosition slugPosition = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
            Slug slug = new Slug(slugPosition);
            new_world.getBasicEnemies().add(slug);
            assertEquals(1, new_world.getBasicEnemies().size());

            new_world.killEnemy(slug);
            assertEquals(0, new_world.getBasicEnemies().size());
       }

       /**
        * Test for run Battle
        */
        @Test
        public void testRunBattle(){
            PathPosition slugPosition = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
            Slug slug = new Slug(slugPosition);
            new_world.getBasicEnemies().add(slug);
            assertEquals(1, 1);
            assertEquals(slug.getName(), new_world.runBattles().get(0).getName());
       }

       /**
        * Test for removeUnequippedInventoryItemByCoordinates
        */
       @Test
       public void TestRemoveUnequippedInventoryItemByCoordinates(){
            Staff staff = new Staff(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            new_world.addUnequippedInventoryItem(staff);
            assertEquals(staff, new_world.getUnequippedInventoryItems().get(0));
            assertEquals(1,new_world.getUnequippedInventoryItems().size());
            new_world.removeUnequippedInventoryItemByCoordinates(1, 1);
            assertEquals(0,new_world.getUnequippedInventoryItems().size());
       }

       /**
        * Test for spawn an equipment in world
        */
       @Test
        public void TestPossiblySpawnEquipments(){
            // Equipment equipmentsList;
            // equipmentsList = new_world.possiblySpawnEquipments();
            // assertEquals(equipmentsList.getName(),);

            // List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
            // orderedPath = createOrderedPath();
            // LoopManiaWorld new_world2 = new LoopManiaWorld(8, 8, orderedPath);
            // To do 
            // new_world2.getItem().setCycles(2);
            // equipmentsList = new_world2.possiblySpawnEquipments();
            // assertEquals(1, equipmentsList.size());
            // assertEquals("HealthPotion", equipmentsList.get(0).getName());

            // new_world.getItem().setCycles(3);
            // equipmentsList = new_world.possiblySpawnEquipments();
            // assertEquals(1, equipmentsList.size());
            // assertEquals("gold", equipmentsList.get(0).getName());
       }

       /**
        * Test for run tick moved
        */
       @Test
        public void TestTickGame(){
            new_world.runTickMoves();
            assertEquals(1,new_world.getCharacter().getX());
            new_world.possiblySpawnEnemies();
            assertEquals(1, new_world.getBasicEnemies().get(0).getX());
            int previous = new_world.getBasicEnemies().get(0).getY();
            new_world.runTickMoves();
            assertEquals(1, new_world.getBasicEnemies().get(0).getX());
            int after = new_world.getBasicEnemies().get(0).getY();
            assertNotEquals(previous, after);
            new_world.getItem().setGold(100);

        }
        /**
         * Test for set rare item
         */
        @Test
        public void testSetRateItem(){
            new_world.setRandom(10);

            new_world.getItem().setCycles(1);
            assertEquals(false, new_world.checkRareItems("TheOneRing"));
            new_world.getItem().setCycles(2);
            assertEquals(false, new_world.checkRareItems("Anduril"));
            new_world.getItem().setCycles(3);
            assertEquals(false, new_world.checkRareItems("TreeStump"));

            ArrayList<String> rareItems = new ArrayList<String>();
            int x = 1;
            int y = 2;
            TheOneRing rareItem1 = new TheOneRing(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            rareItems.add(rareItem1.getName());
            Anduril rareItem2 = new Anduril(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            rareItems.add(rareItem2.getName());
            TreeStump rareItem3 = new TreeStump(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            rareItems.add(rareItem3.getName());
            new_world.setRareItem(rareItems);

            new_world.getItem().setCycles(0);
            assertEquals(false, new_world.checkRareItems("TheOneRing"));
            new_world.getItem().setCycles(6);
            assertEquals(true, new_world.checkRareItems("TheOneRing"));

            new_world.getItem().setCycles(3);
            assertEquals(false, new_world.checkRareItems("Anduril"));
            new_world.getItem().setCycles(37);
            assertEquals(true, new_world.checkRareItems("Anduril"));

            new_world.getItem().setCycles(4);
            assertEquals(false, new_world.checkRareItems("TreeStump"));
            new_world.getItem().setCycles(21);
            assertEquals(true, new_world.checkRareItems("TreeStump"));

        }

        /**
         * test if the equip can added in to unequipInventory and eqiuip
         * the equipmnet in the correct space
         */
        @Test
        public void TestEquipAndUnequip(){
            Character Link = new Character(character_position);
            new_world.setCharacter(Link);

            Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
            Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
            Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
            Shield shield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            Helmet helmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
            Anduril anduril = new Anduril(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0));
            TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));
            TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
            HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3));

            new_world.addUnequippedInventoryItem(sword);
            new_world.addEquippedInventoryItem(sword);
            new_world.addUnequippedInventoryItem(stake);
            new_world.addEquippedInventoryItem(stake);
            new_world.addUnequippedInventoryItem(anduril);
            new_world.addEquippedInventoryItem(anduril);
            new_world.addUnequippedInventoryItem(armour);
            new_world.addEquippedInventoryItem(armour);
            new_world.addUnequippedInventoryItem(armour);
            new_world.addEquippedInventoryItem(armour);
            new_world.addUnequippedInventoryItem(helmet);
            new_world.addEquippedInventoryItem(helmet);
            new_world.addUnequippedInventoryItem(helmet);
            new_world.addEquippedInventoryItem(helmet);

            new_world.addUnequippedInventoryItem(treeStump);
            new_world.addEquippedInventoryItem(treeStump);

            new_world.addUnequippedInventoryItem(staff);
            new_world.addEquippedInventoryItem(staff);

            new_world.addUnequippedInventoryItem(theOneRing);
            new_world.addEquippedInventoryItem(theOneRing);

            new_world.addUnequippedInventoryItem(shield);
            new_world.addEquippedInventoryItem(shield);
            new_world.addUnequippedInventoryItem(shield);
            new_world.addEquippedInventoryItem(shield);

            new_world.addUnequippedInventoryItem(theOneRing);
            new_world.addEquippedInventoryItem(theOneRing);

            assertEquals(staff, new_world.getEquippedInventoryItems().get(0));
            assertEquals(armour, new_world.getEquippedInventoryItems().get(1));
            assertEquals(shield, new_world.getEquippedInventoryItems().get(2));
            assertEquals(helmet, new_world.getEquippedInventoryItems().get(3));
            assertEquals(theOneRing, new_world.getEquippedInventoryItems().get(4));

            new_world.removeItemByPositionInequippedInventoryItems(0);
            assertEquals(armour, new_world.getEquippedInventoryItems().get(0));

            new_world.addUnequippedInventoryItem(sword);
            new_world.addUnequippedInventoryItem(shield);
            assertEquals(sword, new_world.getUnequippedInventoryItems().get(0));
            new_world.removeItemByPositionInUnequippedInventoryItems(0);
            assertEquals(shield, new_world.getUnequippedInventoryItems().get(0));
        }

        /**
         * test if the activateWeaponsByEnemyInBattle can add equip attribute to character
         * or enemy and test if the unactivatedWeaponAttribute can make attribute delete
         */
        @Test
        public void TestWeaponinBattle() {
            Character Link = new Character(character_position);
            new_world.setCharacter(Link);

            PathPosition slugPosition = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
            PathPosition zombiePosition = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
            PathPosition vampirePosition = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
            PathPosition doggiePosition = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
            PathPosition elanmuskePosition = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));

            Slug slug = new Slug(slugPosition);
            Zombie zombie = new Zombie(zombiePosition);
            Vampire Vampire = new Vampire(vampirePosition);
            Doggie Doggie = new Doggie(doggiePosition);
            FinalBoss FinalBoss = new FinalBoss(elanmuskePosition);

            Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
            Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
            Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
            Shield shield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            Helmet helmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
            Anduril anduril = new Anduril(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0));
            TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1));

            new_world.addUnequippedInventoryItem(stake);
            new_world.addUnequippedInventoryItem(staff);
            new_world.addUnequippedInventoryItem(armour);
            new_world.addUnequippedInventoryItem(shield);
            new_world.addUnequippedInventoryItem(helmet);
            new_world.addUnequippedInventoryItem(anduril);
            new_world.addUnequippedInventoryItem(treeStump);

            new_world.addEquippedInventoryItem(staff);
            new_world.addEquippedInventoryItem(armour);
            new_world.addEquippedInventoryItem(helmet);

            new_world.activateWeaponAttribute(slug);
            assertEquals(-3, slug.getDamage());

            new_world.activateWeaponAttribute(zombie);
            assertEquals(0, zombie.getDamage());

            new_world.addEquippedInventoryItem(stake);
            new_world.addEquippedInventoryItem(shield);
            new_world.activateWeaponAttribute(Vampire);
            assertEquals(40, Link.getDamage());
            new_world.unactivatedWeaponAttribute(Vampire);
            assertEquals(15, Link.getDamage());

            new_world.addEquippedInventoryItem(treeStump);
            new_world.activateWeaponAttribute(Doggie);
            assertEquals(90, Link.getDefence());
            new_world.unactivatedWeaponAttribute(Doggie);
            assertEquals(45, Link.getDefence());

            new_world.addEquippedInventoryItem(anduril);
            new_world.activateWeaponAttribute(FinalBoss);
            assertEquals(60, Link.getDamage());
            new_world.unactivatedWeaponAttribute(FinalBoss);
            assertEquals(30, Link.getDefence());

        }

        /**
         * test if the VillageAddHealth() can run in frontend
         */        
        @Test
        public void TestVillage(){
            Character Link = new Character(character_position2);
            new_world.setCharacter(Link);
            new_world.loadCard(3);
            new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),5,1);
            new_world.getCharacter().addHealth(-5);
            new_world.villageAddHealth();
            assertEquals(100, new_world.getCharacter().getHealth());
        }
        
        /**
         * test if the Toweruse() can run in frontend
         */  
        @Test
        public void TestTower(){
            Character Link = new Character(character_position3);
            
            PathPosition slugPosition = new PathPosition(0, Arrays.asList(new Pair<>(6, 2)));
            Slug slug = new Slug(slugPosition);
            new_world.activateWeaponAttribute(slug);
            new_world.setCharacter(Link);
            new_world.getBasicEnemies().add(slug);
            new_world.loadCard(2);
            new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),7,2);
            new_world.TowerUse();
            assertEquals(15, new_world.getBasicEnemies().get(0).getHealth());
        }        

        /**
         * test if the Trapuse() can run in frontend
         */  
        @Test
        public void TestTrap(){
            
            PathPosition slugPosition = new PathPosition(0, Arrays.asList(new Pair<>(6, 2)));
            Slug slug = new Slug(slugPosition);
            new_world.activateWeaponAttribute(slug);
            new_world.getBasicEnemies().add(slug);
            new_world.loadCard(5);
            new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),6,2);
            new_world.TrapUse();
            assertEquals(15, new_world.getBasicEnemies().get(0).getHealth());
        }
        /**
         * test if the Campfireuse() can run in frontend
         */  
        @Test
        public void TestCampfire(){
            Character Link = new Character(character_position3);
            Character Link2 = new Character(character_position2);
            new_world.setCharacter(Link);
            new_world.loadCard(6);
            new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),7,2);
            new_world.CampfireUse();
            assertEquals(20, new_world.getCharacter().getDamage());
            new_world.setCharacter(Link2);
            new_world.CampfireUse();
            assertEquals(5, new_world.getCharacter().getDamage());
        }

        /**
         * test if the BARRACKUSe() can run in frontend
         */  
        @Test
        public void TestBarracks(){
            Character Link = new Character(character_position3);
            new_world.setCharacter(Link);
            new_world.loadCard(4);
            new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),6,2);
            boolean result = new_world.BarracksUse();
            assertTrue(result);
            assertEquals(50, new_world.getCharacter().getAlly().get(0).getHealth());
        }
        
        /**
         * test if the TowerSpueruse() can run in frontend
         */  
        @Test
        public void TestSuperTower(){
            Character Link = new Character(character_position3);
            
            PathPosition slugPosition = new PathPosition(0, Arrays.asList(new Pair<>(6, 2)));
            Slug slug = new Slug(slugPosition);
            new_world.activateWeaponAttribute(slug);
            new_world.setCharacter(Link);
            new_world.getBasicEnemies().add(slug);
            new_world.loadCard(7);
            new_world.convertCardToBuildingByCoordinates(new_world.getCardEntities().get(0).getX(),new_world.getCardEntities().get(0).getY(),7,2);
            new_world.SuperTowerUse();
            assertEquals(5, new_world.getBasicEnemies().get(0).getHealth());
        }
                /**
         * test if the character can pick health potion and gold
        */
        @Test
        public void getSpawnEquipmentTest() {
            Pair<Integer,Integer> pos = world.possiblyGetEquipmentSpawnPosition();
            assertTrue(world.getOrderPath().contains(pos));
        }
        /**
         * test if the gold and health potion can be spawned randomly
         */
        @Test
        public void pickTest() {
            PathPosition pathPosition = new PathPosition(0, createOrderedPath());
            Character character = new Character(pathPosition);
            world.setCharacter(character);
            world.possiblySpawnEquipments();
            assertFalse(world.getHealthPotionPicked());
            pathPosition = new PathPosition(1, createOrderedPath());
            character = new Character(pathPosition);
            world.setCharacter(character);
            assertEquals("HealthPotion",world.possiblySpawnEquipments().getName());
        }
}