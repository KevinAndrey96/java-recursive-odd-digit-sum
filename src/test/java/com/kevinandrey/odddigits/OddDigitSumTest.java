package com.kevinandrey.odddigits;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OddDigitSumTest {

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "16796, 17",
            "5674, 12",
            "2468, 0",
            "9999, 36",
            "1000, 1",
            "1234567890, 25"
    })
    void sumsOnlyTheOddDigits(String digits, long expected) {
        assertEquals(expected, OddDigitSum.of(new Figure(digits)));
    }

    @Test
    void listsTheOddDigitsInOrder() {
        assertEquals(List.of(1, 7, 9), OddDigitSum.oddDigits(new Figure("16796")));
        assertEquals(List.of(), OddDigitSum.oddDigits(new Figure("2468")));
    }

    @Test
    @DisplayName("matches a straightforward loop on 500 random figures")
    void agreesWithAnIterativeReference() {
        Random random = new Random(20170916);
        for (int sample = 0; sample < 500; sample++) {
            StringBuilder digits = new StringBuilder().append(1 + random.nextInt(9));
            int length = Figure.MINIMUM_DIGITS + random.nextInt(200);
            while (digits.length() < length) {
                digits.append(random.nextInt(10));
            }

            assertEquals(iterativeSum(digits.toString()), OddDigitSum.of(new Figure(digits.toString())), digits.toString());
        }
    }

    @Test
    @DisplayName("a million digits fit in the stack thanks to the logarithmic recursion depth")
    void handlesVeryLongFigures() {
        Figure figure = new Figure("1".repeat(1_000_000));

        assertEquals(1_000_000L, OddDigitSum.of(figure));
    }

    private static long iterativeSum(String digits) {
        long total = 0;
        for (char character : digits.toCharArray()) {
            int digit = character - '0';
            if (digit % 2 != 0) {
                total += digit;
            }
        }
        return total;
    }
}
