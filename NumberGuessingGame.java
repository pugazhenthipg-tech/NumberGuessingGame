import java.util.Random;
import java.util.Scanner;

/**
 * Task 1 - Number Guessing Game
 *
 * The computer picks a secret number. The player has a limited number of
 * attempts, earns points for guessing quickly, and can play again.
 */
public class NumberGuessingGame {

    static final String[] DIFFICULTY_NAMES = {"Easy", "Medium", "Hard"};
    static final int[] MINIMUM = {1, 1, 1};
    static final int[] MAXIMUM = {50, 100, 500};
    static final int[] MAX_ATTEMPTS = {10, 7, 5};
    static final int[] BASE_SCORE = {100, 200, 300};
    static final int[] PENALTY = {10, 25, 50};

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) {
            runDemo();
        } else {
            runGame();
        }
        scanner.close();
    }

    static void runGame() {
        System.out.println("=== Number Guessing Game ===");

        int difficulty = chooseDifficulty();
        if (difficulty == -1) {
            System.out.println("Input ended. Goodbye!");
            return;
        }

        boolean playAgain = true;
        while (playAgain) {
            if (!playRound(difficulty)) {
                return;
            }

            playAgain = askYesNo("Play again? (y/n): ");
            if (!playAgain) {
                break;
            }

            if (!askYesNo("Keep " + DIFFICULTY_NAMES[difficulty]
                    + " difficulty? (y/n): ")) {
                difficulty = chooseDifficulty();
                if (difficulty == -1) {
                    System.out.println("Input ended. Goodbye!");
                    return;
                }
            }
        }

        System.out.println("Thanks for playing!");
    }

    static int chooseDifficulty() {
        while (true) {
            System.out.println();
            System.out.println("Choose a difficulty:");
            System.out.println("  1. Easy   (1-50,  10 attempts, base 100)");
            System.out.println("  2. Medium (1-100,  7 attempts, base 200)");
            System.out.println("  3. Hard   (1-500,  5 attempts, base 300)");

            String line = readLine("Difficulty (1-3): ");
            if (line == null) {
                return -1;
            }

            try {
                int choice = Integer.parseInt(line.trim());
                if (choice >= 1 && choice <= 3) {
                    return choice - 1;
                }
            } catch (NumberFormatException e) {
                // The message below handles bad input.
            }

            System.out.println("Please enter 1, 2 or 3.");
        }
    }

    /**
     * Plays one round.
     *
     * @return false when input ends, so the caller can stop
     */
    static boolean playRound(int difficulty) {
        int minimum = MINIMUM[difficulty];
        int maximum = MAXIMUM[difficulty];
        int attemptsAllowed = MAX_ATTEMPTS[difficulty];
        int secret = minimum + random.nextInt(maximum - minimum + 1);

        System.out.println();
        System.out.println("I picked a number between " + minimum + " and " + maximum + ".");
        System.out.println("You have " + attemptsAllowed + " attempts.");

        int attemptsUsed = 0;
        boolean won = false;

        while (attemptsUsed < attemptsAllowed && !won) {
            String line = readLine("Your guess: ");
            if (line == null) {
                System.out.println();
                System.out.println("Input ended. Goodbye!");
                return false;
            }

            int guess;
            try {
                guess = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
                continue;
            }

            if (guess < minimum || guess > maximum) {
                System.out.println("Stay between " + minimum + " and " + maximum
                        + ". That guess did not use an attempt.");
                continue;
            }

            attemptsUsed++;

            if (guess == secret) {
                won = true;
                System.out.println("Correct! You got it in "
                        + attemptsUsed + " attempt(s).");
            } else {
                int left = attemptsAllowed - attemptsUsed;
                if (left == 0) {
                    System.out.println("Out of attempts!");
                } else if (guess < secret) {
                    System.out.println("Too low. " + left + " attempt(s) left.");
                } else {
                    System.out.println("Too high. " + left + " attempt(s) left.");
                }
            }
        }

        int score = 0;
        if (won) {
            score = Math.max(10, BASE_SCORE[difficulty]
                    - (attemptsUsed - 1) * PENALTY[difficulty]);
        }

        System.out.println("The number was " + secret + ".");
        System.out.println("Final score: " + score + " point(s).");
        return true;
    }

    static boolean askYesNo(String prompt) {
        while (true) {
            String line = readLine(prompt);
            if (line == null) {
                return false;
            }

            String answer = line.trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }

            System.out.println("Please answer y or n.");
        }
    }

    static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }

    static void runDemo() {
        System.out.println("=== Number Guessing Game (demo) ===");

        int difficulty = 0;
        int secret = 42;
        int low = MINIMUM[difficulty];
        int high = MAXIMUM[difficulty];
        int attempts = 0;

        System.out.println("Easy mode, with the secret fixed at 42 for the demo.");

        while (low <= high) {
            int guess = (low + high) / 2;
            attempts++;

            if (guess == secret) {
                System.out.println("Guess " + guess + " -> Correct!");
                break;
            } else if (guess < secret) {
                System.out.println("Guess " + guess + " -> Too low");
                low = guess + 1;
            } else {
                System.out.println("Guess " + guess + " -> Too high");
                high = guess - 1;
            }
        }

        int score = Math.max(10, BASE_SCORE[difficulty]
                - (attempts - 1) * PENALTY[difficulty]);
        System.out.println("Secret: " + secret + ", attempts: " + attempts
                + ", score: " + score);
    }
}
