package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("Syntaxe: <host> <port>");
        }

        ClientCalc client = new ClientCalc(args[0], new Integer(args[1]));

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        while (true) {
            System.out.print("CLient > ");
            StringBuilder userInput = new StringBuilder(myObj.nextLine());
            userInput.append('\r').append('\n');
            client.sendCalcRequest(userInput.toString());
        }

    }
}
