package com.narola.onlinefooddeliverysystemV1.input;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHandler {

    public static int getNumberInput() {
        int input;
        Scanner sc = new Scanner(System.in);
        try {
            input = sc.nextInt();
            return input;
        } catch (InputMismatchException e) {
            System.out.println("Enter valid input.");
            input = getNumberInput();
        } catch (NoSuchElementException e) {
            System.out.println("Enter valid input.");
            input = getNumberInput();
        }
        return input;
    }

    public static double getDoubleInput() {
        double input;
        Scanner sc = new Scanner(System.in);
        try {
            input = sc.nextDouble();
            return input;
        } catch (InputMismatchException e) {
            System.out.println("Enter valid input.");
            input = getDoubleInput();
        } catch (NoSuchElementException e) {
            System.out.println("Enter valid input.");
            input = getDoubleInput();
        }
        return input;
    }

    /***
     * Take string input when it is mandatory
     * @return
     */
    public static String getStringInput() {
        return getStringInput(false);
    }

    /***
     * Take string/number input when it is optional
     * @return
     */
    public static String getStringInput(boolean isOptional) {
        String input;
        Scanner sc = new Scanner(System.in);
        try {
            input = sc.nextLine();
            if (!isOptional && input.isEmpty()) {
                System.out.println("Input cannot be empty!");
                input = getStringInput();
            }
            return input;
        } catch (NoSuchElementException e) {
            System.out.println("Enter valid input.");
            input = getStringInput();
        }
        return input;
    }
}
