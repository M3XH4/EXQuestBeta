# EXQuestBeta

## Overview
EXQuestBeta is a classic, text-based RPG developed in Java. Step into the world of Gaia as a warrior guided by a friendly Spirit Guide. Your adventure will be filled with perilous battles, valuable loot, and mysterious encounters. Engage in turn-based combat, manage your inventory, learn powerful spells, and level up to become a legendary hero. The game saves your progress, allowing you to continue your quest at any time.

## Features
- **Classic Text-Based Adventure:** A nostalgic command-line RPG experience.
- **Turn-Based Combat:** Strategize your moves against a variety of monsters like Slimes, Boars, and Lesser Vampires.
- **Player Progression:** Gain experience points from battles to level up, increasing your health, mana, and attack power.
- **Inventory System:** Collect, equip, and manage a wide range of items including weapons, armor sets (helmet, torso, gloves, leggings, boots), and consumable potions.
- **Spell Casting:** Wield elemental spells like Fireball, Blizzard, and Thunder, each with its own mana cost and incantation.
- **Randomized Scenarios:** Every journey is unique! Roaming the world can lead to surprise battles, a chance to rest at a camp, or an encounter with a travelling merchant.
- **In-Game Economy:** Earn coins from defeating enemies and use them to buy powerful gear or sell unwanted items at the market.
- **Save & Load:** Your progress is automatically saved. The game can be closed and resumed later, picking up right where you left off.
- **Immersive Audio:** Features sound effects for key actions like starting the game, equipping items, and leveling up, powered by the JLayer library.
- **Color-Coded Interface:** Utilizes ANSI colors to enhance readability and highlight important information in the console.

## How to Play

### Prerequisites
- Java Development Kit (JDK)
- The JLayer library (`jl1.0.1.jar`) included in the `libs` directory.

### Running the Game
1.  **Clone the repository:**
    ```sh
    git clone https://github.com/m3xh4/exquestbeta.git
    cd exquestbeta
    ```
2.  **Compile the source files:**
    From the root directory of the project, run the following command to compile all Java files into a `bin` directory.

    ```sh
    javac -d bin -cp "libs/JLayer1.0.1/jl1.0.1.jar" src/*.java
    ```
3.  **Run the game:**
    Execute the main class from the root directory. This ensures the game can locate the necessary sound and data files within the `src/files` directory.

    On Windows:
    ```sh
    java -cp "bin;libs/JLayer1.0.1/jl1.0.1.jar" Adventure
    ```
    On macOS/Linux:
    ```sh
    java -cp "bin:libs/JLayer1.0.1/jl1.0.1.jar" Adventure
    ```

4.  Follow the prompts from the Spirit Guide to start your adventure!

## Gameplay
The game is controlled by typing commands in the console.
- **Start:** When you first run the game, you will be prompted to enter a name and begin your adventure. On subsequent runs, you can continue your saved game.
- **Main Hub:** From the main screen, you have several options:
    - `Equip`: Change your equipped weapon and armor.
    - `Open`: View your inventory and use consumable items.
    - `View`: Look at the spells you have learned.
    - `Roam`: Leave the current area to trigger a new, random event.
- **Events:** Roaming can lead to:
    - **Battle:** Engage in turn-based combat. You can `Attack` with your weapon or use a `Spell`.
    - **Market:** Encounter a merchant to `Buy` and `Sell` goods.
    - **Camping:** A safe place to rest and fully restore your health and mana.
- **Saving:** The game state is saved when you start the adventure. Progress is automatically deleted if you choose to "go back to your world".

## Project Structure
- `Adventure.java`: The main entry point for the application. Initializes the player and starts the main game loop.
- `Player.java`: Represents the user's character, inheriting from `Stats` and managing the `Inventory` and `Grimoire`.
- `Enemy.java`: Defines the base class for monsters and includes specific implementations like `Slime`, `Boar`, and `LesserVampire`.
- `ItemManager.java`: A base class that defines all available items.
    - `Inventory.java`: Manages the player's personal items, including equipped gear.
    - `MarketInventory.java`: Manages the items available for sale from the merchant.
- `ScenarioManager.java`: Manages the different game scenarios (`Battle`, `Market`, `Camping`) and triggers them randomly.
- `Interface.java`: Handles all console input and output, displaying stats, menus, and battle prompts.
- `Stats.java`: A core class holding attributes and methods common to both `Player` and `Enemy`, such as health, mana, level, and attack logic.
- `FileManager.java`: Manages the serialization and deserialization of the `Player` object to save and load game data.
- `SoundManager.java`: Uses the JLayer library to play MP3 sound effects during gameplay.
- `Spells.java` & `SpellManager.java`: Define the available spells and manage which spells the player has learned in their `Grimoire`.

## Dependencies
- **JLayer 1.0.1**: Used for decoding and playing MP3 audio files. The required JAR is included in the `libs/` directory.
