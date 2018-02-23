package sample;

import static java.lang.Math.*;
import static java.util.stream.IntStream.rangeClosed;

class LargeUnsignedNumber {
    private String numberInStr;

    private int length;

    LargeUnsignedNumber(String number) {
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

    LargeUnsignedNumber(int number) {
        this (String.valueOf(number));
    }

    String compareWith(LargeUnsignedNumber other) {
        if (this.length > other.length) {
            return "more";
        } else if (this.length < other.length) {
            return "less";
        } else {
            for (int i = this.length - 1; i >= 0; i--) {
                if (numberInStr.charAt(i) > other.numberInStr.charAt(i)) {
                    return "more";
                } else if (numberInStr.charAt(i) < other.numberInStr.charAt(i)) {
                    return "less";
                }
            }
        }

        return "equal";
    }

    LargeUnsignedNumber add(LargeUnsignedNumber other) {
        int shortestLength = min(this.length, other.length);
        int longestLength = max(this.length, other.length);

        StringBuilder newNumber = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < shortestLength; i++) {
            int digit1 = Integer.parseInt(String.valueOf(this.numberInStr.charAt(i)));
            int digit2 = Integer.parseInt(String.valueOf(other.numberInStr.charAt(i)));

            int digitSum = digit1 + digit2 + remainder;

            int digit = digitSum % 10;

            newNumber.append(String.valueOf(digit));

            remainder = digitSum / 10;
        }

        for (int i = shortestLength; i < longestLength; i++) {
            LargeUnsignedNumber biggestNum = (this.length > other.length) ? this : other;

            int digitSum = Integer.parseInt(String.valueOf(biggestNum.numberInStr.charAt(i))) + remainder;

            newNumber.append(String.valueOf(digitSum % 10));

            remainder = digitSum / 10;
        }

        if (remainder != 0) newNumber.append("1");
        return new LargeUnsignedNumber(newNumber.reverse().toString());
    }

    LargeUnsignedNumber minus(LargeUnsignedNumber other) {
        String comparison = this.compareWith(other);
        if (comparison.equals("less"))
            throw new IllegalArgumentException("result is negative: " + this.toString() + " - " + other.toString());
        else if (comparison.equals("equal")) return new LargeUnsignedNumber("0");

        StringBuilder newNumber = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < other.length; i++) {
            int digitDifference = this.numberInStr.charAt(i) - other.numberInStr.charAt(i) + remainder;

            int digit = (digitDifference < 0) ? digitDifference + 10 : digitDifference;

            newNumber.append(String.valueOf(digit));

            remainder = (digitDifference < 0) ? -1 : 0;
        }

        for (int i = other.length; i < this.length; i++) {
            int digitDifference = Integer.valueOf(String.valueOf(this.numberInStr.charAt(i))) + remainder;

            int digit = (digitDifference < 0) ? digitDifference + 10 : digitDifference;

            newNumber.append(String.valueOf(digit));

            remainder = (digitDifference < 0) ? -1 : 0;
        }

        String newNumberInStr = newNumber.reverse().toString();
        while (newNumberInStr.charAt(0) == '0') {
            newNumberInStr = newNumberInStr.substring(1);
        }

        return new LargeUnsignedNumber(newNumberInStr);
    }










    @Override
    public String toString() {
        return new StringBuilder(numberInStr).reverse().toString();
    }

}
