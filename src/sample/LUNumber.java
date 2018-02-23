package sample;

import static java.lang.Math.*;
import static java.util.stream.IntStream.rangeClosed;

class LUNumber {
    private String numberInStr;

    private int length;

    LUNumber(String number) {
        if (number.isEmpty()) throw new IllegalArgumentException("String in empty");
        if (number.charAt(0) == '0' && number.length() > 1) throw new IllegalArgumentException("Incorrect number");

        for (int i = 0; i < number.length(); i++) {
            char charInNumber = number.charAt(i);
            if (rangeClosed('0', '9').noneMatch((digit) -> digit == charInNumber))
                throw new IllegalArgumentException("Incorrect number");
        }

        this.numberInStr = new StringBuffer(number).reverse().toString();

        length = numberInStr.length();
    }

    LUNumber(int number) {
        this (String.valueOf(number));
    }


    String compareWith(LUNumber other) {
        if (this.length > other.length) return "more";
        if (this.length < other.length) return "less";

        for (int i = this.length - 1; i >= 0; i--) {
            if (numberInStr.charAt(i) > other.numberInStr.charAt(i)) return "more";
            if (numberInStr.charAt(i) < other.numberInStr.charAt(i)) return "less";
        }

        return "equal";
    }


    LUNumber add(LUNumber other) {
        int shortestLength = min(this.length, other.length);
        int longestLength = max(this.length, other.length);

        StringBuilder newNumber = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < shortestLength; i++) {
            int digit1 = Integer.parseInt(String.valueOf(this.numberInStr.charAt(i)));
            int digit2 = Integer.parseInt(String.valueOf(other.numberInStr.charAt(i)));
            int digitsSum = digit1 + digit2 + remainder;

            int digit = digitsSum % 10;

            newNumber.append(String.valueOf(digit));

            remainder = digitsSum / 10;
        }

        for (int i = shortestLength; i < longestLength; i++) {
            LUNumber biggestNum = (this.length > other.length) ? this : other;
            int digit1 = Integer.parseInt(String.valueOf(biggestNum.numberInStr.charAt(i)));
            int digitSum = digit1 + remainder;

            newNumber.append(String.valueOf(digitSum % 10));

            remainder = digitSum / 10;
        }

        if (remainder != 0) newNumber.append("1");

        return new LUNumber(newNumber.reverse().toString());
    }

    LUNumber minus(LUNumber other) {
        String comparison = this.compareWith(other);
        if (comparison.equals("less")) throw new IllegalArgumentException("result is negative");
        if (comparison.equals("equal")) return new LUNumber("0");

        StringBuilder newNumber = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < other.length; i++) {
            int digitsDifference = this.numberInStr.charAt(i) - other.numberInStr.charAt(i) - remainder;

            int digit = (digitsDifference < 0) ? digitsDifference + 10 : digitsDifference;

            newNumber.append(String.valueOf(digit));

            remainder = (digitsDifference < 0) ? 1 : 0;
        }

        for (int i = other.length; i < this.length; i++) {
            int digitsDifference = Integer.valueOf(String.valueOf(this.numberInStr.charAt(i))) - remainder;

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










    @Override
    public String toString() {
        return new StringBuilder(numberInStr).reverse().toString();
    }

}
