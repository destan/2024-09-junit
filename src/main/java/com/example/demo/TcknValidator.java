package com.example.demo;

public class TcknValidator {

    public boolean validate(String tckn) {

        assert tckn != null : "tckn is null"; // -ea

        //if (tckn.contains("3")) {
        //    throw new ArithmeticException("3 is bad");
        //}

        if (tckn.length() != 11) {
            throw new IllegalArgumentException("Tckn should be exactly 11 digits");
        }

        if (tckn.charAt(0) == '0') {
            throw new IllegalArgumentException("Tckn cannot start with zero");
        }

        int oddSum = 0, evenSum = 0, controlDigit = 0;
        for (int i = 0; i <= 8; i++) {
            if (i % 2 == 0) {
                oddSum += Character.getNumericValue(tckn.charAt(i));

            } else {
                evenSum += Character.getNumericValue(tckn.charAt(i));
            }
        }
        controlDigit = (oddSum * 7 - evenSum) % 10;
        if (Character.getNumericValue(tckn.charAt(9)) != controlDigit) {
            return false;
        }
        return Character.getNumericValue(tckn.charAt(10)) == (controlDigit + evenSum + oddSum) % 10;
    }

    public static void main(String[] args) {
        TcknValidator validator = new TcknValidator();

        System.out.println(validator.validate(null));

        System.out.println(validator.validate("0123456789"));
        System.out.println(validator.validate("32286582130"));
    }

}
