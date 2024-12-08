# ver-0.0.1-ISlots
> This ver is a prototype **Seven Slots** game

# ISlots.java

The `ISlots` interface defines the basic functionalities of a slot game. It includes:

- **Variable `slotSize`**: An array that stores the size of the slots.
- **Map `symbolData`**: Stores the game symbols and their properties (value and weight).

## Methods

- **`setSlotSize(int value)`**: Allows setting the size of the slots.
- **`game()`**: Executes the game logic.

This interface should be implemented by classes that provide specific functionalities for slot-type games.

# SevenSlots Class

The `SevenSlots` class implements the `ISlots` interface and contains the core functionality of the slot game.

## Key Features

- **Total Winnings**: Tracks the total winnings of the player.
- **Static Block**: Initializes the `symbolData` map with various symbols, their values, and weights.
- **Slot Size**: Allows setting the size of the slot machine through the `setSlotSize(int value)` method.
- **Game Logic**: The `game()` method handles the main game loop, allowing players to spin the slots, check for wins, and display results.
- **Random Symbol Generation**: Uses weighted random selection to generate symbols for the slot machine, ensuring a fair distribution based on predefined weights.

## Gameplay

Players can press ENTER to play or type 'exit' to leave the game. The game checks for winning combinations and calculates winnings based on the symbols displayed. The game continues until the player decides to exit.
