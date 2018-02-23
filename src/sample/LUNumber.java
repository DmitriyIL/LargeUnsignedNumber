package sample;

import static java.lang.Math.*;
import static java.util.stream.IntStream.rangeClosed;

class LUNumber {
    private String reversedNumber;

    private int length;

    LUNumber(String number) {
        if (number.isEmpty()) throw new IllegalArgumentException("String in empty");
        if (number.charAt(0) == '0' && number.length() > 1) throw new IllegalArgumentException("Incorrect number");

        for (int i = 0; i < number.length(); i++) {
            char charInNumber = number.charAt(i);
            if (rangeClosed('0', '9').noneMatch((digit) -> digit == charInNumber))
                throw new IllegalArgumentException("Incorrect number");
        }

        this.reversedNumber = new StringBuffer(number).reverse().toString();

        length = reversedNumber.length();
    }

    LUNumber(int number) {
        this (String.valueOf(number));
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
        String sum = new StringBuilder(sum(this.toString(), other.toString()))
                .reverse()
                .toString();
        return new LUNumber(sum);
    }

    private String sum(String number1, String number2) {
        int shortestLength = min(number1.length(), number2.length());
        int longestLength = max(number1.length(), number2.length());

        StringBuilder newNumber = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < shortestLength; i++) {
            int digit1 = toInt(number1.charAt(i));
            int digit2 = toInt(number2.charAt(i));
            int digitsSum = digit1 + digit2 + remainder;

            int totalDigit = digitsSum % 10;

            newNumber.append(String.valueOf(totalDigit));

            remainder = digitsSum / 10;
        }

        for (int i = shortestLength; i < longestLength; i++) {
            String biggestNum = (number1.length() > number2.length()) ? number1 : number2;
            int digit1 = toInt(biggestNum.charAt(i));
            int digitsSum = digit1 + remainder;

            int totalDigit = digitsSum % 10;

            newNumber.append(String.valueOf(totalDigit));

            remainder = digitsSum / 10;
        }

        if (remainder != 0) newNumber.append("1");

        return newNumber.toString();
    }

    LUNumber minus(LUNumber other) {
        String comparison = this.compareWith(other);
        if (comparison.equals("less")) throw new IllegalArgumentException("result is negative");
        if (comparison.equals("equal")) return new LUNumber("0");

        StringBuilder newNumber = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < other.length; i++) {
            int digitsDifference = this.reversedNumber.charAt(i) - other.reversedNumber.charAt(i) - remainder;

            int digit = (digitsDifference < 0) ? digitsDifference + 10 : digitsDifference;

            newNumber.append(String.valueOf(digit));

            remainder = (digitsDifference < 0) ? 1 : 0;
        }

        for (int i = other.length; i < this.length; i++) {
            int digitsDifference = toInt(this.reversedNumber.charAt(i)) - remainder;

            int digit = (digitsDifference < 0) ? digitsDifference + 10 : digitsDifference;

            newNumber.append(String.valueOf(digit));

            remainder = (digitsDifference < 0) ? 1 : 0;
        }

        String newNumberInStr = newNumber.reverse().toString();
        int excessZeros = 0;
        while (newNumberInStr.charAt(excessZeros) == '0') {
            excessZeros++;
        }

        return new LUNumber(newNumberInStr.substring(excessZeros));
    }
    
    
    LUNumber times(LUNumber other) {
        if ("0".equals(this.toString()) || "0".equals(other.toString())) return new LUNumber("0");

        String result = "0";

        for (int i = 0; i < other.length; i++) {
            String newNum = this.timesDigit(other.reversedNumber.charAt(i));

            String zeros = new String(new char[i]).replace('\0', '0');

            result = sum(result, zeros + newNum);
        }

        result = new StringBuilder(result).reverse().toString();

        int excessZeros = 0;
        while (result.charAt(excessZeros) == '0') {
            excessZeros++;
        }

        return new LUNumber(result.substring(excessZeros));
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





    private int toInt(char digit) {
        return Integer.valueOf(String.valueOf(digit));
    }

    @Override
    public String toString() {
        return new StringBuilder(reversedNumber).reverse().toString();
    }

}
