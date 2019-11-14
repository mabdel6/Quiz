
/*
Code by: Mario Abdelsayed
12/4/2017

Thisis a quiz program that first requires the user to login using their
credintials and virifies the validity of the credentials by
reading from a file where the credentials are stored.
The program also assigns randomely generated passwords
for each user and stores it in the credentials file. If a student login in, 
the student is able to take the quiz which is generated by pulling questions 
from a file with a bank of questions. If an instructor logs in, the instructor 
is then recognized and given the option to add questions, register students, or 
display statistics such as time spent taking the quiz.
*/

import java.util.*;
import java.io.*;
import java.text.*;


public class Quiz{
  public static void instructor(String role ){
    Scanner scan = new Scanner(System.in);
    int options = 0;
    System.out.println("Enter 1 to register a new student, 2 to display stats,"
            + " 3 to add new questions");
    options  = scan.nextInt();
    while (options < 1 || options > 3){
      System.out.println("Enter a valid response");
      options = scan.nextInt();
    }
   
    
  }
  
  public static void genRandPass(String[] password, String alphabet, int i, int 
          j, int random){
    alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; //a string that stores
    //all letters           
    Random rando = new Random();
    for(i = 0; i < 32; i++){        /*This for loop fills an array with 32
        indexes, each with a uniquely generated random password, one for each
        user.*/                                    
      password[i] = "";
      for(j = 0; j < 6; j++){
        random = rando.nextInt(34);
        password[i] += alphabet.charAt(random);
      }
    }
    
  }
  public static void askQuestions(String ans, int counter, String[] str,
          int rand1, String[] userAnsArray )
          //this method asks students the questions and records 
          //their answers
  {
    while ((ans.toLowerCase().equals("true")== false) &&
            (ans.toLowerCase().equals("false") == false))
    {//Input validation to only accept true or false as an answer
      Scanner scan = new Scanner(System.in);
      System.out.println("Please only enter \"true\" or \"false\"");
            System.out.println("Question " + counter + ": " + str[rand1]); 
            ans = scan.nextLine();
          } 
    userAnsArray[counter-1] = ans;
  }
  public static void writeReport (String firstName, int z, String lastName,
          String currentTime, int counter2, long elapsedTime, String inputUsername,
          String[] userAnsArray, String [] corAnsArray)
          throws IOException //This method displays the report upon instructor
          //request
  {
    PrintWriter infoWriter= new PrintWriter(inputUsername + "_COSC_236_Quiz_" +
            currentTime + ".txt");
    System.out.printf("Quiz report: \n  First name: %s\n Last name: %s\n",
            firstName, lastName); 
    System.out.println("Questions correct: " + counter2);
    System.out.println("Elapsed time: " + elapsedTime + " seconds\n");
    System.out.println("User's answers:");
    infoWriter.println("Quiz report ");
    infoWriter.println("First name: " + firstName);
    infoWriter.println("Last name: " + lastName);
    infoWriter.println("Questions correct: " + counter2);
    infoWriter.println("Elapsed time: " + elapsedTime + " seconds\n");
    infoWriter.println("User's answers:");
    for (z = 0; z < 10; z++){
         infoWriter.println("Question " + (z + 1) + ": " +  userAnsArray[z]);
        }

        infoWriter.println("\nCorrect answers:");
        for (z = 0; z < 10; z++){
           infoWriter.println("Question " + (z + 1) + ": " + corAnsArray[z]);
        }
        infoWriter.close();
        for (z = 0; z < 10; z++){
           System.out.println("Question " + (z + 1) + ": " +  userAnsArray[z]);
         }
        System.out.println("\nCorrect answers:");
        for (z = 0; z < 10; z++){
          System.out.println("Question " + (z + 1) + ": " + corAnsArray[z]);
        }
    
    
    
  }
  
  
  public static void main (String args[]) throws IOException {
    
    
    File info = new File("UsersInfo_103.txt"); //reads from original file
    Scanner infoScan = new Scanner(info); //and creates the scanner for it
    
    File updatedInfo2 = new File("UserInfo_103_Updated.txt"); //creates a new file 
    Scanner infoScan2 = new Scanner(updatedInfo2); //creates the scanner for it
    int i = 0;
    
    PrintWriter infoWrite = new PrintWriter("UserInfo_103_Updated.txt");
    //creates the writer object
    
    String[] password = new String[32];
    String alphabet = "";         
    i = 0;
    int j = 0;
    int random = 0;
    genRandPass(password,alphabet,i,j,random);
    
    
      int passCount = 0;
    int passTitle = 1; //used to ensure no password will be generated for the first row
    //which is occupied by the column headers
    while(infoScan.hasNext()){                                           
      String userInfo = infoScan.nextLine();
      String[] userInfoArr = userInfo.split("\t");                       
      
      
      infoWrite.print(userInfoArr[0]+ "\t"); //prints username column to the new file
      if(passTitle < 2){ //this loop ensures that no password will be
        infoWrite.print("PASSWORD\t"); //will be generated for the first row
        password[passCount] = "";//rather just a column header
        passTitle++;
      }
      else{
        infoWrite.print(password[passCount] + "\t"); // prints and generates passwords to the file
        password[passCount] = "";
      }
      
      infoWrite.print(userInfoArr[1]+ "\t"); //prints first name column to the file
      infoWrite.print(userInfoArr[2]+ "\t"); //prints the last name column to the file
      infoWrite.print(userInfoArr[3]+ "\t"); //prints the email column to the file
      infoWrite.println(userInfoArr[4] + "\t");  //prints the role column to the file
      
      passCount++;
      
    }
    infoWrite.close();
    String inputUsername = ""; //will store user input for username
    String inputPassword =""; //will store user input for password
    
    quizLoop:
      while(!(inputUsername.equalsIgnoreCase("done"))){
          //ensures termination of quiz loop when user enters done
      long elapsedTimeStart = System.nanoTime(); //Time begins counting as quiz begins
      updatedInfo2 = new File("UserInfo_103_Updated.txt");
      infoScan2 = new Scanner(updatedInfo2);
      System.out.println("Please enter your username, then enter a password, enter \'done\' to exit."); 
      Scanner scan2 = new Scanner(System.in);
      inputUsername = scan2.nextLine();
      if(inputUsername.equalsIgnoreCase("done")){//Quiz loop is terminated if user enters done
        System.out.println("Exiting quiz...");
        break quizLoop;
      }
      inputPassword= scan2.nextLine();
      i = 0;
      int exitCounter = 2;
      boolean flag = false;
      
      String[] strInfo = new String[32]; //Stores entire updated user file, line by line
      
      
      for(i = 0; i < 32; i++){
        strInfo[i] = infoScan2.nextLine(); //Assigns each index in array to a new line in the code
      }
      
      
      String firstName = "", lastName = "",email = "", role = "", userAns = "", correctAns = "";
      int score = 0;
      
      whileloop: //
        while(flag==false){
        String[] strInfoArray = new String[6];
        //New array to store each individual column of individual roles as it's own index
        for(j = 0; j < strInfo.length; j++){
          String strInfoArr = strInfo[j];
          //Assigns a row of the file to a string
          
          strInfoArray = strInfoArr.split("\t");
          //String is split by tab into it's own index in our new array
          
          
          
          for(i = 0; i < strInfoArray.length; i++){
            if(strInfoArray[0].equalsIgnoreCase(inputUsername)
                    && !inputUsername.equalsIgnoreCase("username")){
                /*User's information derived from a row in the file, which has been
                       plit into an array by tab*/
              firstName = strInfoArray[2]; // this if statement collects the user info
              lastName = strInfoArray[3]; //when the user enters a correct username
              email = strInfoArray[4];
              role = strInfoArray[5];
             
             
              
              
              if(strInfoArray[1].equals(inputPassword)){
                  //This nested if statement ensures that a flag is set to
                  //true ONLY if username AND password are correct
                  
                flag = true;   //When both username and
                //password is true, the user's info
                //has been verified and a flag is set to true
              
              }
            }
          }
        }
        
        if(flag == true){  //If the flag is true, the while loop breaks,
            //for input validation is done
          break whileloop;
        }
        else{
          while(exitCounter < 5){ //this loop will excute if and only if
              //the user enters an invalid log in
            System.out.println("Please enter a valid username and password."
                    + " Exiting after 3 failed attempts.");
            System.out.println("Attempt " + exitCounter + " out of 3");
            inputUsername = scan2.nextLine();
            inputPassword = scan2.nextLine();
            for(j = 0; j < strInfo.length; j++){
              String strInfoArr = strInfo[j];
              
              strInfoArray = strInfoArr.split("\t");
              
              
              
              for(i = 0; i < strInfoArray.length; i++){
                if(strInfoArray[0].equalsIgnoreCase(inputUsername)&&
                        !inputUsername.equalsIgnoreCase("username")){
                  firstName = strInfoArray[2]; //collectes user info when the user finally enters correct
                  lastName = strInfoArray[3]; //log in info
                  email = strInfoArray[4];
                  role = strInfoArray[5];
                  
                  if(strInfoArray[1].equals(inputPassword)){
                    flag = true;
                    if(flag == true){ //breaks the loop when the password is correct
                      break whileloop;
                    }
                  }
                }
              }
            }
            
            if(exitCounter == 3){ //exits the code after the third login failed attempt
              System.exit(0);
            }
            exitCounter++;
          }
        }
      }
        
        
        
        
        
        
        
        
        Scanner scan = new Scanner(System.in);                                   
        Random randy = new Random();
        int rand = randy.nextInt(10)+1;
        
        //Creates scanners to read questions and answers, and places them both in arrays.
        File file = new File("TestBank.txt");                                    
        Scanner inputFile = new Scanner(file);
        File file2 = new File("Answers.txt");
        Scanner inputFile2 = new Scanner(file2);
        int counter = 1, counter2 = 0;
        String[] questions = new String[10];                                    
        i = 0;
        String[] str  = new String[125];                                         
        while(inputFile.hasNext()){
          str[i] = inputFile.nextLine();
          i++;
        }
        inputFile.close();
        
        i = 0;
        int z = 0;
        String[] str2 = new String[125];
        while(inputFile2.hasNext()){
          str2[i] = inputFile2.nextLine();
          i++;
        }
        inputFile2.close();
        
        
        Random randy1 = new Random();
        Scanner inputFile1 = new Scanner(file);
        
        String[] corAnsArray = new String[10];//stores correct answers in an array
        String[] userAnsArray = new String[10];//stores user's answers in an array
        SimpleDateFormat date = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss");//date/time object
        String currentTime = date.format(new Date());
        while(inputFile1.hasNext() && counter <= 10){
          int rand1 = randy1.nextInt(124);/*uses random object to pick 10 random
          questions, which correlate with 10 random answers, from the same 
           * random indexes of rhe question and answer arrays*/
     
          
          System.out.println("Question " + counter + ": " + str[rand1]);        
          
          String ans = scan.nextLine();
            askQuestions(ans,counter,str,rand1, userAnsArray);
         
          
          
          
          String corAns = str2[rand1];  //Correlates random questions with
          //the same index for random questions
          
          
          if(corAns.equalsIgnoreCase(ans)){            
            counter2++;        //Counts amount of correct answers 
          }
          
          corAnsArray[counter-1] = str2[rand1];
         
          
          counter++;
        }
        inputFile1.close();
        long elapsedTimeDiff = System.nanoTime() - elapsedTimeStart; 
//Substracts initial time from current time to find elapsed time in nanoseconds
        long elapsedTime = elapsedTimeDiff/1000000000;
        //Converts nanoseconds to seconds
        
        
        
        writeReport (firstName, z, lastName, currentTime, counter2, elapsedTime, inputUsername, userAnsArray,corAnsArray);
        
        elapsedTime = 0;
        }
      
      
  }
}