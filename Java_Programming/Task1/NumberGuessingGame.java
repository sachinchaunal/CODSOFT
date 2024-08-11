import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int rangeStart = 1;
        int rangeEnd = 100;
        boolean playAgain = true;
        int score = 0;

        while (playAgain) {
            int targetNumber = random.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
            int attempts = 0;
            int maxAttempts = 10;
            boolean guessedCorrectly = false;

            System.out.println("I have generated a number between " + rangeStart + " and " + rangeEnd + ".");
            System.out.println("You have " + maxAttempts + " attempts to guess it.");

            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    guessedCorrectly = true;
                    score++;
                } else if (userGuess > targetNumber) {
                    System.out.println("Your guess is too high.");
                } else {
                    System.out.println("Your guess is too low.");
                }

                if (!guessedCorrectly) {
                    System.out.println("Attempts left: " + (maxAttempts - attempts));
                }
            }

            if (!guessedCorrectly) {
                System.out
                        .println("Sorry, you've used all your attempts. The correct number was " + targetNumber + ".");
            }

            System.out.print("Do you want to play again? (yes/no): ");
            String response = scanner.next();
            playAgain = response.equalsIgnoreCase("yes");

            if (playAgain) {
                System.out.println("Starting a new round...");
            }
        }

        System.out.println("Game over. Your score is: " + score);
        scanner.close();
    }
}
