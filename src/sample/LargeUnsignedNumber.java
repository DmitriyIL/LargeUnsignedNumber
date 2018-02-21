package sample;

public class LargeUnsignedNumber {

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

    public String compareWith(LargeUnsignedNumber other) {
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












}
