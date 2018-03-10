package sample;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Double.POSITIVE_INFINITY;


public final class LUNumber implements Comparable<LUNumber> {
    private final ArrayList<Byte> reversedNum;

    private ArrayList<Byte> zero() {
        return new ArrayList<>(Collections.singletonList((byte) 0));
    }

    LUNumber(String numberInStr) {
        validation(numberInStr);

        reversedNum = new ArrayList<>(numberInStr.length());

        char[] numInCharArray = numberInStr.toCharArray();
        for (int i = numInCharArray.length - 1; i >= 0; i--) {
            reversedNum.add((byte) (numInCharArray[i] - '0'));
        }
    }


    LUNumber(int number) {
        this(String.valueOf(number));
    }


    LUNumber(ArrayList<Byte> number) {
        reversedNum = number;
    }


    LUNumber(double infinity) {
        reversedNum = null;
    }


    private void validation(String numberInStr) {
        if (numberInStr.isEmpty())
            throw new IllegalArgumentException("String in empty");
        if (numberInStr.charAt(0) == '0' && numberInStr.length() > 1)
            throw new IllegalArgumentException("Incorrect number");
        if (!numberInStr.matches("[0-9]+"))
            throw new IllegalArgumentException("Incorrect number");
    }


    LUNumber add(LUNumber other) {
        if (this.reversedNum == null || other.reversedNum == null) return new LUNumber(POSITIVE_INFINITY);

        ArrayList<Byte> sum = addition(this.reversedNum, other.reversedNum);
        return new LUNumber(sum);
    }


    private ArrayList<Byte> addition(ArrayList<Byte> a, ArrayList<Byte> b) {
        if (a.size() > b.size()) b.addAll(zeros(a.size() - b.size()));
        else a.addAll(zeros(b.size() - a.size()));

        ArrayList<Byte> result = new ArrayList<>();

        int remainder = 0;

        for (int i = 0; i < a.size(); i++) {
            byte digit1 = a.get(i);
            byte digit2 = b.get(i);
            int interimSum = digit1 + digit2 + remainder;

            int totalDigit = interimSum % 10;
            remainder = interimSum / 10;

            result.add((byte) totalDigit);
        }

        if (remainder != 0) result.add((byte) 1);

        return result;
    }


    private ArrayList<Byte> zeros(int amount) {
        ArrayList<Byte> zeros = new ArrayList<>(amount);
        for (int i = 1; i <= amount; i++) {
            zeros.add((byte) 0);
        }
        return zeros;
    }


    LUNumber subtract(LUNumber other) {
        if (this.reversedNum == null && other.reversedNum != null)
            return (new LUNumber(POSITIVE_INFINITY));
        if (other.reversedNum == null)
            throw new IllegalArgumentException("result is uncertain");

        return new LUNumber(subtraction(this.reversedNum, other.reversedNum));
    }


    private ArrayList<Byte> subtraction(ArrayList<Byte> a, ArrayList<Byte> b) {
        int comparison = comparison(a, b);
        if (comparison == 0) return zero();
        if (comparison == -1) throw new IllegalArgumentException("result is negative");
        if (isZero(b)) return a;

        b.addAll(zeros(a.size() - b.size()));

        ArrayList<Byte> result = new ArrayList<>(a.size());

        int remainder = 0;

        for (int i = 0; i < a.size(); i++) {
            int digitsDifference = a.get(i) - b.get(i) - remainder;

            int totalDigit = (digitsDifference < 0) ? digitsDifference + 10 : digitsDifference;
            remainder = (digitsDifference < 0) ? 1 : 0;

            result.add((byte) totalDigit);
        }

        cutZeros(b);
        return cutZeros(result);
    }


    private ArrayList<Byte> cutZeros(ArrayList<Byte> inputNum) {
        if (isZero(inputNum)) return zero();

        while (inputNum.get(inputNum.size() - 1) == 0) {
            inputNum.remove(inputNum.size() - 1);
        }

        return inputNum;
    }


    LUNumber multiply(LUNumber other) {
        if (this.reversedNum == null && isZero(other.reversedNum))
            throw new IllegalArgumentException("result is uncertain");
        if (other.reversedNum == null && isZero(this.reversedNum))
            throw new IllegalArgumentException("result is uncertain");
        if (this.reversedNum == null || other.reversedNum == null)
            return new LUNumber(POSITIVE_INFINITY);

        return new LUNumber(multiplication(this.reversedNum, other.reversedNum));
    }


    private ArrayList<Byte> multiplication(ArrayList<Byte> u, ArrayList<Byte> v) {
        if (isZero(u) || isZero(v)) return zero();

        ArrayList<Byte> result = zero();

        for (int i = 0; i < v.size(); i++) {
            ArrayList<Byte> newNum = zeros(i);
            newNum.addAll(multiplyByDigit(u, v.get(i)));
            result = addition(result, newNum);
        }

        return cutZeros(result);
    }


    private  ArrayList<Byte> multiplyByDigit( ArrayList<Byte> u, byte digit) {
        if (digit == 0) return zero();
        if (digit == 1) return u;

        ArrayList<Byte> result = new ArrayList<>();

        int remainder = 0;

        for (int i = 0; i < u.size(); i++) {
            int composition = u.get(i) * digit + remainder;
            remainder = composition / 10;
            result.add((byte) (composition % 10));
        }

        if (remainder != 0) result.add((byte) remainder);

        return result;
    }


    LUNumber divide(LUNumber other) {
        if (this.reversedNum == null && isZero(other.reversedNum) ||
            this.reversedNum == null && other.reversedNum == null ||
            isZero(this.reversedNum) && isZero(other.reversedNum))
                throw new IllegalArgumentException("result uncertain");


        if (this.reversedNum == null) return new LUNumber(POSITIVE_INFINITY);
        if (this.compareTo(other) == -1 ||
            other.reversedNum == null && isZero(this.reversedNum))
                return new LUNumber("0");
        if (isZero(other.reversedNum)) return new LUNumber(POSITIVE_INFINITY);

        ArrayList<Byte> result = division(this.reversedNum, other.reversedNum, true);

        return new LUNumber(result);
    }


    LUNumber mod(LUNumber other) {
        if (this.reversedNum == null || isZero(this.reversedNum) && isZero(other.reversedNum))
            throw new IllegalArgumentException("result uncertain");

        if (this.compareTo(other) == -1) return this;
        if (isZero(other.reversedNum) ||
            other.reversedNum == null && isZero(this.reversedNum))
                return new LUNumber("0");

        return new LUNumber(division(this.reversedNum, other.reversedNum, false));
    }


    private ArrayList<Byte> division(ArrayList<Byte> u, ArrayList<Byte> v, boolean divFlag) {
        ArrayList<Byte> result = new ArrayList<>();

        ArrayList<Byte> remainder = new ArrayList<>(u.subList(u.size() - v.size(), u.size()));

        for (int i = u.size() - v.size(); i >= 0; i--) {
            byte totalDigit = 9;
            ArrayList<Byte> tempNum = multiplyByDigit(v, totalDigit);

            while (comparison(remainder, tempNum) == -1) {
                tempNum = subtraction(tempNum, v);
                totalDigit--;
            }

            remainder = subtraction(remainder, tempNum);
            if (i != 0) {
                remainder.add(0, u.get(i - 1));
            }
            if (remainder.size() > 1 && remainder.get(remainder.size() - 1) == 0) {
                remainder.remove(remainder.size() - 1);
            }

            result.add(0, totalDigit);
        }

        return (divFlag) ? cutZeros(result) : remainder;
    }

    private boolean isZero(ArrayList<Byte> number) {
        return comparison(number, zero()) == 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        if (this.reversedNum == null) return "+inf";

        StringBuilder strForOutput = new StringBuilder("");

        for (int i = reversedNum.size() - 1; i >= 0; i--) {
            strForOutput.append(reversedNum.get(i));
        }

        return strForOutput.toString();
    }

    public static void main(String[] args) {
        System.out.println(0 % 10);
    }
    @Override
    public int compareTo(LUNumber other) {
        return comparison(this.reversedNum, other.reversedNum);
    }

    private int comparison(ArrayList<Byte> a, ArrayList<Byte> b) {
        if (a == null && b == null) return 0;
        if (a == null) return 1;
        if (b == null) return -1;

        if (a.size() > b.size()) return 1;
        if (a.size() < b.size()) return -1;

        for (int i = a.size() - 1; i >= 0; i--) {
            if (a.get(i) > b.get(i)) return 1;
            if (a.get(i) < b.get(i)) return -1;
        }

        return 0;
    }
}
