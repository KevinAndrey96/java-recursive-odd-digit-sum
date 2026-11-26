package com.kevinandrey.odddigits;

/**
 * An integer figure C with exactly N digits, N being at least {@value #MINIMUM_DIGITS}, as the exercise requires.
 * Kept as text so figures of any length are supported.
 */
public record Figure(String digits) {

    public static final int MINIMUM_DIGITS = 4;

    public Figure {
        if (digits == null || !digits.matches("\\d+")) {
            throw new IllegalArgumentException("La cifra solo puede contener dígitos del 0 al 9.");
        }
        if (digits.length() < MINIMUM_DIGITS) {
            throw new IllegalArgumentException("La cifra debe tener al menos " + MINIMUM_DIGITS + " dígitos.");
        }
        if (digits.charAt(0) == '0') {
            throw new IllegalArgumentException("Una cifra entera no puede empezar por 0.");
        }
    }

    /** Validates the figure against the number of digits N the user declared first. */
    public static Figure of(int declaredDigits, String text) {
        if (declaredDigits < MINIMUM_DIGITS) {
            throw new IllegalArgumentException("N debe ser mayor o igual a " + MINIMUM_DIGITS + ".");
        }
        Figure figure = new Figure(text == null ? null : text.strip());
        if (figure.length() != declaredDigits) {
            throw new IllegalArgumentException("La cifra tiene " + figure.length() + " dígitos y se esperaban " + declaredDigits + ".");
        }
        return figure;
    }

    public int length() {
        return digits.length();
    }

    int digitAt(int index) {
        return digits.charAt(index) - '0';
    }
}
