/*
 * @File Launcher.java
 * @Authors : David González León
 * @Date 20 mars 2021
 */
package ch.heigvd.lab03.server;

import java.io.IOException;

public class Launcher {
   public static void main(String[] args) throws IOException {
      CalculatorServer server = new CalculatorServer();
      server.start();
   }
}
