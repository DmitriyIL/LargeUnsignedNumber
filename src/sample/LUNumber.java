package sample;

import static java.lang.Math.*;
import static java.util.stream.IntStream.rangeClosed;

class LUNumber {
    private String reversedNumber;

    private int length;

    
    LUNumber(String numberInStr) {
        validation(numberInStr);
    
        this.reversedNumber = reverse(numberInStr);

        length = reversedNumber.length();
    }


    LUNumber(int number) {
        this (String.valueOf(number));
    }

    
    private void validation(String numberInStr) {
        if (numberInStr.isEmpty()) throw new IllegalArgumentException("String in empty");
        if (numberInStr.charAt(0) == '0' && numberInStr.length() > 1) throw new IllegalArgumentException("Incorrect number");

        for (int i = 0; i < numberInStr.length(); i++) {
            char charInNumber = numberInStr.charAt(i);
            if (rangeClosed('0', '9').noneMatch((digit) -> digit == charInNumber))
                throw new IllegalArgumentException("Incorrect number");
        }
    }
    

    String compareWith(LUNumber other) {
        if (this.length > other.length) return "more";
        if (this.length < other.length) return "less";

        for (int i = this.length - 1; i >= 0; i--) {
            if (reversedNumber.charAt(i) > other.reversedNumber.charAt(i)) return "more";
            if (reversedNumber.charAt(i) < other.reversedNumber.charAt(i)) return "less";
        }

        return "equal";
    }


    LUNumber add(LUNumber other) {
        String sum = reverse(sum(this.reversedNumber, other.reversedNumber));
        return new LUNumber(sum);
    }


    private String sum(String num1, String num2) {
        int lengthDifference = abs(num1.length() - num2.length());
        String zeros = new String(new char[lengthDifference]).replace('\0', '0');
        if (num1.length() > num2.length()) num2 += zeros; else num1 += zeros;

        StringBuilder result = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < num1.length(); i++) {
            int digit1 = toInt(num1.charAt(i));
            int digit2 = toInt(num2.charAt(i));
            int interimSum = digit1 + digit2 + remainder;

            int totalDigit = interimSum % 10;
            remainder = interimSum / 10;

            result.append(String.valueOf(totalDigit));
        }

        if (remainder != 0) result.append("1");

        return result.toString();
    }


    LUNumber minus(LUNumber other) {
        String comparison = this.compareWith(other);
        if (comparison.equals("less")) throw new IllegalArgumentException("result is negative");
        if (comparison.equals("equal")) return new LUNumber("0");

        int lengthDifference = abs(this.length - other.length);
        String zeros = new String(new char[lengthDifference]).replace('\0', '0');
        other.reversedNumber += zeros;

        StringBuilder result = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < this.length; i++) {
            int digitsDifference = this.reversedNumber.charAt(i) - other.reversedNumber.charAt(i) - remainder;

            int totalDigit = (digitsDifference < 0) ? digitsDifference + 10 : digitsDifference;
            remainder = (digitsDifference < 0) ? 1 : 0;

            result.append(String.valueOf(totalDigit));
        }

        return new LUNumber(cutZeros(result.toString()));
    }
    

    private String cutZeros(String inputNum) {
        int excessZeros = 0;
        while (inputNum.charAt(inputNum.length() - 1 - excessZeros) == '0') {
            excessZeros++;
        }
        return reverse(inputNum.substring(0, inputNum.length() - excessZeros));
    }


    LUNumber times(LUNumber other) {
        if ("0".equals(this.toString()) || "0".equals(other.toString())) return new LUNumber("0");

        String result = "0";

        for (int i = 0; i < other.length; i++) {
            String newNum = this.timesDigit(other.reversedNumber.charAt(i));

            String zeros = new String(new char[i]).replace('\0', '0');

            result = sum(result, zeros + newNum);
        }

        return new LUNumber(cutZeros(result));
    }


    private String timesDigit(char digit) {
        StringBuilder result = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < reversedNumber.length(); i++) {
            int composition = toInt(reversedNumber.charAt(i)) * toInt(digit) + remainder;

            remainder = composition / 10;

            result.append(String.valueOf(composition % 10));
        }

        if (remainder != 0) result.append(String.valueOf(remainder));

        return result.toString();
    }



    private String reverse(String inputStr) {
        return new StringBuilder(inputStr).reverse().toString();
    }

    private int toInt(char digit) {
        return Integer.valueOf(String.valueOf(digit));
    }

    @Override
    public String toString() {
        return new StringBuilder(reversedNumber).reverse().toString();
    }
}
