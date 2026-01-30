/*
Group Members:
- Ariel Penaloza
- Estefania Reyes Carrazco
- Manuel Perez
- Angie Alvarez
Date: 2026-01-29
Enhancement: Multiple Rounds

Contributions Summary:
- Ariel Penaloza: Implemented the multiple-rounds game loop in main().
- Manuel Perez: Added per-round reset logic (deck re-init, shuffle, and card index reset).
- Estefania Reyes Carrazco: Added validated "play again" prompt method.
- Angie Alvarez: Cleaned up noisy debug output and improved input handling (hit/stand shortcuts).
*/

import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King",
            "Ace" };
    private static final int[] DECK = new int[52];
    private static int currentCardIndex = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Added by Ariel Penaloza: wrap a full blackjack game in a loop to support multiple rounds
        boolean playAgain = true;

        while (playAgain) {

            // Added by Manuel Perez: reset state at the start of every round
            currentCardIndex = 0;
            initializeDeck();
            shuffleDeck();

            System.out.println("\n=== New Round ===");

            int playerTotal = dealInitialPlayerCards();
            int dealerTotal = dealInitialDealerCards();

            playerTotal = playerTurn(scanner, playerTotal);

            // Removed early return so the program doesn't exit after a bust
            if (playerTotal > 21) {
                System.out.println("You busted! Dealer wins.");
            } else {
                dealerTotal = dealerTurn(dealerTotal);
                determineWinner(playerTotal, dealerTotal);
            }

            // Added by Estefania Reyes Carrazco: ask if they want to play another round
            playAgain = promptPlayAgain(scanner);
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private static void initializeDeck() {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = i;
        }
    }

    private static void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < DECK.length; i++) {
            int index = random.nextInt(DECK.length);
            int temp = DECK[i];
            DECK[i] = DECK[index];
            DECK[index] = temp;
        }

        // Added by Angie Alvarez: removed deck debug so output stays readable across multiple rounds
        
    }

    private static int dealInitialPlayerCards() {
        int card1 = dealCard();
        int card2 = dealCard();
        System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4] + " and "
                + RANKS[card2] + " of " + SUITS[card2 / 13]);
        return cardValue(card1) + cardValue(card2);
    }

    private static int dealInitialDealerCards() {
        int card1 = dealCard();
        System.out.println("Dealer's card: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4]);
        return cardValue(card1);
    }

    private static int playerTurn(Scanner scanner, int playerTotal) {
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().trim().toLowerCase();

            // Added by Angie Alvarez: accept short inputs for user
            if (action.equals("h"))
                action = "hit";
            if (action.equals("s"))
                action = "stand";

            if (action.equals("hit")) {
                int newCard = dealCard();
                playerTotal += cardValue(newCard);

                // Added by Angie Alvarez: removed noisy debug line ("new card index is ...")
                System.out.println("You drew a " + RANKS[newCard] + " of " + SUITS[DECK[currentCardIndex] % 4]);

                if (playerTotal > 21) {
                    break;
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return playerTotal;
    }

    private static int dealerTurn(int dealerTotal) {
        while (dealerTotal < 17) {
            int newCard = dealCard();
            dealerTotal += cardValue(newCard);
        }
        System.out.println("Dealer's total is " + dealerTotal);
        return dealerTotal;
    }

    private static void determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins!");
        }
    }

    private static int dealCard() {
        return DECK[currentCardIndex++] % 13;
    }

    private static int cardValue(int card) {
        return card < 9 ? card + 2 : 10;
    }

    // Added by Estefania Reyes Carrazco: validated "play again" prompt for multiple rounds
    private static boolean promptPlayAgain(Scanner scanner) {
        while (true) {
            System.out.print("Play another round? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes"))
                return true;
            if (input.equals("n") || input.equals("no"))
                return false;

            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    int linearSearch(int[] numbers, int key) {
        int i = 0;
        for (i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i;
            }
        }
        return -1; // not found
    }
}
