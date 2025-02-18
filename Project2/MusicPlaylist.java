package Project2;

//Royce Lee
//

import java.util.*;

public class MusicPlaylist {
    // TODO: Your Code Here
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Queue<String> queue = new LinkedList<>();
        //Stack<String> stack = new Stack<>();
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

    public static void showOptions(){
        System.out.println("(A) Add song");
        System.out.println("(P) Play song");
        System.out.println("(Pr) Print history");
        System.out.println("(C) Clear history");
        System.out.println("(D) Delete from history");
        System.out.println("(Q) Quit");
        System.out.println();
    }

    public static void addSong(Scanner console, Queue<String> queue){
        System.out.print("Enter song name: ");
        String songName = console.nextLine();
        System.out.println("Successfully added " + songName);
        queue.add(songName);
    }

    public static void playSong(Queue<String> queue, Stack<String> history){
        if (queue.isEmpty()){
            throw new IllegalStateException("There is no music in the queue.");
        }

        System.out.println("Playing song: " + queue.peek());
        history.add(queue.remove());
        

    }

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

    public static void clearHistory(Stack<String> history){
        history.clear();
    }


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