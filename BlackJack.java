/*
Group Members:
- Ariel Penaloza
- Estefania Reyes Carrazco
- Manuel Perez
- Angie Alvarez
Date: 01-29-2026
Enhancement: Multiple Rounds
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

        //Statistics
        int wins = 0;
        int losses = 0;
        int rounds = 0;
        int ties = 0;

        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        //Rounds
        while(playAgain) {
            rounds++;

        //Reset deck each round
        currentCardIndex = 0;
        initializeDeck();
        shuffleDeck();

        System.out.println("\n=== Round " + rounds + " ===");

         int playerTotal = dealInitialPlayerCards();
         int dealerTotal = dealInitialDealerCards();

        playerTotal = playerTurn(scanner, playerTotal);

        if (playerTotal > 21) {
            System.out.println("You busted! Dealer wins.");
            losses++;
            
        } else {
        dealerTotal = dealerTurn(dealerTotal);
        

        String result = determineWinner(playerTotal, dealerTotal);

        //Counting Stadistics
        if(result.equals("win")){
            wins++;
        } else if (result.equals("loss")) {
            losses++;
        } else {
            ties++;
        }
    }
    //Valid response
        while(true){
        System.out.println("Play Again? (y/n): Keep Gambling?");
        String newgame = scanner.nextLine().trim().toLowerCase();
        
        if(newgame.equals("no") || newgame.equals("n")){
            playAgain = false;
            System.out.println("Wins: " + wins);
        	System.out.println("Losses: " + losses);
        	System.out.println("Ties: " + ties);
        	System.out.println("Rounds: " + rounds);
            break;
        } else if (newgame.equals("y") || newgame.equals("yes")) {
            playAgain = true;
            break;
        } else {
            System.out.println("Please type y or n.");
        }
    }
        
        }
        scanner.close();
   }




        //Scanner (Close
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
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {
                int newCard = dealCard();
                playerTotal += cardValue(newCard);
                System.out.println("new card index is " + newCard);
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

    private static String determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
            return "win";
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
            return "tie";
        } else {
            System.out.println("Dealer wins!");
            return "loss";
        }
    }

    private static int dealCard() {
        return DECK[currentCardIndex++] % 13;
    }

    private static int cardValue(int card) {
        return card < 9 ? card + 2 : 10;
    }

    int linearSearch(int[] numbers, int key) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i;
            }
        }
        return -1; // not found
    }
}