import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;

public class Tokenizer {
  
  ArrayList<String> normalizedWordList = new ArrayList<String>();
  
  //constructor - parses from .txt file
  public Tokenizer(String file) { 
    StringBuilder textStore = new StringBuilder();
    String readLine;
    //use try-catch to account for possible exception if file not found
    try (BufferedReader buffRead = new BufferedReader(new FileReader(file))) {
      readLine = buffRead.readLine();   //sets readLine to first line of file
      //append all other lines to file
      while (readLine != null) {
        textStore.append(readLine + '\n');
        readLine = buffRead.readLine();
      }
    }
    catch (IOException e) {
      //print stack trace in case of file not found exception
      e.printStackTrace();
    } 
    separateWords(textStore.toString());
  }
  
  //constructor - parses from array of strings
  public Tokenizer(String[] text) {
    StringBuilder textStore = new StringBuilder();
    //add all lines from string into textStore
    for (int i = 0; i < text.length; i++) {
      textStore.append(text[i] + '\n');   //append each String with a line space between them
    }
    separateWords(textStore.toString());
  }
  
  //Helper - separates words in a string and adds them to normalizedWordList
  private void separateWords(String strText) {
    StringBuilder storeWord = new StringBuilder();  //holds a word to normalize
    //iterate through string, separating words
    for (int i = 0; i < strText.length(); i++) {
      //if character is not illegal character, store to StringBuilder
      if (strText.charAt(i) != ' ' && strText.charAt(i) != '\n' 
            && strText.charAt(i) != '\t' && strText.charAt(i) != '\r')
        storeWord.append(strText.charAt(i));
      //if is illegal character
      else {
        //if stringBuilder not empty, normalize word, append, wipe stringBuilder
        if (storeWord.length() != 0) {
          normalizeWord(storeWord.toString());
          storeWord.delete(0, storeWord.length());
        }
      }  
    }
  }
  
  //Helper - normalizes input string of single word and appends to normalizedWordList
  private void normalizeWord(String word) {
    //puts all letters lower case
    String inString = word.toLowerCase();
    StringBuilder outBuilder = new StringBuilder(); //StringBuilder to remove undersired chars
    //remove all but letters and numbers
    for (int i = 0; i < inString.length(); i++) {
      //if is letter or number
      if ((inString.charAt(i) >= 'a' && inString.charAt(i) <= 'z') || 
          (inString.charAt(i) >= '0' && inString.charAt(i) <= '9'))
        outBuilder.append(inString.charAt(i));
    }
    wordList().add(outBuilder.toString());
  }
  
  //returns ArrayList of normalized words
  public ArrayList<String> wordList() {
    return normalizedWordList;
  } 
}