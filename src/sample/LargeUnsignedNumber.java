package sample;

import static java.lang.Math.*;

class LargeUnsignedNumber {

    private String numberInStr;

    private int length;

    LargeUnsignedNumber(String number) {
        if (number.isEmpty()) throw new IllegalArgumentException("String in empty");

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
            if (numberInStr.charAt(0) > other.numberInStr.charAt(0)) {
                return "more";
            }
            else if (numberInStr.charAt(0) < other.numberInStr.charAt(0)) {
                return "less";
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

            int digitSum = digit1 + digit2;

            int digit = digitSum % 10 + remainder;

            newNumber.append(String.valueOf(digit));

            remainder = digitSum / 10;
        }

        for (int i = shortestLength; i < longestLength; i++) {
            LargeUnsignedNumber biggestNum = (this.length > other.length)? this : other;
            int digit = Integer.parseInt(String.valueOf(biggestNum.numberInStr.charAt(i)));

            newNumber.append(String.valueOf(digit));
        }

        return new LargeUnsignedNumber(newNumber.reverse().toString());
    }










    @Override
    public String toString() {
        return new StringBuilder(numberInStr).reverse().toString();
    }

}
