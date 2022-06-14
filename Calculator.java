package com.company;

import java.util.Scanner;

enum NumberType{arabic, roman}

class Main{
    public static void main(String[] args){
        int numT = 0;
        float result;
        String line, inputLine, outputLine;

        Operand o1 = new Operand();
        Operand o2 = new Operand();
        Operator opr = new Operator();
        Scanner in = new Scanner(System.in);

        try{
            System.out.println("Input:");
            if ((inputLine = in.nextLine()) == null){
                throw new Exception("Неверный ввод.");
            }
            line = inputLine.trim();
            String[] opers = line.split("[^\\*|\\+|\\-|/]");
            int count = 0;
            for (int i = 0; i < opers.length; i++){
                if(opers[i].length() > 0 )
                    count++;
            }

            if (count != 1){
                throw new Exception(String.format("Недопустимое число операторов: %d", count));
            }
            String[] subs = line.split("\\s*(\\s|\\+|\\*|\\-|/)\\s*");
            if(subs.length != 2){
                throw new Exception(String.format("Недопустимое число операндов: %d", subs.length));
            }
            boolean isArabic1 = o1.IsArabic(subs[0]);
            boolean isArabic2 = o2.IsArabic(subs[1]);
            boolean isRoman1, isRoman2;

            if (isArabic1 != isArabic2){
                throw new Exception("Разные типы операндов\n oперанд1: " + subs[0] + "\n oперанд2: " + subs[1]);
            }else if (isArabic1 && isArabic2){
                numT = NumberType.arabic.ordinal();
            }else if (!isArabic1 && !isArabic2){
                isRoman1 = o1.IsRoman(subs[0]);
                isRoman2 = o2.IsRoman(subs[1]);

                if (!isRoman1){
                    throw new Exception("Неверный первый операнд: " + subs[0]);
                }else if (!isRoman2){
                    throw new Exception("Неверный второй операнд: " + subs[1]);
                }else{
                    numT = NumberType.roman.ordinal();
                }
            }
            opr.FindOperator(line);
            if(opr.pos == 0 || opr.pos == line.length() - 1){
                throw new Exception("Неверная позиция оператора: " + line);
            }

            Calc c = new Calc(numT);
            result = c.GetResult(o1,o2,opr);

            if (numT == NumberType.roman.ordinal()){
                outputLine = o1.ArabicToRoman(result);
            }else{
                outputLine = Float.toString(result);
            }
            System.out.println("Result:\n" + outputLine);
        }

        catch(Exception e){
            System.out.println("Error. " + e.getMessage());
        }
        System.out.print("Press Enter to exit.");
        in.nextLine();
    }
}

class Calc{
    public int dataType;

    public Calc(int type) throws Exception {
        if (type != NumberType.arabic.ordinal() && type != NumberType.roman.ordinal()){
            throw new Exception("Неверный тип данных: %d" + type);
        }
        dataType = type;
    }
    public float GetResult(Operand op1, Operand op2, Operator opr) throws Exception{
        float result;
        switch (opr.symb){
            case '+':
                result = op1.value + op2.value;
                break;
            case '-':
                result = op1.value - op2.value;
                break;
            case '/':
                if(op2.value == 0){
                    throw new Exception("Делить на 0 (ноль) нельзя.");
                }
                result = op1.value / op2.value;
                break;
            case '*':
                result = op1.value * op2.value;
                break;
            default:
                throw new Exception("Unknown operation.");
        }
        return result;
    }
}
class Operand{
    public float value;
    String[] Numbers = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };
    String[] Tens = { "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
    public Operand(){
        value = 0;
    }
    public boolean IsArabic(String str) throws Exception{
        if (str.equals(null) || str.equals(""))
            throw new Exception("Wrong operator.");

        int count = 0;
        char[] arabicNumerals = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        char[] strArr = str.toCharArray();
        for(int k = 0; k < strArr.length; k++){
            for (int i = 0; i < arabicNumerals.length; i++){
                if(strArr[k] == arabicNumerals[i]){
                    count++;
                    break;
                }
            }
        }
        if (count == str.length()){
            if ((str.length() == 2 && (strArr[0] == '0' || strArr[1] != '0')) ||
                    (str.length() == 2 && strArr[0] != '1') ||
                    str.length() > 2 ||
                    (str.length() == 1 && strArr[0] == '0')){
                throw new Exception("Недопустимое число: " + str);
            }
            value = Integer.parseInt(str);
            return true;
        }else{
            return false;
        }
    }
    public boolean IsRoman(String str){
        int i = 1;
        for(String s : Numbers){
            if (str.equalsIgnoreCase(s)){
                break;
            }
            i++;
        }
        if (i > Numbers.length){
            return false;
        }else{
            value = i;
            return true;
        }
    }

    public String ArabicToRoman(float val) throws Exception{
        String snum, tens, items;
        int ival, i, j;
        if (val == 0){
            String message = String.format("Результат вычисления: %.2f%n не может быть выражен римскими цифрами.", val);
            throw new Exception(message);
        }
        ival = (int)val;

        if (ival > 0 && ival <= Numbers.length){
            return Numbers[ival - 1];
        }else if (ival > 10 && ival < 100){
            i = ival / 10;
            tens = Tens[i - 1];
            j = ival - (i * 10);
            if (j == 0)
                items = "";
            else
                items = Numbers[j - 1];

            snum = tens + items;
            return snum;
        }else if (ival == 100){
            return "C";
        }else{
            String message = String.format("Результат вычисления: %.2f%n не может быть выражен римскими цифрами.", val);
            throw new Exception(message);
        }
    }

}

class Operator{
    public char symb;
    public int pos;

    public void FindOperator(String str) throws Exception{
        int i, k;
        char[] operations = new char[] { '+', '-', '*', '/' };
        char[] strArr = str.toCharArray();
        pos = -1;
        for(k = 0; k < strArr.length; k++){
            for (i = 0; i < operations.length; i++){
                if(strArr[k] == operations[i]){
                    pos = k;
                    break;
                }
            }
            if(pos >= 0) break;
        }

        if (pos < 0){
            throw new Exception("Не найден символ операции.");
        }else{
            for(k = pos + 1; k < strArr.length; k++){
                for (i = 0; i < operations.length; i++){
                    if(strArr[k] == operations[i]){
                        throw new Exception("Знаков операции больше одного:\n sign1: " + strArr[pos] +"\n sign2: " + strArr[k]);
                    }
                }
            }
        }
        symb = strArr[pos];
        return;
    }
}