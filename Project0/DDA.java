package Project0;

// Royce Lee 
// 11/20/2024
// CSE 121
// This class prompts the user to play a dancing game where the user 
// chooses how many dance moves they want to take, and also how many games they want to play.
// Additionally it gives an average score between all of the games they decided to play.

import java.util.*;
public class DDA {

    public static final String[] ACTIONS = {"⬆️", "➡️", "⬇️", "⬅️", "⏫"};
    public static final String[] RESPONSES = {"UP", "RIGHT", "DOWN", "LEFT", "JUMP"};
    public static final String STAR = "⭐";

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Welcome to Dance Dance Arrayvolution!");
        System.out.println();

        showActions();
        System.out.println();

        System.out.print("How many rounds you wanna play?: ");
        int response = console.nextInt();
        System.out.println();

        double[] stats = new double[response];

        for (int i = 0; i < response; i++){

            String[] moves = makeMoves(console, rand);
            double playingGame = playGame(console, moves);
            System.out.println();

            double finalScore = endScreen(playingGame, moves.length, i);
            //System.out.println(finalScore);

            stats[i] = finalScore;
            
        }
        overallStatistics(stats);
    }

    //Behavior:
    // - This method prints out all of the possible actions, followed up with the correct response
    //Parameters:
    // - There is no parameters because this method is a void.
    //Returns:
    // - Does not return anything, but only prints our what is in the method
    //Exceptions:
    // - If a parameter is forced into this method, this method will not run properly
    public static void showActions() {
        System.out.println("These are the possible actions and their correct responses:");
        for (int i=0; i < ACTIONS.length; i++){
            System.out.println(ACTIONS[i] + ": " + RESPONSES[i]);

        }
    }

    //Behavior:
    // - This method takes in a response from the user to create an array that includes 
    // - random actions for the amount of number that was inputted by the user
    //Parameters:
    // - scanner console : the user inputs the number of moves that they want to do
    // - Random Rand : produces a random move for the number that the user inputted
    //Returns:
    // - returns an array called moves that includes random dance moves 
    //Exceptions:
    // - When the parameters included are not the same class as scanner and random
    public static String[] makeMoves(Scanner console, Random rand){
        System.out.print("How many moves would you like to play? ");
        int numMoves = console.nextInt();
        System.out.println();
        System.out.println("Let's Dance!");

        int randomMove = 0;
        String[] moves = new String[numMoves];

        for (int i = 0; i < moves.length; i++){
            randomMove = rand.nextInt(ACTIONS.length);
            moves[i] = ACTIONS[randomMove];
            //System.out.println(Arrays.toString(moves));
            //System.out.println("(" + (i+1) + ") " + ACTIONS[i] + ": ");
        }
        return moves;
    }

    //Behavior:
    // - takes in a string and uses a for loop to find the index of the given action
    //Parameters:
    // - String move: this method uses a for loop to read through if the move was the same
    // - as the the action
    //Returns:
    // - if it is equal, it would return the action at the i indice in the array
    // - if it is not present, then it returns -1 meaning a non existing indice so a null
    //Exceptions:
    // - The exception would be if the parameter was not a string, but an integer
    public static int getActionIndex(String move){
        for (int i = 0; i < ACTIONS.length; i++){
            if (move == ACTIONS[i]){
                return i;
            } 
        }
        return -1;
    }

    //Behavior:
    // - This method asks the user to input their dance action in response to the picture
    // - that was shown
    //Parameters:
    // - Scanner console : asks the user to input their move according to the action that was shown
    // - String[] moves : the array that the user made previously when asked for how many
    // - actions they wanted to do
    //Returns:
    // - returns the score that the user got according to their response
    //Exceptions:
    // - When the parameter is not the same array of strings of actions. Or when the input is 
    // - more than one group of characters
    public static double playGame(Scanner console, String[] moves){
        double score = 0;

        for (int i = 0; i < moves.length; i++){
            System.out.print("(" + (i+1) + ") " + moves[i] + ": ");
            String move = console.next();

            int userAction = getActionIndex(moves[i]);
            //System.out.println(move.getClass());
            String response = RESPONSES[userAction];


            if (move.toUpperCase().equals(response.toUpperCase())){
                score += 1;
               
            } else if (move.toUpperCase().contains(response.toUpperCase())) {
                score += 0.5;
            }
        }
        return score;
    }

    //Behavior:
    // - This method prints different number of stars according to the score that they got
    //Parameters:
    // - double score : the score is used to find the score percent to find how many stars
    // - are needed to be printed out
    // - int max score : the max score is needed because the score is divided into this
    // - to find the score percent
    //Returns:
    // - Does not return anythign because it is a void method
    //Exceptions:
    // - if the parameters were incorrectly put in
    public static double endScreen(double score, int maxScore, int rounds){
        double scorePercentage = score / maxScore;
        double finalScore = scorePercentage * 100;
        rounds += 1;

        if (finalScore < 40){
            System.out.println("Woah that was groovy!");
            System.out.println("Round " + rounds + " score : " + STAR + " (" + score + "/" 
                                + maxScore + ")");
        } else if (finalScore < 60){
            System.out.println("Woah that was groovy!");
            System.out.println("Round " + rounds + " score : " + STAR + STAR + " (" + score 
                                + "/" + maxScore + ")");
        } else if (finalScore < 80){
            System.out.println("Woah that was groovy!");
            System.out.println("Round " + rounds + " score : " + STAR + STAR + STAR 
                                + " (" + score + "/" + maxScore + ")");
        } else if (finalScore < 100){
            System.out.println("Woah that was groovy!");
            System.out.println("Round " + rounds + " score : " + STAR + STAR + STAR 
                                + STAR + " (" + score + "/" + maxScore + ")");
        } else { 
            System.out.println("Woah that was groovy!");
            System.out.println("Round " + rounds + " score : " + STAR + STAR 
                                + STAR + STAR + STAR + " (" + score + "/" + maxScore + ")");
            
        }
        return finalScore;
    }

    //Behavior:
    // - This method prints out the over all stats of the dance games when there are multiple
    // - games being played. It prompts the user to input how many games, then computes the
    // - average score between all of the games.
    //Parameters:
    // - double[] stats : this parameter is the stats that is being analyzed to compute 
    // - the average score.
    //Returns:
    // - Does not return anythign because it is a void method
    //Exceptions:
    // - if the parameters were incorrectly put in. Such as an integer and not an array.
    public static void overallStatistics(double[] stats){
        System.out.println("Total rounds played: " + stats.length);
        System.out.println();
        double averageScore = 0;

        for (int i = 0; i < stats.length; i++){
            averageScore += stats[i];
        }
        averageScore = averageScore / stats.length;
        System.out.println("Thanks for playing!");
        //System.out.print("You average score today was: " + averageScore);

        if (averageScore < 40){
            System.out.println("You average score today was: " + averageScore + " " + STAR);
        } else if (averageScore < 60){
            System.out.println("You average score today was: " + averageScore + " " + STAR + STAR);
        } else if (averageScore < 80){
            System.out.println("You average score today was: " + averageScore + " " + STAR 
                                                                                    + STAR + STAR);
        } else if (averageScore < 100){
            System.out.println("You average score today was: " + averageScore + " " + STAR 
                                                                            + STAR + STAR + STAR);
        } else { 
            System.out.println("You average score today was: " + averageScore + " " + STAR 
                                                                    + STAR + STAR + STAR + STAR);
        }
    }
}