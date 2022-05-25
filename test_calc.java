
package calc;

import java.util.InputMismatchException;
import java.util.Scanner;

class Main {

    static int num1, num2;
    static int result;
    static char op;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input ");
        String str = scanner.nextLine();
        char[] chars = new char[10];
        for (int i = 0; i < str.length(); i++) {
            chars[i] = str.charAt(i);
            if (chars[i] == '+') {
                op = '+';
            }
            if (chars[i] == '-') {
                op = '-';
            }
            if (chars[i] == '*') {
                op = '*';
            }
            if (chars[i] == '/') {
                op = '/';
            }
        }
        String char1 = String.valueOf(chars);
        String[] strings = char1.split("[+-/*]");
        String s = strings[0];
        String s1 = strings[1];
        String s3 = s1.trim();
        num1 = romanToNumber(s);
        num2 = romanToNumber(s3);
        if (num1 < 0 && num2 < 0) {
            result = 0;
        } else {
            result = calc(num1, num2, op);

            String resultRoman = convertNumToRoman(result);
            System.out.println("Output ");
            System.out.println(resultRoman);
        }
        num1 = Integer.parseInt(s);
        num2 = Integer.parseInt(s3);
        result = calc(num1, num2, op);
        System.out.println("Output");
        System.out.println(result);
    }

    static String convertNumToRoman(int numArabian) {
        String[] roman = {"O", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};

        return roman[numArabian];
    }

    static int romanToNumber(String roman) throws InputMismatchException {
        if (roman.equals("I")) {
            return 1;
        } else if (roman.equals("II")) {
            return 2;
        } else if (roman.equals("III")) {
            return 3;
        } else if (roman.equals("IV")) {
            return 4;
        } else if (roman.equals("V")) {
            return 5;
        } else if (roman.equals("VI")) {
            return 6;
        } else if (roman.equals("VII")) {
            return 7;
        } else if (roman.equals("VIII")) {
            return 8;
        } else if (roman.equals("IX")) {
            return 9;
        } else if (roman.equals("X")) {
            return 10;
        }
        return -1;
    }

    static int calc(int a, int b, char op) {
        int result = 0;
        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                try {
                    result = a / b;
                } catch (InputMismatchException c) {
                    System.out.println("Exception : " + c);
                    break;
                }
                break;
            default:
                System.out.println("Неверно");
        }
        return result;
    }
}


