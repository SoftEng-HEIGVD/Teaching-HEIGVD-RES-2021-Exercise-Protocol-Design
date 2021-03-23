/*
 * @File CalculatorServer.java
 * @Authors : David González León
 * @Date 20 mars 2021
 */
package ch.heigvd.lab03.server;

import ch.heigvd.lab03.server.messages.ErrorType;
import ch.heigvd.lab03.server.messages.MessageType;
import ch.heigvd.lab03.server.messages.OperationType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorServer {
   static final Logger LOG = Logger.getLogger(CalculatorServer.class.getName());


   private final int LISTEN_PORT = 5587;

   public void start() throws IOException {
      LOG.info("Starting server");
      Socket clientSocket;
      BufferedReader reader;
      PrintWriter writer;
      ServerSocket serverSocket = new ServerSocket(LISTEN_PORT);
      LOG.log(Level.INFO,"Local ip address : {0}", new Object[]{serverSocket.getLocalSocketAddress()});
      while (true) {
         LOG.info("Start listening to requests...");
         clientSocket = serverSocket.accept();
         LOG.info("New client. Starting to serve this new client");
         reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         writer = new PrintWriter(clientSocket.getOutputStream(), true);
         try {
            communicationFunction(reader, writer);
         } catch (IOException e) {
            writer.println(ErrorType.SYSTEM_ERROR.getMessage());
         }
         reader.close();
         writer.close();
         clientSocket.close();
      }
   }

   private void communicationFunction(BufferedReader reader, PrintWriter writer) throws IOException {
      String[] message = reader.readLine().split(" ");
      if (MessageType.valueOf(message[0]) != MessageType.CONN) {
         writer.println(ErrorType.WRONG_MESSAGE.getMessage());
         return;
      } else {
         writer.println(MessageType.CONN_OK.toString());
      }
      while (true) {
         message = reader.readLine().split(" ");
         if (message.length==0){
            writer.println(ErrorType.SYNTAX_ERROR.getMessage());
            continue;
         }
         if (message[0].equals("q")){
            writer.println("Closing server");
            return;
         }
         switch (MessageType.valueOf(message[0])) {
            case ERROR:
            case CONN_OK:
            case STOP_OK:
            case RES:
               writer.write(ErrorType.WRONG_MESSAGE.getMessage());
               break;
            case CONN:
               writer.println("CONN_OK");
            case STOP:
               writer.println("STOP_OK");
               return;
            case COMPUTE:
               if (message.length != 4) {
                  writer.println(ErrorType.SYNTAX_ERROR.getMessage());
                  break;
               }
               int a = 0;
               int b = 0;
               try {
                  a = Integer.parseInt(message[2]);
                  b = Integer.parseInt(message[3]);
               } catch (NumberFormatException exception) {
                  writer.println(ErrorType.SYNTAX_ERROR.getMessage());
               }
               switch (OperationType.valueOf(message[1])) {
                  case ADD:
                     writer.println("RES " + (a + b));
                     break;
                  case SUB:
                     writer.println("RES " + (a - b));
                     break;
                  case MUL:
                     writer.println("RES " + (a * b));
                     break;
                  case DIV:
                     if (b == 0) {
                        writer.println(ErrorType.COMPUTATION_ERROR.getMessage());
                        break;
                     }
                     writer.println("RES " + (a / b));
                     break;
                  default:
                     writer.println(ErrorType.SYNTAX_ERROR.getMessage());
               }
               break;
            default:
               writer.println(ErrorType.SYNTAX_ERROR.getMessage());
         }
      }
   }


}
