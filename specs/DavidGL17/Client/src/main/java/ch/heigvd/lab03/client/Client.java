/*
 * @File Client.java
 * @Authors : David González León
 * @Date 23 mars 2021
 */
package ch.heigvd.lab03.client;

import ch.heigvd.lab03.server.messages.ErrorType;
import ch.heigvd.lab03.server.messages.MessageType;
import ch.heigvd.lab03.server.messages.OperationType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

   private final static String serverAdress = "localhost";
   private final static int serverPort = 5587;


   public static void main(String[] args) {
      System.out.println("Welcome to the client of the Calculator application.\nConnecting to the server...");
      try {
         Socket socket = new Socket(serverAdress, serverPort);
         BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter os = new PrintWriter(socket.getOutputStream(), true);
         os.println("CONN");
         String response = is.readLine();
         if (!response.equals("CONN_OK")) {
            System.out.println("Wrong server answer");
            return;
         }
         System.out.println("Connection to the server succesful! You can now write the formulas you want using the " +
                            "following operators : +, -, *, /. please make sur to put spaces between the numbers and " +
                            "the operators (for example write 4 + 5 and not 4+5). Enter q to stop");
         BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
         String input = "";
         String[] inputSplit;
         String message;
         input = userInput.readLine();
         while (!input.equals("q")) {
            inputSplit = input.split(" ");
            if (inputSplit.length != 3) {
               System.out.println("Please enter a valid equation");
               continue;
            }
            message = MessageType.COMPUTE.name();
            switch (inputSplit[1]) {
               case "+":
                  message += " " + OperationType.ADD.name();
                  break;
               case "-":
                  message += " " + OperationType.SUB.name();
                  break;
               case "*":
                  message += " " + OperationType.MUL.name();
                  break;
               case "/":
                  message += " " + OperationType.DIV.name();
                  break;
            }
            message += " " + inputSplit[0] + " " + inputSplit[2];
            os.println(message);
            response = is.readLine();
            String[] responseSplit = response.split(" ");
            if (responseSplit.length < 2) {
               throw new IOException();
            }
            switch (MessageType.valueOf(responseSplit[0])) {
               case RES:
                  System.out.println("The answer is : " + responseSplit[1]);
                  break;
               case ERROR:

                  switch (ErrorType.getErrorWithMessage(response)) {
                     case COMPUTATION_ERROR:
                     case SYNTAX_ERROR:
                        System.out.println("Please enter a valid equation");
                        break;
                     case WRONG_MESSAGE:
                        System.out.println("Error in syntax");
                        break;
                     case SYSTEM_ERROR:
                        System.out.println("The server had an error and had to shutdown.");
                        break;
                  }
            }
            input = userInput.readLine();
         }

      } catch (UnknownHostException e) {
         e.printStackTrace();
         System.out.println("The server adress was wrong, closing the app");
      } catch (ConnectException e) {
         System.out.println("There was an issue connecting to the server, closing the app");
      } catch (IOException e) {
         System.out.println("There was an issue while talking to the server, closing the app");
         e.printStackTrace();
      }
   }
}
