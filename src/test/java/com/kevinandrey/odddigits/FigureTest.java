package com.kevinandrey.odddigits;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FigureTest {

    @Test
    @DisplayName("N = 4 is accepted (the 2017 version rejected it with N <= 4)")
    void acceptsTheMinimumOfFourDigits() {
        assertEquals("5674", Figure.of(4, " 5674 ").digits());
    }

    @Test
    void rejectsLessThanFourDigits() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> Figure.of(3, "567"));

        assertTrue(error.getMessage().contains("mayor o igual a 4"));
        assertThrows(IllegalArgumentException.class, () -> new Figure("567"));
    }

    @Test
    void theFigureMustHaveExactlyNDigits() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> Figure.of(5, "5674"));

        assertEquals("La cifra tiene 4 dígitos y se esperaban 5.", error.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"12a4", "-1234", "12 34", "1.234", "１２３４"})
    void acceptsOnlyAsciiDigits(String text) {
        assertThrows(IllegalArgumentException.class, () -> new Figure(text));
    }

    @Test
    void anIntegerFigureCannotStartWithZero() {
        assertThrows(IllegalArgumentException.class, () -> new Figure("0123"));
    }
}
