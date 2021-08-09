package unsw.loopmania;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import org.javatuples.Pair;

public class HerosCastle extends StaticEntity{
    private boolean healthPotion, protectiveGear, characterInCastle;
    private Mode mode;
    private ArrayList<ShopItem> shop;

    public HerosCastle(SimpleIntegerProperty x, SimpleIntegerProperty y, Mode mode) {
        super(x, y);
        this.mode = mode;
        healthPotion = false;
        protectiveGear = false;
        characterInCastle = false;
        shop = new ArrayList<ShopItem>();
        fillShopItems();
    }

    /**
     * fill equipment for sell in shop
     */
    private void fillShopItems() {
        shop.add(ShopItem.SWORD);
        shop.add(ShopItem.STAKE);
        shop.add(ShopItem.STAFF);
        shop.add(ShopItem.ARMOUR);
        shop.add(ShopItem.SHIELD);
        shop.add(ShopItem.HELMET);
        shop.add(ShopItem.HEALTHPOTION);
        shop.add(ShopItem.DOGGIECOIN);
    }

    public ArrayList<ShopItem> getShop() {
        return shop;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public boolean getCharacterInCastle() {
        return characterInCastle;
    }

    /**
     * check if player are allowed to buy health potion in survival mode
     * @return boolean
     */
    public boolean healthPotionAvailable() {
        if (mode.equals(Mode.SURVIVAL) && healthPotion) {
            System.err.println("error: unable to buy more than 1 health potion in survival mode");
            return false;
        }
        return true;
    }

    /**
     * check if player are allowed to buy protective gear in berserker mode
     * @return boolean
     */
    public boolean protectiveGearAvailable() {
        if (mode.equals(Mode.BERSERKER) && protectiveGear) {
            System.err.println("error: unable to buy more than 1 protective gear in berseker mode");
            return false;
        }
        return true;
    }

    /**
     * set displayShop and reset healthPotion and protectiveGear
     * @param characterInCastle
     */
    public void setDisplayShop(boolean characterInCastle) {
        this.characterInCastle = characterInCastle;
        if (characterInCastle) {
            healthPotion = false;
            protectiveGear = false;
        }
    }
    
    /**
     * check if character have sufficient gold to buy equipment
     * @param currentGold of character
     * @param price of equipment
     * @return boolean
     */
    public boolean checkGold(int currentGold, int price) {
        if (currentGold - price >= 0) {
            return true;
        }
        System.err.println("error: insufficient gold to purchase this equipment");
        return false;
    }
    
    /**
     * update goal after buying / selling equipments
     * @param item
     * @param price of equipment
     * @param buy or sell
     */
    public void updateGold(Item item, int price, boolean buy) {
        int updatedGold;
        if (buy) {
            updatedGold = item.getGold() - price;
        } else {
            updatedGold = item.getGold() + price;
        }
        item.setGold(updatedGold);
    }

    /**
     * buy equipment and add new equipment to world
     * @param world
     * @param shopItem
     * @param item
     * @return boolean
     */
    public boolean buyEquipment(LoopManiaWorld world, ShopItem shopItem, Item item) {
        if (!characterInCastle) {
            System.err.println("error: character not in castle, unable to buy equipment");
            return false;
        }
           
        Pair<Integer, Integer> firstAvailableSlot = world.getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            world.getItem().setGold(world.getItem().getGold() + 5);
            world.getItem().setExperience(world.getItem().getExperience() + 10);
            world.removeItemByPositionInUnequippedInventoryItems(0);
            System.out.println(item.getClass().getSimpleName()+" removed");
            firstAvailableSlot = world.getFirstAvailableSlotForItem();
        }
        
        SimpleIntegerProperty x = new SimpleIntegerProperty(firstAvailableSlot.getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(firstAvailableSlot.getValue1());

        switch(shopItem) {
            case HEALTHPOTION:
                if (healthPotionAvailable() && checkGold(item.getGold(), shopItem.HEALTHPOTION.getBuyingPrice())) {
                    healthPotion = true;
                    updateGold(item, shopItem.HEALTHPOTION.getBuyingPrice(), true);
                    Entity health = new HealthPotion(x, y);
                    world.addUnequippedInventoryItem(health);
                    System.err.println("HealthPotion bought");
                    return true;
                }
                break;
            case ARMOUR:
                if (protectiveGearAvailable() && checkGold(item.getGold(), shopItem.ARMOUR.getBuyingPrice())) {
                    protectiveGear = true;
                    updateGold(item, shopItem.ARMOUR.getBuyingPrice(), true);
                    Entity armour = new Armour(x, y);
                    world.addUnequippedInventoryItem(armour);
                    System.err.println("Armour bought");
                    return true;
                }
                break;
            case SHIELD:
                if (protectiveGearAvailable() && checkGold(item.getGold(), shopItem.SHIELD.getBuyingPrice())) {
                    protectiveGear = true;
                    updateGold(item, shopItem.SHIELD.getBuyingPrice(), true);
                    Entity shield = new Shield(x, y);
                    world.addUnequippedInventoryItem(shield);
                    System.err.println("Shield bought");
                    return true;
                }
                break;
            case HELMET:
                if (protectiveGearAvailable() && checkGold(item.getGold(), shopItem.HELMET.getBuyingPrice())) {
                    protectiveGear = true;
                    updateGold(item, shopItem.HELMET.getBuyingPrice(), true);
                    Entity helmet = new Helmet(x, y);
                    world.addUnequippedInventoryItem(helmet);
                    System.err.println("Helmet bought");
                    return true;
                }
                break;
            case SWORD:
                if (checkGold(item.getGold(), shopItem.SWORD.getBuyingPrice())) {
                    updateGold(item, shopItem.SWORD.getBuyingPrice(), true);
                    Entity sword = new Sword(x, y);
                    world.addUnequippedInventoryItem(sword);
                    System.err.println("Sword bought");
                    return true;
                }
                break;
            case STAKE:
                if (checkGold(item.getGold(), shopItem.STAKE.getBuyingPrice())) {
                    updateGold(item, shopItem.STAKE.getBuyingPrice(), true);
                    Entity stake = new Stake(x, y);
                    world.addUnequippedInventoryItem(stake);
                    System.err.println("Stake bought");
                    return true;
                }
                break;
            case STAFF:
                if (checkGold(item.getGold(), shopItem.STAFF.getBuyingPrice())) {
                    updateGold(item, shopItem.STAFF.getBuyingPrice(), true);
                    Entity staff = new Staff(x, y);
                    world.addUnequippedInventoryItem(staff);
                    System.err.println("Staff bought");
                    return true;
                }
                break;
        }
        System.err.println("error: failed to buy equipment");
        return false;
    }

    /**
     * check if unequippedEquipment contain sellEntity
     * @param world
     * @param sellEntity
     * @return boolean
     */
    public boolean checkUnequippedEquipment(LoopManiaWorld world, Entity sellEntity) {
        String name = sellEntity.getClass().getName();
        String[] names = name.split("\\.");
        for (Entity unequipped : world.getUnequippedInventoryItems()) {
            String unequipName = unequipped.getClass().getName();
            String[] unequipNames = unequipName.split("\\.");
            if (unequipNames[2].equals(names[2])) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if equippedEquipment contains sellEntity
     * @param world
     * @param sellEntity
     * @return boolean
     */
    public boolean checkEquippedEquipment(LoopManiaWorld world, Entity sellEntity) {
        String name = sellEntity.getClass().getName();
        String[] names = name.split("\\.");
        for (Entity equipped : world.getEquippedInventoryItems()) {
            if (equipped != null) {
                String equipName = equipped.getClass().getName();
                String[] equipNames = equipName.split("\\.");
                if (equipNames[2].equals(names[2])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * sell unequippedEquipment
     * @param world
     * @param sellEntity
     * @return boolean
     */
    public void sellUnequippedEquipment(LoopManiaWorld world, Entity sellEntity) {
        String name = sellEntity.getClass().getName();
        String[] names = name.split("\\.");
        for (Entity unequipped : world.getUnequippedInventoryItems()) {
            String unequipName = unequipped.getClass().getName();
            String[] unequipNames = unequipName.split("\\.");
            if (unequipNames[2].equals(names[2])) {
                world.removeUnequippedInventoryItem(unequipped);
                return;
            }
        }
    }

    /**
     * sell equippedEquipment
     * @param world
     * @param sellEntity
     * @return boolean
     */
    public void sellEquippedEquipment(LoopManiaWorld world, Entity sellEntity) {
        String name = sellEntity.getClass().getName();
        String[] names = name.split("\\.");
        for (Entity equipped : world.getEquippedInventoryItems()) {
            if (equipped != null) {
                String equipName = equipped.getClass().getName();
                String[] equipNames = equipName.split("\\.");
                if (equipNames[2].equals(names[2])) {
                    world.removeEquippedInventoryItem(equipped);
                    return;
                }
            }
        }
    }

    /**
     * sell equipment and removed sell equipment from world
     * @param world
     * @param sellEntity
     * @param item
     * @return boolean
     */
    public boolean sellEquipment(LoopManiaWorld world, Entity sellEntity, Item item) {
        if (!characterInCastle) {
            System.err.println("error: character not in castle, unable to sell equipment");
            return false;
        }

        String name = sellEntity.getClass().getName();
        String[] names = name.split("\\.");
        switch (names[2]) {
            case "Sword":
                if (checkUnequippedEquipment(world, sellEntity)) {
                    sellUnequippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.SWORD.getSellingPrice(), false);
                    System.err.println("Sword sold");
                    return true;
                } else if (checkEquippedEquipment(world, sellEntity)) {
                    sellEquippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.SWORD.getSellingPrice(), false);
                    System.err.println("Sword sold");
                    return true;
                }
                return false;
            case "Stake":
                if (checkUnequippedEquipment(world, sellEntity)) {
                    sellUnequippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.STAKE.getSellingPrice(), false);
                    System.err.println("Stake sold");
                    return true;
                } else if (checkEquippedEquipment(world, sellEntity)) {
                    sellEquippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.STAKE.getSellingPrice(), false);
                    System.err.println("Stake sold");
                    return true;
                }
                return false;
            case "Staff":
                if (checkUnequippedEquipment(world, sellEntity)) {
                    sellUnequippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.STAFF.getSellingPrice(), false);
                    System.err.println("Staff sold");
                    return true;
                } else if (checkEquippedEquipment(world, sellEntity)) {
                    sellEquippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.STAFF.getSellingPrice(), false);
                    System.err.println("Staff sold");
                    return true;
                }
                return false;
            case "Armour":
                if (checkUnequippedEquipment(world, sellEntity)) {
                    sellUnequippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.ARMOUR.getSellingPrice(), false);
                    System.err.println("Armour sold");
                    return true;
                } else if (checkEquippedEquipment(world, sellEntity)) {
                    sellEquippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.ARMOUR.getSellingPrice(), false);
                    System.err.println("Armour sold");
                    return true;
                }
                return false;
            case "Shield":
                if (checkUnequippedEquipment(world, sellEntity)) {
                    sellUnequippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.SHIELD.getSellingPrice(), false);
                    System.err.println("Shield sold");
                    return true;
                } else if (checkEquippedEquipment(world, sellEntity)) {
                    sellEquippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.SHIELD.getSellingPrice(), false);
                    System.err.println("Shield sold");
                    return true;
                }
                return false;
            case "Helmet":
                if (checkUnequippedEquipment(world, sellEntity)) {
                    sellUnequippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.HELMET.getSellingPrice(), false);
                    System.err.println("Helmet sold");
                    return true;
                } else if (checkEquippedEquipment(world, sellEntity)) {
                    sellEquippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.HELMET.getSellingPrice(), false);
                    System.err.println("Helmet sold");
                    return true;
                }
                return false;
            case "HealthPotion":
                if (checkUnequippedEquipment(world, sellEntity)) {
                    sellUnequippedEquipment(world, sellEntity);
                    updateGold(item, ShopItem.HEALTHPOTION.getSellingPrice(), false);
                    System.err.println("HealthPotion sold");
                    return true;
                }
                return false;
        }
        System.err.println("error: unable to sell equipment");
        return false;
    }

    /**
     * sell doggie coin in hero's castle, update available doggie coin quantity
     * @param world
     * @return boolean
     */
    public boolean sellDoggieCoin(LoopManiaWorld world) {
        if (world.getItem().getDoggieCoin() <= 0) {
            System.err.println("error: no doggie coin available for sell");
            return false;
        }

        updateGold(world.getItem(), world.getItem().getPriceOfDoggieCoin(), false);
        world.getItem().setDoggieCoin(world.getItem().getDoggieCoin() - 1);
        return true;
    }
}
