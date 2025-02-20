package Project2;

// Royce
// 2/21/2024
// CSE 122
// TA: Maggie
// This class gives the user options to add, play, print history, and delete history of 
// their songs that they choose to use depending on what the user chooses to do

import java.util.*;

public class MusicPlaylist {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Queue<String> queue = new LinkedList<>();
        Stack<String> history = new Stack<>();
        
        System.out.println("Welcome to the CSE 122 Music Playlist!");

        String choice = "";
        while (!choice.equalsIgnoreCase("Q")) {
            
            showOptions();
            System.out.print("Enter your choice: ");
            choice = console.nextLine();

            if (choice.equalsIgnoreCase("A")) {
                // WHEN THEY ADD THE SONG
                addSong(console, queue);
                System.out.println();
                System.out.println();
                    
            } else if (choice.equalsIgnoreCase("P")) {
                // WHEN PLAY THE SONG
                playSong(queue, history);
                System.out.println();
                System.out.println();

            } else if (choice.equalsIgnoreCase("Pr")) {
                // WHEN THEY PRINT THEIR HISTORY
                printHistory(history);
                System.out.println();
                System.out.println();
                
            } else if (choice.equalsIgnoreCase("C")) {
                // WHEN THEY CLEAR THEIR HISTORY
                clearHistory(history);
                System.out.println();
                System.out.println();
                
            } else if (choice.equalsIgnoreCase("D")) {
                //WHEN THEY DELETE THEIR HISTORY
                deleteHistory(console, history);
                System.out.println();
                
            } else {
                //System.out.println("Invalid choice: " + choice);
                //System.out.println("Please try again");
            }
        }
    }

//Behavior:
// - gives all of the possible options that the user can input for the desired use
//Parameter:
// - no parameter used in this method
//Return:
// - returns nothing because it is a void method that prints the options.
    public static void showOptions(){
        System.out.println("(A) Add song");
        System.out.println("(P) Play song");
        System.out.println("(Pr) Print history");
        System.out.println("(C) Clear history");
        System.out.println("(D) Delete from history");
        System.out.println("(Q) Quit");
        System.out.println();
    }

// Behavior:
// - when the user inputs a, the program will prompt the user to add a desired song
// - which is added into the queue where they will be able to play the song
// Parameter:
// - Scanner console: prompts the user to enter the song name they want
// - Queue<String> queue: the songs that the user inputs will be put in the queue which will be later played
// Return:
// - returns nothing because it is a void method. Only produces the print statements and adds 
// - the songs to the queue
    public static void addSong(Scanner console, Queue<String> queue){
        System.out.print("Enter song name: ");
        String songName = console.nextLine();
        System.out.println("Successfully added " + songName);
        queue.add(songName);
    }

// Behavior:
// - When the user inputs p, the program will take out the song that was added first from
// - the queue and add that into the stack that stores the history of songs played.
// Parameter:
// - Queue<String> queue:  a queue consisting of Strings that has all of the songs that are in
// - queue
// - Stack<String> history: a stack consisting of Strings that has all of the played songs
// Return:
// - Returns nothing because it is a void method.
// Exception:
// - When there is no music in the queue, it will throw an error because without any songs
// - in the queue, there is nothing to play
    public static void playSong(Queue<String> queue, Stack<String> history){
        if (queue.isEmpty()){
            throw new IllegalStateException("There is no music in the queue.");
        }

        System.out.println("Playing song: " + queue.peek());
        history.add(queue.remove());
        

    }

// Behavior:
// -  This method prints all of the played songs which were stored in the history stack
// - prints the history in played order (older is last and recent is on the top)
// - uses an auxiliary stack so that it preserves the history stack while printing it
// Parameter:
// - Stack<String> history: uses the history stack to print out the history stack contents
// Return:
// - Returns nothing because it is a void method.
// Exception: 
// - when there is nothing in the history stack, an error will be thrown because there is nothing
// - to print.
    public static void printHistory(Stack<String> history){
        if (history.isEmpty()){
            throw new IllegalStateException("There is no history");
        }

        Stack<String> aux = new Stack<>();

        while (!history.isEmpty()){
            System.out.println("    " + history.peek());
            aux.push(history.pop());
        }

        while (!aux.isEmpty()){
            history.push(aux.pop());
        }
    }

// Behavior:
// - cleared all of the history stack content
// Parameter:
// - Stack<String> history: uses this stack to clear contents in this stack
// Return:
// - returns nothing because it is a void method
    public static void clearHistory(Stack<String> history){
        history.clear();
    }

// Behavior:
// - This method prompts the user to input a number which is the number of songs they 
// - want to delete from the history. When a negative number is inputted, it would
// - delete from the beginning of the history and when a positive number is inputted,
// - it will delete from the recent history. 
// - Uses an auxiliary stack so that when their response is a negative number, 
// - it preserves the elements inside the stack besides the deleted songs.
// Parameter:
// - Scanner console: the user needs to decide how many and from which side they want to 
// - delete the songs
// - Stack<String> history: uses the contents in the history stack to delete
// Return:
// - returns nothing because it is a void method.
// Exception: 
// - When the response the user inputs is greater than the history stack size, they will
// - not be able to delete more than what there is so an exception is thrown.
    public static void deleteHistory(Scanner console, Stack<String> history){
        System.out.println("A positive number will delete from recent history.");
        System.out.println("A negative number will delete from the beginning of history.");
        System.out.print("Enter number of songs to delete: ");
        int response = Integer.parseInt(console.nextLine());
        int absVal = Math.abs(response);
        Stack<String> aux = new Stack<>();

        if (Math.abs(response) > history.size()){
            throw new IllegalArgumentException("This is greater than the history size.");
        }

        if (response < 0){
            //when they respond with negative number
            while (!history.isEmpty()){
                aux.push(history.pop());
            }
            for (int i = 0; i <absVal; i++){
                aux.pop();
            }
            while(!aux.isEmpty()){
                history.push(aux.pop());
            }

        } else if (response > 0){
            //when they respond with positive number
            for (int i = 0; i < absVal; i++){
                history.pop();
            }
        }
    }
}