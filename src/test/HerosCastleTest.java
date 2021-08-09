package test;

import java.util.ArrayList;
import java.util.Arrays;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.*;

public class HerosCastleTest {
    /**
     * unit test for shop item list
     */
    @Test
    public void shopItemsTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);

        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));

        // check if shop shows all items
        ArrayList<ShopItem> shop = new ArrayList<ShopItem>();
        shop.add(ShopItem.SWORD);
        shop.add(ShopItem.STAKE);
        shop.add(ShopItem.STAFF);
        shop.add(ShopItem.ARMOUR);
        shop.add(ShopItem.SHIELD);
        shop.add(ShopItem.HELMET);
        shop.add(ShopItem.HEALTHPOTION);
        shop.add(ShopItem.DOGGIECOIN);
        assertEquals(shop, castle.getShop());
        assertEquals("Sword", ShopItem.SWORD.getName());
    }

    /**
     * test character not in shop
     */
    @Test
    public void characterNotInShopTest() {
        // not enough gold to but an item
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(20, 75, 1);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        Entity sellEntity = new Sword(x, y);
        assertDoesNotThrow(() -> castle.setDisplayShop(false));
        assertEquals(false, castle.buyEquipment(world, ShopItem.STAFF, world.getItem()));
        assertEquals(false, castle.sellEquipment(world, sellEntity, world.getItem()));

    }

    /**
     * unit test, lack of gold cannot buy an equipment
     */
    @Test
    public void lackOfGold() {
        // not enough gold to but an item
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(20, 75, 1);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);


        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(false, castle.buyEquipment(world, ShopItem.STAFF, world.getItem()));
    }

    /**
     * test standard mode can buy as many health potion as the player wants with enough gold
     */
    @Test
    public void StandardModeHealthPotionTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(1000, 300, 4);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));

        // buy >1 health potions
        assertEquals(true, castle.healthPotionAvailable());
        assertEquals(true, castle.buyEquipment(world, ShopItem.HEALTHPOTION, world.getItem()));
        assertEquals(true, castle.healthPotionAvailable());
        assertEquals(true, castle.buyEquipment(world, ShopItem.HEALTHPOTION, world.getItem()));
        assertEquals(840, item.getGold());
    }

    /**
     * test standard mode can buy as many protective gear as the player wants with enough gold
     */
    @Test
    public void StandardModeProtectiveGearTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(1000, 300, 4);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));

        // buy >1 protective gears
        assertEquals(true, castle.protectiveGearAvailable());
        assertEquals(true, castle.buyEquipment(world, ShopItem.SHIELD, world.getItem()));
        assertEquals(true, castle.protectiveGearAvailable());
        assertEquals(true, castle.buyEquipment(world, ShopItem.ARMOUR, world.getItem()));
        assertEquals(880, world.getItem().getGold());
    }

    /**
     * test survival mode can only buy 1 health potion
     */
    @Test
    public void SurvivalModeHealthPotionTest1() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(100, 75, 1);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        // buy health potion in survival mode
        HerosCastle castle = new HerosCastle(x, y, Mode.SURVIVAL);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(true, castle.healthPotionAvailable());
        assertEquals(true, castle.buyEquipment(world, ShopItem.HEALTHPOTION, world.getItem()));
        assertEquals(false, castle.healthPotionAvailable());
        assertEquals(20, world.getItem().getGold());
    }

    /**
     * test survival mode can only buy 1 health potion whenever player enters heros castle
     */
    @Test
    public void SurvivalModeHealthPotionTest2() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        // buy health potion in survival mode, enter hero's castle twice
        HerosCastle castle = new HerosCastle(x, y, Mode.SURVIVAL);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(true, castle.healthPotionAvailable());
        assertEquals(true, castle.buyEquipment(world, ShopItem.HEALTHPOTION, world.getItem()));
        assertEquals(false, castle.healthPotionAvailable());
        assertEquals(220, world.getItem().getGold());

        assertDoesNotThrow(() -> castle.setDisplayShop(false));
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(true, castle.buyEquipment(world, ShopItem.HEALTHPOTION, world.getItem()));
        assertEquals(false, castle.healthPotionAvailable());
    }

    /**
     * test berserker mode can only buy 1 protective gear
     */
    @Test
    public void BerserkerModeTest1() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(100, 75, 1);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        // buy different protective gear in berserker mode
        HerosCastle castle = new HerosCastle(x, y, Mode.BERSERKER);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(true, castle.protectiveGearAvailable());
        assertDoesNotThrow(() -> castle.buyEquipment(world, ShopItem.SHIELD, world.getItem()));
        assertEquals(false, castle.protectiveGearAvailable());
    }

    /**
     * test berserker mode can only buy 1 protective gear whenever player enters heros castle
     */
    @Test
    public void BerserkerModeTest2() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        // buy different protective gear in berserker mode, enter hero's castle twice
        HerosCastle castle = new HerosCastle(x, y, Mode.BERSERKER);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(true, castle.protectiveGearAvailable());
        assertDoesNotThrow(() -> castle.buyEquipment(world, ShopItem.SHIELD, world.getItem()));
        assertEquals(false, castle.protectiveGearAvailable());

        assertDoesNotThrow(() -> castle.setDisplayShop(false));
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(true, castle.buyEquipment(world, ShopItem.SHIELD, world.getItem()));
        assertEquals(false, castle.protectiveGearAvailable());
    }

    /**
     * test for buying all type of equipment
     */
    @Test
    public void buyEquipmentTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(1000, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        // buy different protective gear in berserker mode, enter hero's castle twice
        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(true, castle.buyEquipment(world, ShopItem.HELMET, world.getItem()));
        assertEquals(true, castle.buyEquipment(world, ShopItem.SWORD, world.getItem()));
        assertEquals(true, castle.buyEquipment(world, ShopItem.STAKE, world.getItem()));
        assertEquals(true, castle.buyEquipment(world, ShopItem.STAFF, world.getItem()));
    }

    /**
     * test player cannot sell items that they don't have
     */
    @Test
    public void sellNotExistEquipmentTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        Entity sellEntity = new Sword(x, y);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(false, castle.checkUnequippedEquipment(world, sellEntity));
        assertEquals(false, castle.checkEquippedEquipment(world, sellEntity));
        assertEquals(false, castle.sellEquipment(world, sellEntity, world.getItem()));
    }

    /**
     * test selling unequipped equipment
     */
    @Test
    public void sellUneuippedEquipmentTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);
        
        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        Entity sellEntity = new Sword(x, y);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(sellEntity));
        assertEquals(true, castle.checkUnequippedEquipment(world, sellEntity));
        assertEquals(false, castle.checkEquippedEquipment(world, sellEntity));
        assertEquals(true, castle.sellEquipment(world, sellEntity, world.getItem()));
        assertEquals(310, world.getItem().getGold());
    }

    /**
     * test selling equipped equipment
     */
    @Test
    public void sellEuippedEquipmentTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);
        
        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        Entity sellEntity = new Sword(x, y);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(sellEntity));
        assertDoesNotThrow(() -> world.addEquippedInventoryItem(sellEntity));
        assertEquals(false, castle.checkUnequippedEquipment(world, sellEntity));
        assertEquals(true, castle.checkEquippedEquipment(world, sellEntity));
        assertEquals(true, castle.sellEquipment(world, sellEntity, world.getItem()));
        assertEquals(310, world.getItem().getGold());
    }

    /**
     * test selling all unequipped equipment
     */
    @Test
    public void sellAllUneuippedEquipmentTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        SimpleIntegerProperty a = new SimpleIntegerProperty(-1);
        SimpleIntegerProperty b = new SimpleIntegerProperty(-1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);
        
        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));

        Entity sword = new Sword(a, b);
        Entity stake = new Stake(a, b);
        Entity staff = new Staff(a, b);
        Entity armour = new Armour(a, b);
        Entity shield = new Shield(a, b);
        Entity helmet = new Helmet(a, b);
        Entity healthPotion = new HealthPotion(a, b);
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(sword));
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(stake));
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(staff));
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(armour));
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(shield));
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(helmet));
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(healthPotion));
        assertEquals(true, castle.sellEquipment(world, sword, world.getItem()));
        assertEquals(true, castle.sellEquipment(world, stake, world.getItem()));
        assertEquals(true, castle.sellEquipment(world, staff, world.getItem()));
        assertEquals(true, castle.sellEquipment(world, armour, world.getItem()));
        assertEquals(true, castle.sellEquipment(world, shield, world.getItem()));
        assertEquals(true, castle.sellEquipment(world, helmet, world.getItem()));
        assertEquals(true, castle.sellEquipment(world, healthPotion, world.getItem()));
    }

    /**
     * test selling all equipped equipment
     */
    @Test
    public void sellAllEquippedEquipmentTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        SimpleIntegerProperty a = new SimpleIntegerProperty(-1);
        SimpleIntegerProperty b = new SimpleIntegerProperty(-1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);
        
        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));

        Entity sword = new Sword(a, b);
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(sword));
        assertDoesNotThrow(() -> world.addEquippedInventoryItem(sword));
        assertEquals(true, castle.sellEquipment(world, sword, world.getItem()));
        Entity stake = new Stake(a, b);
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(stake));
        assertDoesNotThrow(() -> world.addEquippedInventoryItem(stake));
        assertEquals(true, castle.sellEquipment(world, stake, world.getItem()));
        Entity staff = new Staff(a, b);
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(staff));
        assertDoesNotThrow(() -> world.addEquippedInventoryItem(staff));
        assertEquals(true, castle.sellEquipment(world, staff, world.getItem()));
        Entity armour = new Armour(a, b);
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(armour));
        assertDoesNotThrow(() -> world.addEquippedInventoryItem(armour));
        assertEquals(true, castle.sellEquipment(world, armour, world.getItem()));
        Entity shield = new Shield(a, b);
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(shield));
        assertDoesNotThrow(() -> world.addEquippedInventoryItem(shield));
        assertEquals(true, castle.sellEquipment(world, shield, world.getItem()));
        Entity helmet = new Helmet(a, b);
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(helmet));
        assertDoesNotThrow(() -> world.addEquippedInventoryItem(helmet));
        assertEquals(true, castle.sellEquipment(world, helmet, world.getItem()));
        Entity healthPotion = new HealthPotion(a, b);
        assertDoesNotThrow(() -> world.addUnequippedInventoryItem(healthPotion));
        assertDoesNotThrow(() -> world.addEquippedInventoryItem(healthPotion));
        assertEquals(false, castle.sellEquipment(world, healthPotion, world.getItem()));
    }

    @Test
    public void sellDoggieCoinTestError() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);
                
        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));
        assertEquals(0, world.getItem().getDoggieCoin());
        assertEquals(false, castle.sellDoggieCoin(world));
    }

    @Test
    public void sellDoggieCoinTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Item item = new Item(300, 125, 3);
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);
                
        HerosCastle castle = new HerosCastle(x, y, Mode.STANDARD);
        assertDoesNotThrow(() -> castle.setDisplayShop(true));

        assertDoesNotThrow(() -> world.getItem().setDoggieCoin(5));
        assertDoesNotThrow(() -> world.getItem().setPriceOfDoggieCoin(10));
        assertEquals(5, world.getItem().getDoggieCoin());
        assertEquals(true, castle.sellDoggieCoin(world));
        assertEquals(310, world.getItem().getGold());
    }
}
