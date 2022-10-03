package main;

import client.Client;
import exceptions.ConnectionException;
import exceptions.ConnectionTimeoutException;
import exceptions.InvalidAddressException;

import java.util.Scanner;

public class Main {
    static boolean argPort = false;
    public static void main(String[] args)  {
        String addr  = "";
        int port = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (args.length < 1) {
                System.out.print("enter address: ");
                try {
                    addr = scanner.nextLine();
                } catch (IllegalStateException e) {
                    break;
                }
                System.out.print("enter port: ");
                try {
                    port = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            }else {
                argPort = true;
                port = Integer.parseInt(args[0]);
            }
            Client client = null;
            try {
                client = new Client(addr, port);
            } catch (InvalidAddressException e) {
                System.err.println("Connection fail");
                continue;
            } catch (ConnectionTimeoutException e){
                System.err.println("You have entered the wrong server address, or the server is busy.");
                if (argPort){
                    break;
                }
                continue;
            } catch (ConnectionException e) {
                e.printStackTrace();
                break;
            }
            client.run();
        }
    }
}
