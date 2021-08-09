package unsw.loopmania;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;

public class HerosCastleController {
    @FXML
    private AnchorPane shopAnchorPaneRoot;
    @FXML
    private Label goldLabel;
    @FXML
    private ImageView swordImageView;
    @FXML
    private ImageView stakeImageView;
    @FXML
    private ImageView staffImageView;
    @FXML
    private ImageView healthPotionImageView;
    @FXML
    private ImageView armourImageView;
    @FXML
    private ImageView shieldImageView;
    @FXML
    private ImageView helmetImageView;
    @FXML
    private ImageView doggieCoinImageView;
    @FXML
    private Button buySwordButton;
    @FXML
    private Button buyStakeButton;
    @FXML
    private Button buyStaffButton;
    @FXML
    private Button buyHealthPotionButton;
    @FXML
    private Button buyArmourButton;
    @FXML
    private Button buyShieldButton;
    @FXML
    private Button buyHelmetButton;
    @FXML
    private Button sellSwordButton;
    @FXML
    private Button sellStakeButton;
    @FXML
    private Button sellStaffButton;
    @FXML
    private Button sellHealthPotionButton;
    @FXML
    private Button sellArmourButton;
    @FXML
    private Button sellShieldButton;
    @FXML
    private Button sellHelmetButton;
    @FXML
    private Button sellDoggieCoinButton;
    @FXML
    private Label doggieCoinLabel;

    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;
    private LoopManiaWorld world;
    private AudioClip buyBGM;
    private AudioClip sellBGM;
    private Image swordImage;
    private Image stakeImage;
    private Image staffImage;
    private Image healthPotionImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image doggieCoinImage;

    private Entity sword;
    private Entity stake;
    private Entity staff;
    private Entity armour;
    private Entity shield;
    private Entity helmet;
    private Entity healthPotion;
    
    public HerosCastleController(LoopManiaWorld world) {
        this.world = world;
        SimpleIntegerProperty a = new SimpleIntegerProperty(-1);
        SimpleIntegerProperty b = new SimpleIntegerProperty(-1);
        this.sword = new Sword(a, b);
        this.stake = new Stake(a, b);
        this.staff = new Staff(a, b);
        this.armour = new Armour(a, b);
        this.shield = new Shield(a, b);
        this.helmet = new Helmet(a, b);
        this.healthPotion = new HealthPotion(a, b);
    }
    
    @FXML
    public void initialize() {
        swordImage = new Image((new File("src/images/sword.png")).toURI().toString(), 75, 60, false, false);
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString(), 75, 60, false, false);
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString(), 75, 60, false, false);
        healthPotionImage = new Image((new File("src/images/healthPotion.png")).toURI().toString(), 75, 60, false, false);
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString(), 75, 60, false, false);
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString(), 60, 60, false, false);
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString(), 75, 60, false, false);
        doggieCoinImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString(), 60, 60, false, false);
        
        swordImageView.setImage(swordImage);
        stakeImageView.setImage(stakeImage);
        staffImageView.setImage(staffImage);
        healthPotionImageView.setImage(healthPotionImage);
        armourImageView.setImage(armourImage);
        shieldImageView.setImage(shieldImage);
        helmetImageView.setImage(helmetImage);
        doggieCoinImageView.setImage(doggieCoinImage);
        
        doggieCoinLabel.setText("quantity: " + world.getItem().getDoggieCoin());
        sellDoggieCoinButton.setText("Sell $" + world.getItem().getPriceOfDoggieCoin());
        buyBGM = new AudioClip((new File("src/audio/pick.mp3")).toURI().toString());
        sellBGM = new AudioClip((new File("src/audio/gold.mp3")).toURI().toString());
        showButtons();
    }

    /**
     * switcher to game scene
     * @param gameSwitcher of menu
     */
    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    /**
     * OnAction method for buying sword
     */
    @FXML
    public void buySword() {
        world.getHerosCastle().buyEquipment(world, ShopItem.SWORD, world.getItem());
        buyBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for buying stake
     */
    @FXML
    public void buyStake() {
        world.getHerosCastle().buyEquipment(world, ShopItem.STAKE, world.getItem());
        buyBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for buying staff
     */
    @FXML
    public void buyStaff() {
        world.getHerosCastle().buyEquipment(world, ShopItem.STAFF, world.getItem());
        buyBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for buying health potion
     */
    @FXML
    public void buyHealthPotion() {
        world.getHerosCastle().buyEquipment(world, ShopItem.HEALTHPOTION, world.getItem());
        buyBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for buying armour
     */
    @FXML
    public void buyArmour() {
        world.getHerosCastle().buyEquipment(world, ShopItem.ARMOUR, world.getItem());
        buyBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for buying shield
     */
    @FXML
    public void buyShield() {
        world.getHerosCastle().buyEquipment(world, ShopItem.SHIELD, world.getItem());
        buyBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for buying helmet
     */
    @FXML
    public void buyHelmet() {
        world.getHerosCastle().buyEquipment(world, ShopItem.HELMET, world.getItem());
        buyBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for selling sword
     */
    @FXML
    public void sellSword() {
        Entity sellEntity = new Sword(new SimpleIntegerProperty(-1),new SimpleIntegerProperty(-1));
        world.getHerosCastle().sellEquipment(world, sellEntity, world.getItem());
        sellBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for selling stake
     */
    @FXML
    public void sellStake() {
        Entity sellEntity = new Stake(new SimpleIntegerProperty(-1),new SimpleIntegerProperty(-1));
        world.getHerosCastle().sellEquipment(world, sellEntity, world.getItem());
        sellBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for selling staff
     */
    @FXML
    public void sellStaff() {
        Entity sellEntity = new Staff(new SimpleIntegerProperty(-1),new SimpleIntegerProperty(-1));
        world.getHerosCastle().sellEquipment(world, sellEntity, world.getItem());
        sellBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for selling health potion
     */
    @FXML
    public void sellHealthPotion() {
        Entity sellEntity = new HealthPotion(new SimpleIntegerProperty(-1),new SimpleIntegerProperty(-1));
        world.getHerosCastle().sellEquipment(world, sellEntity, world.getItem());
        sellBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for selling armour
     */
    @FXML
    public void sellArmour() {
        Entity sellEntity = new Armour(new SimpleIntegerProperty(-1),new SimpleIntegerProperty(-1));
        world.getHerosCastle().sellEquipment(world, sellEntity, world.getItem());
        sellBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for selling shield
     */
    @FXML
    public void sellShield() {
        Entity sellEntity = new Shield(new SimpleIntegerProperty(-1),new SimpleIntegerProperty(-1));
        world.getHerosCastle().sellEquipment(world, sellEntity, world.getItem());
        sellBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for selling helmet
     */
    @FXML
    public void sellHelmet() {
        Entity sellEntity = new Helmet(new SimpleIntegerProperty(-1),new SimpleIntegerProperty(-1));
        world.getHerosCastle().sellEquipment(world, sellEntity, world.getItem());
        sellBGM.play(1);
        showButtons();
    }

    /**
     * OnAction method for selling doggie coin
     */
    @FXML
    public void sellDoggieCoin() {
        world.getHerosCastle().sellDoggieCoin(world);
        sellBGM.play(1);
        showButtons();
    }

    /**
     * enable and disable buttons in shop page, 
     * whenever tha character is egligible to buy or sell an equipment
     */
    public void showButtons() {
        int gold = world.getItem().getGold();
        goldLabel.setText("$" + gold);
        doggieCoinLabel.setText("quantity: " + world.getItem().getDoggieCoin());
        sellDoggieCoinButton.setText("Sell $" + world.getItem().getPriceOfDoggieCoin());
 
        if (gold < ShopItem.SWORD.getBuyingPrice()) {
            buySwordButton.setDisable(true);
        } else if (buySwordButton.isDisabled()) {
            buySwordButton.setDisable(false);
        }
        if (gold < ShopItem.STAKE.getBuyingPrice()) {
            buyStakeButton.setDisable(true);
        } else if (buyStakeButton.isDisabled()) {
            buyStakeButton.setDisable(false);
        }
        if (gold < ShopItem.STAFF.getBuyingPrice()) {
            buyStaffButton.setDisable(true);
        }  else if (buyStaffButton.isDisabled()) {
            buyStaffButton.setDisable(false);
        }
        if (gold < ShopItem.HEALTHPOTION.getBuyingPrice()) {
            buyHealthPotionButton.setDisable(true);
        } else if (buyHealthPotionButton.isDisabled()) {
            buyHealthPotionButton.setDisable(false);
        }
        if (gold < ShopItem.ARMOUR.getBuyingPrice()) {
            buyArmourButton.setDisable(true);
        } else if (buyArmourButton.isDisabled()) {
            buyArmourButton.setDisable(false);
        }
        if (gold < ShopItem.SHIELD.getBuyingPrice()) {
            buyShieldButton.setDisable(true);
        } else if (buyShieldButton.isDisabled()) {
            buyShieldButton.setDisable(false);
        }
        if (gold < ShopItem.HELMET.getBuyingPrice()) {
            buyHelmetButton.setDisable(true);
        } else if (buyHelmetButton.isDisabled()) {
            buyHelmetButton.setDisable(false);
        }

        if (!world.getHerosCastle().checkUnequippedEquipment(world, sword) &&
            !world.getHerosCastle().checkEquippedEquipment(world, sword)
        ) {
            sellSwordButton.setDisable(true);
        } else if (sellSwordButton.isDisabled()) {
            sellSwordButton.setDisable(false);
        }
        if (!world.getHerosCastle().checkUnequippedEquipment(world, stake) &&
            !world.getHerosCastle().checkEquippedEquipment(world, stake)
        ) {
            sellStakeButton.setDisable(true);
        } else if (sellStakeButton.isDisabled()) {
            sellStakeButton.setDisable(false);
        }
        if (!world.getHerosCastle().checkUnequippedEquipment(world, staff) &&
            !world.getHerosCastle().checkEquippedEquipment(world, staff)
        ) {
            sellStaffButton.setDisable(true);
        } else if (sellStaffButton.isDisabled()) {
            sellStaffButton.setDisable(false);
        }
        if (!world.getHerosCastle().checkUnequippedEquipment(world, armour) &&
            !world.getHerosCastle().checkEquippedEquipment(world, armour)
        ) {
            sellArmourButton.setDisable(true);
        } else if (sellArmourButton.isDisabled()) {
            sellArmourButton.setDisable(false);
        }
        if (!world.getHerosCastle().checkUnequippedEquipment(world, shield) &&
            !world.getHerosCastle().checkEquippedEquipment(world, shield)
        ) {
            sellShieldButton.setDisable(true);
        } else if (sellShieldButton.isDisabled()) {
            sellShieldButton.setDisable(false);
        }
        if (!world.getHerosCastle().checkUnequippedEquipment(world, helmet) &&
            !world.getHerosCastle().checkEquippedEquipment(world, helmet)
        ) {
            sellHelmetButton.setDisable(true);
        } else if (sellHelmetButton.isDisabled()) {
            sellHelmetButton.setDisable(false);
        }
        if (!world.getHerosCastle().checkUnequippedEquipment(world, healthPotion) &&
            !world.getHerosCastle().checkEquippedEquipment(world, healthPotion)
        ) {
            sellHealthPotionButton.setDisable(true);
        } else if (sellHealthPotionButton.isDisabled()) {
            sellHealthPotionButton.setDisable(false);
        }
        if (world.getItem().getDoggieCoin() <= 0) {
            sellDoggieCoinButton.setDisable(true);
        } else if (sellDoggieCoinButton.isDisable()) {
            sellDoggieCoinButton.setDisable(false);
        }
    }
}
