# Assumptions

## Epic story 1: Character implementation

### 1.1 Fight mode
* the character attacks the nearest enemy first
* the character can only attack one enemy at the same time
* the character get experience, gold and equipment after the battle finishes
* the character stops moving when in fighting
* Character attacks enemies first
* if the defence is greater than the damage, there will be no damge to character, otherwise, the damage to character is: damage - defence

### 1.2 Equipment
* the equipment will be sorted from the latest to the oldest
* the oldest equipment will be dropped automatically when the inventory is full
* the equipment will be automatically marked if it is the best of its kind

### 1.3 Ally soldier
* the soldier attacks the same enemy and at the same time as the character does
* the soldier shares the same position with character
* Character can have at most 2 soldiers

## Epic story 2: Enemy implementation
* All enemies in battle stop moving until the fight finishes
* Enemy can attack soldier and character at the same time
* Enemy attacks first

### 2.1 Doggie
* the doggie can only stun character for one round, while the soldier can still attack the doggie

## Epic story 3: Game's Mode
* human player can only select one mode in a game

### 3.1 Standard Mode
* if no mode is chosen, it will be standard mode
* game will automatically end when character's health bar reaches 0 or charater achieves goal of the world

### 3.2 Survival Mode
* When game starts, everything beside shopping health potion in Hero's Castle and goal behaves the same as the Standard Mode

### 3.3 Berserker Mode
* When game starts, everything beside shopping protective gear (including armour, helmets and shields) in Hero's Castle and goal behaves the same as the Standard Mode

### 3.4 Confusing Mode
* When game starts, everything beside rare items behaves the same as the Standard Mode
* there is only three rare items in the game: the one ring, anduril and treestump

## Epic story 4: Goal
* when goal is met, game will automatically stop
* there is only four type of goals: gold, experience, cycle and bosses

## Epic story 5: Item

### 5.1 Gold
* The gold is in a positive integer form.
* It will be cleared after the game is over.
* The player cannot buy items in debt.

### 5.2 The equipment
* The player can pause the game to replace or equip items.
* The equipments would be effective once it is dragged into the equipment bar.
* There are five equipment slots which are weapon, body armour, shield, cap and ring.
* The items would be stored in the character pack when the character get them.
* The player can drag the equipments from the pack to equipment slot.
* Once the equipments are dragged to the equipment slot, it cannot be sold or drag back to pack slot.
* The equipment would be automatically exchange into gold when the old one be replaced.
* Each equipment is sold for half the purchase price.
* Different equipments attribute can be superposition.
* When the packet slots are run out, no items can be added.
* when the pack is full, cannot buy any items.
* Dropped items will automatically fill the backpack if it has space.
* Equipments of the same type cannot be stacked in a single pack space.

### 5.3 Staff
* The staff only be used when the character fight with more than one enemy.

### 5.4 The one ring
* when the character have less or equal than 0 healt, it will automatically use

### 5.5 Anduril
* this item can triple character's total damage
* it only triple damage against boss(including Doggie and Elan Muske), otherwise, it add a normal scalar damage to character.

### 5.6 Tree Stump
* this item can double character's total defence
* it only double defence against boss(including Doggie and Elan Muske), otherwise, it add a normal scalar defence to character.

### 5.7 Transport
* human player can use Transport to help character directly transport to location of the next 8 squares

## Epic story 6: human player
    
### 6.1 pause game
* player can equip equipments and place buiding while game is paused, except for battle time

### 6.2 create building
* the building can only be placed in suitable area.

### 6.3 buy and sell the items
* the sold price is only half of the bought price.

### 6.4 equip and replace items
* The items can be replaced by player drag the items to the previous position of equipement.
* inventory is sorted from lastest to oldest in time.

## Epic story 7: droped card -> building
* All the cards have possibility dropped when an enemie was killed and can be autometically collected by the character
* there can only be one building on a tile

### 7.1 Zombie Pit & Vampire Castle
* can produce enemie units from both building.

### 7.2 Village & Barracks
* the solider will atuomatically follow the character when the character pass through.

### 7.3 Tower & Trap & Campfire
* the trap will be eliminate after it is used.
* the tower will automatically attack the enemies when enemies enter the shooting radius.

### 7.4 Hero Castle
* the character will start the game from Hero Castle
* Hero Castle cannot present as a card which can be collected.
* the Castle will provide the 'shop' if character get to assigned cycles.
* character can only shop(buy and sell equipments) when they are in the Hero's Castle
* character cannot sell equipped health potion
* character cannot buy/sell all rare items and transport in Hero's Castle

### 7.5 Super tower
* human player can only use super tower card to upgrade tower
* the super tower card can not be placed on any other building
* it can not be placed on position which do not contains tower.