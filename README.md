# Advanced Programming Java Mini Projects

This repository contains Java Mini projects developed for the Advanced Programming (AP) course.

## Projects

### 1. Chat Application
A real-time socket-based chat application using JavaFX.
I created These two Files with
**Features:**
- Client-server communication
- Multi-client support
- Styled chat bubbles
- Real-time messaging

**Technologies:**
- Java
- JavaFX
- Socket Programming
- Threads


### 2. Notepad Application
A lightweight text editor built with JavaFX.
I Created package called notepad and with these three files under it:
Main.java(Main class that extends Application class), TabEditorController.java(Controller class for managing tab editor functionality), TextFileTab.java(Tab class for with save method and constructor)


**Features:**
- Create and edit text files
- New, Open, Save, Rename and Exit files functionality
- CUT, COPY, PASTE functionality
- Multi  Tab support
- Simple editor interface

**Technologies:**
- Java
- JavaFX
- File Handling


### 3. Poker Game
### Game Core Logic
The game creates a full 52-card deck and shuffles it at the start of every round. It deals 2 cards to the player and 2 hidden cards to the dealer. It manages the full poker flow using phases: Pre-Flop, Flop, Turn, River, and Showdown. Community cards are revealed step-by-step as the game progresses.

### Betting System

The game includes a betting system where the player can place bets, call existing bets, or fold the round. All bets are added into a central pot, and the player’s chip balance decreases when betting or calling. Folding immediately ends the player’s participation in the round.

### Poker Rules and Winner Logic

The game evaluates hands using standard poker rankings. It can detect Pair, Two Pair, Three of a Kind, Straight, Flush, Full House, and Four of a Kind. At Showdown, it compares the player and dealer hands to determine the winner based on the strongest hand value.

## User Interface and Experience
The game uses a casino-style JavaFX interface with styled cards, buttons, and a poker-table layout. Dealer cards are hidden until the final showdown, while community cards appear gradually. The interface updates dynamically with pot size, chip balance, and game status, making gameplay interactive and visually clear.


**Technologies:**
- Java
- JavaFX
- OOP Concepts


