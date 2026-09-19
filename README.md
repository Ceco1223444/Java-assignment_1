# Java Assignment 1 – Adversarial Search (Blocker)

This folder is part of a school project for the Symbolic AI course at Leiden University.

**Author:** Tsvetelin Simeonov – tsvetelintsvetelinovsimoenov@gmail.com

## What the project is about

The assignment is to build an agent that plays a small invented game called **Blocker**.
Two agents (A and B) compete in a maze to collect the most food. Each turn an agent picks
one of six moves: `up`, `down`, `left`, `right`, `eat` (collect food on the current cell) or
`block` (drop a wall on the current cell). The game ends when all food is gone or when one
agent has no legal move left; you win by collecting more food or by trapping the other agent.

The maze is loaded from a text file (`data/board.txt`), e.g.:

```
5 5
#####
##* #
##A*#
# B##
#####
```

`#` = wall, `*` = food, `A`/`B` = agent start positions.

### Parts of the assignment

- **1a – Game state** (`State.java`): read the board, print it, deep-copy it, compute legal
  moves, execute a move, detect terminal states (leaf) and compute the win/tie/loss value
  (`1`, `0`, `-1`) from an agent's perspective. `UnitTests.java` checks this part.
- **1b – Search** (`Game.java`): implement recursive depth-first **minimax** with a depth
  cut-off, then extend it with **alpha-beta pruning** and compare the number of visited nodes.

The full assignment description is in [`Symbolic_AI__Adversarial_Minimax.pdf`](Symbolic_AI__Adversarial_Minimax.pdf).

## Project layout

```
src/adversarialsearch/   Java sources (Main, Game, State, UnitTests)
data/board.txt           starting board
bin/                     Eclipse compiled output
```

Open the folder in Eclipse (File > Import > Existing Projects) and run `Main`, or run
`UnitTests` to check the `State` implementation.
