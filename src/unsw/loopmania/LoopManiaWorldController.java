package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.javatuples.Pair;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import java.util.EnumMap;

import java.io.File;
import java.io.IOException;

/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ITEM,
    VampireCastleCard,
    VampireCastleBuilding,
    ZombiePitCard,
    ZombiePit,
    TrapCard,
    Trap,
    TowerCard,
    Tower,
    SuperTowerCard,
    SuperTower,
    VillageCard,
    Village,
    CampfireCard,
    Campfire,
    BarracksCard,
    Barracks,
    Sword,
    Stake,
    Staff,
    Armour,
    Shield,
    Helmet,
    Gold,
    HealthPotion,
    TheOneRing,
    Anduril,
    TreeStump,
    Transport
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;


    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    private Image soldierImage;
    private Image slugImage;
    private Image zombieImage;
    private Image vampireImage;
    private Image doggieImage;
    private Image finalBossImage;

    private Image heroCastleImage;
    private Image vampireCastleImage;
    private Image zombiePitImage;
    private Image towerImage;
    private Image superTowerImage;
    private Image villageImage;
    private Image barracksImage;
    private Image trapImage;
    private Image campfireImage;
    

    private Image vampireCastleCardImage;
    private Image zombiePitCardImage;
    private Image towerCardImage;
    private Image superTowerCardImage;
    private Image villageCardImage;
    private Image barracksCardImage;
    private Image trapCardImage;
    private Image campfireCardImage;

    private Image swordImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image theOneRingImage;
    private Image healthPotionImage;
    private Image spawnHealthPotionImage;
    private Image spawnGoldImage;
    private Image andurilImage;
    private Image treeStumpImage;
    private Image transportImage;

    private Image swordSlotImage;
    private Image armourSlotImage;
    private Image shieldSlotImage;
    private Image helmetSlotImage;
    private Image ringSlotImage;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;
    /**
     * object handling switching to the hero's castle page
     */
    @FXML
    private MenuSwitcher herosCastleSwitcher;
    @FXML
    private HBox soldierHBox;
    @FXML
    private ImageView health;
    @FXML
    private ImageView gold;
    @FXML
    private Label goldLabel;
    @FXML
    private Label experienceLabel;
    @FXML
    private Label cyclesLabel;
    @FXML
    private Label damageLabel;
    @FXML
    private Label defenceLabel;
    @FXML
    private ProgressBar healthProgressBar;
    @FXML
    private Label goalLabel;
    @FXML
    private Button shopButton;
    private AudioClip bgm;
    private AudioClip equipBGM;
    private List<Soldier> soldiers;
    private int soldierNumbers = 0;

    private Image healthImage;
    private Image goldImage;
    private ImageView swordSlotView;
    private ImageView armourSlotView;
    private ImageView shieldSlotView;
    private ImageView helmetSlotView; 
    private ImageView ringSlotView;
    private int equipInventoryX;
    private int equipInventoryY;
    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        playMusic();
        entityImages = new ArrayList<>(initialEntities);
        heroCastleImage = new Image((new File("src/images/heroCastle.png")).toURI().toString(), 64, 64, false, false);
        soldierImage = new Image((new File("src/images/soldier.png")).toURI().toString(), 32, 40, false, false);
        slugImage = new Image((new File("src/images/slug.png")).toURI().toString(), 64, 64, false, false);
        zombieImage = new Image((new File("src/images/zombie.gif")).toURI().toString(), 64, 64, false, false);
        vampireImage = new Image((new File("src/images/vampire.gif")).toURI().toString(), 64, 64, false, false);
        doggieImage = new Image((new File("src/images/doggie.png")).toURI().toString(), 64, 64, false, false);
        finalBossImage = new Image((new File("src/images/final_boss.png")).toURI().toString(), 64, 64, false, false);
        vampireCastleImage = new Image((new File("src/images/vampireCastle.png")).toURI().toString(), 64, 64, false, false);
        zombiePitImage = new Image((new File("src/images/zombiePit.png")).toURI().toString(), 64, 64, false, false);
        towerImage = new Image((new File("src/images/tower.png")).toURI().toString(), 64, 64, false, false);
        superTowerImage = new Image((new File("src/images/superTower.png")).toURI().toString(), 64, 64, false, false);
        villageImage = new Image((new File("src/images/village.png")).toURI().toString(), 64, 64, false, false);
        barracksImage = new Image((new File("src/images/barracks.png")).toURI().toString(), 64, 64, false, false);
        trapImage = new Image((new File("src/images/trap.png")).toURI().toString(), 64, 64, false, false);
        campfireImage = new Image((new File("src/images/campfire.png")).toURI().toString(), 64, 64, false, false);
        heroCastleImage = new Image((new File("src/images/heroCastle.png")).toURI().toString(), 64, 64, false, false);
        spawnHealthPotionImage = new Image((new File("src/images/healthPotion.png")).toURI().toString(), 40, 40, false, false);
        spawnGoldImage = goldImage = new Image((new File("src/images/goldPile.png")).toURI().toString(), 40, 40, false, false);
        
        vampireCastleCardImage = new Image((new File("src/images/vampireCastle.png")).toURI().toString(), 64, 64, false, false);
        zombiePitCardImage = new Image((new File("src/images/zombiePit.png")).toURI().toString(), 64, 64, false, false);
        towerCardImage = new Image((new File("src/images/tower.png")).toURI().toString(), 64, 64, false, false);
        superTowerCardImage = new Image((new File("src/images/superTower.png")).toURI().toString(), 64, 64, false, false);
        villageCardImage = new Image((new File("src/images/village.png")).toURI().toString(), 64, 64, false, false);
        barracksCardImage = new Image((new File("src/images/barracks.png")).toURI().toString(), 64, 64, false, false);
        trapCardImage = new Image((new File("src/images/trap.png")).toURI().toString(), 64, 64, false, false);
        campfireCardImage = new Image((new File("src/images/campfire.png")).toURI().toString(), 64, 64, false, false);

        andurilImage = new Image((new File("src/images/anduril.png")).toURI().toString(), 32, 32, false, false);
        treeStumpImage = new Image((new File("src/images/treeStump.png")).toURI().toString(), 32, 32, false, false);
        swordImage = new Image((new File("src/images/sword.png")).toURI().toString(), 32, 32, false, false);
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString(), 32, 32, false, false);
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString(), 32, 32, false, false);
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString(), 32, 32, false, false);
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString(), 28, 28, false, false);
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString(), 30, 30, false, false);
        theOneRingImage = new Image((new File("src/images/ring.png")).toURI().toString(), 32, 32, false, false);
        healthPotionImage = new Image((new File("src/images/healthPotion.png")).toURI().toString(), 32, 32, false, false);
        transportImage = new Image((new File("src/images/transport.png")).toURI().toString(), 32, 32, false, false);

        swordSlotImage = new Image((new File("src/images/swordSlot.png")).toURI().toString(), 32, 32, false, false);
        armourSlotImage = new Image((new File("src/images/armourSlot.png")).toURI().toString(), 32, 32, false, false);
        shieldSlotImage = new Image((new File("src/images/shieldSlot.png")).toURI().toString(), 32, 32, false, false);
        helmetSlotImage = new Image((new File("src/images/helmetSlot.png")).toURI().toString(), 32, 32, false, false);
        ringSlotImage = new Image((new File("src/images/ringSlot.png")).toURI().toString(), 32, 32, false, false);
        
        swordSlotView = new ImageView(swordSlotImage);
        armourSlotView = new ImageView(armourSlotImage);
        shieldSlotView = new ImageView(shieldSlotImage);
        helmetSlotView = new ImageView(helmetSlotImage);  
        ringSlotView = new ImageView(ringSlotImage);

        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    public void initialize() {
        
        Image grassImage = new Image((new File("src/images/grass.jpg")).toURI().toString(), 64, 64, false, false);
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString(), 32, 32, false, false);
        ImageView HCView = new ImageView(heroCastleImage);

        healthImage = new Image((new File("src/images/heart.png")).toURI().toString(), 30, 30, false, false);
        goldImage = new Image((new File("src/images/goldPile.png")).toURI().toString(), 30, 30, false, false);
        health.setImage(healthImage);
        gold.setImage(goldImage);
        updateItems();
        updateGoal();
        shopButton.setDisable(true);

        equippedItems.add(swordSlotView, 0, 0);
        equippedItems.add(armourSlotView, 1, 0);
        equippedItems.add(shieldSlotView, 2, 0);
        equippedItems.add(helmetSlotView, 3, 0);
        equippedItems.add(ringSlotView, 4, 0);

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(grassImage);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }
        
        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView groundView = new ImageView(grassImage);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        squares.add(HCView, 1, 1);
        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);
    }

    /**
     * create and run the timer
     */
    public void startTimer(){
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            world.runTickMoves();
            world.pickGoldHealthPotion();
            if (world.getHealthPotionPicked()) {
                world.setHealthPotionPicked(false);
                Item item = world.getItem();
                Pair<Integer, Integer> firstAvailableSlot = world.getFirstAvailableSlotForItem();
                if (firstAvailableSlot == null){
                    // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
                    item.setGold(item.getGold() + 5);
                    item.setExperience(item.getExperience() + 10);
                    world.removeItemByPositionInUnequippedInventoryItems(0);
                    System.out.println(item.getClass().getSimpleName()+" removed");
                    firstAvailableSlot = world.getFirstAvailableSlotForItem();
                }
                int x = firstAvailableSlot.getValue0();
                int y = firstAvailableSlot.getValue1();
                HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                world.addUnequippedInventoryItem(healthPotion);
                onLoadHealthPotion(healthPotion);
            }
            if (world.getHerosCastle().getCharacterInCastle()) {
                shopButton.setDisable(false);
                shopButton.fire();
            } else {
                shopButton.setDisable(true);
            }
            
            updateItems();
            checkIfGoalMet();
            checkLoseGame();
            world.villageAddHealth();
            world.CampfireUse();
            world.TowerUse();
            world.SuperTowerUse();
            world.TrapUse();
            soldiers = world.getCharacter().getAlly();
            if (soldierNumbers > soldiers.size()) {
                for (int diff = 0; diff < soldierNumbers - soldiers.size(); diff++) {
                    System.out.println("-----1 soldier died----");
                    soldierHBox.getChildren().remove(0);
                }
                soldierNumbers = soldiers.size();
            }
            if (world.BarracksUse()) {
                onLoadSoldier(soldiers.get(soldiers.size() - 1));
                soldierNumbers++;
                System.out.println("-----1 soldier added----");
            }
            
            List<BasicEnemy> defeatedEnemies = world.runBattles();
            checkLoseGame();
            for (BasicEnemy e: defeatedEnemies){
                reactToEnemyDefeat(e);
            }
            
            List<Entity> unequippedItems = world.getUnequippedInventoryItems();
            for (Entity eachEquip: unequippedItems){
                if (eachEquip != null) {
                    String name = eachEquip.getClass().getName();
                    String[] names = name.split("\\.");
                    String equipName = names[2];
                    switch (equipName) {
                        case "Sword":
                            onLoadSword((Sword)eachEquip);
                            break;
                        case "Stake":
                            onLoadStake((Stake)eachEquip);
                            break;
                        case "Staff":
                            onLoadStaff((Staff)eachEquip);
                            break;
                        case "Armour":
                            onLoadArmour((Armour)eachEquip);
                            break;
                        case "Shield":
                            onLoadShield((Shield)eachEquip);
                            break;
                        case "Helmet":
                            onLoadHelmet((Helmet)eachEquip);
                            break;
                        case "HealthPotion":
                            onLoadHealthPotion((HealthPotion)eachEquip);
                            break;
                        case "TheOneRing":
                            onLoadTheOneRing((TheOneRing)eachEquip);
                            break;
                        case "Anduril":
                            onLoadAnduril((Anduril)eachEquip);
                            break;
                        case "TreeStump":
                            onLoadTreeStump((TreeStump)eachEquip);
                            break;
                    }
                }
            }

            List<Entity> equippedItems = world.getEquippedInventoryItems();
            for (Entity eachEquip: equippedItems) {
                if (eachEquip != null) {
                    String name = eachEquip.getClass().getName();
                    String[] names = name.split("\\.");
                    String equipName = names[2];
                    switch (equipName) {
                        case "Sword":
                            onLoadSwordEquipped((Sword)eachEquip);
                            break;
                        case "Stake":
                            onLoadStakeEquipped((Stake)eachEquip);
                            break;
                        case "Staff":
                            onLoadStaffEquipped((Staff)eachEquip);
                            break;
                        case "Armour":
                            onLoadArmourEquipped((Armour)eachEquip);
                            break;
                        case "Shield":
                            onLoadShieldEquipped((Shield)eachEquip);
                            break;
                        case "Helmet":
                            onLoadHelmetEquipped((Helmet)eachEquip);
                            break;
                        case "TheOneRing":
                            onLoadTheOneRingEquipped((TheOneRing)eachEquip);
                            break;
                        case "Anduril":
                            onLoadAndurilEquipped((Anduril)eachEquip);
                            break;
                        case "TreeStump":
                            onLoadTreeStumpEquipped((TreeStump)eachEquip);
                            break;
                    }
                }
            }

            List<BasicEnemy> newEnemies = world.possiblySpawnEnemies();
            for (BasicEnemy newEnemy: newEnemies) {
                String name = newEnemy.getClass().getName();
                String[] names = name.split("\\.");
                String enemyName = names[2];
                switch (enemyName) {
                    case "Slug":
                        onLoadSlug(newEnemy);
                        break;
                    case "Vampire":
                        onLoadVampire(newEnemy);
                        break;
                    case "Zombie":
                        onLoadZombie(newEnemy);
                        break;
                    case "Doggie":
                        onLoadDoggie(newEnemy);
                        break;
                    case "FinalBoss":
                        onLoadFinalBoss(newEnemy);
                }
            }
            if (world.possiblySpawnEquipments()!=null) {
                Equipment newHealthPotionGold = world.possiblySpawnEquipments();
                switch (newHealthPotionGold.getName()) {
                    case "Gold":
                        onLoadSpawnGold(newHealthPotionGold);
                        break;
                    case "HealthPotion":
                        onLoadSpawnHealthPotion(newHealthPotionGold);
                        break;
                }
            }

            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * update gold, experience, cycles, damage and defence on frontend
     */
    public void updateItems() {
        healthProgressBar.setProgress(world.getCharacter().getHealth() * 0.01);
        goldLabel.setText("$" + world.getItem().getGold());
        experienceLabel.setText("" + world.getItem().getExperience());
        cyclesLabel.setText("" + world.getItem().getCycles());
        damageLabel.setText("" + world.getCharacter().getDamage());
        defenceLabel.setText("" + world.getCharacter().getDefence());
    }

    /**
     * show goal in frontend
     */
    public void updateGoal() {
        goalLabel.setText(world.getGoal().toString());
    }

    /**
     * check if character met all goals, if yes, stop game and show win page
     */
    public void checkIfGoalMet() {
        if (world.getGoal().checkGoal(world)) {
            timeline.stop();
            showWinPage();
        }
    }
    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        System.out.println("pausing");
        updateItems();
        timeline.stop();
    }

    /**
     * check if game lose, if yes, stop game and show game over page
     */
    public void checkLoseGame() {
        if (world.getCharacter().getDead()) {
            isPaused = true;
            System.out.println("Game Over");
            updateItems();
            timeline.stop();
            showLossPage();
        }
    }

    /**
     * terminate game
     */
    public void terminate(){
        pause();
    }

    /**
     * play background music, recursively play the same audio clip
     */
    public void playMusic() {
        bgm = new AudioClip((new File("src/audio/GoronCity.mp3")).toURI().toString());
        bgm.setVolume(0.05);
        bgm.setCycleCount(60);
        equipBGM = new AudioClip((new File("src/audio/equip.mp3")).toURI().toString());
        bgm.play();
    }

    /**
     * popup page when game win, play win audio, exit game when button click
     */
    public void showWinPage() {
        bgm.stop();
        Stage popup = new Stage();
        popup.setAlwaysOnTop(true);
        popup.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        Label lossLabel = new Label("Congratulation!!!");
        lossLabel.setFont(Font.font("Verdana", 20));
        Button closeButton = new Button("Exit");
        closeButton.setFont(Font.font("Verdana", 14));
        closeButton.setOnAction(e -> {
            System.exit(0);
        });
        Image winImage = new Image((new File("src/images/trophy.png")).toURI().toString(), 100, 100, false, false);
        ImageView winImageView = new ImageView(winImage);
        root.getChildren().addAll(lossLabel, winImageView, closeButton);

        Scene scene = new Scene(root, 300, 300);
        popup.setScene(scene);
        popup.setTitle("Win");
        popup.show();

        AudioClip winBGM = new AudioClip((new File("src/audio/SolutionFanfare.mp3")).toURI().toString());
        winBGM.setVolume(0.3);
        winBGM.play();
    }

    /**
     * popup page when game lose, play lose audio, exit game when button click
     */
    public void showLossPage() {
        bgm.stop();
        Stage popup = new Stage();
        popup.setAlwaysOnTop(true);
        popup.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        Image gameOverImage = new Image((new File("src/images/loseScreen.png")).toURI().toString(), 200, 100, false, false);
        ImageView gameOverImageView = new ImageView(gameOverImage);
        Button closeButton = new Button("Exit");
        closeButton.setFont(Font.font("Verdana", 14));
        closeButton.setOnAction(e -> {
            System.exit(0);
        });
        Image loseImage = new Image((new File("src/images/loseCry.png")).toURI().toString(), 100, 100, false, false);
        ImageView loseImageView = new ImageView(loseImage);
        root.getChildren().addAll(gameOverImageView, loseImageView, closeButton);

        Scene scene = new Scene(root, 300, 300);
        popup.setScene(scene);
        popup.setTitle("Loss");
        popup.show();

        AudioClip loseBgm = new AudioClip((new File("src/audio/GameOver.mp3")).toURI().toString());
        loseBgm.setVolume(0.3);
        loseBgm.play();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }
    
    /**
     * load a vampire card from the world, and pair it with an image in the GUI
     */
    private void loadVampireCard() {
        VampireCastleCard vampireCastleCard = (VampireCastleCard)world.loadCard(0);
        onLoadVampireCastleCard(vampireCastleCard);
    }

    /**
     * load a ZombiePit card from the world, and pair it with an image in the GUI
     */
    private void loadZombiePitCard() {
        ZombiePitCard zombiePitCard = (ZombiePitCard)world.loadCard(1);
        onLoadZombiePitCard(zombiePitCard);
    }

    /**
     * load a Tower card from the world, and pair it with an image in the GUI
     */
    private void loadTowerCard() {
        TowerCard towerCard = (TowerCard)world.loadCard(2);
        onLoadTowerCard(towerCard);
    }

    /**
     * load a Super Tower card from the world, and pair it with an image in the GUI
     */
    private void loadSuperTowerCard() {
        SuperTowerCard superTowerCard = (SuperTowerCard)world.loadCard(7);
        onLoadSuperTowerCard(superTowerCard);
    }

    /**
     * load a village card from the world, and pair it with an image in the GUI
     */
    private void loadVillageCard() {
        VillageCard villageCard = (VillageCard)world.loadCard(3);
        onLoadVillageCard(villageCard);
    }

    /**
     * load a barracks card from the world, and pair it with an image in the GUI
     */
    private void loadBarracksCard() {
        BarracksCard barracksCard = (BarracksCard)world.loadCard(4);
        onLoadBarracksCard(barracksCard);
    }

    /**
     * load a trap card from the world, and pair it with an image in the GUI
     */
    private void loadTrapCard() {
        TrapCard trapCard = (TrapCard)world.loadCard(5);
        onLoadTrapCard(trapCard);
    }

    /**
     * load a campfire card from the world, and pair it with an image in the GUI
     */
    private void loadCampfireCard() {
        CampfireCard campfireCard = (CampfireCard)world.loadCard(6);
        onLoadCampfireCard(campfireCard);
    }

    /**
     * get a equip and card
     * @param type
     */
    public void getEquipOrCard(int type, int x, int y) {
        Random rand = new Random();
        int chance = rand.nextInt(11);
        switch (type) {
            case 0:
                switch (chance % 11) {
                    case 1:
                        Entity sword = new Sword(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        world.addUnequippedInventoryItem(sword);
                        onLoadSword((Sword)sword);
                        break;
                    case 2:
                        Entity stake = new Stake(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        world.addUnequippedInventoryItem(stake);
                        onLoadStake((Stake)stake);
                        break;
                    case 3:
                        Entity staff = new Staff(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        world.addUnequippedInventoryItem(staff);
                        onLoadStaff((Staff)staff);
                        break;
                    case 4:
                        Entity armour = new Armour(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        world.addUnequippedInventoryItem(armour);
                        onLoadArmour((Armour)armour);
                        break;
                    case 5:
                        Entity shield = new Shield(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        world.addUnequippedInventoryItem(shield);
                        onLoadShield((Shield)shield);
                        break;
                    case 6:
                        Entity helmet = new Helmet(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        world.addUnequippedInventoryItem(helmet);
                        onLoadHelmet((Helmet)helmet);
                        break;
                    case 7:
                        Entity healthPotion = new HealthPotion(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        if (rand.nextInt(5) == 0) {
                            world.addUnequippedInventoryItem(healthPotion);
                            onLoadHealthPotion((HealthPotion)healthPotion);
                        }
                        break;
                    case 8:
                        Entity theOneRing = new TheOneRing(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        if (world.checkRareItems("TheOneRing")){
                            world.addUnequippedInventoryItem(theOneRing);
                            onLoadTheOneRing((TheOneRing)theOneRing);
                            break;
                        }
                        break;
                    case 9:
                        Entity tp = new Transport(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        world.addUnequippedInventoryItem(tp);
                        onLoadTransport((Transport)tp);
                        break;
                    case 10:
                        Entity anduril = new Anduril(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        if (world.checkRareItems("Anduril")){
                            world.addUnequippedInventoryItem(anduril);
                            onLoadAnduril((Anduril)anduril);
                            break;
                        }
                    case 0:
                        Entity treeStump = new TreeStump(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                        if(world.checkRareItems("TreeStump")){
                            world.addUnequippedInventoryItem(treeStump);
                            onLoadTreeStump((TreeStump)treeStump);
                            break;
                        }
                        break;
                }
                break;
            case 1:
                switch (chance % 8) {
                    case 1:
                        loadZombiePitCard();
                        break;
                    case 2:
                        loadVampireCard();
                    case 3:
                        loadTowerCard();
                        break;
                    case 4:
                        loadVillageCard();
                        break;
                    case 5:
                        loadBarracksCard();
                        break;
                    case 6:
                        loadTrapCard();
                        break;
                    case 7:
                        loadSuperTowerCard();
                        break;
                    case 0:
                        loadCampfireCard();
                        break;
                }
                break;
        }
    }
    
    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(BasicEnemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...

        Pair<Integer, Integer> firstAvailableSlot = world.getFirstAvailableSlotForItem();

        Random random = new Random();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            world.getItem().setGold(world.getItem().getGold() + 5);
            world.getItem().setExperience(world.getItem().getExperience() + 10);
            world.removeItemByPositionInUnequippedInventoryItems(0);
            System.out.println(world.getItem().getClass().getSimpleName()+" removed");
            firstAvailableSlot = world.getFirstAvailableSlotForItem();
        }
        
        int x = firstAvailableSlot.getValue0();
        int y = firstAvailableSlot.getValue1();
        int z = random.nextInt(8);
        
        AudioClip killedBGM = new AudioClip((new File("src/audio/retro_sound_2.wav")).toURI().toString());
        killedBGM.setVolume(0.3);
        killedBGM.play();

        String name = enemy.getClass().getName();
        String[] names = name.split("\\.");
        String deadEnemyName = names[2];
        switch (deadEnemyName) {
            case "Slug":
                // set 20% chance
                if (random.nextInt(5) < 2) {
                    getEquipOrCard(0,x,y);
                    getEquipOrCard(1,0,z);
                }      
                break;
            case "Vampire":
                // set 80% chance
                if (random.nextInt(5) < 4) {
                    getEquipOrCard(0,x,y);
                    getEquipOrCard(1,0,z);
                } 
                break;
            case "Zombie":
                // set 50% chance
                if (random.nextInt(2) == 0) {
                    getEquipOrCard(0,x,y);
                    getEquipOrCard(1,0,z);
                } 
                break;
            case "Doggie":
                // set 100% chance
                getEquipOrCard(0,x,y);
                getEquipOrCard(1,0,z);
                break;
            case "FinalBoss":
                // set 100% chance
                getEquipOrCard(0,x,y);
                getEquipOrCard(1,0,z);
                break;
        }
    }

    /**
     * load a vampire castle card into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param vampireCastleCard
     */
    private void onLoadVampireCastleCard(VampireCastleCard vampireCastleCard) {
        ImageView vampireCastleCardView = new ImageView(vampireCastleCardImage);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(vampireCastleCardView, DRAGGABLE_TYPE.VampireCastleCard, cards, squares);

        addEntity(vampireCastleCard, vampireCastleCardView);
        cards.getChildren().add(vampireCastleCardView);
    }

    /**
     * load a zombie pit castle card into the GUI.
     * @param ZombiePitCard
     */
    private void onLoadZombiePitCard(ZombiePitCard zombiePitCard) {
        ImageView zombiePitCardView = new ImageView(zombiePitCardImage);
        addDragEventHandlers(zombiePitCardView, DRAGGABLE_TYPE.ZombiePitCard, cards, squares);
        addEntity(zombiePitCard, zombiePitCardView);
        cards.getChildren().add(zombiePitCardView);
    }

    /**
     * load a tower castle card into the GUI.
     * @param towerCard
     */
    private void onLoadTowerCard(TowerCard towerCard) {
        ImageView towerCardView = new ImageView(towerCardImage);
        addDragEventHandlers(towerCardView, DRAGGABLE_TYPE.TowerCard, cards, squares);
        addEntity(towerCard, towerCardView);
        cards.getChildren().add(towerCardView);
    }

    /**
     * load a super ower castle card into the GUI.
     * @param towerCard
     */
    private void onLoadSuperTowerCard(SuperTowerCard superTowerCard) {
        ImageView superTowerCardView = new ImageView(superTowerCardImage);
        addDragEventHandlers(superTowerCardView, DRAGGABLE_TYPE.SuperTowerCard, cards, squares);
        addEntity(superTowerCard, superTowerCardView);
        cards.getChildren().add(superTowerCardView);
    }

    /**
     * load a village castle card into the GUI.
     * @param villageCard
     */
    private void onLoadVillageCard(VillageCard villageCard) {
        ImageView villageCardView = new ImageView(villageCardImage);
        addDragEventHandlers(villageCardView, DRAGGABLE_TYPE.VillageCard, cards, squares);
        addEntity(villageCard, villageCardView);
        cards.getChildren().add(villageCardView);
    }

    /**
     * load a barracks castle card into the GUI.
     * @param barracksCard
     */
    private void onLoadBarracksCard(BarracksCard barracksCard) {
        ImageView barracksCardView = new ImageView(barracksCardImage);
        addDragEventHandlers(barracksCardView, DRAGGABLE_TYPE.BarracksCard, cards, squares);
        addEntity(barracksCard, barracksCardView);
        cards.getChildren().add(barracksCardView);
    }

    /**
     * load a trap castle card into the GUI.
     * @param trapCard
     */
    private void onLoadTrapCard(TrapCard trapCard) {
        ImageView trapCardView = new ImageView(trapCardImage);
        addDragEventHandlers(trapCardView, DRAGGABLE_TYPE.TrapCard, cards, squares);
        addEntity(trapCard, trapCardView);
        cards.getChildren().add(trapCardView);
    }

    /**
     * load a campfire castle card into the GUI.
     * @param campfireCard
     */
    private void onLoadCampfireCard(CampfireCard campfireCard) {
        ImageView campfireCardView = new ImageView(campfireCardImage);
        addDragEventHandlers(campfireCardView, DRAGGABLE_TYPE.CampfireCard, cards, squares);
        addEntity(campfireCard, campfireCardView);
        cards.getChildren().add(campfireCardView);
    }
    /**
     * load a sword into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param sword
     */
    private void onLoadSword(Sword sword) {
        ImageView swordView = new ImageView(swordImage);
        addDragEventHandlers(swordView, DRAGGABLE_TYPE.Sword, unequippedInventory, equippedItems);
        addEntity(sword, swordView);
        equipInventoryX = sword.getX();
        equipInventoryY = sword.getY();
        unequippedInventory.add(swordView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a stake into the GUI.
     * @param stake
     */
    private void onLoadStake(Stake stake) {
        ImageView stakeView = new ImageView(stakeImage);
        addDragEventHandlers(stakeView, DRAGGABLE_TYPE.Stake, unequippedInventory, equippedItems);
        addEntity(stake, stakeView);
        equipInventoryX = stake.getX();
        equipInventoryY = stake.getY();
        unequippedInventory.add(stakeView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a staff into the GUI.
     * @param staff
     */
    private void onLoadStaff(Staff staff) {
        ImageView staffView = new ImageView(staffImage);
        addDragEventHandlers(staffView, DRAGGABLE_TYPE.Staff, unequippedInventory, equippedItems);
        addEntity(staff, staffView);
        equipInventoryX = staff.getX();
        equipInventoryY = staff.getY();
        unequippedInventory.add(staffView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a armour into the GUI.
     * @param armour
     */
    private void onLoadArmour(Armour armour) {
        ImageView armourView = new ImageView(armourImage);
        addDragEventHandlers(armourView, DRAGGABLE_TYPE.Armour, unequippedInventory, equippedItems);
        addEntity(armour, armourView);
        equipInventoryX = armour.getX();
        equipInventoryY = armour.getY();
        unequippedInventory.add(armourView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a shield into the GUI.
     * @param shield
     */
    private void onLoadShield(Shield shield) {
        ImageView shieldView = new ImageView(shieldImage);
        addDragEventHandlers(shieldView, DRAGGABLE_TYPE.Shield, unequippedInventory, equippedItems);
        addEntity(shield, shieldView);
        equipInventoryX = shield.getX();
        equipInventoryY = shield.getY();
        unequippedInventory.add(shieldView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a helmet into the GUI.
     * @param helmet
     */
    private void onLoadHelmet(Helmet helmet) {
        ImageView helmetView = new ImageView(helmetImage);
        addDragEventHandlers(helmetView, DRAGGABLE_TYPE.Helmet, unequippedInventory, equippedItems);
        addEntity(helmet, helmetView);
        equipInventoryX = helmet.getX();
        equipInventoryY = helmet.getY();
        unequippedInventory.add(helmetView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a the one ring into the GUI.
     * @param theOneRing
     */
    private void onLoadTheOneRing(TheOneRing theOneRing) {
        ImageView theOneRingView = new ImageView(theOneRingImage);
        addDragEventHandlers(theOneRingView, DRAGGABLE_TYPE.TheOneRing, unequippedInventory, equippedItems);
        addEntity(theOneRing, theOneRingView);
        equipInventoryX = theOneRing.getX();
        equipInventoryY = theOneRing.getY();
        unequippedInventory.add(theOneRingView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a tp into the GUI
     * @param tp
     */
    private void onLoadTransport(Transport tp) {
        ImageView view = new ImageView(transportImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.Transport, unequippedInventory, equippedItems);
        addEntity(tp, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * load a anduril into the GUI.
     * @param anduril
     */
    private void onLoadAnduril(Anduril anduril) {
        ImageView andurilView = new ImageView(andurilImage);
        addDragEventHandlers(andurilView, DRAGGABLE_TYPE.Anduril, unequippedInventory, equippedItems);
        addEntity(anduril, andurilView);
        equipInventoryX = anduril.getX();
        equipInventoryY = anduril.getY();
        unequippedInventory.add(andurilView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a Tree Stump into the GUI.
     * @param treeStump
     */
    private void onLoadTreeStump(TreeStump treeStump) {
        ImageView treeStumpView = new ImageView(treeStumpImage);
        addDragEventHandlers(treeStumpView, DRAGGABLE_TYPE.TreeStump, unequippedInventory, equippedItems);
        addEntity(treeStump, treeStumpView);
        equipInventoryX = treeStump.getX();
        equipInventoryY = treeStump.getY();
        unequippedInventory.add(treeStumpView, equipInventoryX, equipInventoryY);
    }

    /**
     * check if the equipped inventory have item with col cordinate
     * @param col
     * @return
     */
    private Node getNodeFromGridPane(int col) {
        for (Node node : equippedItems.getChildren()){
            if (GridPane.getRowIndex(node) == col){
                return node;
            }
        }
        return null;
    }
    
    /**
     * load sword in to equip Inventory in GUI
     * @param sword
     */
    private void onLoadSwordEquipped(Sword sword) {
        ImageView swordView = new ImageView(swordImage);
        addEntity(sword, swordView);
        if (getNodeFromGridPane(0) != null) {
            equippedItems.getChildren().remove(0 , 0);
        }
        equippedItems.add(swordView, 0, 0);
    }
    
    /**
     * load a stake into the GUI.
     * @param stake
     */
    private void onLoadStakeEquipped(Stake stake) {
        ImageView stakeView = new ImageView(stakeImage);
        addEntity(stake, stakeView);
        if (getNodeFromGridPane(0) != null) {
            equippedItems.getChildren().remove(0 , 0);
        }
        equippedItems.add(stakeView, 0, 0);
    }
    
    /**
     * load a staff into the GUI.
     * @param staff
     */
    private void onLoadStaffEquipped(Staff staff) {
        ImageView staffView = new ImageView(staffImage);
        addEntity(staff, staffView);
        if (getNodeFromGridPane(0) != null) {
            equippedItems.getChildren().remove(0 , 0);
        }
        equippedItems.add(staffView, 0, 0);
    }
    
    /**
     * load a armour into the GUI.
     * @param armour
     */
    private void onLoadArmourEquipped(Armour armour) {
        ImageView armourView = new ImageView(armourImage);
        addEntity(armour, armourView);
        if (getNodeFromGridPane(1) != null) {
            equippedItems.getChildren().remove(1 , 0);
        }
        equippedItems.add(armourView, 1, 0);
    }

    /**
     * load a shield into the GUI.
     * @param shield
     */
    private void onLoadShieldEquipped(Shield shield) {
        ImageView shieldView = new ImageView(shieldImage);
        addEntity(shield, shieldView);
        if (getNodeFromGridPane(2) != null) {
            equippedItems.getChildren().remove(2 , 0);
        }
        equippedItems.add(shieldView, 2, 0);
    }

    /**
     * load a helmet into the GUI.
     * @param helmet
     */
    private void onLoadHelmetEquipped(Helmet helmet) {
        ImageView helmetView = new ImageView(helmetImage);
        addEntity(helmet, helmetView);
        if (getNodeFromGridPane(3) != null) {
            equippedItems.getChildren().remove(3 , 0);
        }
        equippedItems.add(helmetView, 3, 0);
    }

    /**
     * load a the one ring into the GUI.
     * @param theOneRing
     */
    private void onLoadTheOneRingEquipped(TheOneRing theOneRing) {
        ImageView theOneRingView = new ImageView(theOneRingImage);
        addEntity(theOneRing, theOneRingView);
        if (getNodeFromGridPane(4) != null) {
            equippedItems.getChildren().remove(4 , 0);
        }
        equippedItems.add(theOneRingView, 4, 0);
    }

    /**
     * load Anduril in to equip Inventory in GUI
     * @param anduril
     */
    private void onLoadAndurilEquipped(Anduril anduril) {
        ImageView andurilView = new ImageView(andurilImage);
        addEntity(anduril, andurilView);
        if (getNodeFromGridPane(0) != null) {
            equippedItems.getChildren().remove(0 , 0);
        }
        equippedItems.add(andurilView, 0, 0);
    }

    /**
     * load TreeStump in to equip Inventory in GUI
     * @param treeStump
     */
    private void onLoadTreeStumpEquipped(TreeStump treeStump) {
        ImageView treeStumpView = new ImageView(treeStumpImage);
        addEntity(treeStump, treeStumpView);
        if (getNodeFromGridPane(2) != null) {
            equippedItems.getChildren().remove(2 , 0);
        }
        equippedItems.add(treeStumpView, 2, 0);
    }

    /**
     * load an enemy into the GUI
     * @param enemy
     */
    private void onLoadDoggie(BasicEnemy doggie) {
        ImageView view = new ImageView(doggieImage);
        addEntity(doggie, view);
        squares.getChildren().add(view);
    }

    /**
     * load an finalBoss into the GUI
     * @param finalBoss
     */
    private void onLoadFinalBoss(BasicEnemy finalBoss) {
        ImageView view = new ImageView(finalBossImage);
        addEntity(finalBoss, view);
        squares.getChildren().add(view);
    }

    /**
     * load an slug into the GUI
     * @param enemy
     */
    private void onLoadSlug(BasicEnemy enemy) {
        ImageView view = new ImageView(slugImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load an healthPotion into the squre GUI
     * @param healthPotion
     */
    private void onLoadSpawnHealthPotion(Equipment healthPotion) {
        ImageView view = new ImageView(spawnHealthPotionImage);
        addEntity(healthPotion, view);
        squares.getChildren().add(view);
    }

    /**
     * load an gold into the squre GUI.
     * @param gold
     */
    private void onLoadSpawnGold(Equipment gold) {
        ImageView view = new ImageView(spawnGoldImage);
        addEntity(gold, view);
        squares.getChildren().add(view);
    }

    /**
     * load a zombie into the GUI.
     * @param enemy
     */
    private void onLoadZombie(BasicEnemy enemy) {
        ImageView view = new ImageView(zombieImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load a vampire into the GUI.
     * @param enemy
     */
    private void onLoadVampire(BasicEnemy enemy) {
        ImageView view = new ImageView(vampireImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load a solider into the GUI.
     * @param soldier
     */
    private void onLoadSoldier(Soldier soldier) {
        ImageView view = new ImageView(soldierImage);
        addEntity(soldier, view);
        soldierHBox.getChildren().add(view);
    }
    
    /**
     * load a building into the GUI
     * @param building
     */
    private void onLoadVampireCastle(VampireCastleBuilding building){
        ImageView view = new ImageView(vampireCastleImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoadZombiePit(ZombiePit building){
        ImageView view = new ImageView(zombiePitImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * load a health potion into the GUI.
     * @param healthPotion
     */
    private void onLoadHealthPotion(HealthPotion healthPotion) {
        ImageView healthPotionView = new ImageView(healthPotionImage);
        addDragEventHandlers(healthPotionView, DRAGGABLE_TYPE.HealthPotion, unequippedInventory, equippedItems);
        addEntity(healthPotion, healthPotionView);
        equipInventoryX = healthPotion.getX();
        equipInventoryY = healthPotion.getY();
        unequippedInventory.add(healthPotionView, equipInventoryX, equipInventoryY);
    }

    /**
     * load a Tower into the GUI
     * @param building
     */
    private void onLoadTower(Tower building){
        ImageView view = new ImageView(towerImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * load a SuperTower into the GUI
     * @param building
     */
    private void onLoadSuperTower(SuperTower building){
        ImageView view = new ImageView(superTowerImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * load a Village into the GUI
     * @param building
     */
    private void onLoadVillage(Village building){
        ImageView view = new ImageView(villageImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoadBarracks(Barracks building){
        ImageView view = new ImageView(barracksImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoadTrap(Trap building){
        ImageView view = new ImageView(trapImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * load a Campfire into the GUI
     * @param building
     */
    private void onLoadCampfire(Campfire building){
        ImageView view = new ImageView(campfireImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType){
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Entity building = null;
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != targetGridPane && db.hasImage()) {

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        SimpleIntegerProperty xs = new SimpleIntegerProperty(0);
                        SimpleIntegerProperty ys = new SimpleIntegerProperty(0);
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType) {
                            case VampireCastleCard:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                VampireCastleBuilding newBuilding1 = (VampireCastleBuilding)convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                onLoadVampireCastle(newBuilding1);
                                equipBGM.play(1);
                                break;
                            case ZombiePitCard:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                ZombiePit newBuilding2 = (ZombiePit)convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                onLoadZombiePit(newBuilding2);
                                equipBGM.play(1);
                                break;
                            case TowerCard:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Tower newBuilding3 = (Tower)convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                onLoadTower(newBuilding3);
                                equipBGM.play(1);
                                break;
                            case SuperTowerCard:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                for(Entity b : world.getBuildingEntity()){
                                    if(b.getX() == x && b.getY() == y) building = b;
                                }
                                if(building != null){
                                    if(building.getClass().getName().equals("unsw.loopmania.Tower")){
                                        world.removeBuilding(building);
                                        onLoadSuperTower((SuperTower)convertCardToBuildingByCoordinates(nodeX, nodeY, x, y));
                                        equipBGM.play(1);
                                        break;
                                    }
                                }
                                System.err.println("if you do not have a tower building, you can not generate a super tower");
                                break;
                            case TrapCard:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Trap newBuilding4 = (Trap)convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                onLoadTrap(newBuilding4);
                                equipBGM.play(1);
                                break;
                            case VillageCard:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Village newBuilding5 = (Village)convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                onLoadVillage(newBuilding5);
                                equipBGM.play(1);
                                break;
                            case BarracksCard:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Barracks newBuilding6 = (Barracks)convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                onLoadBarracks(newBuilding6);
                                equipBGM.play(1);
                                break;
                            case CampfireCard:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Campfire newBuilding7 = (Campfire)convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                onLoadCampfire(newBuilding7);
                                equipBGM.play(1);
                                break;
                            case Sword:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(sword);
                                onLoadSwordEquipped(sword);    
                                equipBGM.play(1);
                                break;
                            case Stake:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(stake);
                                onLoadStakeEquipped(stake);
                                equipBGM.play(1);
                                break;
                            case Staff:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(staff);
                                onLoadStaffEquipped(staff);
                                equipBGM.play(1);
                                break;
                            case Anduril:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);                 
                                removeItemByCoordinates(nodeX, nodeY);
                                Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(anduril);
                                onLoadAndurilEquipped(anduril);
                                equipBGM.play(1);                                
                                break;
                            case Armour:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(armour);
                                onLoadArmourEquipped(armour);
                                equipBGM.play(1);
                                break;
                            case Shield:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                Shield shield = new Shield(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(shield);
                                onLoadShieldEquipped(shield);
                                equipBGM.play(1);
                                break;
                            case TreeStump:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(treeStump);                                   
                                onLoadTreeStumpEquipped(treeStump);
                                equipBGM.play(1);                                
                                break;
                            case Helmet:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                Helmet helmet = new Helmet(new SimpleIntegerProperty(3), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(helmet);
                                onLoadHelmetEquipped(helmet);
                                equipBGM.play(1);
                                break;
                            case TheOneRing:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                TheOneRing theonering = new TheOneRing(new SimpleIntegerProperty(4), new SimpleIntegerProperty(0));
                                targetGridPane.add(image, x, y, 1, 1);
                                world.addEquippedInventoryItem(theonering);               
                                onLoadTheOneRingEquipped(theonering);
                                equipBGM.play(1);                                
                                break;
                            case HealthPotion:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                HealthPotion healthPotion = new HealthPotion(xs, ys);
                                healthPotion.refillHealth(world.getCharacter());
                                equipBGM.play(1);
                                break;
                            case Transport:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                Transport tp = new Transport(xs, ys);
                                tp.tpForward(world);
                                equipBGM.play(1);
                                break;
                            default:
                                break;
                        }
                        
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if(event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Entity convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
                BuildingPathCheck Checker = new BuildingPathCheck();
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType){
                    case VampireCastleCard:
                        draggedEntity.setImage(vampireCastleCardImage);
                        break;
                    case ZombiePitCard:
                        draggedEntity.setImage(zombiePitCardImage);
                        break;
                    case TrapCard:
                        draggedEntity.setImage(trapCardImage);
                        break;
                    case TowerCard:
                        draggedEntity.setImage(towerCardImage);
                        break;
                    case VillageCard:
                        draggedEntity.setImage(villageCardImage);
                        break;
                    case CampfireCard:
                        draggedEntity.setImage(campfireCardImage);
                        break;
                    case BarracksCard:
                        draggedEntity.setImage(barracksCardImage);
                        break;
                    case Sword:
                        draggedEntity.setImage(swordImage);
                        break;
                    case Stake:
                        draggedEntity.setImage(stakeImage);
                        break;
                    case Staff:
                        draggedEntity.setImage(staffImage);
                        break;
                    case Anduril:
                        draggedEntity.setImage(andurilImage);
                        break;
                    case Armour:
                        draggedEntity.setImage(armourImage);
                        break;
                    case Shield:
                        draggedEntity.setImage(shieldImage);
                        break;
                    case TreeStump:
                        draggedEntity.setImage(treeStumpImage);
                        break;
                    case Helmet:
                        draggedEntity.setImage(helmetImage);
                        break;
                    case TheOneRing:
                        draggedEntity.setImage(theOneRingImage);
                        break;
                    default:
                        break;
                }
                
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    int xi = targetGridPane.getColumnIndex(n);
                    int yi = targetGridPane.getRowIndex(n);
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                        if (currentlyDraggedType == DRAGGABLE_TYPE.VampireCastleCard){
                            if(!Checker.VampireCastle(xi,yi)){
                                n.setOpacity(0.8);
                            }
                        }else if(currentlyDraggedType == DRAGGABLE_TYPE.ZombiePitCard){
                            if(!Checker.ZombiePit(xi,yi)){
                                n.setOpacity(0.8);
                           }
                        }else if(currentlyDraggedType == DRAGGABLE_TYPE.VillageCard){
                            if(!Checker.Village(xi,yi)){
                                n.setOpacity(0.2);
                           }
                        }else if(currentlyDraggedType == DRAGGABLE_TYPE.CampfireCard){
                            if(!Checker.Campfire(xi,yi)){
                                n.setOpacity(0.8);
                           }
                        }else if(currentlyDraggedType == DRAGGABLE_TYPE.TowerCard){
                            if(!Checker.Tower(xi,yi)){
                                n.setOpacity(0.8);
                           }
                        }else if(currentlyDraggedType == DRAGGABLE_TYPE.TrapCard){
                            if(!Checker.Trap(xi,yi)){
                                n.setOpacity(0.2);
                           }
                        }else if(currentlyDraggedType == DRAGGABLE_TYPE.BarracksCard){
                            if(!Checker.Barracks(xi,yi)){
                                n.setOpacity(0.2);
                           }
                        }
                    }
                });

                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
                            }
                
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
            
        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case SPACE:
            if (isPaused){
                startTimer();
            }
            else{
                pause();
            }
            break;
        default:
            break;
        }
    }

    /**
     * set a shop switcher the GUI
     * @param mainMenuSwitcher
     */
    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        pause();
        mainMenuSwitcher.switchMenu();
    }

    /**
     * set a shop switcher the GUI
     * @param herosCastleSwitcher
     */
    public void setShopSwitcher(MenuSwitcher herosCastleSwitcher){
        this.herosCastleSwitcher = herosCastleSwitcher;
    }

    @FXML
    private void switchToHerosCastle() throws IOException {
        pause();
        herosCastleSwitcher.switchMenu();
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    equippedItems.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   equippedItems.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }

    /**
     * get world
     */
    public LoopManiaWorld getWorld() {
        return world;
    }
    
    /**
     * set world
     */
    public void setWorld(LoopManiaWorld world) {
        this.world = world;
    }
}
