# Number Guessing Game

A Java console game where the computer picks a secret number and the player tries to guess it within a limited number of attempts.

## Features

- Three difficulty levels:
  - **Easy:** `1`–`50`, `10` attempts, base score `100`
  - **Medium:** `1`–`100`, `7` attempts, base score `200`
  - **Hard:** `1`–`500`, `5` attempts, base score `300`
- `Too low` / `Too high` feedback after each guess
- Input validation (invalid or out-of-range guesses do not cost an attempt)
- Score calculation based on difficulty and attempts used
- Play-again option with the choice to change difficulty
- Non-interactive `--demo` mode

## Compile and Run

```bash
javac NumberGuessingGame.java
java NumberGuessingGame
```

Run demo mode:

```bash
java NumberGuessingGame --demo
```
