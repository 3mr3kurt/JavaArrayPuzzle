package project4;

import java.util.*;
/**
* This class is a program that lays out how many paths can be taken to get to the end of the supplied array.
* Requirements for the command line input array as follows:
* Elements of the array must be positive integers from 0 to 99.
* The final number at the end of the input array must be 0.
*
* The program expects a valid array, and gives error messages if no valid input is supplied.
* The program prints rows of arrays that illustrate the paths the program took to traverse the input array
* It also prints how many solutions i.e. collections of rows the program found
* @author Emre Kurt
* @version 11/15/2021
*/
public class WayFinder{
  public static void main(String[] args){

    ArrayList<String> inputArray = new ArrayList<String>(Arrays.asList(args));

    if(inputArray.size()==0 || inputArray.isEmpty()){
      System.err.println("Error: the program expects an argument");
      System.exit(0);
    }

    for(int i = 0;i<inputArray.size()-1;i++){
      try{
        Integer.parseInt(inputArray.get(i));
        if(Integer.parseInt(inputArray.get(i))<0 || Integer.parseInt(inputArray.get(i))>99){
          System.err.println("Error: please enter positive integers from 0 to 99");
          System.exit(0);
        }
      }
      catch (NumberFormatException e){
        System.err.println("Error: please enter positive integers from 0 to 99");
        System.exit(0);
      }

    }
    if(!inputArray.isEmpty() && Integer.parseInt(inputArray.get(inputArray.size()-1))!=0){
      System.err.println("Error: the last digit in the array must be 0");
      System.exit(0);
    }

    ArrayList<Integer> checkedIndices = new ArrayList<Integer>();
    ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();

    int[] solutionCounter = {0};

    solve(inputArray, 0, checkedIndices, output, solutionCounter);
    if(solutionCounter[0]==0){
      System.out.println("No way through this puzzle.");
      System.exit(0);
    }
    if(inputArray.size()==1 && Integer.valueOf(inputArray.get(0))==0){
      System.out.println("There is 1 way through this puzzle.");
      System.out.println(inputArray.toString());
    }
    else{
      System.out.println("There are "+solutionCounter[0]+" ways through the puzzle");
    }

  }
  /**
  * Recursively traverses through input array, stores valid paths into an ArrayList (output )of ArrayLists (valid paths of inputArray),
  * and prints out each valid path to the end once the end index is reached.
  *
  *
  * @param inputArray the array supplied in the command line, turned into an ArrayList of Strings in the main method
  * @param index the index of which the program begins traversal, ideally 0 when first called
  * @param checkedIndices an ArrayList of Integer objects that stores the indices already passed, prevents stack overflow and unnecessary loops
  * @param output the ArrayList of ArrayLists that store valid paths to the end of the array, each element being printed out once the recursive break point is reached
  * @param solutionCounter integer array that stores how many solutions one has for the input array i.e. how many times the break point is hit
  *
  * Program prints collection of ArrayLists with 'L' or 'R' appended for each path choice
  */
  public static void solve(ArrayList<String> inputArray, int index, ArrayList<Integer>checkedIndices, ArrayList<ArrayList<String>> output, int[] solutionCounter){

    int end = inputArray.size()-1;

    if(index==end){
      for(ArrayList<String> list : output){
        System.out.println(list.toString());
      }
      System.out.print("\n");
      solutionCounter[0]+=1;
      return;
    }

    Integer nextIndexRight = Integer.valueOf((index + Integer.parseInt(inputArray.get(index))));
    Integer nextIndexLeft = Integer.valueOf((index - Integer.parseInt(inputArray.get(index))));

    if(nextIndexRight <= end && !checkedIndices.contains(nextIndexRight)){
      ArrayList<String> copyOfInput = new ArrayList<String>(inputArray.size());
      for(String str : inputArray){
        str = String.format(" %s ", str);
        copyOfInput.add(str);
      }
      String newPos = String.format(" %s", (copyOfInput.get(index).replaceAll("\\s+", "")+'R'));
      copyOfInput.set(index, newPos);
      output.add(copyOfInput);
      checkedIndices.add(nextIndexRight);
      solve(inputArray, nextIndexRight, checkedIndices, output, solutionCounter);
      checkedIndices.remove(nextIndexRight);
      if(output.size()-1!=0){
        output.remove(output.size()-1);
      }

    }

    if(nextIndexLeft >= 0 && !checkedIndices.contains(nextIndexLeft)){
      ArrayList<String> copyOfInput = new ArrayList<String>(inputArray.size());
      for(String str : inputArray){
        str = String.format(" %s ", str);
        copyOfInput.add(str);
      }
      String newPos = String.format(" %s", (copyOfInput.get(index).replaceAll("\\s+", "")+'L'));
      copyOfInput.set(index, newPos);
      if (checkedIndices.contains(nextIndexRight)){
        output.remove(output.size()-1);
        checkedIndices.remove(checkedIndices.size()-1);
      }
      output.add(copyOfInput);
      checkedIndices.add(nextIndexLeft);
      solve(inputArray, nextIndexLeft, checkedIndices, output, solutionCounter);
      checkedIndices.remove(nextIndexLeft);
      if(output.size()-1!=0){
        output.remove(output.size()-1);
      }

    }

  }

}
