package Project3;
//Royce Lee
// 2/20/2024
//CSE 122
//TA: Maggie
// This class is similar to Wordle game where the game adjusts the possible total word list.

import java.io.*;
import java.util.*;

public class Absurdle  {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

   
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = pruneDictionary(contents, wordLength);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            String pattern = recordGuess(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
    }

    
    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    
    // Returns true if the game is finished, meaning the user guessed the word. Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }

    
    // Loads the contents of a given file Scanner into a List<String> and returns it.
    // - Scanner dictScan: contains file contents
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }

    

//Behavior:
// - this method reads the words from the provided dictionary 
//Parameter:
// - List<String> contents: this is the arraylist that has all of the words from the txt files
// - int wordLength: this is used to compare the length of the word in the txt file so that 
//                   the program only extracts the desired word length 
//Returns:
// - dictionary: this returns the dictionary that has all of the desired lengthed words
//Exception:
// - When the word length size is less than 1, a new exception is thrown
    public static Set<String> pruneDictionary(List<String> contents, int wordLength) {
        if (wordLength < 1){
            throw new IllegalArgumentException("wordLength is less than 1");
        }
        
        Set<String> dictionary = new HashSet<>();
        int lengthOfWord = 0;

        for (int i = 0; i < contents.size(); i++){
            String testWord = contents.get(i);
            if (testWord.length() == wordLength){
                dictionary.add(testWord);
            }

        }
        return dictionary;
    }

//Behavior:
// - This method takes in the user guess and filters it so that it only leaves the word 
//   that match the common word pattern. If the pattern that was created wasnt already in 
//   the map, it creates a new entry and adds a set into the data map. Uses a helper method 
//   which determines the most frequent pattern in the data map.
//Parameter:
// - String guess: this is users guess which is used
// - Set<String> words: words are the lists that have all of the viable words
// - int wordLength: wordLength is used so that it could filter out the unnecessary words.
//Returns:
// - records: the most common pattern after the guesses
//Exceptions:
// - When the set of word is empty or when the guess does not have the same size as the word,
// a new exception is thrown.
    public static String recordGuess(String guess, Set<String> words, int wordLength) {
        if (words.isEmpty() || guess.length() != wordLength){
            throw new IllegalArgumentException
            ("The set of words is empty or the guess does not have the same size");
        }
//USE A NESTED COLLECTION

        String records = "";
        Map<String, Set<String>> data = new TreeMap<>();
        for (String word : words){
            String patternForDict = patternFor(word, guess);
            if (!data.containsKey(patternForDict)){
                data.put(patternForDict, new TreeSet<>());
            }
            //if it already is in the data
            data.get(patternForDict).add(word);
        }

        records = maxWordPattern(words,data);

        words.clear();
        words.addAll(data.get(records));
        return records;
    }

//Behavior:
// - This method records the highest word pattern that is in the current data nested collection.
//   This method also makes sure that the recordGuess method updates the words set correctly
//Parameter:
// - Set<String> words: words in the text file 
// - Map<String, Set<String>> data: a nested collection map that contains the word as the key
//                                  and the pattern as the value for the corresponding word
//Returns:
// - record: the highest pattern that is in the data so that it could be used 
//           in the recordGuess method
    public static String maxWordPattern(Set<String> words, Map<String, Set<String>> data){

    String records = "";
    int count = 0;
        for(String word2 : data.keySet()){
            Set <String> values = data.get(word2);
            int size = values.size();

            //if the size of the value for that word is greater than the count
            // then make the count equal the size
            if (size > count){
                count = size;
                records = word2;
            }
        }
        return records;
    }

//Behavior:
// - compares the first parameter to the second parameter to create a pattern that shows 
// the correct letters at a specific spot and a correct letter but at the wrong spot
//Parameter:
// - String word: Uses the word that in the first parameter to compare to the guess 
// - String guess: guess is the word that the user guesses which is compared to the correct word 
//Returns:
// - returns a pattern that shows you white for wrong letter, yellow for correct letter but wrong 
//   spot, and green for correct letter at the right spot

    public static String patternFor(String word, String guess) {
        String pattern = "";

        //Create Array List where each element in the guess is an element in the list
        List<String> guessList = new ArrayList<>();
        for (int i = 0; i < guess.length(); i++){
            String guessChar = "" + guess.charAt(i);
            guessList.add(guessChar);
        }

        //Create a Map of Character and Integer
        Map<Character, Integer> match = new HashMap<>();
     
        for (int i = 0; i < word.length(); i++){
            String charToString = "" + word.charAt(i);
            if (!match.containsKey(word.charAt(i))){
                match.put(word.charAt(i), 1);
            } else {
                match.put(word.charAt(i), match.get(word.charAt(i)) + 1);
            }
        }

        //Exact
        for(int i = 0; i < guessList.size(); i++){
            if (guessList.get(i).equals("" + word.charAt(i))){
                guessList.set(i, GREEN);
                match.put(word.charAt(i), match.get(word.charAt(i)) - 1);
            }
        }

        for (int i = 0; i < guessList.size(); i++){
            if (!guessList.get(i).equals(GREEN)) { // Skip already matched greens
                char guessChar = guess.charAt(i);
                if (match.containsKey(guessChar) && match.get(guessChar) > 0) {
                    guessList.set(i, YELLOW);
                    match.put(guessChar, match.get(guessChar) - 1); // Reduce count
                }
            }
        }

        //Sets everying in the guessList that is not green or yellow as gray.
        for (int i = 0; i < guessList.size(); i++) {
            if (!guessList.get(i).equals(GREEN) && !guessList.get(i).equals(YELLOW)) {
                guessList.set(i, GRAY);
            }
        }

        //Turns the array list into a string
        for (int i = 0; i< guessList.size(); i++){
            pattern += guessList.get(i);
        }
        return pattern;
    }
}