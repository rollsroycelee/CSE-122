package Project1;

// Royce Lee
// 1/30/2024
// CSE 122
// TA: Maggie
// This class prompts the user to add, remove, save, and load their tasks and make their
// own todo list (default). 
// If EXTENSION_FLAG is true, the user can see all of the tasks they finished when marking 
// their tasks as completed.

import java.io.*;
import java.util.*;

public class TodoListManager {
    public static final boolean EXTENSION_FLAG = true;

    public static void main(String[] args) throws FileNotFoundException {
        // TODO: Your Code Here
        
        Scanner console = new Scanner(System.in);
        List <String> todos = new ArrayList <>();
        List <String> finished = new ArrayList <>();

        System.out.println("Welcome to your TODO List Manager!");

        String choice = "";
        while (!choice.equalsIgnoreCase("Q")) {
            System.out.println("What would you like to do?");
            System.out.print("(A)dd TODO, (M)ark TODO as done, " 
                            + "(L)oad TODOs, (S)ave TODOs, (Q)uit? ");
            choice = console.nextLine();

            if (choice.equalsIgnoreCase("A")) {
                // ADD A TODO 
                addItem(console, todos);
                printTodos(todos);

            } else if (choice.equalsIgnoreCase("M")) {
                // MARK TODO AS DONE
                //finished parameter is the list that stores the finished tasks
                markItemAsDone(console, todos, finished);
                printTodos(todos);

            } else if (choice.equalsIgnoreCase("L")) {
                // LOAD TODOS
                loadItems(console, todos);
                printTodos(todos);

            } else if (choice.equalsIgnoreCase("S")) {
                //SAVE THE TODO LIST
                saveItems(console,todos);
                printTodos(todos);

            } else if (!choice.equalsIgnoreCase("Q")) {
                System.out.println("Unknown input: " + choice);
                printTodos(todos);
            }
        }
    }


// Behavior: Prints the total todo list that includes all tasks the user needs to do
// Parameter:
// - List<String> todos: the array that stores all of the tasks in the todo list
// Returns: returns nothing because void method
    public static void printTodos(List<String> todos) {

        System.out.println("Today's TODOs:");
        int size = todos.size();

        if(size >= 1){
            for(int i = 0; i < size; i++){
                System.out.println("  " + (i+1) + ": " + todos.get(i));
            }
        } else { 
            System.out.println("  You have nothing to do yet today! Relax!");
        }
    }


// Behavior: adds the item that the user inputs into the todo list
// Parameter:
// - Scanner console: the user inputs what they want to add into the todo list
// - List<String> todos: the array that stores all of the tasks in the todo list
// Returns: returns nothing because void method
    public static void addItem(Scanner console, List<String> todos) {

        System.out.print("What would you like to add? ");
        String userTodo = console.nextLine();

        if (todos.size() > 0){
            System.out.print("Where in the list should it be (1-" + (todos.size() + 1) + ")?" +
             " (Enter for end): ");
            String location = console.nextLine();
            if (location.equals("")){
                todos.add(userTodo);
            } else {
                int numTodo = Integer.parseInt(location);
                todos.add(numTodo-1, userTodo);
            }
        } else {
            todos.add(userTodo);
        }
    }


// Behavior: 
// EXTENSION FLAG = false behavior: prompts the user to input which task tehy finished and remove
//                  it from the todo list array / total todo list.
// EXTENSION FLAG = true behavior: runs the completedTodos method to input the task that is 
//                  marked as done into the finished arraysList so that it could print the finished
//                  tasks
// Parameter:
// - Scanner console: the user inputs what specific task they finished
// - List<String> todos: the array that stores all of the tasks in the todo list
// - List<String> finished: the array that stores the finished tasks
// Returns: returns nothing because void method
    public static void markItemAsDone(Scanner console, List<String> todos, List<String> finished) {

        if (todos.size() > 0){
            System.out.print("Which item did you complete (1-" + todos.size() + ")? ");
            String input = console.nextLine();
            int num = Integer.parseInt(input);

            //extension part
            if (EXTENSION_FLAG) {
                finished.add(todos.get(num-1));
                completedTodos(finished);
            }
            todos.remove(num - 1);
        } else {
            System.out.println("All done! Nothing left to mark as done!");
        }
    }


// Behavior: loads todo tasks from the saved file into the current todo list
// Parameter:
// - Scanner console: the user inputs what they want to add into the todo list
// - List<String> todos: the array that stores all of the tasks in the todo list
// Returns: returns nothing because void method
    public static void loadItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {

        System.out.print("File name? ");
        String fileName = console.nextLine();
        File inputFile = new File(fileName);
        Scanner fileScan= new Scanner(inputFile); 
        todos.clear();
        
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            todos.add(line);
        }
    }


// Behavior: saves the current list of todo tasks into a file that the user desires
// Parameter:
// - Scanner console: the user inputs the desired name of the file
// - List<String> todos: the array that stores all of the tasks in the todo list
// Returns: returns nothing because void method
    public static void saveItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        PrintStream output = new PrintStream(new File(fileName));

        for (int i = 0; i < todos.size(); i++){
            output.println(todos.get(i));
        }
    }


// Behavior: prints out all of the finished tasks only when EXTENSION_FLAG is true from the
//           finished task array
// Parameter:
// - List<String> finished: the array that stores all of the finished tasks
// Returns: returns nothing because void method
    public static void completedTodos(List <String> finished){

        System.out.println();
        System.out.println("Finished TODOs:");
        int size = finished.size();
        System.out.println();

        for(int i = 0; i < size; i++){
            System.out.println("  " + (i+1) + ": " + finished.get(i));
        }
        System.out.println("Good job on completing these tasks so far!");
        System.out.println();
    }
}