package com.narola.onlinefooddeliverysystem.input;

import java.time.LocalTime;
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
        }
        catch (NoSuchElementException e) {
            System.out.println("Enter valid input.");
            input = getNumberInput();
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

    /*public static String timeConst() {
        String input;
        Scanner sc = new Scanner(System.in);
        try {
            input = sc.nextLine();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty!");
                input = timeConst();
            }
            input = String.valueOf(LocalTime.parse(input));
            return input;
        } catch (Exception e) {
            System.out.println("Enter valid input as hh:mm:ss.");
            input = timeConst();
        }
        return input;
    }*/

   /* public static String timeWithEmptyConst() {
        String input;
        Scanner sc = new Scanner(System.in);
        try {
            input = sc.nextLine();
            if (input.isEmpty()) {
                return input;
            }
            input = String.valueOf(LocalTime.parse(input));
            return input;
        } catch (Exception e) {
            System.out.println("Enter valid input as hh:mm:ss.");
            input = timeWithEmptyConst();
        }
        return input;
    }*/

}
