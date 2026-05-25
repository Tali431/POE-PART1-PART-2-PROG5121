package com.mycompany.registration;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class Registration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        MessageStorage storage = new MessageStorage();

        // ── Registration ──────────────────────────────────────────
        System.out.print("Enter First Name: ");
        login.firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        login.surname = scanner.nextLine();

        System.out.print("Enter Username: ");
        login.userName = scanner.nextLine();

        System.out.print("Enter Password: ");
        login.password = scanner.nextLine();

        System.out.print("Enter cellphone number: ");
        login.cellPhone = scanner.nextLine();

        System.out.println(login.registerUser());

        while (!login.checkUsername() || !login.checkPasswordComplexity()) {
            System.out.println("Try to register again!");

            System.out.print("Enter Username: ");
            login.userName = scanner.nextLine();

            System.out.print("Enter Password: ");
            login.password = scanner.nextLine();

            System.out.print("Enter cellphone number: ");
            login.cellPhone = scanner.nextLine();

            System.out.println(login.registerUser());
        }

        // ── Login ─────────────────────────────────────────────────
        System.out.print("Enter Username: ");
        login.enteredUserName = scanner.nextLine();

        System.out.print("Enter Password: ");
        login.enteredPassword = scanner.nextLine();

        System.out.print("Enter cellphone number: ");
        login.enteredCell = scanner.nextLine();

        System.out.println(login.returnLoginStatus());

        while (!login.loginUser()) {
            System.out.println("Try to login again.");

            System.out.print("Enter Username: ");
            login.enteredUserName = scanner.nextLine();

            System.out.print("Enter Password: ");
            login.enteredPassword = scanner.nextLine();

            System.out.print("Enter cellphone number: ");
            login.enteredCell = scanner.nextLine();

            System.out.println(login.returnLoginStatus());
        }

        // ── Menu (JOptionPane) ────────────────────────────────────
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        int total = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
        int count = 0;

        while (true) {
            String menu = JOptionPane.showInputDialog("""
                Please select an option:
                1) Send Messages
                2) Show Recently Sent Messages
                3) Quit
            """);

            switch (menu) {
                case "1":
                    if (count >= total) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                        break;
                    }
                    String recipient = JOptionPane.showInputDialog("Enter recipient number (include + country code):");
                    if (!Message.isValidRecipient(recipient)) {
                        JOptionPane.showMessageDialog(null, "Invalid recipient format.");
                        break;
                    }

                    String msg = JOptionPane.showInputDialog("Enter message (max 250 chars):");
                    if (!Message.isValidMessage(msg)) {
                        JOptionPane.showMessageDialog(null, "Message too long.");
                        break;
                    }

                    Message m = new Message(recipient, msg);
                    String action = JOptionPane.showInputDialog("""
                        Choose message option:
                        1) Send
                        2) Disregard
                        3) Store for later
                    """);

                    switch (action) {
                        case "1" -> {
                            m.send();
                            storage.addMessage(m);
                            count++;
                            JOptionPane.showMessageDialog(null, "Message sent!\n" + m.getMessageDetails());
                        }
                        case "2" -> {
                            m.disregard();
                            JOptionPane.showMessageDialog(null, "Message disregarded.");
                        }
                        case "3" -> {
                            m.store();
                            storage.addMessage(m);
                            count++;
                            JOptionPane.showMessageDialog(null, "Message stored.\n" + m.getMessageDetails());
                        }
                        default -> JOptionPane.showMessageDialog(null, "Invalid action.");
                    }
                    break;

                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;

                case "3":
                    JOptionPane.showMessageDialog(null, "Exiting. Total messages: " + storage.getTotalMessages());
                    storage.saveMessagesToJson("messages.json");
                    scanner.close();
                    return;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid menu option.");
            }
        }
    }
}