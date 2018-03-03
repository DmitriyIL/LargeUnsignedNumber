package sample;

import static java.lang.Math.*;

public final class LUNumber {
    private final String reversedNum;


    LUNumber(String numberInStr) {
        validation(numberInStr);

        this.reversedNum = reverse(numberInStr);
    }


    LUNumber(int number) {
        this(String.valueOf(number));
    }


    private void validation(String numberInStr) {
        if (numberInStr.isEmpty())
            throw new IllegalArgumentException("String in empty");
        if (numberInStr.charAt(0) == '0' && numberInStr.length() > 1)
            throw new IllegalArgumentException("Incorrect number");
        if (!numberInStr.matches("[0-9]+"))
            throw new IllegalArgumentException("Incorrect number");
    }


    String compareTo(LUNumber other) {
        return comparison(this.reversedNum, other.reversedNum);
    }


    private String comparison(String a, String b) {
        if (a.length() > b.length()) return "more";
        if (a.length() < b.length()) return "less";

        for (int i = a.length() - 1; i >= 0; i--) {
            if (a.charAt(i) > b.charAt(i)) return "more";
            if (a.charAt(i) < b.charAt(i)) return "less";
        }

        return "equal";
    }


    LUNumber add(LUNumber other) {
        String sum = reverse(addition(this.reversedNum, other.reversedNum));
        return new LUNumber(sum);
    }


    private String addition(String a, String b) {
        int lengthDifference = abs(a.length() - b.length());
        String zeros = new String(new char[lengthDifference]).replace('\0', '0');
        if (a.length() > b.length()) b += zeros;
        else a += zeros;

        StringBuilder result = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < a.length(); i++) {
            int digit1 = toInt(a.charAt(i));
            int digit2 = toInt(b.charAt(i));
            int interimSum = digit1 + digit2 + remainder;

            int totalDigit = interimSum % 10;
            remainder = interimSum / 10;

            result.append(String.valueOf(totalDigit));
        }

        if (remainder != 0) result.append("1");

        return result.toString();
    }


    LUNumber subtract(LUNumber other) {
        return new LUNumber(reverse(subtraction(this.reversedNum, other.reversedNum)));
    }


    private String subtraction(String a, String b) {
        String comparison = comparison(a, b);
        if ("equal".equals(comparison)) return "0";
        if ("less".equals(comparison)) throw new IllegalArgumentException("result is negative");

        int lengthDifference = abs(a.length() - b.length());
        String zeros = new String(new char[lengthDifference]).replace('\0', '0');
        b += zeros;

        StringBuilder result = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < a.length(); i++) {
            int digitsDifference = a.charAt(i) - b.charAt(i) - remainder;

            int totalDigit = (digitsDifference < 0) ? digitsDifference + 10 : digitsDifference;
            remainder = (digitsDifference < 0) ? 1 : 0;

            result.append(String.valueOf(totalDigit));
        }

        return cutZeros(result.toString());
    }
    

    private String cutZeros(String inputNum) {
        if ("0".equals(inputNum)) return "0";

        int excessZeros = 0;
        while (inputNum.charAt(inputNum.length() - 1 - excessZeros) == '0') {
            excessZeros++;
        }
        return inputNum.substring(0, inputNum.length() - excessZeros);
    }


    LUNumber multiply(LUNumber other) {
        return new LUNumber(reverse(multiplication(this.reversedNum, other.reversedNum)));
    }


    private String multiplication(String u, String v) {
        if ("0".equals(u) || "0".equals(v)) return "0";

        String result = "0";

        for (int i = 0; i < v.length(); i++) {
            String newNum = multiplyByDigit(u, v.charAt(i));

            String zeros = new String(new char[i]).replace('\0', '0');

            result = addition(result, zeros + newNum);
        }

        return cutZeros(result);
    }


    private String multiplyByDigit(String u, char digit) {
        if (digit == '0') return "0";
        if (digit == '1') return u;

        StringBuilder result = new StringBuilder("");

        int remainder = 0;

        for (int i = 0; i < u.length(); i++) {
            int composition = toInt(u.charAt(i)) * toInt(digit) + remainder;

            remainder = composition / 10;

            result.append(String.valueOf(composition % 10));
        }

        if (remainder != 0) result.append(String.valueOf(remainder));

        return result.toString();
    }


    LUNumber divide(LUNumber other) {
        if ("less".equals(this.compareTo(other))) return new LUNumber("0");

        String result = division(this.reversedNum, other.reversedNum, true);

        return (result.charAt(0) == '0') ? new LUNumber(result.substring(1)) : new LUNumber(result);
    }


    LUNumber mod(LUNumber other) {
        if ("less".equals(this.compareTo(other))) return this;

        return new LUNumber(division(this.reversedNum, other.reversedNum, false));
    }


    private String division(String u, String v, boolean divFlag) {
        if ("0".equals(v)) throw new ArithmeticException("You can not divide by zero");

        StringBuilder result = new StringBuilder();

        String remainder = u.substring(u.length() - v.length());

        for (int i = u.length() - v.length(); i >= 0; i--) {
            int totalDigit = 9;
            String tempNum = multiplyByDigit(v, toChar(totalDigit));

            while ("less".equals(comparison(remainder, tempNum))) {
                tempNum = subtraction(tempNum, v);
                totalDigit--;
            }

            remainder = subtraction(remainder, tempNum);
            if (i != 0) {
                remainder = u.charAt(i - 1) + remainder;
            }
            if (remainder.length() > 1 && remainder.charAt(remainder.length() - 1) == '0') {
                remainder = remainder.substring(0, remainder.length() - 1);
            }

            result.append(toChar(totalDigit));
        }

        return (divFlag) ? result.toString() : reverse(remainder);
    }


    private String reverse(String inputStr) {
        return new StringBuilder(inputStr).reverse().toString();
    }

    private char toChar(int digit) {
        return Character.forDigit(digit, 10);
    }

    private int toInt(char digit) {
        return digit - '0';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof LUNumber) {
            LUNumber other = (LUNumber) obj;
            if ("equal".equals(this.compareTo(other))) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return reverse(reversedNum);
    }
}
