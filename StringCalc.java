package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

class Main {
    static Scanner scanner = new Scanner(System.in);
    static int num;
    static char chars;
    static String result = "";

    public static void main(String[] args) throws Exception {
        System.out.println("Введите выражение [\"a\" + \"b\", \"a\" - \"b\", \"a\" * x, \"a\" / x] где a и b - строки а x - число  от 1 до 10 включительно  + Enter ");
        String userInput = scanner.nextLine();
        chars = serchOperation(userInput);
        getResult(userInput);
    }

    private static void getResult(String userInput) throws Exception {
        String[] values = userInput.split("[+-/*\"]");
        if (values.length == 5) {
            String[] check = userInput.split("");
            if (!check[check.length - 1].equals("\"")) {
                throw new Exception("Неверно");
            }
            String st01 = values[1];
            String st04 = values[4];
            result = getCalculate(st01, st04, chars);

            if (result.length() <= 40) {
                System.out.println(result);
            } else {
                String res = result.substring(0, 40);
                System.out.println(res + "...");
            }
        } else {
            String st01 = values[1];
            String st03 = values[3];
            num = Integer.parseInt(st03);
            result = getCount(st01, num, chars);
            if (num > 10 | num < 1) {
                throw new Exception("Неверно");
            }

            if (result.length() <= 40) {
                System.out.println(result);
            } else {
                String res = result.substring(0, 40);
                System.out.println(res + "...");
            }
        }
    }

    private static char serchOperation(String userInput) {
        char[] symbol = new char[25];
        for (int i = 0; i < userInput.length(); i++) {
            symbol[i] = userInput.charAt(i);
            if (symbol[i] == '+') {
                chars = '+';
            }
            if (symbol[i] == '-') {
                chars = '-';
            }
            if (symbol[i] == '*') {
                chars = '*';
            }
            if (symbol[i] == '/') {
                chars = '/';
            }
        }
        return chars;
    }

    public static String getCalculate(String num1, String num2, char operation) {
        switch (operation) {
            case '+' -> result = num1 + num2;
            case '-' -> {
                result = num1.replaceAll(num2, "");
                {
                    if (num1.length() == num2.length()) {
                        result = "0";
                    }
                }
            }
            case '*', '/' -> System.out.println("Неверно");
            default -> throw new IllegalArgumentException("Неверно");
        }
        return result;
    }

    public static String getCount(String num1, int num, char operation) {
        if (operation == '+' || operation == '-') {
            System.out.println("Неверно");
        } else if (operation == '*') {
            for (int i = 0; i < num; i++) {
                result = result + num1;
            }
        } else if (operation == '/') {
            try {
                int res2 = num1.length() / num;
                if (num1.length() == num) result = "1";
                else {
                    result = num1.substring(0, res2);
                }
            } catch (ArithmeticException | InputMismatchException e) {
                System.out.println("Exception : " + e);
                return result;
            } finally {
                if (num1.length() < num) System.out.println("Неверно");
            }
        } else {
            throw new IllegalArgumentException("Неверно");
        }
        return result;
    }
}